<template>
  <div>
    <el-main>
      <el-table ref="table" :data="tableData">
        <el-table-column v-for="item in innertableHead"
                         v-if="!item.isNoFilter"
                         :key="item.id"
                         :prop="item.id"
                         :label="item.label"
                         :filters="item.filter"
                         :filter-method="item.filter && filterHandler">
        </el-table-column>
          <el-table-column v-for="item in innertableHead"
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
        data(){
          return {
            innertableHead:{}
          }
        },
      methods:{
        uniqArrObject (arr) {
          let result = {};
          let finalResult = [];
          for (let i = 0; i < arr.length; i++) {
            result[arr[i].text] = arr[i]
          }
          for (let key in result) {
            finalResult.push(result[key])
          }
          return finalResult
        },
        setTextAndValue(filterList, target)
        {
          this.tableData.forEach((item) => {
            filterList.push({
              text: item[target], value: item[target]
            })
          });
          filterList = this.uniqArrObject(filterList)
        },
        filterHandler (value, row, column) {
          const property = column['property'];
          return row[property] == value || row[property].value == value
        },
        setFilters(){
          this.innertableHead = this.tableHead;
          for (let tableDataItem of this.innertableHead) {
            this.setTextAndValue(tableDataItem.filter, tableDataItem.id);
          }
        },
      },
      mounted() {
        this.setFilters()
      }
    }
</script>

<style scoped>

</style>
