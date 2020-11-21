import BaseService from '@/utils/BaseService'
class BaseTrainPlanService extends BaseService {
    constructor() {
        super('base-train-plan')
    }
}
const target = new BaseTrainPlanService()
export default target.proxy()
