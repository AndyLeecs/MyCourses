<template>
  <el-submenu index="/info">
    <template slot="title">个人信息</template>
    <el-menu-item index="/info/account">账户信息</el-menu-item>
    <el-menu-item index="/info/statistic">统计信息</el-menu-item>
  </el-submenu>
</template>

<script>
    export default {
        name: "Info"
    }
</script>

<style scoped>

</style>
