import {save, index} from "@/api/order/orderSet";
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'orderSet',
  //初始化数据
  data() {
    return {
      mainData: {isMany: false},
    }
  },
  //组装模板
  created() {
    this.loadData();
  },

  watch: {
    'mainData.same_piece': {
      handler:function(newVal,oldVal) {
        if(parseInt(newVal) <= 0) {
          this.mainData.same_piece = 1
        } else {
          this.mainData.same_piece = parseInt(newVal)
        }
        console.log(newVal);
      }
    },

    'mainData.same_order': {
      handler:function(newVal,oldVal) {
        if(parseInt(newVal) <= 0) {
          this.mainData.same_order = 1
        } else {
          this.mainData.same_order = parseInt(newVal)
        }
        console.log(newVal);
      }
    },
  },

  mounted() {
  },
  methods: {
    async loadData() {
      await index({
        api: 'admin.orderSet.index',
      }).then(data => {
        if (!isEmpty(data)) {
          data = data.data.data;
          let isMany = false;
          if (data.same_order > 0 || data.same_piece > 0) {
            isMany = true
          }
          data.isMany = isMany;
          this.mainData = data;
        }
      });
    },

    blurPiece() {
      if(!this.mainData.same_piece) {
        this.mainData.same_piece = 1
      }
    },

    blurOrder() {
      if(!this.mainData.same_order) {
        this.mainData.same_order = 1
      }
    },

    async Save() {
      this.mainData.auto_the_goods = Number(this.mainData.auto_the_goods)
      this.mainData.order_failure = Number(this.mainData.order_failure)
      this.mainData.order_after = Number(this.mainData.order_after)
      this.mainData.remind_day = Number(this.mainData.remind_day)
      this.mainData.remind_hour = Number(this.mainData.remind_hour)
      if(this.mainData.auto_the_goods < 1 || this.mainData.auto_the_goods != this.mainData.auto_the_goods.toFixed(0)){
        this.$message({
          message:'自动收货时间必须为正整数',
          type:'warning',
          offset:100
        })
        return
      }
      if(this.mainData.order_failure < 1 || this.mainData.order_failure != this.mainData.order_failure.toFixed(0)){
        this.$message({
          message:'订单失效时间必须为正整数',
          type:'warning',
          offset:100
        })
        return
      }
      if(this.mainData.order_after < 0 || this.mainData.order_after != this.mainData.order_after.toFixed(0)){
        this.$message({
          message:'订单售后时间必须为正整数',
          type:'warning',
          offset:100
        })
        return
      }
      if(this.mainData.same_piece < 1 || this.mainData.same_piece != this.mainData.same_piece.toFixed(0)){
        this.$message({
          message:'同件商品数量必须为正整数',
          type:'warning',
          offset:100
        })
        return
      }
      if(this.mainData.same_order < 1 || this.mainData.same_order != this.mainData.same_order.toFixed(0)){
        this.$message({
          message:'同一订单中，商品数量必须为正整数',
          type:'warning',
          offset:100
        })
        return
      }
      console.dir(this.mainData.remind_day)
      console.dir(this.mainData.remind_hour)
      if(this.mainData.remind_day < 0 || this.mainData.remind_day != this.mainData.remind_day.toFixed(0)){
        this.$message({
          message:'提醒限制的天必须为大于等于零的整数',
          type:'warning',
          offset:100
        })
        return
      }
      if(this.mainData.remind_hour < 0 || this.mainData.remind_hour != this.mainData.remind_hour.toFixed(0)){
        this.$message({
          message:'提醒限制的小时必须为大于等于零的整数',
          type:'warning',
          offset:100
        })
        return
      }
      if(this.mainData.auto_good_comment_day < 0){
         this.$message({
          message:'自动评价设置不能为负数',
          type:'warning',
          offset:100
        })
        return
      }
      
      this.dialogVisible = true;
      let title = '修改成功';
      if (!this.mainData.isMany) {
        this.mainData.same_piece = 0;
        this.mainData.same_order = 0;
      }
      let param = {
        api: 'admin.orderSet.saveConfig',
        packageSettings: this.mainData.isMany ? 1 : 0,
        samePiece: this.mainData.same_piece,
        sameOrder: this.mainData.same_order,
        autoTheGoods: this.mainData.auto_the_goods,
        orderFailure: this.mainData.order_failure,
        orderAfter: this.mainData.order_after,
        remindHour: this.mainData.remind_hour,
        remindDay: this.mainData.remind_day,
        autoGoodCommentDay: this.mainData.auto_good_comment_day,
      }

      await save(param).then(res => {
        if (!isEmpty(res) && res.data.code == '200') {
          this.$message({
            type: 'success',
            message: title
          })
          this.loadData();
        }
        this.dialogVisible = false;
        
      })
    },


  }

}
