<template>
  <el-container>
    <el-menu :default-active="$route.path" :router="true" mode="horizontal">
      <el-menu-item index="/new">新课审批</el-menu-item>
      <el-menu-item index="/pub">发布审批</el-menu-item>
      <el-menu-item index="/statistics">信息统计</el-menu-item>
    </el-menu>
  </el-container>
</template>

<script>
  //todo 学生选课的时候也可以看到详情
  //todo 图表显示
    export default {
        name: "AdminHeader"
    }
</script>

<style scoped>

</style>
