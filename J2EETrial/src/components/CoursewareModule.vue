<template>
  <div>
    <el-dialog :visible.sync="showUpload">
      <el-upload
        class="upload-demo"
        action="http://localhost:8081/api/v1/courseware/upload"
        :on-success="success"
        :on-error="error"
        multiple
        :data='{"course_id":course_id}'
        :file-list="fileList">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip"></div>
      </el-upload>
    </el-dialog>
    <LessonComponent :show-new-btn='!isStu' :contents="coursewares" :click="download" :upload="upload"/>
  </div>
</template>

<script>
  import LessonComponent from "./LessonComponent";
  import http from "../utils/http"
  export default {
      name: "Courseware",
      components: {LessonComponent},
      data(){
        return{
          coursewares:[],
          showUpload: false,
          fileList:[],
          //todo 管理sessionstorage
          course_id:sessionStorage.course_id,
          isStu:sessionStorage.role == "student"
        }
      },
      methods:{
        upload(){
          this.showUpload = true;
        },
        async download(row) {
          let res = await http.get("/courseware/download/"+row.id);
          window.open(res.config.url);

        },
        success(){
          console.log("success");
          this.getAll();
        },
        error(){
          console.log("error");
        },
        async getAll(){
          let res = await http.get("/courseware/all",{"course_id":this.course_id});
          this.coursewares = res.data;
        }
      },
    async mounted() {
        this.getAll();
    }
  }
</script>

