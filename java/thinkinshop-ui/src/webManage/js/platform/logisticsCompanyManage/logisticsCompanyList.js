import {index, del, save} from '@/api/Platform/logisticsCompanyManage'
import pageData from '@/api/constant/page'
import {isEmpty} from "element-ui/src/utils/util";
import { mixinstest } from '@/mixins/index'

export default {
  name: 'logisticsCompanyList',
  mixins: [mixinstest],
  //初始化数据
  data() {
    return {
      page: pageData.data(), 
      sortType: 1,
      switchFlag: false,
      // table高度
      tableHeight: null,

      is_disabled: true
    }
  },
  //组装模板
  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    this.loadData()
  },

  mounted() {
    this.$nextTick(function() {
      this.getHeight()
    })
    window.addEventListener('resize',this.getHeight(),false)

  },

  methods: {
    getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},

    async loadData() {
      const res = await index({
        api: 'admin.express.index',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        keyWord: this.page.inputInfo.name,
        sortType: this.sortType
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
    //排序
    sortChange(obj) {
      this.sortType = 0;
      if (obj.order === 'descending') {
        this.sortType = 1;
      }
      this.demand()
    },
    // 重置
    reset() {
      this.page.inputInfo.name = ''
    },

    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
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
    handleSelectionChange(val) {
      if(val.length==0){
        this.is_disabled = true
      }else{
        this.is_disabled = false
      }
      this.idList = val.map(item => {
        return item.id
      })
    },
    //开关
    async Switch(id) {
      const res = await index({
        api: 'admin.express.expressSwitch',
        id: id
      }).then(data => {
        if(data.data.code == '200') {
          this.$message({
            type: 'success',
            message: '成功!'
          })
          this.loadData();
        }
      });
    },
    //修改序号
    async Sort(obj) {
      await save({
        api: 'admin.express.expressSave',
        id: obj.id,
        sort: obj.sort,
        name: obj.kuaidi_name,
        code: obj.type,
      }).then(res => {
        this.demand();
      })
    },
    //添加/编辑
    Save(id) {
      let routeName = "logisticsCompanyEdit";
      if (isEmpty(id)) {
        routeName = "logisticsCompanySave";
      }
      this.$router.push({
        name: routeName,
        params: {id: id},
        query: {
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      })
    },

    // 删除
    Delete(id) {
      let num = 1;
      let paramIds = id;
      if (isEmpty(id)) {
        num = this.idList.length;
        paramIds = this.idList.toString();
      }
      this.$confirm('确认是否删除', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        del({
          api: 'admin.express.expressDel',
          ids: paramIds
        }).then(res => {
          if(res.data.code == '200') {
            this.loadData();
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
