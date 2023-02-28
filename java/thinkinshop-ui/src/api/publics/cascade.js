import request from '@/api/https'
import { isEmpty } from 'element-ui/src/utils/util'

/**
 * 级联公共方法
 */
export default {
  name: 'cascade',

  //初始化数据
  data() {
  },
  //组装模板
  created() {
  },

  // 获取国家列表
  getCountrys() {
    return request({
      method: 'post',
      params: {
        api: 'admin.goods.getCountry'
      }
    })
  },
  // 获取省级 2=省 3=市 4=区
  async getSheng(level, sid) {
    if (isEmpty(level)) {
      level = 2
    }
    return request({
      method: 'post',
      params: {
        api: 'admin.goods.getRegion',
        level: level,
        sid: sid
      }
    })
  },
  // 获取市级
  async getShi(sid) {
    return this.getSheng(3, sid)
  },
  // 获取县级
  async getXian(sid) {
    return this.getSheng(4, sid)
  }

}
