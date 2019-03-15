<template>
  <el-container>
      <div>
        <el-button icon="el-icon-back" @click="main"  circle></el-button>
      </div>
      <br>
      <el-menu :default-active="$route.path" :router="true" mode="horizontal">
        <el-menu-item index="/courseware">课件</el-menu-item>
        <el-menu-item index="/homework">作业</el-menu-item>
        <el-menu-item index="/forum">论坛</el-menu-item>
        <el-menu-item index="/stuList">学生</el-menu-item>
      </el-menu>
    <el-col :span="4" :offset="12"><label>{{lesson_name}}</label></el-col>
  </el-container>
</template>

<script>
    export default {
        name: "HeaderForCur",
      data() {
        return{
          isStu : '',
          lesson_name:''
        }
      },
      methods:{
          main(){
            this.$router.push("/main");
          }
      },
      mounted() {
          this.isStu = sessionStorage.role == "student";
          this.lesson_name = sessionStorage.lesson_name;
      }
    }
</script>

<style scoped>

</style>
