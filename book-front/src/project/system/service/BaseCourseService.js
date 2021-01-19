import BaseService from '@/utils/BaseService'
class BaseCourseService extends BaseService {
    constructor() {
        super('base-course')
    }
}
const target = new BaseCourseService()
export default target.proxy()
