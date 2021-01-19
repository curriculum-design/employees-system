<template lang="pug">
    el-dialog(title="编辑", :visible.sync="dialogShow", :close-on-click-modal="false", :append-to-body="true")
        el-form(v-model="form" @submit.prevent.native="submitHandler()" label-width="120px")
            el-form-item(label="课程编号")
                el-input(v-model="form.courseNo")
            el-form-item(label="课程名称")
                el-input(v-model="form.courseName")
            el-form-item(label="培训形式")
                el-input(v-model="form.trainStyle")
            el-form-item(label="开设部门/机构")
                el-input(v-model="form.makeCourse")
            el-form-item(label="开课时间")
                el-date-picker(v-model="form.beginTime" format="yyyy-MM-dd" value-format="timestamp")
            el-form-item(label="完成时间")
                el-date-picker(v-model="form.endTime" format="yyyy-MM-dd" value-format="timestamp")
            el-form-item(label="课时")
                el-input-number(v-model="form.classHour" :min="0.1" :step="0.1")
            el-form-item(label="讲师")
                el-input(v-model="form.teacherName")
            el-form-item(label="课程满意度")
                el-input(v-model="form.rate")
            el-form-item.form-action.margin-top-20
                el-button(type="primary", @click="dialogShow = false") 取消
                el-button(type="success" native-type="submit") 保存
</template>

<script>
import formDialogMix from '@/utils/mixins/form-dialog-mix'
import {mapGetters} from 'vuex'
export default {
    mixins: [formDialogMix],
    computed: {
        ...mapGetters['systemMapping']
    }
}
</script>
<style lang="less" rel="stylesheet/less">
</style>
