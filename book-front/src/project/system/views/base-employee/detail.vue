<template lang="pug">
el-dialog(title="明细", :visible.sync="dialogShow", :close-on-click-modal="false", :append-to-body="true" width="960px")
    el-tabs(v-model="activeName")
        el-tab-pane(label="基本信息" name="baseTab")
            el-form(v-model="form" label-width="80px")
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
        el-tab-pane(label="培训计划" name="tab2")
            BaseTrainPlanView(:p="form.realName")
        el-tab-pane(label="培训记录" name="tab3")
            BaseTrainRecordView(:p="form.realName")
        el-tab-pane(label="开课记录" name="tab4")
            BaseTrainRecordView2(:p="form.realName")
</template>

<script>
import formDialogMix from '@/utils/mixins/form-dialog-mix'
import {mapGetters} from 'vuex'
import BaseTrainPlanView from '../base-train-plan/index2'
import BaseTrainRecordView from '../base-train-record/index2'
import BaseTrainRecordView2 from '../base-train-record/index3'
export default {
    components: {BaseTrainPlanView, BaseTrainRecordView, BaseTrainRecordView2},
    mixins: [formDialogMix],
    computed: {
        ...mapGetters['systemMapping']
    },
    data() {
        return {
            activeName: 'baseTab',
        }
    }
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
