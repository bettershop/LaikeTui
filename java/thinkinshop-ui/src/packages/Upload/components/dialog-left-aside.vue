<template>
  <div class="dialog-left-aside">
    <el-aside class="aside" style="width: 160px;">
      <el-menu
        :default-active="classifyActive"
        ref="fileMenu"
        @select="onHandleClassifySelect"
      >
        <!-- <el-menu-item
          v-for="(item, index) of topClassify"
          :index="item.id.toString()"
          :key="item.id"
        > -->
        <el-menu-item
          v-for="item of topClassify"
          :index="item.id.toString()"
          :key="item.id"
        >
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </el-menu>
      <div class="gap"></div>
      <el-menu :default-active="urlAddActive" @select="onHandleAddUrlSelect">
        <el-menu-item index="addUrl">
          <span slot="title">从URL添加</span>
        </el-menu-item>
      </el-menu>
      <div class="gap"></div>
      <el-menu
        :default-active="classifyActive"
        @select="onHandleClassifySelect"
        class="classify-menu"
      >
        <dialog-left-aside-menu-item
          v-for="(item, index) of bottomClassify"
          :key="item.id"
          :index="`${item.id}`"
          :name="item.name"
          @edit="onHandleItemEdit($event, index)"
          @delete="onHandleItemDelete($event, index)"
        >
        </dialog-left-aside-menu-item>
      </el-menu>
      <el-button
        class="create-classify-btn"
        size="medium"
        type="primary"
        icon="el-icon-circle-plus"
        @click="dialogFormVisible = true"
        >创建分类
      </el-button>
    </el-aside>

    <el-dialog
      title="添加分类"
      :visible.sync="dialogFormVisible"
      append-to-body
      width="30%"
    >
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="80px"
      >
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          @click="
            () => {
              this.dialogFormVisible = false;
              this.ruleForm.name = '';
            }
          "
          >取 消</el-button
        >
        <el-button type="primary" @click="createClassify">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Upload from "../../apis/Upload";
import DialogLeftAsideMenuItem from "../components/dialog-left-aside-menu-item.jsx";

let api = new Upload();
/**
 * @emit changeType 切换类型
 */
export default {
  name: "dialog-left-aside",
  components: { DialogLeftAsideMenuItem },
  props: {},
  data() {
    return {
      urlAddActive: "", // addUrl 选中 从URL添加
      classifyActive: "-1", // 分类选中的id
      classify_list: [], // 分类的列表
      dialogFormVisible: false, // 显示添加分类表单
      ruleForm: {
        name: ""
      },
      page: 1,
      pid: "-1",
      rules: {
        name: [
          { required: true, message: "请输入分类名称", trigger: "blur" },
          { min: 1, max: 10, message: "长度在 1 到 10 个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getClassifys();
    // await this.getFileList();
  },
  watch: {
    urlAddActive(val) {
      let type = 1;
      if (val === "addUrl") {
        type = 0;
        this.classifyActive = "";
      }
      this.$emit("changeType", type);
    },

    classifyActive(val) {
      this.$emit("changeGroup", val);
    },
  },
  computed: {
    topClassify() {
      let list = [];
      let obj = {
        id: -1,
        name: '全部'
      }
      for (let i = 0; i < this.classify_list.length; i++) {
        if (i >= 4) {
          break;
        }
        list.push(this.classify_list[i]);
      }
      list[0] = obj
      return list;
    },
    bottomClassify() {
      let list = [];
      for (let i = 0; i < this.classify_list.length; i++) {
        if (i < 4) {
          continue;
        }
        list.push(this.classify_list[i]);
      }

      return list;
    }
  },
  methods: {
    async createClassify() {
      this.$refs.ruleForm.validate(async valid => {
        if (!valid) return false;
        api.createClassify(this.ruleForm.name).then(res => {
          this.dialogFormVisible = false;
          this.ruleForm.name = "";
          this.getClassifys()
          this.$message({
            message: '创建成功',
            type: 'success',
            offset: 100
          })
        })
      });
    },
    /**
     *
     */
    async getClassifys() {
      await api.getClassify().then(res => {
        this.classify_list = res.data.data.list
      })
    },

    /**
     * 切换到从URL添加
     * @param e
     */
    onHandleAddUrlSelect(e) {
      this.urlAddActive = e;
    },
    async getFileList() {
      let res = await api.getFileList({
        page: this.page,
        pid: this.pid
      });
    },
    /**
     * 切换分类
     * @param e
     */
    async onHandleClassifySelect(e) {
      this.classifyActive = e;
      this.urlAddActive = "";
      await this.getFileList();
    },
    /**
     * 分类编辑事件
     * @param e
     * @param index
     */
    async onHandleItemEdit(e, index) {
      let item = this.bottomClassify[index];
      item.name = e;
      // this.$set(this.classify_list, index, item);
      await this.editClassify(item.id, item.name);
    },
    async editClassify(id, name) {
      await api.editClassify(id, name).then(res => {
      })
      await this.getClassify();
    },
    /**
     * 处理删除事件
     * @param e
     * @param index
     */
    async onHandleItemDelete(e, index) {
      let name = this.bottomClassify[index].name;
      try {
        await this.$confirm("是否删除此分类?", '提示', {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        });
        await this.deleteClassify(e)
      } catch (e) {
      }
    },
    async deleteClassify(id) {
      await api.deleteClassify(id).then(res => {
        this.getClassifys()
      })
      // await this.getClassify()
      this.$message({
        type: "success",
        message: "删除成功!"
      });
    }
  }
};
</script>

<style scoped lang="less">
.dialog-left-aside {
  border-right: solid 1px #e6e6e6;
  overflow: hidden auto;

  > .aside {
    width: 200px;

    /deep/ .el-menu {
      background: none;
      border-right: none;
    }
  }

  .gap {
    width: 160px;
    height: 1px;
    background: #d5dbe8;
    margin: 10px auto;
  }

  .create-classify-btn {
    margin: 10px auto;
    display: block;
  }

  .classify-menu {
    max-height: 448px;
    overflow-y: auto;
    &::-webkit-scrollbar {
      display: none;
    }
  }
}
</style>
