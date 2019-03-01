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
    export default {
        name: "AdminHeader"
    }
</script>

<style scoped>

</style>
