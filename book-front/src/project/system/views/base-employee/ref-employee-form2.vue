<template lang="pug">
    ContentBody(title="")
        LocalTable2.mini-table(:data="tableData" ref="multipleTable", :pagination="true" height="400")
            el-table-column(:prop="key", :key="key", :label="value.label || value", v-for="value, key in headerMapping", :show-overflow-tooltip="true", :width="value.width")
                ColumnContent(slot-scope="{row}" :columnDefine="value", :row="row", :value-key="key" :renderContent="value.render")
        .form-action.margin-top-20
            el-button(type="primary" @click="selectEmployee") 确定
            el-button(@click="handleClose()" ) 取消
</template>

<script>
import CurdTableMix from '@/utils/mixins/curd-table-mix.js'
import {mapGetters} from 'vuex'
export default {
    props: ['show', 'value'],
    components: {},
    mixins: [CurdTableMix('$baseEmployeeService')],
    data() {
        return {
            form: {
                related: 0
            },
        }
    },
    methods: {
        async checkSelect(data) {
            this.tableData.filter(d => d.employeeCode === data.employeeCode).map(row => {
                this.$refs.multipleTable.tableComponent.toggleRowSelection(row)
            })
            return {}
        },
        async initLoad() {
            // this.$refs.multipleTable.sizeChangeHandler(20)
            this.tableData = await this.$baseEmployeeService.all({}, {method: 'get'})
        },
        selectEmployee() {
            const filterData = this.$refs.multipleTable.filterData
            if (filterData.length === this.tableData.length) {
                this.$message.error('请先筛选你要选择的员工')
                return
            }
            this.$emit('closeDialog')
            this.dataForm.refEmployeeAssemblyList = this.$refs.multipleTable.filterData
        },
        handleClose() {
            this.$confirm('确认关闭？')
                .then(_ => {
                    this.$emit('closeDialog')
                })
                .catch(_ => {})
        },
    },
    computed: {
        dataForm: {
            get() {
                return this.value
            },
            set(val) {
                this.$emit('input', val)
            },
        },
        ...mapGetters['systemMapping'],
        headerMapping() {
            return {
                employeeCode: {
                    label: '工号',
                },
                realName: {
                    label: '员工姓名',
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
                headerMapping: this.headerMapping
            }
        },
        importOptions() {
            return {
                headerMapping: {
                    '人员编码': 'employeeCode',
                }
            }
        }
    },
}
</script>
<style lang="less" rel="stylesheet/less">
.refEmployeeListShow .el-dialog__body {
    padding-top: 0;
}
.body{
    padding: 10px 20px;
}
</style>

