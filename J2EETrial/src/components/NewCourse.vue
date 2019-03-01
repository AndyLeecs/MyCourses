<template>
    <el-form label-width="100px">
      <el-form-item label="课程名">
        <el-input v-model="course"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="post()">申请</el-button>
      </el-form-item>
    </el-form>
</template>

<script>
  import http from '../utils/http'
    export default {
        name: "NewCourse",
      data(){
          return{
          course:'',
        }
      },
      methods:{
          async post()
          {
            let res = await http.post("/course/new",{"name":this.course})
            if (!res.msg)
            {
              this.$message({
                message: '申请成功，管理员将在两个工作日内审批，过审后您可以发布该课程',
                type: 'success'
              });
            }
          }
      }
    }
</script>

<style scoped>

</style>
