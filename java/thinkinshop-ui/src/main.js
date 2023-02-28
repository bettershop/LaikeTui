import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '../theme/index.css'
// import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import '@/styles/index.scss' // global css
import '@/styles/common.scss'
import '@/styles/icon.scss'

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}
// 引入echarts图表
import echarts from "echarts";
Vue.prototype.$echarts = echarts

import i18n from './lang'

Vue.use(ElementUI,{
  i18n: (key, value) => i18n.t(key, value)
})

import AsyncComputed from "vue-async-computed";
Vue.use(AsyncComputed);
// 导入组件库
import lUpload from './packages/index'
import lTransfer from "./packages/index";
import LPagination from "./packages/index";
// 注册组件库
Vue.use(lUpload)
Vue.use(lTransfer)
Vue.use(LPagination)

// 查看图片
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
Vue.use(Viewer, {
  defaultOptions: {
    zIndex: 9999,
  }
})


import '@/common/commonStyle/common.scss'
import publicFunction from '@/common/commonFunction/common'
Vue.use(publicFunction)

Vue.filter('dateFormat',function  (val) {
  if (val === null || val === '') {
    return '暂无时间';
  }
  const d = new Date(val); // val 为表格内取到的后台时间
  const month = d.getMonth() + 1 < 10 ? `0${d.getMonth() + 1}` : d.getMonth() + 1;
  const day = d.getDate() < 10 ? `0${d.getDate()}` : d.getDate();
  const hours = d.getHours() < 10 ? `0${d.getHours()}` : d.getHours();
  const min = d.getMinutes() < 10 ? `0${d.getMinutes()}` : d.getMinutes();
  const sec = d.getSeconds() < 10 ? `0${d.getSeconds()}` : d.getSeconds();
  const times = `${d.getFullYear()}-${month}-${day} ${hours}:${min}:${sec}`;
  return times;
})

Vue.filter('dateFormat1',function  (val) {
  if (val === null || val === '') {
    return '暂无时间';
  }
  const d = new Date(val); // val 为表格内取到的后台时间
  const month = d.getMonth() + 1 < 10 ? `0${d.getMonth() + 1}` : d.getMonth() + 1;
  const day = d.getDate() < 10 ? `0${d.getDate()}` : d.getDate();
  const times = `${d.getFullYear()}-${month}-${day}`;
  return times;
})

import './utils/rem'


Vue.config.productionTip = false

new Vue({
  el: '#app',
  i18n,
  router,
  store,
  render: h => h(App)
})
