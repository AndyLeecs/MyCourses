<template>
  <div>
    <el-dialog :visible.sync="showMailDialog">
      <el-form>
        <el-form-item label="标题">
          <el-input v-model="Mail.title">
          </el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="Mail.content">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="sendMail">发送</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="showUploadDialog">
      <el-upload
        action="http://localhost:8081/api/v1/stuList/score/upload"
      :data='{"lesson_id":this.lesson_id,"set_public":this.set_public}'
        accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      v-bind:show-file-list="false">
        <el-switch active-text="公开成绩" v-model="set_public"></el-switch>
        <br>
        <br>
        <div slot="tip" class="el-upload__tip">请设置有意义的文件名，内容第一列为学号列，次列为成绩列，带表头</div>
        <el-button size="small" type="primary">点击上传excel表格</el-button>
      </el-upload>
    </el-dialog>

    <div v-if="!isStu&&isCur">
  <el-button @click="showMail">群发邮件</el-button>
    <el-button @click="showUpload">上传成绩</el-button>
    </div>
    <div v-if="isStu">
      <el-button @click="showScores">查看成绩</el-button>
      <el-button @click="dropOut">退课</el-button>
    </div>

    <courses-table v-bind:show-id="true" :courses="students"></courses-table>

  </div>
</template>

<script>

    import CoursesTable from "./CoursesTable";
    import http from '../utils/http'
    export default {
        name: "StudentListModule",
      components: {CoursesTable},
      data()
      {
        return {
          lesson_id : sessionStorage.lesson_id,
          students: [],
          isStu: sessionStorage.role == "student",
          showMailDialog : false,
          showUploadDialog : false,
          Mail:{
            title:'',
            content:''
          },
          set_public:false
        }
      },
      methods:{
          showMail()
          {
            this.showMailDialog = true;
          },
          async sendMail()
          {
            let res = await http.post("/stuList/mail",
              {"title":this.Mail.title, "content":this.Mail.content,
                "lesson_id":this.lesson_id});
            if (!res.msg)this.showMailDialog = false;
          },
          showUpload()
          {
            this.showUploadDialog = true;
          },
        async showScores()
        {
            let res = await http.get("/stuList/score/all/"+this.lesson_id);
            this.$router.push({
              name:"ScoreDetailComponent",
              params:
                {
                  scores:res.data
                }
              }
            )
        },
        async dropOut()
        {
          let res = await http.post("/stuList/dropOut/"+this.lesson_id);
          if(!res.msg)
          {
            this.$router.push(
              "/main"
            )
          }
        }
      },
      async mounted()
      {
        let res = await http.get("/stuList/all/"+sessionStorage.lesson_id);
        this.students = res.data;
      }
    }
</script>

<style scoped>

</style>
