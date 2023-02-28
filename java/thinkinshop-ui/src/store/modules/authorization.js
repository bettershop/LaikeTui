import { getShopInfo } from '@/api/Platform/merchants'
import { getRoleMenu } from '@/api/Platform/permissions'
import { getStorage, setStorage, removeStorage } from '@/utils/storage'
const state = {
  authorizationList: getStorage('menuList') ? getStorage('menuList') : [], // 角色权限列表
}


const mutations = {
  SET_AUTHORIZATIONLIST: (state, authorizationList) => {
    state.authorizationList = authorizationList
  },
  
}

const actions = {

  // // 获取系统管理员角色权限列表
  // getAuthorizationList({ commit }, role, storeId, type) {
  //   return new Promise((resolve, reject) => {
  //     if(type === 0) {
  //       getShopInfo({
  //         api: 'saas.shop.getShopInfo',
  //         pageNo: 1,
  //         pageSize: 1
  //       }).then(res => {
  //         getRoleMenu({
  //           api: 'saas.role.getRoleMenu',
  //           storeId: res.data.data.dataList.id,
  //           roleId: res.data.data.roleId
  //         }).then(res => {
  //           setStorage('menuList',res.data.data.menuList)
  //           commit('SET_AUTHORIZATIONLIST',res.data.data.menuList)
  //           resolve(res.data.data.menuList)
  //         })
  //       }).catch(error => {
  //         reject(error)
  //       })
  //     } else if(type === 2 || type === 3) {
  //       getRoleMenu({
  //         api: 'saas.role.getRoleMenu',
  //         storeId: storeId,
  //         roleId: role
  //       }).then(res => {
  //         setStorage('menuList',res.data.data.menuList)
  //         commit('SET_AUTHORIZATIONLIST',res.data.data.menuList)
  //         resolve(res.data.data.menuList)
  //       }).catch(error => {
  //         reject(error)
  //       })
  //     }
  //   })
    
  // },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}