package com.andi.mycourses.util;

import com.andi.mycourses.entity.DBFile;
import com.andi.mycourses.entity.StuHomework;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author andi
 */
public class FileUtil {

    private static final int BUFFER_SIZE = 64*1024*1024;

    public static String getPureName(DBFile file)
    {
        String name = file.getFileName();
        int index = name.lastIndexOf('.');
        return name.substring(0,index);
    }

    public static DBFile mergeToZip(String rootPath, List<StuHomework> files)
    {
        System.out.println("start to merge to zip");
        DBFile res = null;
        String randPath = UUIDUtil.getUUID();
        try {
            File tempDir = new File(rootPath+"/"+randPath);
            if (!tempDir.exists()){
                tempDir.mkdirs();
            }
            System.out.println("tempDir"+tempDir.getAbsolutePath());
            //生成需要下载的文件

            for (DBFile file : files) {
                getFile(file.getData(), tempDir.getPath(), file.getFileName());
            }
            //压缩为zip
//            byte[] resData = zip(getBytesFromFile(tempDir));
            byte[] resData = toZip(tempDir.getAbsolutePath(), false);

            //返回压缩后的文件
            //todo 修改返回的zip名为作业名
            res = new DBFile(randPath+".zip","'application/zip'",resData);

            //todo 这部分放在哪里处理，finally？
            File[] listFiles = tempDir.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                listFiles[i].delete();
            }
            tempDir.delete();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }

    public static byte[] toZip(String srcDir, boolean keepDirStructure)
    {
        ByteArrayOutputStream bos = null;
        ZipOutputStream zos = null;
        byte[] b = null;
        try{
            bos = new ByteArrayOutputStream();
            zos =  new ZipOutputStream(bos);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);
            b = bos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //todo 流的关闭顺序
            if (bos!=null)
            {
                try {
                    bos.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (zos!=null)
            {
                try {
                    zos.close();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bos.toByteArray();
    }

    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];//todo 这里的buffersize是啥单位
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }

    private static byte[] getBytesFromFile(File file) throws IOException {

        InputStream in = new FileInputStream(file);

        long length = file.length();

//        if (length > Integer.MAX_VALUE) {
//
//            throw new LogicException("文件过大，不能传输");
//
//        }

        byte[] bytes = new byte[(int) length];

        int offset = 0;

        int numRead = 0;

        while (offset < bytes.length && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {

            offset += numRead;

        }

        if (offset < bytes.length) {

            throw new IOException("不能转换，");

        }

        in.close();

        return bytes;

    }

    private static byte[] zip(byte[] data) {

        byte[] b = null;

        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ZipOutputStream zip = new ZipOutputStream(bos);

            ZipEntry entry = new ZipEntry("~~~1.bmp");

            entry.setSize(data.length);//返回条目数据的未压缩大小；如果未知，则返回 -1。

            zip.putNextEntry(entry);// 开始写入新的 ZIP 文件条目并将流定位到条目数据的开始处

            zip.write(data);//将字节数组写入当前 ZIP 条目数据。

            zip.closeEntry();

            zip.close();

            b = bos.toByteArray();

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return b;

    }

    private static void getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()){//判断文件目录是否存在
                dir.mkdir();
            }
            file = new File(filePath+"\\"+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    public static ResponseEntity<Resource> downloadFile(DBFile dbFile)
    {
        System.out.println("start to download file");
        String fileName = dbFile.getFileName();
        try {
            //todo chrome可以，火狐不行，怎么兼容多个浏览器
            fileName = URLEncoder.encode(dbFile.getFileName(), "UTF-8");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    public static DBFile getDBFile(MultipartFile file)
    {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        DBFile dbFile = null;

        try {
            // Check if the file's name contains invalid characters
            if (!fileName.contains("..")) {
//        todo        throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
                dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return dbFile;
    }
}
