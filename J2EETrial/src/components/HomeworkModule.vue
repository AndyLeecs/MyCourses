<template>
  <div>
    <el-dialog :visible.sync="showPubDialog">
      <el-form>
        <el-form-item label="标题">
          <el-input v-model="homework.title"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="homework.content"></el-input>
        </el-form-item>
        <el-form-item label="截止时间">
          <el-date-picker
            v-model="homework.ddl"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间"
            default-time="23:59:59">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="大小限制（MB）">
          <el-switch v-model="homework.hasSizeLimit">
          </el-switch>
          <el-input-number v-if="homework.hasSizeLimit" v-model="homework.sizeLimit" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="类型限制">
          <el-checkbox-group
            v-model="homework.typesLimit">
            <el-checkbox v-for="item in options" :label="item" :key="item">{{item}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">发布作业</el-button>
     </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="showDetailDialog">
      <p id="title">{{homework.title}}</p>
      <p>{{homework.content}}</p>
      <p>截止时间    <el-date-picker
        v-model="homework.ddl"
        type="datetime"
        value-format="yyyy-MM-dd HH:mm:ss"
        readonly>
      </el-date-picker></p>
      <p v-if="homework.hasSizeLimit">大小限制：{{homework.sizeLimit}}MB</p>

      <p>提交格式：
      <span v-for="limit in homework.typesLimit">{{limit}}</span>
      </p>

      <el-button v-if="!isStu||homework.submitted" @click="download">下载</el-button>
      <el-upload v-if="isStu"
                 action="http://localhost:8081/api/v1/homework/upload"
      :data='{"id":homework.id}'
      :before-upload="beforeUpload"
                 :on-success="successUpload"
      v-bind:show-file-list="false"
      with-credentials="true">
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
    </el-dialog>

    <LessonComponent v-bind:show-new-btn='!isStu&&isCur' :contents="homeworks" :click="detail" :upload="pub"/>
  </div>
</template>

<script>
  import LessonComponent from "./LessonComponent";
  import http from "../utils/http"
  import filetypes from "../utils/filetypes"
    export default {
        name: "HomeworkModule",
      components: {LessonComponent},
      data() {
        return {
          homeworks:[],
          fileList:[],
          isStu:sessionStorage.role == "student",
          options: filetypes.types,
          isCur:sessionStorage.isCur,

          showPubDialog: false,
          showDetailDialog: false,

          homework:{
            id:'',
            lesson_id:sessionStorage.lesson_id,
            title:'',
            content:'',
            ddl:'',
            hasSizeLimit:false,
            sizeLimit:'',
            typesLimit:[],
            submitted:false
          }
        }
      },
      methods:{
        pub(){
          this.showPubDialog = true;
        },
        beforeUpload(file){
            let testmsg = file.name.substring(file.name.lastIndexOf('.'));
            let validExt = false;
            for (let ext of this.homework.typesLimit){
              if (ext === testmsg){
                validExt = true;
              }
            }
            if(!validExt)
            this.$message({
              message:'文件类型错误'});
            let validSize = file.size/1024/1024 < this.homework.sizeLimit;
            if(!validSize){
              this.$message({message:'文件大小超出限度'});
            }
          return validExt && validSize
        },
        successUpload(){
          this.$message({message:'成功'});
          this.showDetailDialog = false;
        },
        async download(){
          //学生下载自己的作业，老师下载所有人的作业
          let res;
          if (this.isStu) {
            res = await http.get("/homework/self/"+this.homework.id);
          }else{
            res = await http.get("/homework/download/"+this.homework.id);
          }
          window.open(res.config.url);
        },

        async submitUpload(){
          console.log(this.homework);
          let res = await http.post("/homework/pub", this.homework);
          this.getAll();
          this.showPubDialog = false;
        },

        //如果是老师，查看带下载的详情，如果是学生，查看带上传,下载的详情
        async detail(row) {
          let res = await http.get("/homework/detail/"+row.id,{});
          this.homework = res.data;
          this.showDetailDialog = true;
        },
        async getAll(){
          let res =
            await http.get("/homework/all/"+this.homework.lesson_id,{});
          this.homeworks = res.data;
        }
      },
      async mounted(){
        this.getAll();
      }
    }
</script>

<style scoped>
  #title{
    font-size: large;
  }
</style>
