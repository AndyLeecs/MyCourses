<template>
  <el-container>
    <el-aside width="200px"><InfoSider></InfoSider></el-aside>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
    import InfoSider from "./InfoSider";
    export default {
        name: "InfoStatMain",
      components:{InfoSider}
    }
</script>

<style scoped>

</style>
