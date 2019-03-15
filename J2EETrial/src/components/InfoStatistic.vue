<template>
  <div>
    <el-main>
      <el-table ref="table" :data="tableData">
        <el-table-column v-for="item in tableHead"
                         v-if="!item.isNoFilter"
                         :key="item.id"
                         :prop="item.id"
                         :label="item.label"
                         :filters="item.filter"
                         :filter-method="item.filter && filterHandler">
        </el-table-column>
          <el-table-column v-for="item in tableHead"
                           v-if="item.isNoFilter"
                           :key="item.id"
                           :prop="item.id"
                           :label="item.label">
          </el-table-column>
      </el-table>
    </el-main>
  </div>
</template>

<script>
    export default {
        name: "InfoStatistic",
        props:{
          tableHead:{},
          tableData:{},
        },
      methods:{
        uniqArrObject (arr) {
          let hash=[];
          let final = []
          for (let i = 0; i < arr.length; i++) {
            if(hash.indexOf(arr[i].value)==-1){
              hash.push(arr[i].value);
              final.push(arr[i])
            }
          }
          return final;
        },
        setTextAndValue(target)
        {
          var list = [];
          this.tableData.forEach((item) => {
            list.push({
              text: item[target], value: item[target]
            })
          });
          return this.uniqArrObject(list);

        },
        filterHandler (value, row, column) {
          const property = column['property'];
          return row[property] == value || row[property].value == value
        },
        setFilters(){
          for (let tableDataItem of this.tableHead) {
            tableDataItem.filter = this.setTextAndValue(tableDataItem.id);
            console.log(tableDataItem.id + " "+tableDataItem.filter)
          }
        },
      },
      created() {
        this.setFilters()
      }
    }
</script>

<style scoped>

</style>
