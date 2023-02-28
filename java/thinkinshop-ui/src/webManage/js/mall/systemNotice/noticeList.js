import {index} from '@/api/mall/systemNotice/notice'

export default {
  name: 'noticeList',

  //初始化数据
  data() {
    return {
      noticeList: {},
    }
  },
  //组装模板
  created() {
    this.loadData()
  },

  methods: {
    async loadData() {
      const res = await index({
        api: 'saas.sysNotice.getSysNoticeInfo',
        pageNo: 1,
        pageSize: 999,
      }).then(data => {
        this.noticeList = data.data.data.list;
      });
    },
    detail(id) {
      this.$router.push({path: '/mall/systemNotice/noticeDetail', query: {id: id}})
    }


  }

}
