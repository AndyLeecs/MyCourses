<template>
    <courses-table :onclick="downloadAll" :courses="scores" v-bind:show-values="true"></courses-table>
</template>

<script>
  import http from '../utils/http'
    import CoursesTable from "./CoursesTable";
    export default {
        name: "ScoreDetailComponent",
      components: {CoursesTable},
      data(){
          return{
            scores:[]
          }
      },
      methods:{
          async downloadAll(row){
            if(row.has_extra) {
              let res = await http.get("stuList/score/download/" + row.name);
              window.open(res.config.url);
            }
          }
      },
      mounted() {
          this.scores = this.$route.params.scores;
      }
    }
</script>

<style scoped>

</style>
