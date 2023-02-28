import pageData from '@/api/constant/page'
import {index} from "@/api/data";
import {isEmpty} from "element-ui/src/utils/util";
import source from "@/store/modules/source";
import { mixinstest } from '@/mixins/index'

export default {
  name: 'addvit-report',
  mixins: [mixinstest],
  data() {
    return {
      tbl: "会员消费报表",
      page: pageData.data(),
      source: this.getSource(),
      // table高度
      tableHeight: null
    }
  },
  mounted() {
    this.loadData();
    this.$nextTick(function() {
        this.getHeight()
    })
    window.addEventListener('resize',this.getHeight(),false)
  },
  methods: {
    // 获取table高度
    getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},
    async loadData() {
      const res = await index({
        api: 'admin.data.getUserConsumptionInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        startDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[0],
        endDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[1],
      })
      console.log(source)
      this.total = res.data.data.total
      this.page.tableData = res.data.data.list
      this.page.loading = false
      if(res.data.data.total < 10) {
        this.current_num = this.total
      }
    },
    
    reset() {
      this.page.inputInfo.date = null;
    },
    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      this.page.showPagebox = false
      this.page.loading = true
      this.dictionaryNum = 1
      this.pageSize = 10
      this.loadData().then(() => {
        this.page.loading = false
        if (this.page.tableData.length > 5) {
          this.page.showPagebox = true
        }
      })
    },

    //选择一页多少条
    handleSizeChange(e) {
      console.log(e);
      this.page.loading = true
      // this.current_num = e
      this.pageSize = e
      this.loadData().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.loading = false
      })
    },

    //点击上一页，下一页
    handleCurrentChange(e) {
      this.page.loading = true
      this.dictionaryNum = e
      this.currpage = ((e - 1) * this.pageSize) + 1
      this.loadData().then(() => {
        this.current_num = this.page.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.page.loading = false
      })
    },

  }
}
