import { classIndex, classMove, classSwitch, classTop } from '@/api/plug_ins/template'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'classification',
  mixins: [mixinstest],
  data() {
    return {
      radio1:'分类管理',

      tableData: [],
      loading: true,
      // table高度
      tableHeight: null
    }
  },

  created() {
    this.classIndexs()
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
    async classIndexs() {
      const res = await classIndex({
        api: 'admin.diy.classIndex',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
      })
      this.current_num = 10
      this.total = res.data.data.total
      this.tableData = res.data.data.list
      this.loading = false
      if(this.total < this.current_num) {
          this.current_num = this.total
      }
      console.log(res);
    },

    // 是否显示
    switchs(value) {
      classSwitch({
        api: 'admin.diy.classSwitch',
        id: value.cid
      }).then(res => {
        if(res.data.code == '200') {
          this.classIndexs()
          console.log(res);
          this.$message({
            type: 'success',
            message: '成功!',
            offset: 100
          });
        }
      })
    },

    // 移动
    moveUp(value) {
      if(value !== 0) {
        classMove({
          api: 'admin.diy.classMove',
          id: this.tableData[value - 1].cid,
          id1: this.tableData[value].cid
        }).then(res => {
          if(res.data.code == '200') {
            this.classIndexs()
            console.log(res);
            this.$message({
              type: 'success',
              message: '操作成功!',
              offset: 100
            });
          }
        })
      } else {
        classMove({
          api: 'admin.diy.classMove',
          id: this.tableData[value + 1].cid,
          id1: this.tableData[value].cid
        }).then(res => {
          if(res.data.code == '200') {
            this.classIndexs()
            console.log(res);
            this.$message({
              type: 'success',
              message: '操作成功!',
              offset: 100
            });
          }
        })
      }
    },

    // 置顶
    placedTop(value) {
      classTop({
        api: 'admin.diy.classTop',
        id: value.cid
      }).then(res => {
          console.log(res);
          if(res.data.code == '200') {
              this.classIndexs()
              this.$message({
                  type: 'success',
                  message: '置顶成功!',
                  offset: 100
              })
          }
      })
    },

    //选择一页多少条
    handleSizeChange(e){
      this.loading = true
      console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.classIndexs().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.loading = false
      })
    },

    //点击上一页，下一页
    handleCurrentChange(e){
      this.loading = true
      this.dictionaryNum = e
      this.currpage = ((e - 1) * this.pageSize) + 1
      this.classIndexs().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
    },
  }
}