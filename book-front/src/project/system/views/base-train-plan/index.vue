<template lang="pug">
    ContentBody(title="员工培训计划")
        template(slot="filter")
            el-form(:inline="true", v-model="form" @submit.prevent.native="filter(form)")
                el-form-item(label="员工姓名")
                    el-input(v-model="form.realName")
                el-form-item(label="机构")
                    el-input(v-model="form.org")
                el-form-item(label="部门")
                    el-input(v-model="form.dept")
                el-form-item(label="岗位")
                    el-input(v-model="form.jobName")
                el-form-item(label="课程名称")
                    el-input(v-model="form.courseName")
                el-form-item(label="培训形式")
                    el-input(v-model="form.trainStyle")
                el-form-item(label="开设部门/机构")
                    el-input(v-model="form.makeCourse")
                el-form-item(label="预计开课时间")
                    el-date-picker(v-model="form.beginTime" type="month" format="yyyy-MM" value-format="timestamp")
                    el-date-picker(v-model="form.endTime" type="month" format="yyyy-MM" value-format="timestamp")
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
    mixins: [CurdTableMix('$baseTrainPlanService')],
    data() {
        return {
            editForm: {
                id: '',
                employeeId: '',
                courseName: '',
                trainStyle: '',
                makeCourse: '',
                beginTime: '',
                classHour: '',
                remark: '',
                createTime: '',
                updateTime: '',
                isDel: '',
            }
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
        }
    },
    computed: {
        ...mapGetters['systemMapping'],
        headerMapping() {
            return {
                employeeCode: {
                    label: '工号'
                },
                realName: {
                    label: '员工姓名'
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
                courseName: {
                    label: '课程名称'
                },
                trainStyle: {
                    label: '培训形式'
                },
                makeCourse: {
                    label: '开设部门/机构'
                },
                beginTime: {
                    label: '预计开课时间',
                    format: (t) => this.$format.date(t, 'yyyy-mm')
                },
                classHour: {
                    label: '课时'
                },
                remark: {
                    label: '备注'
                },
            }
        },
        exportOptions() {
            return {
                headerMapping: {
                    ...this.headerMapping
                }
            }
        },
        importOptions() {
            return {
                headerMapping: {
                    '工号': 'employeeCode',
                    '课程名称': 'courseName',
                    '培训形式': 'trainStyle',
                    '开设部门/机构': 'makeCourse',
                    '预计开课时间': 'beginTime',
                    '课时': 'classHour',
                    '备注': 'remark'
                }
            }
        }
    },
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
