// 导入组件，组件必须声明 name
import XButton from "./src";

// 为组件提供 install 安装方法，供按需引入
XButton.install = function(Vue) {
  Vue.component(XButton.name, XButton);
};

// 导出组件
export default XButton;
