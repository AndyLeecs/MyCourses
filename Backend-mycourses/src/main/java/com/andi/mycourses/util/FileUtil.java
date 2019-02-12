package com.andi.mycourses.util;

import com.andi.mycourses.entity.DBFile;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author andi
 */
public class FileUtil {
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
