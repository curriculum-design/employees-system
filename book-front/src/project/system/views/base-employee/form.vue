<template lang="pug">
el-dialog(title="编辑", :visible.sync="dialogShow", :close-on-click-modal="false", :append-to-body="true")
    el-form(v-model="form" @submit.prevent.native="submitHandler()" label-width="80px")
        el-form-item(label="工号" )
            el-input(v-model="form.employeeCode")
        el-form-item(label="员工姓名")
            el-input(v-model="form.realName")
        el-form-item(label="是否在职")
            el-input(v-model="form.onJob")
        el-form-item(label="工种")
            el-input(v-model="form.workType")
        el-form-item(label="机构")
            el-input(v-model="form.org")
        el-form-item(label="部门")
            el-input(v-model="form.dept")
        el-form-item(label="岗位")
            el-input(v-model="form.jobName")
        el-form-item(label="入职时间")
            el-date-picker(v-model="form.joinTime" format="yyyy-MM-dd" value-format="timestamp")
        el-form-item.form-action.margin-top-20
            el-button(type="primary", @click="dialogShow = false") 取消
            el-button(type="success" native-type="submit") 保存
</template>

<script>
import formDialogMix from '@/utils/mixins/form-dialog-mix'
import {mapGetters} from 'vuex'
export default {
    mixins: [formDialogMix],
    computed: {
        ...mapGetters['systemMapping']
    }
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
