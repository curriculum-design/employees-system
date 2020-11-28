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
                el-table-column(label="入职时间" prop="joinTime")
        div()
            el-dialog(title="员工选择" :visible.sync="dialogVisible" width="620px" :close-on-click-modal="false", :append-to-body="true")
                refEmployeeFromList(v-model="form" @closeDialog="closeDialog" class="dialog")


</template>

<script>
import formDialogMix from '@/utils/mixins/form-dialog-mix'
import refEmployeeFromList from './ref-employee-form2'
export default {
    components: {
        refEmployeeFromList
    },
    mixins: [formDialogMix],
    props: {
        'goodsList': {
            type: Array,
            default: function() {
                return []
            }
        },
        'disabled':
            {default: false},
        'requiredAttachments':
            {default: false},
        'disableItem': {
            type: Array,
            default: function() {
                return []
            }
        },
        'enableItem': {
            type: Array,
            default: function() {
                return []
            }
        }
    },
    data() {
        return {
            dialogVisible: false,
            goodsList2: null,
            mygoodsList: null,
        }
    },
    methods: {
        closeDialog() {
            this.dialogVisible = false
        },
        changeEmployee(row) {
            let goods = this.goodsList.filter(o => o.id === row.childrenEmployeeId).pop()
            row.goodsName = goods.goodsName
            row.goodsSpec = goods.goodsSpec
            row.goodsUnit = goods.goodsUnit
        },
        selectAttachment() {
            this.dialogVisible = true
        },
        addAttachment() {
            this.form.refEmployeeAssemblyList.push({})
            // this.dialogVisible = true
            // console.log(this.form)
        },
        removeAttachment(row) {
            this.form.refEmployeeAssemblyList.splice(this.form.refEmployeeAssemblyList.indexOf(row), 1)
        },
        uploadAfterHandler(row) {
            this.$set(row, 'attachmentUrl', row.uploadResult[0])
            delete row.uploadResult
        },
        async check() {
            let _this = this
            return new Promise(function(resolve, reject) {
                let errorList = _this.form.refEmployeeAssemblyList
                    .filter(o => _this.required(o.childrenEmployeeId, 'childrenEmployeeId'))
                if (errorList.length > 0) {
                    _this.$message.error('物料不能为空')
                    reject()
                } else {
                    resolve()
                }
                errorList = _this.form.refEmployeeAssemblyList
                    .filter(o => _this.required(o.num, 'num'))
                if (errorList.length > 0) {
                    _this.$message.error('数量不能为空')
                    reject()
                } else {
                    resolve()
                }
            })
        },
        required(v, key) {
            if ((v == null || v === '') && !this.disableItem.includes(key)) {
                return true
            }
            return false
        }
    }
}
</script>
<style lang="less" rel="stylesheet/less">
.back{
    background-color: white;
}
.dialog{
    margin: -30px 15px 10px 15px;
}
</style>
