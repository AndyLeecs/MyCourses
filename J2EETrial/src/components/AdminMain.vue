<template>
  <el-container>
    <el-header>
      <AdminHeader></AdminHeader>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
    import AdminHeader from "./AdminHeader";
    export default {
        name: "AdminMain",
      components: {AdminHeader}
    }
</script>

<style scoped>

</style>
