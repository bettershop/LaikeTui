<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="device==='mobile'&&sidebar.opened" class="drawer-bg" @click="handleClickOutside" />
    <div class="big-header" :class="{'fixed-header':fixedHeader}">
      <navbar />
    </div>
    <div class="main-container">
      <div class="main-nav">
        <SideBar />
      </div>
      <app-main />
    </div>
    <div class="footer">
      <div class="left-location">
        <span>{{a}}</span>
      </div>
      <div class="right-company">
        <div class="company">
          <a href="javascript:;">{{b}}</a>
        </div>
        <div class="archival-information">
          <a href="javascript:;"> {{c}} </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Navbar,  AppMain } from './components'
import {a} from '@/api/Platform/acopyright.js'
import SideBar from './components/SideBar/index'
import ResizeMixin from './mixin/ResizeHandler'
import { getStorage } from '@/utils/storage'
export default {
  name: 'Layout',
  data() {
    return {
      a:"联系地址：长沙市岳麓区绿地中央广场5栋3408 0731-86453408",
      b:"Copyright © 2021 湖南壹拾捌号网络技术有限公司[官方网站] ",
      c:"湘ICP备17020355号",
      d:"未授权版来客推商城",
      e:"未经<a href='https://www.laiketui.com/' target='_blank' >来客推电商</a>系统授权的用户，仅供从事学习研究之用，不具备商业运作的合法性，如果未获取授权而从事商业行为，<a href='https://www.laiketui.com/' target='_blank' >来客推</a>保留对其使用系统停止升级、关闭、甚至对其商业运作行为媒体曝光和追究法律责任的起诉权利，授权请前往<a href='https://www.laiketui.com/' target='_blank' >www.laiketui.com</a>咨询。"
    }
  },
  components: {
    Navbar,
    SideBar,
    AppMain
  },
  mixins: [ResizeMixin],
  computed: {
    sidebar() {
      return this.$store.state.app.sidebar
    },
    device() {
      return this.$store.state.app.device
    },
    fixedHeader() {
      return this.$store.state.settings.fixedHeader
    },
    classObj() {
      return {
        hideSidebar: !this.sidebar.opened,
        openSidebar: this.sidebar.opened,
        withoutAnimation: this.sidebar.withoutAnimation,
        mobile: this.device === 'mobile'
      }
    },
    websiteInformation() {
      return getStorage('website_information')
    }
  },
  beforeCreate() {
    let me = this;
    a({
      api:"SaaS.b.c"
    }).then(res => {
        console.log(res);
        if(res.data.code == 200){
          me.a = res.data.data.a ;
          me.b = res.data.data.b ;
          me.c = res.data.data.c ;
          me.d = res.data.data.d ;
          me.e = res.data.data.e ;
          window.localStorage.setItem("d",me.d);
          window.localStorage.setItem("e",me.e);
        }
    })
  },
  methods: {
    handleClickOutside() {
      this.$store.dispatch('app/closeSideBar', { withoutAnimation: false })
    },
    f(){
      debugger
      
    }
  }
}
</script>

<style lang="scss" scoped>
  @import "~@/styles/mixin.scss";
  @import "~@/styles/variables.scss";

  .app-main {
    padding-bottom: 20px !important;
  }
  .sidebar-container {
    padding-top: 60px;
    padding-bottom: 40px;
  }
  .big-header {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 2000 !important;
    width: 100% !important;
  }

  .main-nav {
    // width: 220px;
    width: 70px;
    height: calc(100vh - 100px);
    position: fixed;
    left: 0;
    top: 60px;
    z-index: 100;
  }


  .app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;
    background-color: #edf1f5;
    &.mobile.openSidebar{
      position: fixed;
      top: 0;
    }

    .footer {
      position: fixed;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 40px;
      background-color: #222;
      display: flex;
      justify-content: space-between;
      align-items: center;
      z-index: 1010;
      padding: 0 30px;
      color: #ddd;
      font-size: 14px;
      .right-company {
        display: flex;
        font-size: 14px;
        .company {
          margin-right: 30px;
          cursor: pointer;
          a {
            color: #DDDDDD;
            &:hover {
              color: #2d8cf0;
            }
          }

        }

        .archival-information {
          cursor: pointer;
          a {
            color: #DDDDDD;
            &:hover {
              color: #2d8cf0;
            }
          }
        }
      }
    }
  }
  .drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
  }

  // .fixed-header {
  //   position: fixed;
  //   top: 0;
  //   right: 0;
  //   z-index: 9;
  //   width: calc(100% - #{$sideBarWidth});
  //   transition: width 0.28s;
  // }

  .hideSidebar .fixed-header {
    width: calc(100% - 70px)
  }

  .fixed-header {
    padding: 0 !important;
  }

  .mobile .fixed-header {
    width: 100%;
  }
</style>
