<template>
  <div class="dialog">
    <el-dialog
      title="选取文件"
      width="70%"
      :visible.sync="dialogVisible"
      @close="$emit('close')"
      :modal-append-to-body='false'
    >
      <el-container class="container">
        <!--        左侧侧边栏-->
        <dialog-left-aside
          @changeType="containerType = $event"
          @changeGroup="changeGroupId"
          ref="dialogLeftAside"
        ></dialog-left-aside>
        <!--        从URL添加-->
        <el-container v-if="containerType === 0" class="url-add-container">
          <el-main>
            <el-row>
              <el-col :span="14" :offset="1">
                <el-form
                  :model="ruleForm"
                  :rules="rules"
                  ref="ruleForm"
                  label-width="80px"
                  class="url-form"
                >
                  <el-form-item label="URL" prop="url">
                    <el-input
                      v-model="ruleForm.url"
                      placeholder="http://"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="链接文本" prop="text">
                    <el-input v-model="ruleForm.text"></el-input>
                  </el-form-item>
                </el-form>
              </el-col>
            </el-row>
          </el-main>
          <el-footer height="72px">
            <el-button type="primary" @click="onHandleAddUrl"
              >确认添加</el-button
            >
          </el-footer>
        </el-container>
        <!--        上传图片和媒体库-->
        <dialog-container
          v-else
          :groupId="groupId"
          v-on="$listeners"
        ></dialog-container>
      </el-container>
    </el-dialog>
    <!-- <div class="model" v-if="dialogVisible == true" @click="dialogVisible = false"></div> -->
  </div>
</template>

<script>
import dialogLeftAside from "./components/dialog-left-aside";
import dialogContainer from "./components/dialog-container";

import Upload from "@/packages/apis/Upload";

let api = new Upload();

export default {
  name: "u-dialog",
  components: { dialogLeftAside, dialogContainer },
  data() {
    return {
      dialogVisible: true,
      containerType: 1, // 0 从URL添加，1上传图片和媒体库
      ruleForm: {
        url: "",
        text: ""
      },
      rules: {
        url: [
          { required: true, message: "请输入URL", trigger: "blur" }
        ]
      },
      groupId: -1 // 分组id
    };
  },
  created() {},
  watch: {
    dialogVisible() {}
  },
  methods: {
    changeGroupId(groupId) {
      if (groupId) {
        this.groupId = Number.parseInt(groupId);
      } else {
        this.groupId = -1;
      }
    },
    onHandleAddUrl() {
      this.$refs.ruleForm.validate(async valid => {
        if (!valid) return false;
        let res = await api.createByURL(this.ruleForm.url, this.ruleForm.text);
        if (res.code === 200) {
          this.$message.success("添加成功");
          if (res.data.url) {
            this.$emit("getPic", {
              url: res.data.url
            });
          }
          // await this.$refs.dialogLeftAside.onHandleClassifySelect(-1)
          // this.dialogVisible = false;
        } else {
          this.$message.error(res.message);
        }
      });
    }
  }
};
</script>

<style scoped lang="less">
.dialog {
  .model {
    position: fixed;
    width: 100vw;
    height: 100vh;
    left: 0;
    top: 0;
    transform: translate(-50%,-50%);
    opacity: .5;
    background: #000;
    z-index: 999999999;
  }
	/deep/ .el-dialog {
	  position: absolute;
	  left: 50%;
	  top: 50%;
	  transform: translate(-50%, -50%);
	  margin: 0 auto!important;
	  min-width: 1200px;
    height: auto !important;
    z-index: 9999999999;
	}
	
  /deep/ .el-dialog__header {
	  display: flex;
	  align-items: center;
    border-bottom: 1px solid #d5dbe8;
	  padding: 0 20px;
	  height: 60px;
    .el-dialog__headerbtn {
      top: 0;
      height: 60px;
      line-height: 60px;
    }
  }

  /deep/ .el-dialog__body {
    padding: 0;
    .el-form {
      padding: 0 !important;
    }
  }

  .container {
    background: #edf1f5;
    height: 600px;
    width: 100%;
    .url-add-container {
      background: #fff;
      /deep/ .el-footer {
        border-top: 1px solid #e9ecef;
        display: flex;
        align-items: center;
        flex-direction: row-reverse;
      }
    }
  }

  /deep/.url-form {
    .el-form-item {
      margin-bottom: 20px;
    }
  }
}
</style>
