import axios from 'axios'
import ElementUI from 'element-ui';
import { removeUserInfo } from '@/utils/storage'

const http = axios.create({
  timeout: 5000
})

if (process.env.NODE_ENV == 'development') {
  http.defaults.baseURL = 'api' + "/admin/saas/user/login"
  // http.defaults.baseURL = 'https://java.houjiemeishi.com/gw'
} else if (process.env.NODE_ENV == 'production') {
  http.defaults.baseURL = process.env.VUE_APP_BASE_API + "/admin/saas/user/login"
}

// 配置请求拦截器
http.interceptors.request.use(config => {

  // let api = config.params.api;  
  // console.log(">>" + APIMAPPING[api]); 
  // http.defaults.baseURL =  localStorage.getItem("api_url") +  api
  // console.log(config); 

  return config
}, err => {
  return Promise.reject(err)
})

// 配置响应拦截器
http.interceptors.response.use((res) => {

  

  if (res.data.code == '200') {
    ElementUI.Message({
      message: '登录成功',
      type: 'success',
      offset: 100
    })
  } else {
    ElementUI.Message({
      message: res.data.message,
      type: 'error',
      offset: 100
    })
  }
  return res;
}, (error) => {

});

export default http;
