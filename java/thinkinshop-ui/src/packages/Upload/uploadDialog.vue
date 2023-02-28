<template>
  <div class="uploadDialog">
    <el-dialog title="上传图片" :visible.sync="dialogTableVisible">
      <el-row>
        <el-col :xl="6" :lg="6" :md="6" :sm="6" :xs="24" class="colLeft">
          <div class="Nav">
            <div class="trees-coadd">
              <div class="scollhide">
                <div class="trees" v-if="show">
                  <Tree
                    :data="treeData"
                    :render="renderContent"
                    class="trformDataeeBox"
                    ref="tree"
                  ></Tree>
                </div>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :xl="18" :lg="18" :md="18" :sm="18" :xs="24" class="colLeft">
          <div class="conter">
            <div class="bnt acea-row row-middle">
              <el-col class="mb10">
                <el-button
                  type="primary"
                  :disabled="checkPicList.length === 0"
                  @click="checkPics"
                  class="mr10"
                  >使用选中图片
                </el-button>
              </el-col>
            </div>

            <div class="pictrueList acea-row">
              <el-row :gutter="24" class="conter">
                <div v-show="isShowPic" class="imagesNo">
                  <!--                  <el type="ios-images" :size="imageSize" color="#dbdbdb"/>-->
                  <span class="imagesNo_sp">图片库为空</span>
                </div>
                <div class="acea-row">
                  <div
                    class="pictrueList_pic mr10 mb10"
                    v-for="(item, index) in pictrueList"
                    :key="index"
                  >
                    <img
                      :class="item.isSelect ? 'on' : ''"
                      :src="item.satt_dir"
                      @click.stop="changImage(item, index, pictrueList)"
                      :style="{
                        width: imageSize + 'px',
                        height: imageSize + 'px'
                      }"
                    />
                  </div>
                </div>
              </el-row>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { Tree } from "view-design";
import axios from "axios";
import qs from "qs";

axios.defaults.withCredentials = true;

let baseUrl =
  window.location.protocol +
  "//" +
  window.location.host +
  window.location.pathname;

axios.interceptors.request.use(config => {
  config.withCredentials = true;
  return config;
});

/**
 * @description 附件列表
 * @param {Object} param data {Object} 传值
 */
export async function fileListApi(data) {
  let params = {
    module: "system",
    action: "Fupload",
    type: "image",
    page: data.page,
    dataType: "json",
    group_id: data.pid
  };
  let { data: res } = await axios.get(`${baseUrl}`, {
    params: params
  });

  let list = [];
  res.data.list.map(item => {
    list.push({
      att_dir: item.file_url,
      att_id: item.id,
      satt_dir: item.file_url,
      isSelect: item.selected
    });
  });
  return {
    code: 200,
    msg: "ok",
    data: {
      list: list,
      count: res.data.count
    },
    fileData: {
      pid: 0,
      page: 1,
      limit: 12
    }
  };
}

/**
 * @description 附件分类--列表
 * @param {Object} param data {Object} 传值参数
 */
export async function getCategoryListApi() {
  let { data: res } = await axios({
    url: `${baseUrl}?module=software&action=group`,
    method: "post",
    data: qs.stringify({
      m: "list_group"
    })
  });
  res.data.list = [];
  res.data.map(item => {
    let newItem = {
      children: [],
      enname: null,
      id: item.id,
      name: item.name,
      pid: 0,
      title: item.name
    };
    res.data.list.push(newItem);
  });
  console.log(res);
  return res;
}

/**
 * 移除分类
 * @param data
 * @returns {Promise<{msg: string, data: null, status: number}>}
 */
async function removeCategory(data) {
  let postData = {};
  postData = qs.stringify({
    m: "save_group",
    data: JSON.stringify({
      id: data.id,
      name: "",
      is_default: 0,
      is_delete: 1
    })
  });
  await axios.post(`${baseUrl}?module=software&action=group`, postData, {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    }
  });
  return {
    status: 200,
    msg: "ok",
    data: null
  };
}
import { Message } from "element-ui";

