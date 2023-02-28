import pageData from "@/api/constant/page";
import {del, save, index, download} from "@/api/resources/imageManage";
import {exportss} from "@/api/export/index";
import { mixinstest } from '@/mixins/index'

export default {
  name: 'list',
  mixins: [mixinstest],
  //初始化数据
  data() {
    return {
      page: pageData.data(),
      dialogVisible: false,
      mchName: null,
      choseList: [],
      dataForm: {},
      //需要下载的图片
      needImg: null,
      // table高度
      tableHeight: null
    }
  },
  //组装模板
  created() {
    this.loadData()
  },
  mounted() {
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
      await this.getList().then()
    },
    // 获取列表
    async getList() {
      const res = await index({
        api: 'admin.resources.index',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        mchName: this.mchName,
      });
      this.total = res.data.data.total
      this.page.tableData = res.data.data.list
      this.page.loading = false
      if (res.data.data.total < 10) {
        this.current_num = this.total
      }
      if (this.total < this.current_num) {
        this.current_num = this.total
      }
    },

    // 重置
    reset() {
      this.mchName = null
    },
    handleClose() {
      this.dialogVisible = false;
    },

    // 查询
    demand() {
      this.showPagebox = false
      this.page.loading = true
      this.dictionaryNum = 1
      this.loadData().then(() => {
        this.page.loading = false
        if (this.page.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    //选择一页多少条
    handleSizeChange(e) {
      this.page.loading = true
      this.pageSize = e
      this.loadData().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.page.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.page.loading = false
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
    //选中项
    Chose(obj) {
      this.choseList = []
      obj.forEach((item) => {
        this.choseList.push(item.id);
      })
    },
    //批量下载
    async Download() {
      await exportss({
        api: 'admin.resources.downForZip',
        imgIds: this.choseList.toString(),
        exportType: 1
      },'pagegoods')
    },
    //批量删除
    Del() {
      this.$confirm('确定批量删除图片吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        del({
          api: 'admin.resources.del',
          imgIds: this.choseList.toString()
        }).then(res => {
          if(res.data.code == '200') {
            this.demand();
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }

  }

}
