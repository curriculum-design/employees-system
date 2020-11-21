import BaseService from '@/utils/BaseService'
class BaseEmployeeService extends BaseService {
    constructor() {
        super('base-employee')
    }
}
const target = new BaseEmployeeService()
export default target.proxy()
