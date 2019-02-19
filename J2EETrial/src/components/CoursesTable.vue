<template>
  <el-table @row-click="onclick"
    :data="courses"
    stripe
    style="width: 100%">
    <el-table-column
    prop="id" v-if="showId">
    </el-table-column>
    <el-table-column
      prop="name">
    </el-table-column>
  <el-table-column v-if="showValues"
  prop="value">
  </el-table-column>
  </el-table>
</template>

<script>
    export default {
        name: "CoursesTable",
        props:
          {
            courses:{},
            showId : {
              type: Boolean,
              default:false
            },
            onclick:{},
            showValues:{
              type: Boolean,
              default: false
            }
          }
    }

</script>

<style scoped>

</style>
