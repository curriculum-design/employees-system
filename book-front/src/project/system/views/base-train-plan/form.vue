<template lang="pug">
el-dialog(title="编辑", :visible.sync="dialogShow", :close-on-click-modal="false", :append-to-body="true")
    el-form(v-model="form" @submit.prevent.native="submitHandler()" label-width="120px")
        el-form-item(label="工号/员工姓名")
            el-select(v-model="form.employeeId" filterable placeholder="请选择" style="width:50%")
                el-option(v-for="item in employeeList" :key="item.id" :label="item.employeeCode + '/' + item.realName" :value="item.id")
            el-button(type="primary", @click="selectEmployee" style="margin-left:10px") 选择员工
        el-form-item(label="课程名称")
            el-input(v-model="form.courseName")
        el-form-item(label="培训形式")
            el-input(v-model="form.trainStyle")
        el-form-item(label="开设部门/机构")
            el-input(v-model="form.makeCourse")
        el-form-item(label="预计开课时间")
            el-date-picker(v-model="form.beginTime" format="yyyy-MM" type="month" value-format="timestamp")
        el-form-item(label="课时")
            el-input-number(v-model="form.classHour" :min="0.1" :step="0.1")
        el-form-item(label="备注")
            el-input(type="text" v-model="form.remark")
        el-form-item.form-action.margin-top-20
            el-button(type="primary", @click="dialogShow = false") 取消
            el-button(type="success" native-type="submit") 保存
    el-dialog(title="员工选择" :visible.sync="dialogVisible" width="45%" :close-on-click-modal="false", :append-to-body="true")
        RefEmployeeForm(v-model="form" v-if="dialogVisible" @closeDialog="closeDialog" class="dialog")
</template>

<script>
import formDialogMix from '@/utils/mixins/form-dialog-mix'
import RefEmployeeForm from '../base-employee/ref-employee-form'
import {mapGetters} from 'vuex'
export default {
    components: {RefEmployeeForm},
    mixins: [formDialogMix],
    computed: {
        ...mapGetters['systemMapping']
    },
    data() {
        return {
            dialogVisible: false,
            employeeList: []
        }
    },
    async created() {
        this.employeeList = await this.$baseEmployeeService.all({}, {method: 'get'})
    },
    methods: {
        closeDialog() {
            this.dialogVisible = false
        },
        selectEmployee() {
            this.dialogVisible = true
        },
    }
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
