<template>
  <courses-table :onclick="enroll" :courses=" courses" :show-values="true"></courses-table>
</template>

<script>
  import CoursesTable from './CoursesTable'
  import http from '../utils/http'
    export default {
        name: "SelectCourse",
      components:{CoursesTable},
        data(){
          return{
            courses : []
          }
        },
        methods:{
          async enroll(row){
            //row.value表示选择哪个班级
           if(!row.value) row.value = 1;
            let res = await http.post("/lesson/enroll/"+row.id+"/"+row.value);
            this.$message(res.data.msg);
            this.getAll();
          },
          async getAll(){
            let res = await http.get("/lesson/enroll/all");
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
