<template>
  <el-container>
    <el-header>
      <label id="header">请完善个人信息</label>
    </el-header>
    <el-main id="main">
    <el-form :model="Register" :rules="rules" ref="Register" size="small" hide-required-asterisk>
      <el-form-item  label="姓名" prop="name">
        <el-input v-model="Register.name"></el-input>
      </el-form-item>
      <el-form-item v-if="showSID" label="学号" prop="sid">
        <el-input v-model="Register.sid"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input v-model="Register.pass" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input v-model="Register.checkPass" type="password"></el-input>
      </el-form-item>
      <el-form-item>
        <br>
          <el-button id="Btn" type="primary" @click="submitForm('Register')">提交</el-button>
      </el-form-item>
    </el-form>
    </el-main>
  </el-container>
</template>

<script>
    import http from '../utils/http'
    export default {
        name: "Register",
        data()
        {
          var validatePass = (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请输入密码'));
            } else {
              if (this.Register.checkPass !== '') {
                this.$refs.Register.validateField('checkPass');
              }
              callback();
            }
          };
          var validatePass2 = (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请再次输入密码'));
            } else if (value !== this.Register.pass) {
              callback(new Error('两次输入密码不一致!'));
            } else {
              callback();
            }
          };

          return{
            Register:{
              name: '',
              pass: '',
              checkPass: '',
              sid: ''
            },
            showSID : false,
            rules:{
              name: [
                {required: true, message: '请输入姓名', trigger: 'blur'}
              ],
              pass:[
                {validator: validatePass, trigger: 'blur'}
              ],
              checkPass:[
                {validator: validatePass2, trigger:'blur'}
              ],
              sid: [
                {required: true, message: '请输入学号', trigger: 'blur'}
              ]

            }
          }
        },
      mounted(){
         if(this.$route.params.role == "student")
         {
           this.showSID = true;
         }
      },
        methods:{
          submitForm(formName){
            this.$refs[formName].validate(async (valid) => {
                if (valid) {
                  let registerInfo = {"name":this.Register.name,"password":this.Register.pass};
                  console.log(this.showSID);
                  if(this.showSID)
                    registerInfo.sid = this.Register.sid;
                  console.log(registerInfo);
                  const res = await http.post('/'+this.$route.params.role+'/register', registerInfo);
                  if (!res.msg) {
                    sessionStorage.setItem("role",this.$route.params.role);
                    this.$router.push("/main");
                  }
                } else {
                  console.log('error submit!!');
                  return false;
                }
              }
            );
          }
          }
    }
</script>

<style scoped>
  #main {
    width: 50%;
    margin: auto;
    vertical-align: middle;
  }
  #header{
    font-size: xx-large;
  }
  #Btn{
    width:600px
  }
</style>
