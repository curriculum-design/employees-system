<template lang="pug">
    ContentBody
        template(slot="filter")
            el-form(:inline="true", v-model="form" @submit.prevent.native="filter(form)")
                el-form-item(label="预计开课时间")
                    el-date-picker(v-model="form.beginTime" type="month" format="yyyy-MM" value-format="timestamp")
                    el-date-picker(v-model="form.endTime" type="month" format="yyyy-MM" value-format="timestamp")
                el-form-item
                    el-button(type="primary", native-type="submit") 查询
        el-table(:data="tableData" v-loading="loading" border)
            el-table-column(:prop="key", :key="key", :label="value.label || value", v-for="value, key in headerMapping", :show-overflow-tooltip="true", :width="value.width")
                ColumnContent(slot-scope="{row}" :columnDefine="value", :row="row", :value-key="key" :renderContent="value.render")
        template(slot="footer")
            el-pagination(:pageNum.sync="pageNum", :total="total", :pageSize="pageSize",@current-change="pageChangeHandler" @size-change="sizeChangeHandler" layout="total, sizes, prev, pager, next")
        EditForm(:show.sync="editShow", v-if="editShow", v-model="editForm", @submit="submitHandler" v-loading.body="editLoading")
</template>

<script>
import CurdTableMix from '@/utils/mixins/curd-table-mix.js'
import {mapGetters} from 'vuex'
import EditForm from './form'
export default {
    components: {EditForm},
    props: ['p'],
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
        async initLoad() {
            this.form.realName = this.p
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
        ...mapGetters['systemMapping'],
        headerMapping() {
            return {
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
