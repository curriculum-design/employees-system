import Vue from 'vue'
let vm = new Vue()
let rules = [{
    name: 'numberic',
    reg: /^[-+]?[0-9]+$/,
    msg: '只允许输入数字'
}, {
    name: 'alphanumeric',
    reg: /^[A-Za-z0-9]+$/,
    msg: '只允许输入字母和数字'
}, {
    name: 'money',
    reg: /^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$/,
    msg: '金额输入不正确'
}, {
    name: 'positiveint',
    reg: /^\d+$/,
    msg: '正整数输入不正确'
}, {
    name: 'negativeint',
    reg: /^-[1-9]\d*|0$/,
    msg: '负整数输入不正确'
}, {
    name: 'url',
    reg: /^(http:\/\/|https:\/\/)(.{4,})$/,
    msg: 'URL格式不正确'
}, {
    name: 'email',
    reg: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    msg: 'email输入不正确'
}, {
    name: 'mobile',
    reg: /^\d{11}$/,
    msg: '手机输入不正确'
}, {
    name: 'phone',
    reg: /^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/,
    msg: '电话号码输入不正确'
}, {
    name: 'chinese',
    reg: /^[\u4e00-\u9fa5],{0,}$/,
    msg: '只允许输入汉字'
}, {
    name: 'idcard',
    reg: /^\d{15}|\d{18}$/,
    msg: '身份证号码输入不正确'
}, {
    name: 'htmltag',
    reg: /<(\S*?)[^>]*>.*?<\/\1>|<.*? \/>/,
    msg: 'HTML标记'
}, {
    name: 'qq',
    reg: /[1-9][0-9]{4,}/,
    msg: 'qq输入不正确'
}, {
    name: 'ip',
    reg: /((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))/,
    msg: 'ip地址输入不正确'
}, {
    name: 'postcode',
    reg: /[1-9]\d{5}(?!\d)/,
    msg: '邮政编码输入不正确'
}, {
    name: 'blank', // 空白行
    reg: /\n\s*\r/,
    msg: '不能有空白行'
}, {
    name: 'bankcode', // 空白行
    reg: /^(\d{16}|\d{19})$/,
    msg: '银行卡号填写不正确'
}, {
    name: 'imei', // 空白行
    reg: /^[0-9]{15}/,
    msg: 'imei格式错误'
}, {
    name: 'verify', // 空白行
    reg: /^\d{6}$/,
    msg: '验证码格式错误'
}, {
    name: 'empty', // 空白行
    reg: /\S/,
    msg: '不能为空'
}, {
    name: 'yifubao', // 易付宝
    reg: /^(\d{11}|\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)$/,
    msg: '易付宝账号格式错误'
}]

Vue.prototype.$validate = (name, params, msg) => {
    let rule = rules.filter(function(item) {
        return item.name === name
    })
    if (!rule[0].reg.test(params)) {
        vm.$toast(msg || rule[0].msg)
        return false
    }
    return true
}
