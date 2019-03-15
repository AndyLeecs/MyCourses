<template>
  <InfoStatistic v-if="flag" :table-data="tableData" :table-head="tableHead"></InfoStatistic>
</template>

<script>
  import http from '../utils/http'
  import InfoStatistic from "./InfoStatistic";
  export default {
    name: "InfoWork",
    components: {InfoStatistic},
    data() {
      return {
        tableHead: [
          {
            id: 'semester',
            label: '学期',
            filter: []
          },
          {
            id: 'name',
            label: '课程',
            filter: []
          },
          {
            id: 'title',
            label: '项目',
            filter: [],
            isNoFilter : true
          },
          {
            id: 'student',
            label: '学生',
            filter: [],
            isNoFilter : true
          },
          {
            id: 'handle',
            label: '是否提交',
            filter: []
          }
        ],
        tableData: [],
        flag : false
      }
    },
    methods:{
    },
    async mounted(){
      let res = await http.get("/stat/work");
      this.tableData = res.data;
      this.flag = true;
    },

  }
</script>

<style scoped>

</style>
