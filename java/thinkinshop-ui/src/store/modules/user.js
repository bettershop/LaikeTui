import { login } from '@/api/login/index'
import { setUserAdmin } from '@/api/Platform/merchants'
import { getShopInfo } from '@/api/Platform/merchants'
import { getRoleMenu } from '@/api/Platform/permissions'
import router, {resetRouter, sysRouters} from '@/router'
import { getStorage, setStorage, removeStorage } from '@/utils/storage'

const getDefaultState = () => {
  return {
    token: getStorage('laike_admin_uaerInfo') ? getStorage('laike_admin_uaerInfo').token : '',
    name: '', // 用户名
    rolesInfo: getStorage('rolesInfo'),
    // authorizationList: getStorage('menuList'),
    merchants_Logo: getStorage('laike_head_img') ? getStorage('laike_head_img') : '',
    head_img: getStorage('laike_admin_uaerInfo') ? getStorage('laike_admin_uaerInfo').portrait : ''
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  // SET_AUTHORIZATIONLIST: (state, authorizationList) => {
  //   state.authorizationList = authorizationList
  // },
  SET_MERCHANTSLOGO: (state, merchants_Logo) => {
    state.merchants_Logo = merchants_Logo
  },
  SET_HEADIMG: (state, head_img) => {
    state.head_img = head_img
  }
}

const actions = {
  // 用户登录
  login({ commit }, userInfo) {
    const { customerNumber, userName, pwd } = userInfo
    return new Promise((resolve, reject) => {
      login({ customerNumber: customerNumber ? customerNumber : null , userName: userName.trim(), pwd: pwd })
      .then(response => {
        const { data } = response
        commit('SET_TOKEN', data.data.token)
        setStorage('laike_admin_uaerInfo',response.data.data)
        commit('SET_HEADIMG',data.data.portrait)
        setStorage('rolesInfo',{
          type: data.data.type,
          role: parseInt(data.data.role),
          storeId: data.data.storeId
        })
        resolve()
      })
      .catch(error => {
        reject(error)
      })
    })
  },

  // 获取系统管理员角色权限列表
  getAuthorizationList({ commit, state }) {
    return new Promise((resolve, reject) => {
      if(getStorage('rolesInfo').type === 0) {
        getShopInfo({
          api: 'saas.shop.getShopInfo',
          pageNo: 1,
          pageSize: 1,
          storeId: null
        }).then(res => {
          if(res.data.code == '200') {
            const laike_admin_uaerInfo = getStorage('laike_admin_uaerInfo')
            const rolesInfo = getStorage('rolesInfo')
            console.log(res);
            if(!getStorage('website_information')) {
              let website_information = {
                contact_address: res.data.data.dataList[0].contact_address,
                contact_number: res.data.data.dataList[0].contact_number,
                copyright_information: res.data.data.dataList[0].copyright_information,
                record_information: res.data.data.dataList[0].record_information
              }
              setStorage('website_information',website_information)
            }
            // laike_admin_uaerInfo.mchId = res.data.data.dataList[0].mchId
            if(getStorage('rolesInfo').storeId == 0) {
              rolesInfo.storeId = res.data.data.dataList[0].id
              laike_admin_uaerInfo.storeId = res.data.data.dataList[0].id
              setStorage('laike_head_img',res.data.data.dataList[0].merchant_logo)
            }
            setStorage('laike_admin_uaerInfo',laike_admin_uaerInfo)
            setStorage('rolesInfo',rolesInfo)
            setUserAdmin({
              api: 'admin.saas.user.setUserAdmin',
            })
            .then(response => {
              if(response.data.code == '200') {
                const laike_admin_uaerInfo = getStorage('laike_admin_uaerInfo')
                laike_admin_uaerInfo.mchId = response.data.data.mchId
                setStorage('laike_admin_uaerInfo',laike_admin_uaerInfo)
                resolve(response)
              }
            })
            .catch(error => {
              reject(error)
            })
            resolve()
          }
        }).catch(error => {
          reject(error)
        })
      } else if(getStorage('rolesInfo').type === 2 || getStorage('rolesInfo').type === 3) {
        getShopInfo({
          api: 'saas.shop.getShopInfo',
          storeId: getStorage('rolesInfo').storeId
        }).then(res => {
          if(res.data.code == '200') {
            setStorage('laike_head_img',res.data.data.dataList[0].merchant_logo)
            if(!getStorage('website_information')) {
              let website_information = {
                contact_address: res.data.data.dataList[0].contact_address,
                contact_number: res.data.data.dataList[0].contact_number,
                copyright_information: res.data.data.dataList[0].copyright_information,
                record_information: res.data.data.dataList[0].record_information
              }
              setStorage('website_information',website_information)
            }
            resolve(res)
          }
        })
      } else if(getStorage('rolesInfo').type === 1) {
        getShopInfo({
          api: 'saas.shop.getShopInfo',
          storeId: getStorage('rolesInfo').storeId
        }).then(res => {
          if(res.data.code == '200') {
            setStorage('laike_head_img',res.data.data.dataList[0].merchant_logo)
            if(!getStorage('website_information')) {
              let website_information = {
                contact_address: res.data.data.dataList[0].contact_address,
                contact_number: res.data.data.dataList[0].contact_number,
                copyright_information: res.data.data.dataList[0].copyright_information,
                record_information: res.data.data.dataList[0].record_information
              }
              setStorage('website_information',website_information)
            }
            resolve(res)
          }
        })
      }
    })
    
  },

  // 赋予系统管理员商城
  setUserAdmin() {
    return new Promise((resolve, reject) => {
      setUserAdmin({
        api: 'admin.saas.user.setUserAdmin',
        storeId: 0
      })
      .then(response => {
        if(response.data.code == '200') {
          resolve(response)
        }
      })
      .catch(error => {
        reject(error)
      })
    })
  },

  // 退出登录
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        removeStorage('laike_admin_uaerInfo') // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        //把角色信息设置为空列表
        commit('SET_ROLES', [])
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // 移除token
  resetToken({ commit }) {
    return new Promise(resolve => {
      // removeStorage('laike_admin_uaerInfo') // must remove  token  first
      commit('RESET_STATE')
      //把角色信息设置为空列表
      commit('SET_ROLES', [])
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

