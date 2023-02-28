import router from './router'
import store from './store'
import {Message} from 'element-ui'
import NProgress from 'nprogress' // 进度条
import 'nprogress/nprogress.css' // 进度条样式
import getPageTitle from '@/utils/get-page-title'
import {getStorage} from '@/utils/storage'

NProgress.configure({showSpinner: false})

const whiteList = ['/login'] // 没有重定向白名单

router.beforeEach(async (to, from, next) => {
  // 开始进度条
  NProgress.start()

  // 设置页面标题
  document.title = getPageTitle(to.meta.title)
  if (getStorage('laike_admin_uaerInfo')) {
    if (to.path === '/login') {
      // 如果已经登录, 重定向到首页
      next({path: '/'})
      NProgress.done()
    } else {
      const name = store.getters.name
      if (getStorage('rolesInfo') && name) {
        next()
      } else {
        try {
          await store.dispatch('user/getAuthorizationList')
          if (getStorage('laike_head_img')) {
            store.commit('user/SET_MERCHANTSLOGO', getStorage('laike_head_img'))
          }
          const userInfo = getStorage('laike_admin_uaerInfo')
          const {name} = userInfo
          store.commit('user/SET_NAME', name)
          // if (getStorage('rolesInfo').type == 0) {
          //   store.dispatch('user/setUserAdmin')
          // }
          const accessRoutes = await store.dispatch('permission/getAsyncRoutes')
          router.addRoutes(accessRoutes)

          store.dispatch('source/getSource')
          store.dispatch('orderNum/getOrderCount')
          if(store.state.permission.addRoutes.some(item => {
            return item.name == 'home'
          })) {
            next({...to, replace: true})
          } else {
            let path = store.state.permission.addRoutes[0].redirect
            next(path)
          }
          
        } catch (error) {
          console.log("异常")
          console.log(error)
          // 删除token并转到登录页面重新登录
          await store.dispatch('user/resetToken')
          // Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* 没有token*/
    if (whiteList.indexOf(to.path) !== -1) {
      // 直接去免登录白名单
      next()
    } else {
      // 其他没有访问权限的页面被重定向到登录页面。
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
