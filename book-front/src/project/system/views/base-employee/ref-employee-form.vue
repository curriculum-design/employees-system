<template lang="pug">
    ContentBody(title="")
        LocalTable.mini-table(:data="tableData" ref="multipleTable", :pagination="true" height="400")
            el-table-column(type="selection" width="55")
            el-table-column(:prop="key", :key="key", :label="value.label || value", v-for="value, key in headerMapping", :show-overflow-tooltip="true", :width="value.width")
                ColumnContent(slot-scope="{row}" :columnDefine="value", :row="row", :value-key="key" :renderContent="value.render")
        .form-action.margin-top-20
            ImportButtonWithDialog(@inputData="inputData", :saveApi="checkSelect", :options="importOptions")
            el-button(type="primary" @click="selectGoods") 确定
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
        selectGoods() {
            this.$emit('closeDialog')
            this.goodsList2 = this.$refs.multipleTable.tableComponent.selection
            for (let i = 0; i < this.goodsList2.length; i++) {
                let obj = {
                    childrenGoodsId: this.goodsList2[i].id,
                    createTime: this.goodsList2[i].createTime,
                    goodsId: this.goodsList2[i].goodsId,
                    goodsName: this.goodsList2[i].goodsName,
                    goodsNo: this.goodsList2[i].goodsNo,
                    goodsSpec: this.goodsList2[i].goodsSpec,
                    goodsUnit: this.goodsList2[i].goodsUnit,
                    status: this.goodsList2[i].status,
                    updateTime: this.goodsList2[i].updateTime
                }
                // console.log(obj)
                // 判断是否存在  如果存在  则不增加
                let goodsIds = this.dataForm.refGoodsAssemblyList.map(o => o.childrenGoodsId).filter(o => !!o)
                if (!goodsIds.includes(obj.childrenGoodsId)) {
                    this.dataForm.refGoodsAssemblyList.push(obj)
                }
            }
        },
        handleClose() {
            this.$confirm('确认关闭？')
                .then(_ => {
                    this.$emit('closeDialog')
                    this.toggleSelection()
                })
                .catch(_ => {})
        },
        importObj(data) {
            data.parentId = this.parentId
            return this.api.postJson('import-assembly', data)
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
.refGoodsListShow .el-dialog__body {
    padding-top: 0;
}
.body{
    padding: 10px 20px;
}
</style>

