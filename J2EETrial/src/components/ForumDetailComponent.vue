<template>
  <el-container>
    <el-header id="title">
      {{title}}
    </el-header>
    <el-main>

                <ul style="list-style: none">
                  <Treenode @refresh="refresh" :id="id" :comments="comments"/>
                </ul>

      <el-form>
        <el-form-item>
          <el-input v-model="newComment" type="textarea"></el-input>
        </el-form-item>
        <el-button type="primary" @click="pubComment">发帖</el-button>
      </el-form>
    </el-main>
  </el-container>
</template>
<script>
  import http from '../utils/http'
  import Treenode from "./Treenode";
  export default {
    name: 'ForumDetailComponent',
    components: {Treenode},
    data() {
      return {
        id: '',
        title: '',
        comments: '',
        newComment: ''
      }
    },
    methods: {
        async pubComment() {
          let res = await http.post("/forum/pubComment",
            {"id": this.id, "content": this.newComment, "time": new Date().Format("yyyy-MM-dd HH:mm:ss")});
          this.comments = res.data;
          this.newComment = ""
        },
      refresh(data)
      {
        this.comments = data;
      }
    },
    mounted() {
      this.id = this.$route.params.id;
        this.title = this.$route.params.title;
        this.comments = this.$route.params.comments;
    }
  }
</script>
<style scoped>
  #title {
    font-size: x-large;
  }
</style>
