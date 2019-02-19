<template>
    <el-form>
      <el-form-item label="学生总数">
        {{stat.studentCount}}
      </el-form-item>
      <el-form-item label="教师总数">
        {{stat.teacherCount}}
      </el-form-item>
      <el-form-item label="课程总数">
        {{stat.courseCount}}
      </el-form-item>
    </el-form>
</template>

<script>
  import http from '../utils/http'
    export default {
        name: "AdminStatistics",
      data(){
          return{
            stat:''
          }
      },
      async mounted(){
          let res = await http.get("/admin/stat");
          this.stat = res.data;
      }
    }
</script>

<style scoped>

</style>
