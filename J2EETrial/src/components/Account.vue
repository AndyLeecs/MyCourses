<template>
  <el-main>
    <el-form :model="Info" label-width="100px">
      <LabelInputSwitch @save="saveName" label="姓名" :content=Info.username />
      <LabelInputSwitch v-if="Info.sid" @save="saveSID" label="学号" :content=Info.sid />
      <el-form-item v-if="Info.sid">
        <el-button type="danger" @click="logout()">注销</el-button>
      </el-form-item>
    </el-form>
  </el-main>
</template>

<script>
  import LabelInputSwitch from "./LabelInputSwitch";
  import http from '../utils/http'
  export default {
        name: "Account",
    components: {LabelInputSwitch},
    data(){
          return{
            Info:{
              username:'',
              sid:''
            },
            editName:false,
            editSid:false
          }
      },
    async mounted(){
       console.log(sessionStorage.getItem("role"));
       let res = await http.get("/"+sessionStorage.getItem("role")+"/info",{});
       if(!res.msg){
         console.log(res.data);
       this.Info.username = res.data.username;
       if(res.data.sid)
       this.Info.sid = res.data.sid;
         }
    },

      methods:{
          async logout(){
            await http.post("/logout",{});
            //清空用户信息
            sessionStorage.clear();
            this.$router.push("/");
        },
        async saveName(username){
          console.log("save");
          let res = await http.post("/username",{"name":username});
          if (!res.msg) {
            this.Info.username = username;
          }
        },

        async saveSID(sid){
          console.log("save");
          let res = await http.post("/sid",{"sid":sid});
          if (!res.msg) {
            this.Info.sid = sid;
          }
        },
      }
    }
</script>

