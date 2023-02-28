import "../style/dialog-left-aside-menu-item.less";

/**
 * 因为未知原因鼠标滑动事件居然会触发点击事件。所以事件相关都使用 h 函数
 */
export default {
  name: "dialog-left-aside-menu-item",
  props: {
    index: {
      type: String
    },
    name: {
      type: String
    }
  },
  data() {
    return {
      status: 1, // 1 正常显示，2鼠标移入，3编辑中
      value: ""
    };
  },
  watch: {
    name(val) {
      this.value = val;
    }
  },
  mounted() {
    this.value = this.name;
  },
  render(h) {
    return (
      <div
        onMouseover={e => {
          if (this.status !== 3) {
            this.status = 2;
          }
          e.stopPropagation();
          event.stopPropagation();
        }}
        onMouseleave={e => {
          this.status = 1;
          e.stopPropagation();
          event.stopPropagation();
        }}
      >
        {[this.getMenuItemRender(h)]}
      </div>
    );
  },
  methods: {
    getMenuItemRender(h) {
      if (this.status === 1) {
        return this.getMenuItemStatus1Render();
      } else if (this.status === 2) {
        return this.getMenuItemStatus2Render(h);
      } else if (this.status === 3) {
        return this.getMenuItemStatus3Render();
      }
    },
    /**
     * 默认显示的列表
     * @returns {*}
     */
    getMenuItemStatus1Render() {
      return (
        <el-menu-item index={this.index}>
          <span slot="title">{this.name}</span>
        </el-menu-item>
      );
    },
    /**
     * 鼠标移入时的显示列表
     * @returns {*}
     */
    getMenuItemStatus2Render(h) {
      return (
        <el-menu-item class="el-menu-item-flex-jcsb" index={this.index}>
          {/*这里不用 h 函数会导致插槽在后面*/}
          {h(
            "span",
            {
              props: {
                slot: "title"
              }
            },
            this.name
          )}
          <span class="right-btn">
            {[
              h("i", {
                class: ["el-icon-edit-outline"],
                on: {
                  click: () => {
                    this.status = 3;
                  }
                }
              }),
              // h(
              //   "el-popconfirm",
              //   {
              //     props: {
              //       title: "这是一段内容确定删除吗"
              //     },
              //     on: {}
              //   },
              //   [<i class="el-icon-delete" slot="reference"></i>]
              // )
              // <el-popconfirm
              //   title="这是一段内容确定删除吗？"
              //   onConfirm={console.log("111")}
              // >
              //   <el-button slot="reference">删除</el-button>
              // </el-popconfirm>
              h("i", {
                class: ["el-icon-delete"],
                on: {
                  click: () => {
                    this.$emit("delete", this.index);
                  }
                }
              })
            ]}

            {/*下面这段代码有毒，鼠标移入的时候也会触发这个点击事件*/}
            {/*<i*/}
            {/*  className="el-icon-edit-outline"*/}
            {/*  on-click={this.status = 3}*/}
            {/*></i>*/}
            {/*<i class="el-icon-delete" onClick={this.$emit("delete")}></i>*/}
          </span>
        </el-menu-item>
      );
    },
    /**
     * 编辑状态
     * @returns {*}
     */
    getMenuItemStatus3Render() {
      return (
        <el-menu-item class="el-menu-item-flex-jcsb" index={this.index}>
          <el-input
            vModel={this.value}
            onBlur={() => {
              this.status = 1;
              this.$emit("edit", this.value);
            }}
          ></el-input>
        </el-menu-item>
      );
    }
  }
};
