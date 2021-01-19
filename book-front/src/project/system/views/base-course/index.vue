<template lang="pug">
    ContentBody(title="课程信息")
        template(slot="filter")
            el-form(:inline="true", v-model="form" @submit.prevent.native="filter(form)")
                el-form-item(label="课程编号")
                    el-input(v-model="form.courseNo")
                el-form-item(label="课程名称")
                    el-input(v-model="form.courseName")
                el-form-item(label="培训形式")
                    el-input(v-model="form.trainStyle")
                el-form-item(label="开设部门/机构")
                    el-input(v-model="form.makeCourse")
                el-form-item(label="开课时间")
                    el-input(v-model="form.beginTime")
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
    mixins: [CurdTableMix('$baseCourseService')],
    data() {
        return {
            editForm: {
                id: '',
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
                    label: '开课时间'
                },
                endTime: {
                    label: '完成时间'
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
                    '课程编号': 'courseNo',
                    '课程名称': 'courseName',
                    '培训形式': 'trainStyle',
                    '开设部门/机构': 'makeCourse',
                    '开课时间': 'beginTime',
                    '完成时间': 'endTime',
                    '课时': 'classHour',
                    '讲师': 'teacherName',
                    '课程满意度': 'rate',
                }
            }
        }
    },
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
