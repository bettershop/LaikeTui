import {save, index} from "@/api/order/orderSettlement";
import {isEmpty} from "element-ui/src/utils/util";
import {del} from "@/api/order/comment";

export default {
  name: 'orderSettlementDetail',
  //初始化数据
  data() {
    return {
      mainData: {},
      orderInfo: {},
      goodsList: [],
      source: this.getSource(),
    }
  },
  //组装模板
  created() {
    this.loadData();
  },
  mounted() {
  },
  methods: {
    async loadData() {
      await index({
        api: 'admin.orderSettlement.detail',
        orderNo: this.$route.params.orderNo,
      }).then(data => {
        if (!isEmpty(data)) {
          this.orderInfo = data.data.data;
          this.mainData = this.orderInfo.data;
          this.goodsList = this.orderInfo.detail;
        }
      });
    },


  }

}