export default {
  name: "uploadDialog",
  props: {
    value: {
      type: Boolean
    },
    isChoice: {
      type: Boolean,
      default: true
    },
    gridBtn: {
      type: Object,
      default: null
    },
    gridPic: {
      type: Object,
      default: null
    }
  },
  components: { Tree },
  computed: {
    imageSize() {
      if (window.outerWidth <= 1440) {
        return "80";
      }
      return "120";
    }
  },
  watch: {
    value(val) {
      this.dialogTableVisible = val;
    },
    dialogTableVisible(val) {
      this.$emit("input", val);
    }
  },
  data() {
    return {
      dialogTableVisible: false,
      buttonProps: {
        type: "default",
        size: "mini"
      },
      isShowPic: false,
      treeId: "",
      id: "",
      treeData: [
        {
          title: "全部",
          id: ""
        }
      ],
      total: 0,
      uploadName: "",
      checkPicList: [],
      fileData: {
        pid: 0,
        page: 1,
        limit: 12
      },
      show: true,
      pictrueList: []
    };
  },
  async mounted() {
    this.getList();
    this.getFileList();
  },
  methods: {
    // 选中图片
    changImage(item, index, row) {
      let selectItem = "";
      this.$set(
        this.pictrueList[index],
        "isSelect",
        item.isSelect === undefined ? true : !item.isSelect
      );
      selectItem = this.pictrueList.filter(item => {
        return item.isSelect === true;
      });
      this.checkPicList = selectItem;
      this.ids = [];
      this.checkPicList.map((item, index) => {
        this.ids.push(item.att_id);
      });
    },
    addFlag(treedata) {
      treedata.map(item => {
        this.$set(item, "flag", false);
        item.children && this.addFlag(item.children);
      });
    },
    // 树状图
    renderContent(h, { root, node, data }) {
      let actionData = [];
      if (data.id !== "" && data.pid == 0) {
        // 添加子分类按钮
        // actionData.push(h('Button', {
        //     props: Object.assign({}, this.buttonProps, {
        //         icon: 'ios-add'
        //     }),
        //     style: {
        //         marginRight: '8px',
        //         display: data.flag ? 'inline' : 'none'
        //     },
        //     on: {
        //         click: () => {
        //             this.append(root, node, data)
        //         }
        //
        //     }
        // }));
      }
      if (data.id !== "") {
        actionData.push(
          h("el-button", {
            props: Object.assign({}, this.buttonProps, {
              icon: "el-icon-edit"
            }),
            style: {
              marginRight: "8px",
              display: data.flag ? "inline" : "none"
            },
            on: {
              click: () => {
                this.editPic(root, node, data);
              }
            }
          })
        );
        actionData.push(
          h("el-button", {
            props: Object.assign({}, this.buttonProps, {
              icon: "el-icon-delete"
            }),
            style: {
              display: data.flag ? "inline" : "none"
            },
            on: {
              click: () => {
                this.remove(root, node, data, "分类");
              }
            }
          })
        );
      }
      return h(
        "div",
        {
          style: {
            display: "inline-block",
            width: "90%"
          },
          on: {
            mouseenter: () => {
              this.onMouseOver(root, node, data);
            },
            mouseleave: () => {
              this.onMouseOver(root, node, data);
            }
          }
        },
        [
          h("span", [
            h(
              "span",
              {
                style: {
                  cursor: "pointer"
                },
                class: ["ivu-tree-title"],
                on: {
                  click: e => {
                    this.appendBtn(root, node, data, e);
                  }
                }
              },
              data.title
            )
          ]),
          h(
            "span",
            {
              style: {
                display: "inline-block",
                float: "right"
              }
            },
            actionData
          )
        ]
      );
    },
    // 点击使用选中图片
    checkPics() {
      if (this.isChoice === true) {
        if (this.checkPicList.length > 1)
          return Message.warning("最多只能选一张图片");
        this.$emit("getPic", this.checkPicList[0]);
      } else {
        this.$emit("getPicD", this.checkPicList);
        console.log(this.checkPicList);
      }
    },
    // 编辑树表单
    editPic(root, node, data) {
      console.log(data);
      // this.$modalForm(categoryEditApi(data)).then(() => this.getList());
    },
    // 鼠标移入 移出
    onMouseOver(root, node, data) {
      event.preventDefault();
      data.flag = !data.flag;
    },
    // 点击树
    appendBtn(root, node, data, e) {
      this.treeId = data.id;
      this.getFileList();
      let selected = this.$refs.tree.$el.querySelectorAll(
        ".ivu-tree-title-selected"
      );
      for (let i = 0; i < selected.length; i++) {
        selected[i].className = "ivu-tree-title";
      }
      e.path[0].className = "ivu-tree-title  ivu-tree-title-selected"; // 当前点击的元素
    },
    async remove(root, node, data) {
      await removeCategory(data);
      this.treeId = 0;
      await this.getList();
      this.show = false;
      setTimeout(() => {
        this.show = true;
      }, 100);
      await this.getFileList();

      this.checkPicList = [];
    },
    getFrom() {},
    // 分类列表树
    async getList() {
      // let data = {
      //     title: '全部图片',
      //     id: ''
      // };
      try {
        let res = await getCategoryListApi(this.uploadName);
        this.treeData = res.data.list;
        // this.treeData.unshift(data);
        this.treeData2 = [...this.treeData];
        this.addFlag(this.treeData);
      } catch (res) {
        console.log(res);
        // this.$Message.error(res.msg);
      }
    },
    // 点击添加
    append(root, node, data) {
      this.treeId = data.id;
      this.getFrom();
    },
    // 文件列表
    async getFileList() {
      console.log(this.fileData);
      this.fileData.pid = this.treeId;
      try {
        let res = await fileListApi(this.fileData);
        this.pictrueList = res.data.list;
        if (this.pictrueList.length) {
          this.isShowPic = false;
        } else {
          this.isShowPic = true;
        }
        this.total = res.data.count;
      } catch (res) {
        Message.error(res.msg);
      }
    }
  }
};
</script>

