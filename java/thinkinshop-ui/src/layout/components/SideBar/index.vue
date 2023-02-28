<template>
  <div class="mainSideBar">
    <div class="left-nav">
      <ul class="side-nav">
        <li class="side-nav-item" v-for="(route,index) in routes" :key="index" @mouseenter="mouseenter(route)" @mouseleave="mouseleave">
          <div class="nav-maininfo" :class="{active: activeName === route.name}" @click="change(route)">
            <div class="icon-font" @click="handleSelect(route.name)">
              <img :src="(activeName === route.name || toggle === route.name) ? route.meta.icon[1] : route.meta.icon[0]" alt="">
              <span>{{ route.meta.title }}</span>
            </div>
            <i v-show="toggle === route.name" class="icon"></i>
            <span v-if="route.meta.title === '订单'" class="red-dot"></span>
          </div>

          <ul class="nav-info">
            <li v-for="(child,index) in route.children" :key="index" @click="handleSelect(`${route.name}/${child.path}`);change(route)" v-show="child.meta.is_core == 1">
              <span :class="{ active : child.name === $route.path.split('/')[2]}">
                {{ generateTitle(child.meta.title) }}
                <i class="orderListNum" v-if="child.meta.title === '订单列表'">({{orderListNum}})</i>
                <i class="salesReturnListNum" v-if="child.meta.title === '退货列表'">({{refundListNum}})</i>
              </span>
            </li>
          </ul>
        </li>
      </ul>
    </div>
    <div class="right-nav" :class="classObj">
      <SideItem class="itemBar" :mainNav="mainNav"  :class="classobj" :item="navList" />
    </div>
  </div>
</template>

<script>
import SideItem from  './sideItem'
import { mapGetters } from '@/store'
export default {
    name: 'mainSideBar',
    components: {
      SideItem
    },
    data() {
      return {
        activeName: 'home',
        toggle: 'home',
        navList: [
          {path: "homeReport", name: "homeReport", meta: { title: "商城首页",is_core: 1}},
        ],
        mainNav: 'home'
      }
    },
    created() {
      console.log(this.$store.state.permission.addRoutes);
      if(this.$store.state.permission.addRoutes.some(item => {
        return item.name == 'home'
      })) {
        if (this.$route.path.split('/')[1] !== 'home') {
          this.$router.replace('/home/homeReport')
        }
        this.$store.state.permission.addRoutes.map(item => {
          if(item.name == 'home') {
            this.navList = item.children
          }
        })
      } else {
        let routes = this.$store.state.permission.addRoutes[0]
        this.activeName = routes.name
        this.toggle = routes.name
        this.navList = routes.children
        this.mainNav = routes.name

        if (this.$route.path.split('/')[1] !== routes.name) {
          this.$router.replace(routes.redirect)
        }
      }
      
    },

    computed: {
      item() {
        return this.routes[0].children
      },
      routes() {
        return this.$store.getters.permission_routes.filter(item => {
          if(item.meta) {
            return item
          }
        })
      },
      activeMenu() {
        const route = this.$route
        const { meta, path } = route
        // if set path, the sidebar will highlight the path you set
        if (meta.activeMenu) {
            return meta.activeMenu
        }
        return path
      },
      sidebar() {
        return this.$store.state.app.sidebar
      },
      classObj() {
        return {
          active: this.sidebar.opened,
          actives: !this.sidebar.opened
        }
      },
      classobj() {
        return {
          active: this.sidebar.opened,
          actives: !this.sidebar.opened
        }
      },
      opened() {
        return this.sidebar.opened
      },

      orderListNum() {
        return this.$store.getters.orderListNum
      },

      refundListNum() {
        return this.$store.getters.refundListNum
      }
    },

    watch: {
      opened() {
        if(this.opened) {
          this.toggle = this.activeName
        } else {
          this.toggle = ''
        }
      },

      '$route.path'() {
        this.activeName = this.$route.path.split('/')[1]
        this.toggle = this.$route.path.split('/')[1]
      }
    },

    methods:{
      // 切换菜单栏
      handleSelect(index) {
        // if(index == 'plug_ins/seckill') {
        //   window.location.href = 'https://java.admin.houjiemeishi.com/seckill/'
        // } else {
        //   this.$router.push('/' + index);
        // }
        this.$router.push('/' + index);
      },

      mouseenter(value) {
        this.toggle = value.name
      },

      mouseleave() {
        this.toggle = this.activeName
        if(!this.opened) {
          this.toggle = ''
        }

      },

      change(value) {
        this.navList = value.children
        this.mainNav = value.name
        this.toggle = value.name
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

<style scoped lang="scss">
.mainSideBar {
  height: 100%;
  display: flex;
  justify-content: space-between;
  cursor: pointer;
  z-index: 101;
  .left-nav {
    width: 70px;
    height: 100%;
    background-color: #343e4c;
    overflow-y: auto;
    z-index: 100;
    &::-webkit-scrollbar {
      display: none;
    }
    .side-nav {
      background-color: #343e4d;
      border-bottom: none !important;
      margin: 0;
      padding: 0;
      .side-nav-item {
        width: 70px;
        height: 80px;
        margin: 0;
        padding: 0;
        color: #b2bcd1;
        font-size: 14px;
        &:hover .nav-info {
          display: block;
        }
        .nav-maininfo {
          width: 70px;
          height: 80px;
          display: flex;
          justify-content: center;
          align-items: center;
          position: relative;
          &:hover {
            color: #fff;
          }
          &.active {
            background-color: #161c24;
            color: #fff;
          }
          .icon-font {
            width: 70px;
            height: 80px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            img {
              width: 22px;
              height: 22px;
              margin-bottom: 5px;
            }
            span {
              font-size: 16px;
              font-weight: bold
            }
          }
          i {
            content: '';
            position: absolute;
            right: 0;
            top: 50%;
            transform: translateY(-50%);
            width: 0;
            height: 0;
            border: 8px solid transparent;
            border-right-color: #FFFFFF;
          }

          .red-dot {
            display: inline-block;
            width: 10px;
            height: 10px;
            background-color: red;
            border: 1px solid red;
            border-radius: 50%;
            position: absolute;
            top: 17px;
            right: 7px;
          }
        }

        .nav-info {
          width: 150px;
          height: calc(100vh - 100px);
          position: fixed;
          left: 70px;
          top: 60px;
          display: none;
          background-color:#fff;
          color: #414658;
          margin: 0;
          padding: 10px;
          box-shadow: 3px 0px 3px rgba(185, 183, 183, 0.1);
          z-index: 10000;
          li {
            width: 130px;
            height: 40px;
            line-height: 40px;
            margin: 8px 0;
            margin: 8px 0;
            border-radius: 2px;
            &.active span {
              background-color: #e9f4ff;
              color: #2890ff;
            }
            &:hover {
              color: #2890ff !important;
            }
            span {
              display: block;
              width: 100%;
              height: 100%;
              padding-left: 6px;
              font-size: 16px;
              &.active {
                background-color: #e9f4ff;
                color: #2890ff;
              }
            }

            .orderListNum,.salesReturnListNum {
              font-style: normal;
              color: red;
              // margin-left: 8px;
            }
          }
        }
      }
    }
  }


  .right-nav {
    width: 150px;
    height: calc(100vh - 100px);
    position: fixed;
    left: 70px;
    transition: left 0.28s ease;
    z-index: 1;
    &.active {
      left: 70px;
    }
    &.actives {
      left: -80px;
      height: calc(100vh - 100px);
    }
    .itemBar {
      &.active {
        display: block;
      }
      &.actives {
        display: none;
      }
    }
  }
}
</style>
