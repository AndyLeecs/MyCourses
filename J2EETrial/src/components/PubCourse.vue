<template>
  <div>
    <el-dialog :title="title" :visible.sync="dialogTableVisible">
    <el-form>
      <el-form-item label="时间">
        <el-date-picker
          v-model="pubTable.date"
          value-format="yyyyMMdd"
          unlink-panels
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="班次">
        <el-input-number v-model="pubTable.count" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-switch
          v-model="pubTable.limitNum"
          active-text="控制班额">
        </el-switch>
      </el-form-item>
      <el-form-item v-if="pubTable.limitNum" label="班额">
        <el-input-number  v-model="pubTable.limit" :min="1"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button id="Btn" type="primary" @click="submitForm()">申请</el-button>
      </el-form-item>
    </el-form>
    </el-dialog>
  <courses-table :onclick="pub" :courses=" courses"></courses-table>
  </div>
</template>

<script>
  import http from "../utils/http"
  import CoursesTable from "./CoursesTable";

  export default {
        name: "PubCourse",
        components: {CoursesTable},
        data() {
          return{
            coursewares : [],
            dialogTableVisible : false,
            title:'',
            pubTable:{
              id: '',
              date:'',
              count:'',
              limit:'',
              limitNum:true
            }
          }
        },
        methods:
          {
            pub(row)
            {
              console.log("row"+row);
              this.title = row.name;
              this.pubTable.id = row.id;
              this.dialogTableVisible =true;
            },
            async submitForm()
            {
              // let lesson = this.pubTable;
              // lesson.id =
              let res = await http.post("/lesson/pub",this.pubTable)
              this.dialogTableVisible = false;
            }
          },
      async mounted() {
        let res = await http.get("/course/forpub",{});
        console.log(res.data);
        this.courses = res.data;
      }
    }
</script>

