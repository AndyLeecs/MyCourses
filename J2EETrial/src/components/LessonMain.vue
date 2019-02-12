<template>
  <el-container>
    <el-header>
      <HeaderForCur></HeaderForCur>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
  //todo 显示课程名
  import HeaderForCur from './HeaderForCur'
  import Account from "./Account";
  export default {
    name: "LessonMain",
    components: {
      // Account,
      HeaderForCur
    }
  }
</script>

<style scoped>

</style>
