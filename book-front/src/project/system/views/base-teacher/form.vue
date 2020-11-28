<template lang="pug">
    el-dialog(title="编辑", :visible.sync="dialogShow", :close-on-click-modal="false", :append-to-body="true")
        el-form(v-model="form" @submit.prevent.native="submitHandler()" label-width="120px")
            el-form-item(label="工号/员工姓名")
                el-select(v-model="form.employeeCode" filterable placeholder="请选择" @change="employeeChange" clearable=)
                    el-option(v-for="item in employeeList" :key="item.employeeCode" :label="item.employeeCode + '/' + item.realName" :value="item.employeeCode")
            el-form-item(label="讲师姓名")
                el-input(v-model="form.teacherName")
            el-form-item(label="类型")
                el-input(v-model="form.workType")
            el-form-item(label="主讲课程")
                el-input(v-model="form.speakCourse")
            el-form-item(label="认证/合作时间")
                el-date-picker(v-model="form.teamWorkTime" format="yyyy-MM-dd" value-format="timestamp")
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
        ...mapGetters['systemMapping'],
    },
    data() {
        return {
            employeeList: [],
        }
    },
    async created() {
        this.employeeList = await this.$baseEmployeeService.all({}, {method: 'get'})
    },
    methods: {
        employeeChange: function(code) {
            if (code) {
                const v = this.employeeList.filter(d => d.employeeCode === code).pop()
                this.form.teacherName = v.realName
                this.form.workType = '内部讲师'
            } else {
                this.form.teacherName = ''
                this.form.workType = '外部讲师'
            }
        },
    },
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
