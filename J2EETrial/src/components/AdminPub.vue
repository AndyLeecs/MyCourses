<template>
  <el-table :data="courses"
            stripe
            style="width: 100%">
    <el-table-column label="课程名"
      prop="name">
    </el-table-column>
    <el-table-column label="教师名"
    prop="teacher">
  </el-table-column>
    <el-table-column label="开始时间"
      prop="start">
    </el-table-column>
    <el-table-column label="结束时间"
      prop="end">
    </el-table-column>
    <el-table-column label="班次"
      prop="count">
    </el-table-column>
    <el-table-column label="班额"
      prop="limit">
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="upvote(scope.row)">通过
        </el-button>
        <el-button
          size="mini"
          type="danger"
          @click="downvote(scope.row)">不通过
        </el-button>
      </template>
    </el-table-column>
    <!--<ApproveComponent :upvote="upvote" :downvote="downvote"></ApproveComponent>-->
  </el-table>
</template>

<script>
  import http from '../utils/http'
  import ApproveComponent from "./ApproveComponent";
  export default {
    name: "AdminPub",
    components: {ApproveComponent},
    data(){
      return{
        courses:[]
      }
    },
    methods:{
      async upvote(row){
        let res = await http.post("/admin/upvotePub/"+row.id);
        this.getAll()
      },
      async downvote(row){
        let res = await http.post("/admin/downvotePub/"+row.id);
        this.getAll()
      },
      async getAll(){
        let res = await http.get("/admin/pub");
        this.courses = res.data;
      }
    },
    async mounted(){
        this.getAll();
    }
  }
</script>

<style scoped>

</style>
