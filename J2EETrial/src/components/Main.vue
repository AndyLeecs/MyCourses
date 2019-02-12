<template>
  <el-container>
    <el-header>
      <Header></Header>
    </el-header>
    <el-main>
    <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
  import Header from './Header'
  import Account from "./Account";
    export default {
        name: "Main",
        components: {
          // Account,
          Header
        }
    }
</script>

<style scoped>

</style>
