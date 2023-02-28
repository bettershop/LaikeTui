import { getCouponCardInfo, getAssignGoods, getAssignGoodsClass, addCoupon } from '@/api/plug_ins/coupons'
import { goodsStatus } from '@/api/members/membersList'
import { getTime } from '@/utils/utils'
import treeTransfer from 'el-tree-transfer'
export default {
  name: 'editorCoupons',

  components: {
    treeTransfer
  },
  data() {
    return {
      ruleForm: {
        coupons_type: '',
        coupons_name: '',
        grade: '',
        type: 1,
        issue_number: '',
        issue_discount: '',
        face_value: '',
        consumption_threshold: '',
        available_range: 1,
        date: '',
        coupons_time: '',
        instructions: '',
        select_goods: [],
        limit_count:0,
      },

      defaultProps: {
        children: 'child',
        label: 'cname'
      },

      couponsTypeList: [
        {
          value: 1,
          label: '免邮券'
        },
        {
          value: 2,
          label: '满减券'
        },
        {
          value: 3,
          label: '折扣券'
        },
        {
          value: 4,
          label: '会员赠送'
        }
      ],// 优惠券类型

      membersGrade: [],// 会员等级

      typeList: [
        {
          value: 1,
          name: '满减'
        },
        {
          value: 0,
          name: '折扣'
        },
      ],

      availableRangeList: [
        {
          value: 1,
          name: '全部商品'
        },
        {
          value: 2,
          name: '指定商品'
        },
        {
          value: 3,
          name: '指定分类'
        }
      ],
      classIdList: [],

      goodsList: [], // 商品列表

      // 弹框数据
      dialogVisible: false,
      title: ['选择分类', '已选'],
      mode: "transfer",
      fromData: [],
      toData: [],
      limit_type:0,
    }
  },

  created() {
    this.goodsStatus()
    this.getAssignGoods()
    this.getAssignGoodsClass()
    this.getCouponCardInfos()
    this.getCouponConfigInfo()
  },

  watch: {
    'toData': {
      handler: function () {
        this.classIdList = []
        this.getClassId(this.toData)
      }
    }
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'couponsList') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },


  methods: {
    async getCouponCardInfos() {
      const res = await getCouponCardInfo({
        api: 'admin.coupon.getCouponCardInfo',
        mchId: 0,
        hid: this.$route.query.id,
      })
      let couponsInfo = res.data.data.list[0]
      // for (const item of this.couponsTypeList) {
      //   if (item.label === couponsInfo.activity_type) {
      //     this.ruleForm.coupons_type = item.value
      //     break
      //   }
      // }

      if(couponsInfo.activity_type == '会员赠券') {
        this.ruleForm.coupons_type = 4
      } else if(couponsInfo.activity_type == '免邮券') {
        this.ruleForm.coupons_type = 1
      }else if(couponsInfo.activity_type == '满减卷') {
        this.ruleForm.coupons_type = 2
      } else {
        this.ruleForm.coupons_type = 3
      }

      this.ruleForm.coupons_time = couponsInfo.day

      this.ruleForm.grade = couponsInfo.grade_id
      this.ruleForm.limit_count = couponsInfo.receive
      this.ruleForm.coupons_name = couponsInfo.name
      this.ruleForm.issue_number = couponsInfo.circulation
      this.ruleForm.face_value = couponsInfo.money
      this.ruleForm.issue_discount = couponsInfo.discount
      this.ruleForm.consumption_threshold = couponsInfo.z_money
      for (const item of this.availableRangeList) {
        if (item.name === couponsInfo.type) {
          this.ruleForm.available_range = item.value
          break
        }
      }
      if (couponsInfo.goodsIdList) {
        this.ruleForm.select_goods = couponsInfo.goodsIdList.map(item => {
          return item.id
        })
      }
      if (couponsInfo.classIdList) {
        this.toData = couponsInfo.classIdList.map(item => {
          return {
            child: [],
            cid: item.cid,
            cname: item.pname,
            level: item.level,
            sid: item.sid
          }
        })
      }
      this.ruleForm.date = [getTime(couponsInfo.start_time), getTime(couponsInfo.end_time)]
      this.ruleForm.instructions = couponsInfo.Instructions
      console.log(res);
    },

     // 获取会员等级列表
     async goodsStatus() {
      const res = await goodsStatus({
          api: 'admin.user.goodsStatus'
      })

      console.log(res);
      let levelList = res.data.data.map(item => {
          return {
              value: item.id,
              label: item.name
          }
      })

      this.membersGrade = levelList
    },

    async getAssignGoods() {
      const res = await getAssignGoods({
        api: 'admin.coupon.getAssignGoods',
        pageSize:9999,
      })
      this.goodsList = res.data.data.list
    },

    async getCouponConfigInfo() {
      const res = await getAssignGoods({
        api: 'admin.coupon.getCouponConfigInfo',
      })
      this.limit_type = res.data.data.data.limit_type
    },

    async getAssignGoodsClass() {
      const res = await getAssignGoodsClass({
        api: 'admin.coupon.getAssignGoodsClass'
      })
      this.fromData = this.recursionNodes(res.data.data.list)
    },

    recursionNodes(childNodes) {
      const nodes = childNodes
      nodes.map((item, index) => {
        if (item.child && item.level > 1) {
          item.level = item.level + '-' + (index + 1)
        }
        if (item.child) {
          this.recursionNodes(item.child)
        }
      })
      return nodes
    },

    getClassId(childNodes) {
      const nodes = childNodes
      for (const item of nodes) {
        this.classIdList.push(item.cid)
        if (item.child) {
          this.getClassId(item.child)
        }
      }
    },

    exgNumber(){
      this.ruleForm.limit_count = Number(this.ruleForm.limit_count)
        if(this.ruleForm.limit_count != this.ruleForm.limit_count.toFixed(0)){
          this.$message({
            message:'领取限制不能为小数',
            type:'warning',
            offset:100
          })
        }
    },


    // 切换模式 现有树形穿梭框模式transfer 和通讯录模式addressList
    changeMode() {
      if (this.mode == "transfer") {
        this.mode = "addressList";
      } else {
        this.mode = "transfer";
      }
    },
    // 监听穿梭框组件添加
    add(fromData, toData, obj) {
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log("fromData:", fromData);
      console.log("toData:", toData);
      console.log("obj:", obj);
    },
    // 监听穿梭框组件移除
    remove(fromData, toData, obj) {
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log("fromData:", fromData);
      console.log("toData:", toData);
      console.log("obj:", obj);
    },

    // 弹框方法
    dialogShow() {
      this.dialogVisible = true
    },

    handleClose(done) {
      this.dialogVisible = false
      this.getAssignGoodsClass()
    },

    remove(node, data) {
      const parent = node.parent;
      const children = parent.data.children || parent.data;
      const index = children.findIndex(d => d.cid === data.cid);
      children.splice(index, 1);
    },

    submitForm() {
      this.ruleForm.limit_count = Number(this.ruleForm.limit_count)
        if(this.ruleForm.limit_count != this.ruleForm.limit_count.toFixed(0)){
          return this.ruleForm.limit_count = this.ruleForm.limit_count.toFixed(0)
        }
      if(this.ruleForm.type == 0){
        this.ruleForm.face_value = ''
      }else{
        this.ruleForm.issue_discount = ''
      }
      if (this.ruleForm.coupons_type !== 4) {
        addCoupon({
          api: 'admin.coupon.addCoupon',
          mchId: 0,
          id: this.$route.query.id,
          activityType: this.ruleForm.coupons_type,
          name: this.ruleForm.coupons_name,
          circulation: this.ruleForm.issue_number,
          money: this.ruleForm.face_value,
          discount:this.ruleForm.issue_discount,
          zmoney: this.ruleForm.consumption_threshold,
          type: this.ruleForm.available_range,
          menuList: this.ruleForm.available_range == 2 ? this.ruleForm.select_goods.join(',') : null,
          classList: this.ruleForm.available_range == 3 ? this.classIdList.join(',') : null,
          startTime: this.ruleForm.date[0],
          endTime: this.ruleForm.date[1],
          instructions: this.ruleForm.instructions,
          limitCount:this.ruleForm.limit_count
        }).then(res => {
          if (res.data.code == '200') {
            this.$message({
              message: '编辑成功',
              type: 'success',
              offset: 100
            })
            console.log(res);
            this.$router.go(-1)
          }
        });
      } else {
        addCoupon({
          api: 'admin.coupon.addCoupon',
          mchId: 0,
          id: this.$route.query.id,
          activityType: this.ruleForm.coupons_type,
          name: this.ruleForm.coupons_name,
          money: this.ruleForm.type == 1 ? this.ruleForm.face_value : null,
          discount: this.ruleForm.type == 0 ? this.ruleForm.issue_discount : null,
          zmoney: this.ruleForm.consumption_threshold,
          type: this.ruleForm.available_range,
          menuList: this.ruleForm.available_range == 2 ? this.ruleForm.select_goods.join(',') : null,
          classList: this.ruleForm.available_range == 3 ? this.classIdList.join(',') : null,
          day: this.ruleForm.coupons_time,
          gradeId: this.ruleForm.grade,
          instructions: this.ruleForm.instructions,
        }).then(res => {
          if (res.data.code == '200') {
            this.$message({
              message: '添加成功',
              type: 'success',
              offset: 100
            })
            this.$router.go(-1)
          }
        })
      }
    }

  }
}