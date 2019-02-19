<template>
  <el-table
            :data="courses"
            stripe
            style="width: 100%">
    <el-table-column label="课程名"
      prop="name">
    </el-table-column>
    <el-table-column label="教师名"
    prop="teacher">
    </el-table-column>
    <ApproveComponent :downvote="downvote(scope.row)" :upvote="upvote(scope.row)"/>
  </el-table>
</template>

<script>
  import http from '../utils/http'
  import ApproveComponent from "./ApproveComponent";

  export default {
        name: "AdminNew",
      components: {ApproveComponent},
      data(){
          return{
            courses:[]
          }
        },
        methods:{
          async upvote(row){
            let res = await http.post("/admin/upvoteNew/"+row.id);
            this.getAll();
          },
          async downvote(row){
            let res = await http.post("admin/downvoteNew/"+row.id);
            this.getAll();
          },
          async getAll()
          {
            let res = await http.get("/admin/new");
            this.courses = res.data;
          }
        },
      async mounted(){
        this.getAll();
      }

    }
</script>

