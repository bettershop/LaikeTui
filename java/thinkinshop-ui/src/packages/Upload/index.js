// 导入组件，组件必须声明 name
import LUpload from "./index.vue";

// 导入样式
import "./style/index";

// 为组件提供 install 安装方法，供按需引入
LUpload.install = function(Vue) {
  Vue.component(LUpload.name, LUpload);
};

// 导出组件
export default LUpload;
