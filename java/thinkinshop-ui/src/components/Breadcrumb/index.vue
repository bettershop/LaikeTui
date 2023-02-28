<template>
  <el-breadcrumb class="app-breadcrumb">
    <transition-group name="breadcrumb" id="breadcrumb">
      <el-breadcrumb-item v-for="(item,index) in newList" :key="index">
        <span v-if="item.redirect==='noRedirect'||index==newList.length-1" class="no-redirect">{{ generateTitle(item.meta.title) }}</span>
        <a v-else @click.prevent="handleLink(item)">{{ generateTitle(item.meta.title) }}</a>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import pathToRegexp from 'path-to-regexp'
export default {
  data() {
    return {
      levelList: null,
      newList: []
    }
  },
  watch: {
    $route() {
      this.getBreadcrumb()
    }
  },
  created() {
    this.getBreadcrumb()
    console.log(this.levelList);
    console.dir(this.newList);
  },
  methods: {
    getBreadcrumb() {
      let matched = this.$route.matched.filter(item => item.meta && item.meta.title)
      const first = matched[0]

      this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
      this.newList = []
      let redirect = this.levelList[1].redirect
      this.levelList.filter(item => {
        this.newList.push(item)
      })
      if(this.newList.length == 3 && this.newList[1].meta.title == this.newList[2].meta.title && this.newList[2].meta.title !== '订单列表') {
        this.newList.pop()
      }
      if(this.newList[0].meta.title == '会员' && this.newList[1].meta.title !== '会员列表') {
        let arr = {
          meta: {
            title: "会员列表"
          },
          redirect: '/members/membersList'
        }
        this.newList.splice(1, 0, arr)

        if(this.newList[2].meta.title == '添加会员等级' || this.newList[2].meta.title == '编辑会员等级') {
          let arrs = {
            meta: {
              title: "会员等级"
            },
            redirect: '/members/membersLevel'
          }
          this.newList.splice(2, 0, arrs)
        }
      }

      if(this.newList[1].meta.title == '秒杀') {
        if(this.newList[2].meta.title == '添加商品' || this.newList[2].meta.title == '商品列表') {
          let arrs = {
            meta: {
              title: "秒杀标签"
            },
            redirect: '/plug_ins/seckill/seckillLabel'
          }
          this.newList.splice(2, 0, arrs)
        }

        if(this.newList[2].meta.title == '编辑' ||this.newList[2].meta.title == '查看商品') {
          let arrs1 = {
            meta: {
              title: "秒杀标签"
            },
            redirect: '/plug_ins/seckill/seckillLabel'
          }
          let arrs2 = {
            meta: {
              title: "商品列表"
            },
            redirect: '/plug_ins/seckill/goodsList'
          }
          this.newList.splice(2, 0, arrs1)
          this.newList.splice(3, 0, arrs2)
        }

        if(this.newList[2].meta.title == '售后管理') {
          let arrs = {
            meta: {
              title: "秒杀订单"
            },
            redirect: '/plug_ins/seckill/seckillOrder'
          }
          
          this.newList.splice(2, 0, arrs)
        }
        if(this.newList[2].meta.title == '售后详情') {
          let arrs1 = {
            meta: {
              title: "秒杀订单"
            },
            redirect: '/plug_ins/seckill/seckillOrder'
          }
          let arrs2 = {
            meta: {
              title: "售后管理"
            },
            redirect: '/plug_ins/seckill/afterSaleList'
          }
          
          this.newList.splice(2, 0, arrs1)
          this.newList.splice(3, 0, arrs2)
        }

        if(this.newList[2].meta.title == '订单结算') {
          let arrs = {
            meta: {
              title: "秒杀订单"
            },
            redirect: '/plug_ins/seckill/seckillOrder'
          }
          
          this.newList.splice(2, 0, arrs)
        }
        if(this.newList[2].meta.title == '查看') {
          let arrs1 = {
            meta: {
              title: "秒杀订单"
            },
            redirect: '/plug_ins/seckill/seckillOrder'
          }
          let arrs2 = {
            meta: {
              title: "订单结算"
            },
            redirect: '/plug_ins/seckill/orderSettlementList'
          }
          
          this.newList.splice(2, 0, arrs1)
          this.newList.splice(3, 0, arrs2)
        }

        if(this.newList[2].meta.title == '评价管理') {
          let arrs = {
            meta: {
              title: "秒杀订单"
            },
            redirect: '/plug_ins/seckill/seckillOrder'
          }
          
          this.newList.splice(2, 0, arrs)
        }
        if(this.newList[2].meta.title == '修改') {
          let arrs1 = {
            meta: {
              title: "秒杀订单"
            },
            redirect: '/plug_ins/seckill/seckillOrder'
          }
          let arrs2 = {
            meta: {
              title: "评价管理"
            },
            redirect: '/plug_ins/seckill/commentList'
          }
          
          this.newList.splice(2, 0, arrs1)
          this.newList.splice(3, 0, arrs2)
        }

        if(this.newList[2].meta.title == '订单详情' || this.newList[2].meta.title == '商品发货' || this.newList[2].meta.title == '编辑订单') {
          let arrs = {
            meta: {
              title: "秒杀订单"
            },
            redirect: '/plug_ins/seckill/seckillOrder'
          }
          
          this.newList.splice(2, 0, arrs)
        }
      }

      if(this.newList[1].meta.title == '积分商城') {
        if(this.newList[2].meta.title == '添加商品' || this.newList[2].meta.title == '编辑') {
          let arrs = {
            meta: {
              title: "积分商品"
            },
            redirect: '/plug_ins/integralMall/integralGoodsList'
          }
          this.newList.splice(2, 0, arrs)
        }

        if(this.newList[2].meta.title == '售后管理') {
          let arrs = {
            meta: {
              title: "兑换订单"
            },
            redirect: '/plug_ins/integralMall/integralOrder'
          }
          
          this.newList.splice(2, 0, arrs)
        }
        if(this.newList[2].meta.title == '售后详情') {
          let arrs1 = {
            meta: {
              title: "兑换订单"
            },
            redirect: '/plug_ins/integralMall/integralOrder'
          }
          let arrs2 = {
            meta: {
              title: "售后管理"
            },
            redirect: '/plug_ins/integralMall/afterSaleList'
          }
          
          this.newList.splice(2, 0, arrs1)
          this.newList.splice(3, 0, arrs2)
        }

        if(this.newList[2].meta.title == '订单结算') {
          let arrs = {
            meta: {
              title: "兑换订单"
            },
            redirect: '/plug_ins/integralMall/integralOrder'
          }
          
          this.newList.splice(2, 0, arrs)
        }
        if(this.newList[2].meta.title == '查看') {
          let arrs1 = {
            meta: {
              title: "兑换订单"
            },
            redirect: '/plug_ins/integralMall/integralOrder'
          }
          let arrs2 = {
            meta: {
              title: "订单结算"
            },
            redirect: '/plug_ins/integralMall/orderSettlementList'
          }
          
          this.newList.splice(2, 0, arrs1)
          this.newList.splice(3, 0, arrs2)
        }

        if(this.newList[2].meta.title == '评价管理') {
          let arrs = {
            meta: {
              title: "兑换订单"
            },
            redirect: '/plug_ins/integralMall/integralOrder'
          }
          
          this.newList.splice(2, 0, arrs)
        }
        if(this.newList[2].meta.title == '修改') {
          let arrs1 = {
            meta: {
              title: "兑换订单"
            },
            redirect: '/plug_ins/integralMall/integralOrder'
          }
          let arrs2 = {
            meta: {
              title: "评价管理"
            },
            redirect: '/plug_ins/integralMall/commentList'
          }
          
          this.newList.splice(2, 0, arrs1)
          this.newList.splice(3, 0, arrs2)
        }

        if(this.newList[2].meta.title == '订单详情' || this.newList[2].meta.title == '商品发货' || this.newList[2].meta.title == '编辑订单') {
          let arrs = {
            meta: {
              title: "兑换订单"
            },
            redirect: '/plug_ins/integralMall/integralOrder'
          }
          
          this.newList.splice(2, 0, arrs)
        }
      }
    },
    isDashboard(route) {
      const name = route && route.name
      if (!name) {
        return false
      }
      return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase()
    },
    pathCompile(path) {
      const { params } = this.$route
      var toPath = pathToRegexp.compile(path)
      return toPath(params)
    },
    handleLink(item) {
      const { redirect, path } = item
      if(item.meta.returns || item.returns) {
        this.$router.go(-1)
      } else {
        if (redirect) {
          this.$router.push(redirect)
          return
        }
        this.$router.push(this.pathCompile(path))
      }
      console.log(redirect);
    },

    generateTitle(title) {
        const hasKey = this.$te('route.' + title)
        if (hasKey) {
          const translatedTitle = this.$t('route.' + title)
          return translatedTitle
        }
        return title
    }
  }
}
</script>

<style lang="scss" scoped>
.app-breadcrumb.el-breadcrumb {
  display: inline-block;
  font-size: 18px;
  line-height: 60px;
  margin-left: 20px;

  .no-redirect {
    color: #414658;
    cursor: text;
  }

  a  {
    color: #414658;
  }

  a:hover {
    color: #2890ff;
  }

  span {
    color: #6a7076 !important;
  }

}
</style>
