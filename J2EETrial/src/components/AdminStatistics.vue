<template>
    <el-form>
      <el-form-item label="学生总数">
        {{stat.studentCount}}
      </el-form-item>
      <el-form-item label="教师总数">
        {{stat.teacherCount}}
      </el-form-item>
      <el-form-item label="课程总数">
        {{stat.courseCount}}
      </el-form-item>
      <el-form-item label="创建课程审核情况">
        <ve-pie :data="courseApproveData"></ve-pie>
      </el-form-item>
    </el-form>
</template>

<script>
  import http from '../utils/http'
    export default {
        name: "AdminStatistics",
      data(){
          return{
            stat:'',
            courseApproveData : {
              columns : ['status','num'],
              rows:[
                {'status':'已通过','num':''},
                {'status':'已驳回','num':''},
                {'status':'待审批','num':''}
              ]
            }
          }
      },
      async mounted(){
          let res = await http.get("/admin/stat");
          this.stat = res.data[0];
          let courseApprove = res.data[1];
          let chartData = this.courseApproveData.rows;
          chartData[0].num = courseApprove.up;
          chartData[1].num = courseApprove.down;
          chartData[2].num = courseApprove.unsettle;
      }
    }
</script>

<style scoped>

</style>
