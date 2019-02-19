<template>
      <el-container>
        <el-menu :default-active="$route.path" :router="true" mode="horizontal">
            <el-menu-item index="/course/current">正在进行</el-menu-item>
            <el-menu-item v-show="isStu" index="/course/select">可选课程</el-menu-item>
            <el-menu-item v-show="!isStu" index="/course/pub">发布课程</el-menu-item>
          <el-menu-item v-show="!isStu" index="/course/new">新建课程</el-menu-item>
          <el-menu-item v-show="!isStu" index="/course/past">历史开课</el-menu-item>
          <InfoSubMenu></InfoSubMenu>
        </el-menu>
      </el-container>
</template>

<script>
  import InfoSubMenu from './InfoSubMenu'
    export default {
        name: "Main",
        components: {
          InfoSubMenu,
        },
        data()
        {
          return{
            isStu : ''
          }
        },
      mounted() {
          this.isStu = (sessionStorage.role == "student");
      }
    }
</script>

<style scoped>

</style>
