import BaseService from '@/utils/BaseService'
class BaseTrainRecordService extends BaseService {
    constructor() {
        super('base-train-record')
    }
}
const target = new BaseTrainRecordService()
export default target.proxy()
