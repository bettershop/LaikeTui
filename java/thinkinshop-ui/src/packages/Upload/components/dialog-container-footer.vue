<template>
  <div class="dialog-container">
    <el-footer height="72px">
      <el-row
        type="flex"
        justify="space-between"
        align="middle"
        style="height: 100%"
      >
        <div>
          <el-row type="flex" style="height: 40px" v-if="selectList.length">
            <div style="margin-right: 20px;">
              <div class="footer-left-info">
                <el-row align="top" type="flex">
                  <el-col>
                    <h4 style="line-height: 12px;">
                      已选{{ selectList.length }}个
                    </h4>
                  </el-col>
                </el-row>
                <el-row align="bottom">
                  <el-col>
                    <el-link
                      :underline="false"
                      type="danger"
                      style="line-height: 12px;"
                      @click="$emit('clear')"
                      >清空
                    </el-link>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div>
              <el-image
                style="width: 40px;height: 40px;margin-right: 8px;"
                v-for="(item, index) of selectList"
                :class="{ 'image-checked': index === selectKey }"
                :src="item.url"
                :key="index"
              ></el-image>
            </div>
          </el-row>
        </div>
        <div>
          <div style="display:flex;flex-direction: row-reverse">
            <div>
              <el-button @click="openDialog" :disabled="selectList.length <= 0">移动至分类</el-button>
              <el-button type="primary" :disabled="selectList.length <= 0" @click="$emit('add')"
                >确定添加</el-button
              >
            </div>
          </div>
        </div>
      </el-row>

      <el-dialog
        title="移动分类"
        :visible.sync="innerVisible"
        append-to-body
        width="30%"
      >
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="80px"
        >
          <el-form-item label="分类名称" prop="group_id">
            <el-select v-model="ruleForm.group_id">
              <el-option
                v-for="(item,index) in classify_list"
                :key="index"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button
            @click="
              () => {
                this.innerVisible = false;
                this.ruleForm.group_id = '';
              }
            "
            >取 消</el-button
          >
          <el-button type="primary" @click="onMoveClassify">确 定</el-button>
        </div>
      </el-dialog>
    </el-footer>
  </div>
</template>

<script>
import Upload from '@/packages/apis/Upload'
let api = new Upload();

export default {
  name: "dialog-container-footer",
  props: {
    list: {
      type: Array,
      default: () => {
        return [];
      }
    },
    checkedKey: {
      type: [Number, null],
      default: null
    }
  },
  watch: {
    list: {
      handler(val) {
      },
      immediate: true,
      deep: true
    }
  },
  data() {
    return {
      innerVisible: false,
      classify_list: [],
      ruleForm: {
        group_id: "",
        data: ""
      },
      rules: {
        group_id: [{ required: true, message: "请选择分类", trigger: "blur" }]
      }
    };
  },
  computed: {
    selectList() {
      return this.list.filter(item => item.checked);
    },
    selectKey() {
      return this.selectList.findIndex(
        item => item.id === this.list[this.checkedKey].id
      );
    }
  },
  created() {},
  methods: {
    async openDialog() {
      this.innerVisible = true;
      await this.getClassifys();
    },
    async getClassifys() {
      await api.getClassify().then(res => {
        this.classify_list = res.data.data.list
      })
    },
    onMoveClassify() {
      this.$refs.ruleForm.validate(async valid => {
        if (!valid) return false;
        await api.moveClassify({
          groupId: this.ruleForm.group_id,
          id: this.list[this.checkedKey].id
        });
        this.$message.success("移动成功");
        this.$emit("move");
        this.innerVisible = false;
      });
    }
  }
};
</script>

<style scoped lang="less">
.dialog-container {
  z-index: 99999999 !important;
  .el-footer {
    .footer-left-info {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      height: 40px;
    }

    .image-checked {
      height: 43px;
      width: 43px;
      padding: 1px;
      border: 2px solid #2890ff;
    }
  }
}
</style>
