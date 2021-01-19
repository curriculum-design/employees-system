<template lang="pug">
    div()
        el-form-item(label="员工列表" prop="refEmployeeAssemblyList")
            el-table(:data="form.refEmployeeAssemblyList" border)
                el-table-column(label="员工姓名" prop="realName")
                el-table-column(label="是否在职" prop="onJob")
                el-table-column(label="工种" prop="workType")
                el-table-column(label="机构" prop="org")
                el-table-column(label="部门" prop="dept")
                el-table-column(label="岗位" prop="jobName")
                el-table-column(label="入职时间" width="100px")
                    template(slot-scope="scope") {{scope.row.joinTime ? $format.date(scope.row.joinTime, "yyyy-mm-dd") : ''}}
        div
            el-dialog(title="员工选择" :visible.sync="dialogVisible" width="700px" :close-on-click-modal="false", :append-to-body="true" @open="openDialog")
                el-select(v-model="state" multiple filterable remote reserve-keyword placeholder="请输入关键词" :remote-method="querySearchAsync" @change="handleChange")
                    el-option(v-for="item in employees" :key="item.id" :label="item.realName" :value="item.id")
                el-table(:data="tableData" border)
                    el-table-column(label="员工姓名" prop="realName")
                    el-table-column(label="是否在职" prop="onJob")
                    el-table-column(label="工种" prop="workType")
                    el-table-column(label="机构" prop="org")
                    el-table-column(label="部门" prop="dept")
                    el-table-column(label="岗位" prop="jobName")
                    el-table-column(label="入职时间" width="100px")
                        template(slot-scope="scope") {{scope.row.joinTime ? $format.date(scope.row.joinTime, "yyyy-mm-dd") : ''}}
                    el-table-column(width="60px")
                        template(slot-scope="scope")
                            .icon-btn.el-icon-delete.danger(type="danger", @click="tableData.splice(scope.$index, 1)")
                .form-action.margin-top-20
                    el-button(type="primary", @click="closeDialog()") 取消
                    el-button(type="success", @click="selectEmployee") 保存
</template>

<script>
import formDialogMix from '@/utils/mixins/form-dialog-mix'
import refEmployeeFromList from './ref-employee-form2'

export default {
    components: {
        refEmployeeFromList
    },
    mixins: [formDialogMix],
    props: {},
    data() {
        return {
            dialogVisible: false,
            state: '',
            employees: [],
            tableData: []
        }
    },
    methods: {
        openDialog() {
            let employees = this.form.refEmployeeAssemblyList
            if (employees) {
                this.tableData = []
                this.form.refEmployeeAssemblyList.forEach(v => {
                    this.tableData.push(v)
                })
            }
        },
        selectEmployee() {
            if (this.tableData.length === 0) {
                this.$message.error('请先筛选你要选择的员工')
                return
            }
            this.closeDialog()
            this.form.refEmployeeAssemblyList = []
            this.tableData.forEach(v => {
                this.form.refEmployeeAssemblyList.push(v)
            })
        },
        handleChange(item) {
            if (item) {
                item.forEach(v => {
                    let value = this.employees.filter(i => i.id === v).pop()
                    let tableValue = this.tableData.filter(i => i.id === v).pop()
                    if (!tableValue) {
                        this.tableData.push(value)
                    }
                })
            }
        },
        async querySearchAsync(queryString, cb) {
            // 搜索人员
            this.employees = await this.$baseEmployeeService.search({q: queryString}, {method: 'get'})
        },
        closeDialog() {
            this.dialogVisible = false
        },
        selectAttachment() {
            this.dialogVisible = true
        },
        addAttachment() {
            this.form.refEmployeeAssemblyList.push({})
        },
        removeAttachment(row) {
            this.form.refEmployeeAssemblyList.splice(this.form.refEmployeeAssemblyList.indexOf(row), 1)
        },
        uploadAfterHandler(row) {
            this.$set(row, 'attachmentUrl', row.uploadResult[0])
            delete row.uploadResult
        }
    }
}
</script>
<style lang="less" rel="stylesheet/less">
.back {
    background-color: white;
}

.dialog {
    margin: -30px 15px 10px 15px;
}
</style>
