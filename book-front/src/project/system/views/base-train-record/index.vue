<template lang="pug">
    ContentBody(title="员工培训记录")
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
                el-form-item(label="课程编号")
                    el-input(v-model="form.courseNo")
                el-form-item(label="课程名称")
                    el-input(v-model="form.courseName")
                el-form-item(label="开设部门/机构")
                    el-input(v-model="form.makeCourse")
                el-form-item(label="开课时间")
                    el-date-picker(v-model="timeRange" type="daterange")
                el-form-item(label="讲师")
                    el-input(v-model="form.teacherName")
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
    mixins: [CurdTableMix('$baseTrainRecordService')],
    data() {
        return {
            editForm: {
                id: '',
                employeeId: '',
                courseNo: '',
                courseName: '',
                trainStyle: '',
                makeCourse: '',
                beginTime: '',
                endTime: '',
                classHour: '',
                teacherId: '',
                teacherName: '',
                rate: '',
                score: '',
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
        async submitHandler(val) {
            if (!val.id) {
                // 如果ID 不为空, 判断数据的CourseNo 是否存在
                let res = await this.$baseTrainRecordService.existCourseNo({'courseNo': val.courseNo}, {method: 'get'})
                if (res) {
                    await this.$confirm('是否确定修改?')
                }
            }
            this.submit(val, 'id').then(data => {
                this.editShow = false
                this.loadData()
            })
        }
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
                courseNo: {
                    label: '课程编号'
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
                    label: '开课时间',
                    format: (t) => this.$format.date(t, 'yyyy-mm-dd')
                },
                endTime: {
                    label: '完成时间',
                    format: (t) => this.$format.date(t, 'yyyy-mm-dd')
                },
                classHour: {
                    label: '课时'
                },
                teacherName: {
                    label: '讲师'
                },
                rate: {
                    label: '课程满意度'
                },
                score: {
                    label: '考核成绩'
                },
                remark: {
                    label: '备注'
                },
            }
        },
        exportOptions() {
            return {
                headerMapping: this.headerMapping
            }
        },
        importOptions() {
            return {
                headerMapping: {
                    '工号': 'employeeCode',
                    '课程编号': 'courseNo',
                    '课程名称': 'courseName',
                    '培训形式': 'trainStyle',
                    '开设部门/机构': 'makeCourse',
                    '开课时间': 'beginTime',
                    '完成时间': 'endTime',
                    '课时': 'classHour',
                    '讲师': 'teacherName',
                    '课程满意度': 'rate',
                    '考核成绩': 'score',
                    '备注': 'remark'
                }
            }
        }
    },
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
