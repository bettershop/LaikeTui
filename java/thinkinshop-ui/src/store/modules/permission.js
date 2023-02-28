import {asyncRoutes, constantRoutes} from '@/router'
import {getStorage} from '@/utils/storage'
import request from "@/api/https";
import Layout from "@/layout/index";
import {isEmpty} from "element-ui/src/utils/util";

/**
 * 使用 meta.role 确定当前用户是否有权限
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.includes(route.meta.roles)
  } else {
    return true
  }
}

/**
 * 通过递归过滤异步路由表
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = {...route}
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = tmp.children.filter(route => {
          if (hasPermission(roles, route)) {
            return route
          }
        })
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}


const actions = {
  getAsyncRoutes({commit}) {
    const res = []
    return request({
      method: 'post',
      params: {
        api: 'saas.role.getAsyncRoutesByRoutes',
      },
    }).then(routes => {
      debugger 
      console.log(routes);
      if(routes.data.code == '200') {
        let route = routes.data.data;
        if (route.menu.length !== 0) {
          route.menu.forEach((menu, index) => {
            let icons = [];
            if (!isEmpty(menu.image)) {
              icons.push(menu.image)
            }
            if (!isEmpty(menu.image1)) {
              icons.push(menu.image1)
            }
            //一级菜单
            let topMenu = {
              path: "/" + menu.module,
              component: Layout,
              redirect: "/" + menu.module + "/" + menu.children[0].module,
              name: menu.module,
              meta: {title: menu.title, icon: icons},
            }
            //递归子菜单
            topMenu.children = actions.getMenus(menu.children)

            res.push(topMenu)
          })
        }
        return actions.generateRoutes(commit, res);
      }
    })
  },
  //菜单递归
  getMenus(menuList) {
    console.log("递归子菜单")
    if (isEmpty(menuList)) {
      return [];
    }
    menuList.forEach((currentMenu, index) => {
      let childrenMenu = {
        path: currentMenu.module,
        name: currentMenu.module,
        meta: {title: currentMenu.title, is_core: currentMenu.is_core},
      }
      //是否有子菜单
      if (!currentMenu.isChildren) {
        childrenMenu.component = resolve => require([`@/views${currentMenu.url}`], resolve)
      } else {
        childrenMenu.redirect = currentMenu.url
        childrenMenu.component = {
          render(c) {
            return c('router-view')
          }
        }
        //继续递归
        childrenMenu.children = actions.getMenus(currentMenu.children);
      }
      menuList[index] = childrenMenu
    })
    return menuList;
  },
  generateRoutes(commit, authorizationList) {
    return new Promise(resolve => {
      let authorizationLists = authorizationList
      if(getStorage('laike_admin_uaerInfo').type == 1) {
        authorizationLists = authorizationLists.filter(item => {
          if(item.meta.title !== '平台') {
            return item
          }
        })
      }
      commit('SET_ROUTES', authorizationLists)
      resolve(authorizationList)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
