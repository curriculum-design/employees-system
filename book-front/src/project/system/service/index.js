import Vue from 'vue'
import BaseService from '@/utils/BaseService'

Vue.config.httpRoot = '/management-system-api/v1/'

Vue.prototype.$api = new Proxy({}, {
    get(target, key) {
        return new BaseService(key.replace(/([A-Z])/g, '-$1').toLowerCase()).proxy()
    }
})

Vue.prototype.$loginService = require('./LoginService.js').default

Vue.prototype.$erpCustomerService = require('./ErpCustomerService.js').default
Vue.prototype.$erpCustomerAttachmentService = require('./ErpCustomerAttachmentService.js').default
Vue.prototype.$erpThirdStorageService = require('./ErpThirdStorageService.js').default
Vue.prototype.$sysInitTemplateService = require('./SysInitTemplateService.js').default
Vue.prototype.$sysUserService = require('./SysUserService.js').default
Vue.prototype.$baseEmployeeService = require('./BaseEmployeeService.js').default
Vue.prototype.$baseTrainRecordService = require('./BaseTrainRecordService.js').default
Vue.prototype.$baseTrainPlanService = require('./BaseTrainPlanService.js').default
