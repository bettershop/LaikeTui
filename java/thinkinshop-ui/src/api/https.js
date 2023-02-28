import axios from 'axios'
import ElementUI from 'element-ui';
import { removeStorage, getStorage } from '@/utils/storage'

import APIMAPPING from '@/api/apimapping'

const request = axios.create({
  timeout: 10000
})


if (process.env.NODE_ENV == 'development') {
  request.defaults.baseURL = 'api' 
  // request.defaults.baseURL = 'https://java.houjiemeishi.com'
} else if (process.env.NODE_ENV == 'production') { 
  // let api = config.params.api;
  request.defaults.baseURL = process.env.VUE_APP_BASE_API;
}

// 配置请求拦截器
request.interceptors.request.use(config => {    
   
  let api = config.params.api;    
  let baseURI = 'api'

  if (process.env.NODE_ENV == 'production') {  
      baseURI = process.env.VUE_APP_BASE_API;
  } 
  config.baseURL =   baseURI +  APIMAPPING[api]  

  config.params.api = "";
  if (config.method === 'post') {
    const userInfo = getStorage('laike_admin_uaerInfo')
    config.params = {
      //商城id
      storeId: userInfo.storeId,
      //来源
      storeType: 8,
      ...config.params,
      //token
      accessId: userInfo.token
    }

    if(config.params.exportType && config.params.exportType == 1){
      config.headers = {
        'Content-Type': 'application/octet-stream;charset=utf-8'
      }
      config.responseType="blob";
    }
  }
  return config
}, err => {
  return Promise.reject(err)
})

// 配置响应拦截器
request.interceptors.response.use((res) => {
  if(res.data.code == '203') {
    window.localStorage.clear()
    window.location.href = '/login'
    location.reload();
  }
  if(res.data instanceof Blob){
    return res
  }
  if (res.status && res.data.code == '200') {
    return res;
  } else {
    ElementUI.Message({
      message: res.data.message,
      type: 'error',
      offset: 100
    })
    return res
  }

  console.log(res);

}, (error) => {

});

export default request;
