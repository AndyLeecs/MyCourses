<template>
  <el-table @row-click="onclick"
    :data="courses"
    stripe
    style="width: 100%">
    <el-table-column
      prop="name">
    </el-table-column>
  </el-table>
</template>

<script>
    export default {
        name: "CoursesTable",
        props:
          {
            courses:{},
            onclick:{}
          }
    }

</script>

<style scoped>

</style>
