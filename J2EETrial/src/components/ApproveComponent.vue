<template>
    <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button
                    size="mini"
                    @click="upvote">通过
            </el-button>
            <el-button
                    size="mini"
                    type="danger"
                    @click="downvote">不通过
            </el-button>
        </template>
    </el-table-column>
</template>
<script>
    export default {
        name: 'ApproveComponent',
        props: {
            downvote: {},
            upvote: {}
        }
    }
</script>
