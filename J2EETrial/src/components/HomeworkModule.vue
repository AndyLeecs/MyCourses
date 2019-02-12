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
            type="date"
            value-format="yyyyMMdd"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="大小限制（MB）">
          <el-switch v-model="homework.hasSizeLimit">
          </el-switch>
          <el-input-number v-if="homework.hasSizeLimit" v-model="num1" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="类型限制">
          <el-checkbox-group
            v-model="homework.typesLimit">
            <el-checkbox v-for="item in options" :label="item" :key="item">{{item}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <!--<el-form-item>-->
      <!--<el-upload-->
        <!--class="upload-demo"-->
        <!--action="http://localhost:8081/api/v1/homework/upload"-->
        <!--:on-success="success"-->
        <!--:on-error="error"-->
        <!--multiple-->
        <!--:data=''-->
        <!--:file-list="fileList"-->
      <!--auto-upload="false">-->
        <!--<el-button slot="trigger" size="small" type="primary">添加附件</el-button>-->
        <!--<div slot="tip" class="el-upload__tip"></div>-->
      <!--</el-upload>-->
        <!--</el-form-item>-->
        <el-form-item>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">发布作业</el-button>
     </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="showDetailDialog">

    </el-dialog>

    <LessonComponent :show-new-btn='!isStu' :contents="homeworks" :click="detail" :upload="upload"/>
  </div>
</template>

<script>
  //todo 添加附件
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

          showPubDialog: false,
          showDetailDialog: false,

          homework:{
            title:'',
            content:'',
            ddl:'',
            hasSizeLimit:'',
            sizeLimit:'',
            typesLimit:[]
          }
        }
      },
      methods:{
        upload(){
          this.showPubDialog = true;
        },
        submitUpload(){

        },
        //如果是老师，查看带下载的详情，如果是学生，查看带成绩，上传,下载的详情
        async detail(row) {
          let res = await http.get("/homework/detail",row.id);

          this.showDetailDialog = true;
        },
        success(){
          console.log("success");
        },
        error(){
          console.log("error");
        },
        async getAll(){

        }
      },
      async mounted(){
        this.getAll();
      }
    }
</script>

<style scoped>

</style>
