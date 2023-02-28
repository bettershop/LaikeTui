import {setStorage, getStorage} from "@/utils/storage";
import store from "@/store/index";
import {isEmpty} from "element-ui/src/utils/util";

const data = {
  install(Vue) {
    Vue.prototype.fn1 = function (value) {
      console.log(value);
    }

    Vue.prototype.fn2 = function () {
      console.log(456);
    }

    //获取来源
    Vue.prototype.getSource = function () {
      let obj = getStorage("laike_source");
      if (isEmpty(obj)) {
        store.dispatch("source/getSource").then(r => obj = getStorage("laike_source"));
      }
      let map = new Map();
      for (let i = 0, l = obj.length; i < l; i++) {
        let source = obj[i];
        map.set(source.value, source.label);
      }
      return map;
    }

  }
}

export default data;
