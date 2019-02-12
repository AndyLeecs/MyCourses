<template>
    <el-form-item :label=label>
        <div v-if="!edit">
            {{content}}
            <el-button type="text" icon="el-icon-edit" @click="edit=true"></el-button>
        </div>
        <el-row v-else>
            <el-col :span="20">
                <el-input v-model="username"></el-input>
            </el-col>
            <el-button type="primary" @click="save()">保存</el-button>
            <el-button @click="edit=false">取消</el-button>
        </el-row>
        <!--<el-input :placeholder=Info.username></el-input>-->
    </el-form-item>
</template>
<script>
    export default {
        name: 'LabelInputSwitch',
        props: {
            label:{},
            content: {},
        },
      data(){
          return{
            edit:'',
            username:''
          }
      },

      methods:{
          save(){
            this.edit=false
            this.$emit('save', this.username);
          }
      }
    }
</script>
