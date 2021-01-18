<template lang="pug">
    ContentBody
        template(slot="filter")
            el-form(:inline="true", v-model="form" @submit.prevent.native="filter(form)")
                el-form-item(label="开课时间")
                    el-date-picker(v-model="timeRange" type="daterange")
                el-form-item
                    el-button(type="primary", native-type="submit") 查询
        el-table(:data="tableData" v-loading="loading" border)
            el-table-column(:prop="key", :key="key", :label="value.label || value", v-for="value, key in headerMapping", :show-overflow-tooltip="true", :width="value.width")
                ColumnContent(slot-scope="{row}" :columnDefine="value", :row="row", :value-key="key" :renderContent="value.render")
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
    props: ['p'],
    mixins: [CurdTableMix('$baseTrainRecordService')],
    data() {
        return {
            editForm: {
                id: '',
                employeeId: '',
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
        async initLoad() {
            this.form.teacherName = this.p
            this.form.byTeacher = true
            this.filter(this.form)
        },
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
