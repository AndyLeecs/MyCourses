<template>
  <InfoStatistic v-if="flag" :table-data="tableData" :table-head="tableHead"></InfoStatistic>
</template>

<script>
  import http from '../utils/http'
  import InfoStatistic from "./InfoStatistic";
  export default {
    name: "InfoScore",
    components: {InfoStatistic},
    data() {
      return {
        tableHead: [
          {
            id: 'semester',
            label: '学期',
            filter: [],
          },
          {
            id: 'name',
            label: '课程',
            filter: [],
          },
          {
            id: 'teacher',
            label: '教师',
            filter: [],
          },
          {
            id: 'title',
            label: '项目',
            filter: [],
            isNoFilter :true
          },
          {
            id: 'score',
            label: '成绩',
            filter: [],
            isNoFilter :true
          },
        ],
        tableData: [],
        flag : false
      }
    },
    methods:{
    },
    async mounted(){
      let res = await http.get("/stat/score");
      this.tableData = res.data;
      this.flag = true;
    },

  }
</script>

<style scoped>

</style>
