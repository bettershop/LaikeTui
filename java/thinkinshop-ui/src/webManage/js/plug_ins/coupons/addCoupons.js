import { addCoupon, getCouponConfigInfo, getAssignGoods, getAssignGoodsClass } from '@/api/plug_ins/coupons'
import { goodsStatus } from '@/api/members/membersList'
import treeTransfer from 'el-tree-transfer'
export default {
    name: 'addCoupons',
    components:{ 
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
              limit_count:1,
          },

          rules: {
            coupons_type: [
              {required: true, message: '请选择优惠卷类型', trigger: 'change'}
            ],
            coupons_name: [
              {required: true, message: '请填写优惠劵名称', trigger: 'blur'}
            ],
            grade: [
              {required: true, message: '请选择会员等级', trigger: 'change'}
            ],
            issue_number: [
              {required: true, message: '请填写发行数量', trigger: 'blur'}
            ],
            face_value: [
              {required: true, message: '请填写面值', trigger: 'blur'}
            ],
            issue_discount: [
              {required: true, message: '请填写折扣值', trigger: 'blur'}
            ],
            consumption_threshold: [
              {required: true, message: '请填写消费门槛', trigger: 'blur'}
            ],
            date: [
              {required: true, message: '请填写有效时间', trigger: 'change'}
            ],
            coupons_time: [
              {required: true, message: '请填写有效时间', trigger: 'change'}
            ]
          },

          defaultProps: {
            children: 'child',
            label: 'cname'
          },

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

          couponsTypeList: [],// 优惠券类型

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
          fromData:[],
          toData:[],
          limit_type:0,
        }
    },

    created() {
      this.getAssignGoods()
      this.getAssignGoodsClass()
      this.goodsStatus()
      this.getCouponConfigInfos()
    },

    watch: {
      'toData': {
        handler:function() {
          this.classIdList = []
          this.getClassId(this.toData)
        }
      },

      'ruleForm.coupons_type': {
        handler:function() {
          if(this.ruleForm.coupons_type !== 4) {
            this.ruleForm.type = null
          } else {
            this.ruleForm.type = 1
          }
        }
      },

      'ruleForm.limit_count':{
        handler(){
          if(this.ruleForm.limit_count < 1){
            this.ruleForm.limit_count = 1
            this.$message({
              message: '数量不能小于1',
              type: 'warning',
              offset:100
            })
          }
        }
      }
    },

    methods: {
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

      async getAssignGoods() {
        const res = await getAssignGoods({
          api: 'admin.coupon.getAssignGoods',
          pageSize:9999,
        })
        console.log(res);
        this.goodsList = res.data.data.list
      },

      async getAssignGoodsClass() {
        const res = await getAssignGoodsClass({
          api: 'admin.coupon.getAssignGoodsClass'
        })
        this.fromData = this.recursionNodes(res.data.data.list)
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
        // levelList.unshift({
        //     value: 0,
        //     label: '普通会员'
        // })

        this.membersGrade = levelList
      },

      async getCouponConfigInfos() {
        const res = await getCouponConfigInfo({
            api: 'admin.coupon.getCouponConfigInfo'
        })
        let obj = res.data.data.typeList
        this.limit_type = res.data.data.data.limit_type
        for (var i in obj) {
          this.couponsTypeList.push({
            value: parseInt(i),
            label: obj[i]
          })
        }
        console.log(this.couponsTypeList)
        
      },

      recursionNodes(childNodes) {
        const nodes = childNodes
        nodes.map((item,index) => {
          if (item.child && item.level > 1) {
            item.level = item.level + '-' + (index+1)
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

      // 切换模式 现有树形穿梭框模式transfer 和通讯录模式addressList
      changeMode() {
          if (this.mode == "transfer") {
              this.mode = "addressList";
          } else {
              this.mode = "transfer";
          }
      },
      // 监听穿梭框组件添加
      add(fromData,toData,obj){
          // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
          // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
          console.log("fromData:", fromData);
          console.log("toData:", toData);
          console.log("obj:", obj);
      },
      // 监听穿梭框组件移除
      remove(fromData,toData,obj){
          // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
          // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
          console.log("fromData:", fromData);
          console.log("toData:", toData);
          console.log("obj:", obj);
      },

      // 弹框方法
      dialogShow() {
        this.dialogVisible = true
        // this.getAssignGoodsClass()
      },
        
      handleClose(done) {
        this.dialogVisible = false
        this.getAssignGoodsClass()
      },
  
      remove(node, data) {
        const parent = node.parent;
        const children = parent.data.child || parent.data;
        const index = children.findIndex(d => d.cid === data.cid);
        children.splice(index, 1);
      },

      submitForm(formName) {
        this.ruleForm.limit_count = Number(this.ruleForm.limit_count)
        if(this.ruleForm.limit_count != this.ruleForm.limit_count.toFixed(0)){
          return this.ruleForm.limit_count = this.ruleForm.limit_count.toFixed(0)
        }
        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            try {
              if(this.ruleForm.coupons_type !== 4) {
                addCoupon({
                  api: 'admin.coupon.addCoupon',
                  mchId: 0,
                  activityType: this.ruleForm.coupons_type,
                  name: this.ruleForm.coupons_name,
                  circulation: this.ruleForm.issue_number,
                  money: this.ruleForm.coupons_type == 2 ? this.ruleForm.face_value : null,
                  discount: this.ruleForm.coupons_type == 3 ? this.ruleForm.issue_discount : null,
                  zmoney: this.ruleForm.consumption_threshold,
                  type: this.ruleForm.available_range,
                  menuList: this.ruleForm.available_range == 2 ? this.ruleForm.select_goods.join(',') : null,
                  classList: this.ruleForm.available_range == 3 ? this.classIdList.join(',') : null,
                  startTime: this.ruleForm.date[0],
                  endTime: this.ruleForm.date[1],
                  instructions: this.ruleForm.instructions,
                  limitCount:this.ruleForm.limit_count
                }).then(res => {
                  if(res.data.code == '200') {
                    this.$message({
                      message: '添加成功',
                      type: 'success',
                      offset:100
                    })
                    this.$router.go(-1)
                  }
                })
              } else {
                addCoupon({
                  api: 'admin.coupon.addCoupon',
                  mchId: 0,
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
                  if(res.data.code == '200') {
                    this.$message({
                      message: '添加成功',
                      type: 'success',
                      offset:100
                    })
                    this.$router.go(-1)
                  }
                })
              }
            } catch(error) {
              this.$message({
                message: error.message,
                type: 'error',
                offset: 100
              })
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      }
    }
}