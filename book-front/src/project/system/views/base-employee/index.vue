<template lang="pug">
    ContentBody(title="员工列表")
        template(slot="filter")
            el-form(:inline="true", v-model="form" @submit.prevent.native="filter(form)")
                el-form-item(label="中文名")
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
                    el-date-picker(v-model="timeRange" type="daterange")
                el-form-item
                    el-button(type="primary", native-type="submit") 查询
                    el-button(type="success" @click="handleEdit()") 新增
                    ExportAllButton(:api="list", :filter="form", :total="total", :options="exportOptions")
                    ImportButtonWithDialog(@inputData="inputData", :saveApi="uploadSave", :options="importOptions", @complied="initLoad")
        el-table(:data="tableData" v-loading="loading" border)
            el-table-column(:prop="key", :key="key", :label="value.label || value", v-for="value, key in headerMapping", :show-overflow-tooltip="true", :width="value.width")
                ColumnContent(slot-scope="{row}" :columnDefine="value", :row="row", :value-key="key" :renderContent="value.render")
            el-table-column(label="操作" min-width="120")
                template(slot-scope="scope")
                    .icon-btn.el-icon-edit-outline(@click="handleEdit(scope.row.id)")
                    .icon-btn.el-icon-delete.danger(type="danger", @click="deleteConfirm(scope.row.id)")
        template(slot="footer")
            el-pagination(:pageNum.sync="pageNum", :total="total", :pageSize="pageSize",@current-change="pageChangeHandler" @size-change="sizeChangeHandler" layout="total, sizes, prev, pager, next")
        EditForm(:show.sync="editShow", v-model="editForm", @submit="submitHandler" v-loading.body="editLoading")
</template>

<script>
import CurdTableMix from '@/utils/mixins/curd-table-mix.js'
import {mapGetters} from 'vuex'
import EditForm from './form'

export default {
    components: {EditForm},
    mixins: [CurdTableMix('$baseEmployeeService')],
    data() {
        return {
            editForm: {
                id: '',
                employeeCode: '',
                realName: '',
                onJob: '',
                workType: '',
                org: '',
                dept: '',
                jobName: '',
                joinTime: '',
                createTime: '',
                updateTime: '',
                isDel: '',
            },
        }
    },
    methods: {
        async uploadSave(data) {
            return this.api.postJson('upload-save', data)
        },
        submitHandler(val) {
            this.submit(val, 'id').then(data => {
                this.editShow = false
                this.loadData()
            })
        },
    },
    computed: {
        timeRange: {
            get() {
                if (this.form.beginTime) {
                    return [new Date(this.form.beginTime), new Date(this.form.endTime)]
                }
                return []
            },
            set(time) {
                const [startTime, endTime] = time || []
                if (startTime) {
                    this.$set(this.form, 'beginTime', startTime.getTime())
                    this.$set(this.form, 'endTime', endTime.getTime())
                } else {
                    this.$set(this.form, 'beginTime', null)
                    this.$set(this.form, 'endTime', null)
                }
            },
        },
        ...mapGetters['systemMapping'],
        headerMapping() {
            return {
                employeeCode: {
                    label: '工号',
                },
                realName: {
                    label: '中文名',
                },
                onJob: {
                    label: '是否在职',
                },
                workType: {
                    label: '工种',
                },
                org: {
                    label: '机构',
                },
                dept: {
                    label: '部门',
                },
                jobName: {
                    label: '岗位',
                },
                joinTime: {
                    label: '入职时间',
                    format: (t) => this.$format.date(t, 'yyyy-mm-dd'),
                },
            }
        },
        exportOptions() {
            return {
                headerMapping: this.headerMapping,
            }
        },
        importOptions() {
            return {
                headerMapping: {
                    '人员编码': 'employeeCode',
                    '中文名称': 'realName',
                    '是否在职': 'onJob',
                    '工种': 'workType',
                    '机构': 'org',
                    '部门': 'dept',
                    '岗位': 'jobName',
                    '入职时间': 'joinTime',
                },
            }
        },
    },
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
