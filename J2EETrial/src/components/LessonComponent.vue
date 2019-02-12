<template>
    <div>
        <el-button v-if="showNewBtn" type="primary" @click="upload">new...</el-button>
        <courses-table :onclick="click" :courses="contents"></courses-table>
    </div>
</template>
<script>
    import CoursesTable from "./CoursesTable"

    export default {
        name: 'LessonComponent',
        components: {CoursesTable},
        props: {
            contents: {},
            click: {},
            upload: {},
            showNewBtn:{}
        }
    }
</script>
