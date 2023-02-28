// 导入button组件
import XButton from "./Button";
import LUpload from "./Upload";
import LTransfer from "./Transfer";
import LPagination from "./Pagination";
import "element-ui/lib/theme-chalk/index.css";
import "view-design/dist/styles/iview.css";

import Vue from "vue";
import Element from "element-ui";
// 组件列表
const components = [XButton, LUpload, LTransfer, LPagination];

// 异步计算属性
import AsyncComputed from "vue-async-computed";

Vue.use(AsyncComputed);

Vue.use(Element);

// 定义 install 方法，接收 Vue 作为参数。如果使用 use 注册插件，那么所有的组件都会被注册
const install = function(Vue) {
  // 判断是否安装
  if (install.installed) return;
  // 遍历注册全局组件
  components.map(component => Vue.component(component.name, component));
};

// 判断是否是直接引入文件
if (typeof window !== "undefined" && window.Vue) {
  install(window.Vue);
}

export default {
  // 导出的对象必须具有 install，才能被 Vue.use() 方法安装
  install,
  // 以下是具体的组件列表
  XButton,
  LUpload,
  LTransfer,
  LPagination
};