<style scoped lang="less">
.acea-row {
  display: flex;
  flex-wrap: wrap;
}

.mb10 {
  margin-bottom: 10px;
}

.mr10 {
  margin-right: 10px;
}

.selectTreeClass {
  background: #d5e8fc;
}
@media only screen and (max-width: 1441px) {
  /deep/ .ivu-modal {
    top: 50px !important;
    width: 70% !important;
  }
}
.treeBox {
  width: 100%;
  height: 100%;
}
.treeBox /deep/ .ivu-tree-title-selected,
.treeBox .ivu-tree-title-selected:hover {
  color: #2d8cf0 !important;
  background-color: #fff !important;
}
.treeBox /deep/ .ivu-btn-icon-only {
  width: 20px !important;
  height: 20px !important;
}
.treeBox /deep/ .ivu-tree-title:hover {
  color: #2d8cf0 !important;
  background-color: #fff !important;
}
.pictrueList_pic {
  width: 110px;
  height: 110px;
  cursor: pointer;
}
.pictrueList_pic img {
  width: 100%;
  height: 100%;
}
@media only screen and (max-width: 1441px) {
  .pictrueList_pic {
    width: 80px;
    height: 80px;
    cursor: pointer;
  }
  .pictrueList_pic img {
    width: 100%;
    height: 100%;
  }
}
.trees-coadd {
  width: 100%;
  border-radius: 4px;
  overflow: hidden;
  position: relative;
}
.trees-coadd .scollhide {
  overflow-x: hidden;
  overflow-y: scroll;
  padding: 10px 0 10px 0;
  box-sizing: border-box;
}
.trees-coadd .scollhide .trees {
  width: 100%;
  max-height: 374px;
}
.trees-coadd .scollhide::-webkit-scrollbar {
  display: none;
}
.treeSel /deep/ .ivu-select-dropdown-list {
  padding: 0 5px !important;
  box-sizing: border-box;
}
.imagesNo {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  margin: 65px 0;
}
.imagesNo .imagesNo_sp {
  font-size: 13px;
  color: #dbdbdb;
  line-height: 3;
}
.Modal {
  width: 100%;
  height: 100%;
  background: #fff !important;
}
.Nav {
  width: 100%;
  border-right: 1px solid #eee;
}
.colLeft {
  padding-right: 0 !important;
  height: 100%;
}
.conter {
  width: 99%;
  height: 100%;
}
.conter .bnt {
  width: 100%;
  padding: 0 13px 10px 15px;
  box-sizing: border-box;
}
.conter .pictrueList {
  padding-left: 25px;
  width: 100%;
}
.conter .pictrueList img {
  width: 100%;
  border: 2px solid #fff;
}
.conter .pictrueList img.on {
  border: 2px solid #5fb878;
}
.conter .footer {
  padding: 20px;
}
</style>
