<template lang="pug">
    ContentBody(title="讲师列表")
        template(slot="filter")
            el-form(:inline="true", v-model="form" @submit.prevent.native="filter(form)")
                el-form-item(label="讲师姓名")
                    el-input(v-model="form.teacherName")
                el-form-item(label="类型")
                    el-input(v-model="form.workType")
                el-form-item(label="部门")
                    el-input(v-model="form.dept")
                el-form-item(label="机构")
                    el-input(v-model="form.org")
                el-form-item(label="岗位")
                    el-input(v-model="form.jobName")
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
    mixins: [CurdTableMix('$baseTeacherService')],
    data() {
        return {
            editForm: {
                id: '',
                teacherName: '',
                workType: '',
                org: '',
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
                teacherName: {
                    label: '讲师姓名'
                },
                workType: {
                    label: '类型'
                },
                org: {
                    label: '机构'
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
                speakCourse: {
                    label: '主讲课程',
                },
                teamWorkTime: {
                    label: '认证/合作时间',
                    format: (t) => this.$format.date(t, 'yyyy-mm-dd'),
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
                    '讲师姓名': 'teacherName',
                    '类型': 'workType',
                    '主讲课程': 'speakCourse',
                    '认证/合作时间': 'teamWorkTime',
                }
            }
        }
    },
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
