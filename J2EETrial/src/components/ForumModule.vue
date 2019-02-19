<template>
    <div>
      <el-dialog :visible.sync="showPubTopicDialog">
        <el-form>
          <el-form-item label="标题">
            <el-input v-model="topic.title"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input type="textarea" v-model="comment.content"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="submitTopic">发布</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
      <LessonComponent :show-new-btn='!isStu' :contents="topics" :click="detail" :upload="showPubTopic"/>
    </div>
</template>

<script type="text/jsx">
  import LessonComponent from "./LessonComponent";
  import http from "../utils/http"
  import ForumDetailComponent from "./ForumDetailComponent";

  export default {
        name: "ForumModule",
        components: {ForumDetailComponent, LessonComponent},
        data(){
          return{
            isStu:'',
            topics:[],
            comments:[],
            showPubTopicDialog:false,
            topic:{
              id:'',
              course_id:sessionStorage.course_id,
              title:'',
            },
            comment:{
              id:'',
              reply_to_id:'',
              writer_name:'',
              content:'',
              time:''
            }
          }
        },
      methods:{
          async detail(row){
            let res = await http.get("/forum/detail/"+row.id,{});
            this.$router.push({
              name: 'ForumDetailComponent',
              params:{
                id :row.id,
                title : row.name,
                comments : res.data
              }
            });
            // this.topic.title = row.name;
            // this.comments = res.data;
            // console.log(res.data);
          },
          showPubTopic(){
            this.showPubTopicDialog = true;
          },
          async submitTopic(){
            let res = await http.post("/forum/pubTopic",{"course_id":this.topic.course_id,
              "title":this.topic.title,"content":this.comment.content,"time":new Date()});
            this.topics = res.data;
            this.showPubTopicDialog = false;
          }
      },

      async mounted(){
        console.log("start to mount");
        let res = await http.get("/forum/all/"+sessionStorage.course_id,{});
        this.topics = res.data;
      }
    }
</script>

<style scoped>
  #title{
    font-size: x-large;
  }
  #subtitle{
    font-size: large;
  }
</style>
