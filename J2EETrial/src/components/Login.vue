<template>
  <el-container>
    <el-main id="main">
        <img src="../assets/mycourses.png">
        <el-row><br></el-row>
        <el-row>
          <el-col :span="20">
            <el-form :model="Login" :rules="rules" ref="Login" label-width="100px" hide-required-asterisk>
              <el-form-item label="邮箱" prop="email" >
                <el-input v-model="Login.email" autocomplete="off">
                </el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password" >
                <el-input type="password" v-model="Login.password" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item>
                <el-row>
                  <el-col :span="12" align="left">
                    <el-switch
                      v-model="keepName"
                      active-text="记住邮箱">
                    </el-switch>
                  </el-col>
                  <el-col :span="12" align="right">
                    <el-button type="text" size="mini" @click="emailDialogVisible=true" plain>注册</el-button>
                  </el-col>
                </el-row>
              </el-form-item>
              <el-form-item>
                <el-button id="Btn" type="primary" @click="submitForm('Login')">登录</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
    </el-main>
    <el-footer></el-footer>
    <el-dialog title="请填写nju邮箱" :visible.sync="emailDialogVisible">
      <el-form>
      <el-form-item prop="newEmail">
          <el-input v-model="newEmail.email" autocomplete="off">
          </el-input>
      </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="emailDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="verify()">确 定</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
  import http from '../utils/http'
  export default {
    name: 'Login',
    data() {
      return {
        Login: {
          email: '',
          password: ''
        },
        keepName: false,
        rules: {
          email: [
            {required: true, message: '请输入邮箱', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        },
        emailDialogVisible: false,
        newEmail : {
          email:''
        },
      };
    },
    mounted(){
      if (this.$cookies.isKey('name')) {
        this.Login.email = this.$cookies.get('name');
        this.keepName = true;
      }
    },
    methods: {
      submitForm(formName) {
        //检查合法性
        this.$refs[formName].validate(async (valid) => {
            if (valid) {
              const res = await http.post('/login', this.Login);
                if (res.data.valid) {
                  console.log("true");
                  // sessionStorage.role = res.data.role;
                  sessionStorage.setItem("role",res.data.role);
                  this.$router.push(
                    "/main"
                  );
                }
              } else {
                console.log('error submit!!');
                return false;
            }
          }
          );
        //检查cookie
        if (this.keepName) {
          this.$cookies.set("name",this.Login.email, -1);
        }
        else {
          this.$cookies.remove("name");
        }
      },
      async verify() {
        if (this.newEmail.email) {
          this.newEmail.email = this.newEmail.email.trim();
          let postFix = this.newEmail.email.split("@")[1];
          if (this.newEmail.email.length > 0 &&
            (postFix == "nju.edu.cn" || postFix == "smail.nju.edu.cn")) {
            const res = await http.post('/verify', this.newEmail);
            console.log(res);
            if (res.data.success) {
              alert("已发送验证链接，请查看您的邮箱");
            }
          }else
            alert("邮箱格式错误");
          }else
            alert("邮箱不能为空");
        this.emailDialogVisible = false;
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

  #Btn{
    width: 390px;
  }
</style>
