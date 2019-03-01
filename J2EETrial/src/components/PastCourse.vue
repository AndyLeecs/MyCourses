<template>
  <courses-table :onclick="detail" :courses=" courses"></courses-table>
</template>

<script>
  import http from "../utils/http"
  import CoursesTable from "./CoursesTable";
  import LessonMain from "./LessonMain";
  export default {
    name: "PastCourse",
    components:{
      CoursesTable
    },
    data() {
      return{
        courses:[]
      }
    },
    methods:{
      async detail(row) {
        // let res = await http.get("/lesson/detail",row.id);
        this.$router.push(
          "/lesson");
        sessionStorage.isCur = false;
        sessionStorage.lesson_id = row.id;
        sessionStorage.course_id = row.course_id;
      }
    },
    async mounted() {
      let res = await http.get("/lesson/past/"+sessionStorage.role,{});
      this.courses = res.data;
    }
  }
</script>

<style scoped>

</style>
