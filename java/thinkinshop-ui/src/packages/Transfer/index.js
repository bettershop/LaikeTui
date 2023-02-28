// 导入组件，组件必须声明 name
import Ltransfer from "./index.vue";

// 导入样式
import "./style/index";

// 为组件提供 install 安装方法，供按需引入
Ltransfer.install = function(Vue) {
  Vue.component(Ltransfer.name, Ltransfer);
};

// 导出组件
export default Ltransfer;
