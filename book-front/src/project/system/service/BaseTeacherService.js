import BaseService from '@/utils/BaseService'
class BaseTeacherService extends BaseService {
    constructor() {
        super('base-teacher')
    }
}
const target = new BaseTeacherService()
export default target.proxy()
