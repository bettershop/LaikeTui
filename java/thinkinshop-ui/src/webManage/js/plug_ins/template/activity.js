import { activityList, activityMove, activityDel, activitySwitch } from '@/api/plug_ins/template'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'activity',
  mixins: [mixinstest],
  data() {
    return {
      radio1:'活动管理',

      tableData: [],
      loading: true,
      // table高度
      tableHeight: null
    }
  },

  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    this.activityLists()
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
    async activityLists() {
      const res = await activityList({
        api: 'admin.diy.activityList',
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
      activitySwitch({
        api: 'admin.diy.activitySwitch',
        id: value.id
      }).then(res => {
        if(res.data.code == '200') {
          this.activityLists()
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
        activityMove({
          api: 'admin.diy.activityMove',
          moveId: this.tableData[value - 1].id,
          moveId2: this.tableData[value].id
        }).then(res => {
          if(res.data.code == '200') {
            this.activityLists()
            console.log(res);
            this.$message({
              type: 'success',
              message: '操作成功!',
              offset: 100
            });
          }
        })
      } else {
        activityMove({
          api: 'admin.diy.activityMove',
          moveId: this.tableData[value + 1].id,
          moveId2: this.tableData[value].id
        }).then(res => {
          if(res.data.code == '200') {
            this.activityLists()
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

    // 编辑
    Edit(value) {
      this.$router.push({
        path: '/plug_ins/template/editorActivity',
        query: {
          id: value.id,
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      })
    },

    // 删除
    Delete(value) {
      this.$confirm('确定要删除此活动吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          activityDel({
              api: 'admin.diy.activityDel',
              id: value.id
          }).then(res => {
              console.log(res);
              if(res.data.code == '200') {
                  this.activityLists()
                  this.$message({
                    type: 'success',
                    message: '删除成功!',
                    offset: 100
                  })
              }
          })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除',
          offset: 100
        });          
      });
    },

    // 活动商品
    Activity(value) {
      this.$router.push({
        path: '/plug_ins/template/activityGoods',
        query: {
          id: value.id
        }
      })
    },

    //选择一页多少条
    handleSizeChange(e){
      this.loading = true
      console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.activityLists().then(() => {
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
      this.activityLists().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
    },
  }
}