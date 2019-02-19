<template>
  <div>
    <el-input v-model="password" auto-complete="false">
    </el-input>
    <el-button @click="login">登录</el-button>
  </div>
</template>

<script>
  import http from '../utils/http'
    export default {
        name: "Admin",
      data(){
          return {
            password: ''
          }
      },
      methods:{
          async login(){
            let res = await http.post("/admin/login",{"password":this.password});
            if (res.data.valid)
            {
              sessionStorage.role = "admin";
              this.$router.push("/admin/main")
            }
          }
      }
    }
</script>

<style scoped>

</style>
