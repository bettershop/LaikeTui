import {index} from '@/api/mall/systemNotice/notice'

export default {
  name: 'noticeDetail',

  //初始化数据
  data() {
    return {
      notice: {},
    }
  },
  //组装模板
  created() {
    this.loadData()
  },

  methods: {
    async loadData() {
      console.log(this.$route.query.id)
      const res = await index({
        api: 'saas.sysNotice.getSysNoticeInfo',
        id: this.$route.query.id,
      }).then(data => {
        this.notice = data.data.data.list[0];
      });
    }


  }

}
