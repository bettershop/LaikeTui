<template>
  <section class="app-main">
    <div class="loading" v-if="this.tag == true" v-loading="loading">

    </div>
    <div class="container" v-else>
      <div class="bread-nav">
        <breadcrumb class="breadcrumb-container" />
        <el-button icon="el-icon-arrow-left" v-if="is_return()" class="bgColor" type="primary" @click="returns">返回</el-button>
      </div>
      <transition name="fade-transform" mode="out-in">
        <div class="table-container">
          <router-view />
          <!-- <keep-alive>
            <router-view v-if="$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!$route.meta.keepAlive"></router-view> -->
        </div>
      </transition>
    </div>
  </section>
</template>

<script>
import Breadcrumb from '@/components/Breadcrumb'
export default {
  name: 'AppMain',
  components: {
    Breadcrumb
  },
  data() {
    return {
      tag: true,
      loading: true,
    }
  },
  created() {
    this.changeLoading()
  },
  computed: {

  },

  methods: {
    async changeLoading() {
      setTimeout(() => {
        this.loading = false
        this.tag = false
      },2000)
      
    },

    returns() {
      this.$router.go(-1)
    },

    is_return() {
      if(this.$route.path == '/plug_ins/integralMall/commentEdit' || this.$route.path == '/plug_ins/seckill/commentEdit') return true
      if(this.$route.path == '/plug_ins/integralMall/orderDetails' || this.$route.path == '/plug_ins/seckill/orderDetails') return true
      if(this.$route.path == '/plug_ins/integralMall/orderSettlementDetail' || this.$route.path == '/plug_ins/seckill/orderSettlementDetail') return true
      if(this.$route.path == '/plug_ins/integralMall/afterSaleDetails' || this.$route.path == '/plug_ins/seckill/afterSaleDetails') return true
      return false
    }
  },

}
</script>

<style scoped lang="scss">
.app-main {
  height: calc(100vh - 100px);
  width: 100%;
  position: relative;
  top: 60px;
  overflow: hidden;
  overflow-y: auto;
  &::-webkit-scrollbar {
    width: 8px;
    height: 900px;
  }
  &::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background: #97a0b4
  }
  &::-webkit-scrollbar-track {
    border-radius: 20px;
    background: #fff;
  }
  .loading {
    margin-top: 80px;
  }

  .container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    .bread-nav {
      width: 100%;
      height: 60px;
      line-height: 60px;
      border-bottom: 1px solid #D5DBE8;
      display: flex;
      justify-content: space-between;
      align-items: center;
      .bgColor {
        width: 90px;
        height: 40px;
        margin-right: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
    .table-example {
      width: 100%;
      flex: 1;
      padding: 20px;
      background-color: #edf1f5;
      margin-top: 20px;
    }
  }
}

.table-container {
  width: 100%;
  height: calc(100% - 80px);
  margin-top: 20px;
  padding: 0 20px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
