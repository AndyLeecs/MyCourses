<template>
  <el-container>
    <el-menu :default-active="$route.path" :router="true" mode="vertical">
      <el-menu-item v-show="isStu" index="/attend">选课</el-menu-item>
      <el-menu-item v-show="isStu" index="/dropout">退课</el-menu-item>
      <el-menu-item v-show="isStu" index="/score">成绩</el-menu-item>
      <el-menu-item v-show="!isStu" index="/enrollment">选课</el-menu-item>
      <el-menu-item v-show="!isStu" index="/published">开课</el-menu-item>
      <el-menu-item v-show="!isStu" index="/work">作业</el-menu-item>
    </el-menu>
  </el-container>
</template>

<script>
    export default {
        name: "InfoSider",
      data(){
          return{
            isStu : sessionStorage.role === "student"
          }
      }
    }
</script>

<style scoped>

</style>
