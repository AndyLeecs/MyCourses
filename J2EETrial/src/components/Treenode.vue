<template>
  <div>
<span v-for="comment in comments">
                  <li>
                  <div align="left">
                    {{comment.label.writer_name}}&emsp;{{comment.label.time}}
                    <br>
                    {{comment.label.content}}
                    <br>
            <el-form v-if="showReply[comment.id]">
            <el-form-item>
            <el-input v-model="newReply[comment.id]" type="textarea"></el-input>
            </el-form-item>
              <el-form-item>
                <el-button type="text" @click="pubReply(comment.id)">提交</el-button>
              </el-form-item>
            </el-form>
           <el-button v-else size="small" type="primary" @click="reply(comment.id)">回复</el-button>
                  </div>
                    <ul v-if="comment.children">
                  <Treenode @refresh="refresh" :id="id" :comments="comment.children"/>
                    </ul>
                  </li>
                  </span>
  </div>
</template>
<script>
  import http from '../utils/http'
    export default {
        name: 'Treenode',
        props: {
            id : {},
            comments: {},
        },
      data(){
          return{
            newReply: [],
            showReply : []
          }
      },
      methods:{
        reply(id) {
          this.$set(this.showReply, id, true);
        },
        async pubReply(id) {
          console.log(this.id);
          let res = await http.post("/forum/reply",
            {
              "id": this.id, "parent_id": id, "content": this.newReply[id], "time": new Date()
            });
          this.refresh(res.data);
          // this.comments = res.data;
          this.$set(this.showReply, id, false);
        },
        refresh(data)
        {
          this.$emit('refresh',data);
        }
      },


      mounted() {
        console.log("mounted");
        for (let i = 0; i <= this.comments.length; i++) {
          this.showReply.push(false)
        }
      }
    }
</script>
