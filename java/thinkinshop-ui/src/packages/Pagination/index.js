// 导入组件，组件必须声明 name
import Lpagination from "./index.vue";

// 导入样式
import "./style/index.less";

// 为组件提供 install 安装方法，供按需引入
Lpagination.install = function(Vue) {
  Vue.component(Lpagination.name, Lpagination);
};

// 导出组件
export default Lpagination;
