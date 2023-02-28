<template>
  <div class="l-transfer">
    <div class="transfer_left">
      <div class="transfer_left_top">
        选择属性/属性值
        <a href="javascript:;" @click="checkQX">{{
          qxFlag ? "取消全选" : "全选"
        }}</a>
      </div>
      <div class="transfer_left_bottom">
        <div class="iptBox">
          <el-input
            @input="searchIpt"
            prefix-icon="el-icon-search"
            v-model="keyword"
            placeholder="请输入关键字"
            size="small"
          ></el-input>
        </div>

        <div class="treeBox">
          <el-tree
            :data="data"
            show-checkbox
            default-expand-all
            node-key="id"
            ref="tree"
            highlight-current
            @check="getCheck"
            :props="defaultProps"
          >
            <span class="custom-tree-node" slot-scope="{ node }">
              <el-tooltip :content="node.label" placement="bottom">
                <span>{{ node.label }}</span>
              </el-tooltip>
            </span>
          </el-tree>

          <div class="loading" v-if="dataLeng > 5">
            <a href="javascript:;" v-if="loadingType == 0" @click="scroll"
              >加载更多</a
            >
            <template v-else-if="loadingType == 1">
              <i class="el-icon-loading"></i>
              正在加载中
            </template>
            <template v-else>暂无更多数据</template>
          </div>
        </div>
      </div>
    </div>

    <div
      class="transfer_center"
      :class="{ disabled: checkAll1.length == 0 }"
      @click="saveCheck"
    >
      <i class="el-icon-arrow-right"></i>
    </div>

    <div class="transfer_right">
      <div class="transfer_right_top">
        已选

        <div class="transfer_right_top_Box">
          <template v-if="text">
            <a href="javascript:;" @click="rightClick">{{ text }}</a>
            <p class="hr">|</p>
          </template>
          <a href="javascript:;" @click="clearCheck">清空</a>
        </div>
      </div>

      <div class="checkBox">
        <el-tooltip
          v-for="(item, index) of checkAll"
          :key="index"
          :content="item.name"
          placement="bottom"
        >
          <div>
            <div>{{ item.name }}</div>
            <i class="el-icon-circle-close" @click="delCheck(index)"></i>
          </div>
        </el-tooltip>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "l-transfer",
  props: {
    defaultProps: {
      type: Object,
      default: () => {
        return {
          children: "children",
          label: "name"
        };
      }
    },
    text: {
      type: String,
      default: ""
    },
    limit: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      loadingType: 0,
      keyword: "",
      page: 1,
      qxFlag: false,
      checkAll: [],
      checkAll1: [],
      data: [],
      data1: []
    };
  },
  computed: {
    dataLeng() {
      return this.getLeng(this.data);
    }
  },
  created() {
    this.axios();
  },
  methods: {
    rightClick(e) {
      this.$emit("clicktext", e);
    },
    getLeng(data, num = 0) {
      let arr = [];
      data.filter(item => {
        num += 1;

        if (item.children && item.children.length > 0) {
          arr.push(...item.children);
        }
      });
      if (arr.length > 0) {
        return this.getLeng(arr, num);
      } else {
        return num;
      }
    },
    checkQX() {
      this.qxFlag = !this.qxFlag;
      if (this.qxFlag) {
        this.$refs.tree.setCheckedNodes(this.data);

        this.checkAll1 = this.$refs.tree.getCheckedNodes();
      } else {
        this.$refs.tree.setCheckedNodes([]);

        this.checkAll1 = [];
      }
    },
    // 点击选中
    getCheck(checked, checkAll) {
      this.checkAll1 = JSON.parse(JSON.stringify(checkAll.checkedNodes));

      // 全选按钮变选中状态
      if (this.dataLeng == checkAll.checkedNodes.length && this.dataLeng != 0) {
        this.qxFlag = true;
      } else {
        this.qxFlag = false;
      }
    },
    // 删除选中
    delCheck(index) {
      this.checkAll.splice(index, 1);
      this.page = 1;
      this.axios();
    },
    // 点击箭头保存选中到右侧列表
    saveCheck() {
      if (this.checkAll1.length == 0) {
        return;
      }
      let checkAll = JSON.parse(JSON.stringify(this.checkAll1));
      this.checkAll.push(...checkAll);
      this.checkAll1 = [];

      this.data1.filter(items => {
        items.isCheck = false;
        this.checkAll.filter(item => {
          if (items.name == item.name) {
            items.isCheck = true;
          }
        });
      });

      this.data = this.data1.filter(item => !item.isCheck);

      let filterAll = [];
      this.checkAll.filter(item => {
        if (filterAll.length == 0) {
          filterAll.push({
            attrName: item.name0,
            list: [item]
          });
        } else {
          let i = filterAll.findIndex(it => {
            return it.attrName == item.name0;
          });

          if (i < 0) {
            filterAll.push({
              attrName: item.name0,
              list: [item]
            });
          } else {
            filterAll[i].list.push(item);
          }
        }
      });

      let resultAll = [];
      filterAll.filter(item => {
        item.list.filter(it => {
          resultAll.push(it);
        });
      });

      this.checkAll = resultAll;

      this.qxFlag = false;

      this.page = 1;
      this.loadFlag = false;
      this.axios();
    },
    // 清空选择
    clearCheck() {
      this.checkAll = [];
      this.checkAll1 = [];

      this.page = 1;
      this.axios();
    },
    searchIpt() {
      this.page = 1;
      this.axios();
    },
    // 点击请求更多
    scroll() {
      if (this.loadingType != 0) {
        return;
      }
      this.loadingType = 1;
      this.page++;
      this.axios();
    },
    axios() {
      let obj = {
        keyword: this.keyword,
        checkAll: this.checkAll,
        page: this.page
      };
      this.$emit(
        "axios",
        obj,
        res => {
          // 成功请求回调
          if (this.page == 1) {
            this.data1 = res;
          } else {
            this.data1.push(...res);
          }

          this.data = this.data1.filter(item => !item.isCheck);
          this.qxFlag = false;
          if (res.length > 0) {
            this.loadingType = 0;
          } else {
            this.loadingType = 2;
          }
        },
        () => {
          // 失败请求回调
        }
      );
    }
  }
};
</script>
