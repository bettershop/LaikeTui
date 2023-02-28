<template>
  <el-container class="dialog-container">
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane label="上传文件" name="upload" class="upload-tab-pane">
        <div class="upload-wrap">
          <el-upload
            class="upload-drag"
            drag
            :action="actionUrl"
            name="file"
            multiple
            :data="uploadData"
            :show-file-list="false"
            :before-upload="handleBeforeUpload"
            :on-success="handleUploadSuccess"
            :on-change="changes"
            accept="image/*,audio/*,video/*"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div
              class="el-upload__tip"
              slot="tip"
              style="text-align: center"
              v-if="maxSize"
            >
              最大上传文件大小：{{ maxSizeText }}
            </div>
          </el-upload>
        </div>
        <div class="gap"></div>
        <dialog-container-footer></dialog-container-footer>
      </el-tab-pane>
      <el-tab-pane label="媒体库" name="library" class="file-list-tab-pane">
        <div class="file-list">
          <div class="file-list-left">
            <el-form
              class="file-info-form"
              :model="ruleForm2"
              :rules="rules2"
              ref="ruleForm2"
            >
              <el-row
                class="file-list-left-header"
                type="flex"
              >
                <el-col :span="4" class="all-date">
                  <el-date-picker
                    v-model="ruleForm2.time"
                    type="month"
                    @change="onTimeChange"
                    placeholder="全部日期"
                  >
                  </el-date-picker>
                </el-col>

                <el-col :span="5">
                  <el-input
                    v-model="ruleForm2.title"
                    placeholder="搜索文件项目"
                    @blur="queryFileList"
                  ></el-input>
                </el-col>
              </el-row>
            </el-form>

            <ul
              class="infinite-list"
              v-infinite-scroll="load"
              style="overflow: auto"
            >
              <li
                v-for="(item, index) of list"
                :key="index"
                @click="onHandleClickFileLi(index,item)"
                :class="{ active: checkedKey === index }"
              >
                <img :src="item.url" alt="" />
                <el-checkbox
                  class="infinite-list-checked"
                  v-if="item.checked"
                  v-model="item.checked"
                ></el-checkbox>
              </li>
              <!--              <li v-for="i in count" class="infinite-list-item">{{ i }}</li>-->
            </ul>
          </div>

          <div class="file-list-right">
            <template v-if="imageInfo">
              <h4>
                附件详情
              </h4>
              <p>{{ imageInfo.image_name }}</p>
              <div class="image-info">
                <img class="image" :src="imageInfo.url" />
                <div class="image-info-content">              
                  <p>{{ imageInfo.add_time | dateFormat1 }}</p>
                  <p>{{ imageInfo.size }}</p>
                  <p>{{ imageInfo.widthAndHeight }}</p>
                  <div v-if="imageInfo.groupName">
                    <el-link
                      type="primary"
                      :underline="false"
                      @click="onMoveClassify(imageInfo.id)"
                      >移出{{ imageInfo.groupName }}</el-link
                    >
                  </div>
                  <div>
                    <el-link
                      type="danger"
                      :underline="false"
                      @click="onDeleteFile(imageInfo.id)"
                      >永久删除</el-link
                    >
                  </div>
                </div>
              </div>

              <el-form
                class="file-info-form"
                :model="ruleForm"
                :rules="rules"
                ref="ruleForm"
				        label-position="top"
              >
                <el-form-item label="URL" prop="url">
                  <el-input v-model="ruleForm.url" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="标题" prop="title">
                  <el-input
                    v-model="ruleForm.title"
                    
                  ></el-input>
                </el-form-item>
                <el-form-item label="说明" prop="explain">
                  <el-input
                    v-model="ruleForm.explain"
                    
                  ></el-input>
                </el-form-item>

                <el-form-item label="代替文本" prop="alternative_text">
                  <el-input
                    v-model="ruleForm.alternative_text"
                    
                  ></el-input>
                </el-form-item>
                <el-form-item label="图像描述" prop="describe">
                  <el-input
                    type="textarea"
                    :rows="2"
                    v-model="ruleForm.describe"
                    
                  ></el-input>
                </el-form-item>
              </el-form>
            </template>
          </div>
        </div>

        <dialog-container-footer
          :list="list"
          :checkedKey="checkedKey"
          @move="queryFileList"
          @clear="onHandleClear"
          @add="onHandleAdd"
        ></dialog-container-footer>
      </el-tab-pane>
    </el-tabs>
  </el-container>
</template>

<script>
import dialogContainerFooter from "./dialog-container-footer";
import Config from "../../apis/Config";
import Upload from "../../apis/Upload";
import moment from "moment";
import { conver } from "../../utils/utils";
import { getStorage } from '@/utils/storage'
let api = new Upload();

let actionUrl = Config.baseUrl + "/resources/file/uploadFiles"

export default {
  name: "dialog-container",
  inject: ["indexCom"],
  props: {
    groupId: {
      type: Number
    },
    groupList: {
      type: Array,
      default: () => {
        return [];
      }
    }
  },
  data() {
    return {
      activeName: "library", // upload library
      actionUrl,
      list: [],
      page: 0,
      pageSize: 30,
      checkedKey: null,
      hasMore: true,
      ruleForm: {
        url: "",
        title: "",
        explain: "",
        alternative_text: "",
        describe: ""
      },
      rules: {},
      ruleForm2: {
        time: "",
        title: "",
        start_time: "",
        end_time: ""
      },
      rules2: {},
    };
  },
  computed: {
    uploadData() {
      {
        return {
          api: 'resources.file.uploadFiles',
          storeId: this.$route.path.split('/')[1] == 'Platform' ? 0 : getStorage('laike_admin_uaerInfo').storeId,
          groupId: this.groupId,
          // mchId: getStorage('laike_admin_uaerInfo').mchId,
          uploadType: 2,
          accessId: this.$store.getters.token
        };
      }
    },
    maxSelectNum() {
      return this.indexCom.maxSelectNum;
    },
    limit() {
      return this.indexCom.limit;
    },
    maxSize() {
      return this.indexCom.maxSize;
    },
    maxSizeText() {
      return conver(this.indexCom.maxSize);
    }
  },
  asyncComputed: {
    // 异步获取图片信息
    async imageInfo() {
      if (this.checkedKey !== null) {
        if(this.list[this.checkedKey].url) {
          let res = await fetch(this.list[this.checkedKey].url).catch(err => {
            return {
              id: this.list[this.checkedKey].id,
              image_name: this.list[this.checkedKey].image_name,
              url: this.list[this.checkedKey].url,
              add_time: this.list[this.checkedKey].add_time,
            }
          })
          console.log(res);
          if(res && res.status == 200) {
            let data = await res.blob();
            let size = Math.ceil(data.size / 1024);
            let { width, height } = await this.getMeta(
              this.list[this.checkedKey].url
            )
            console.log(456);
            return {
              id: this.list[this.checkedKey].id,
              image_name: this.list[this.checkedKey].image_name,
              url: this.list[this.checkedKey].url,
              add_time: this.list[this.checkedKey].add_time,
              size: `${size}kb`,
              widthAndHeight: `${width} x ${height}`,
            };
          } else {
            console.log(123);
            return {
              id: this.list[this.checkedKey].id,
              image_name: this.list[this.checkedKey].image_name,
              url: this.list[this.checkedKey].url,
              add_time: this.list[this.checkedKey].add_time,
            }
          }
        } else {
          return {
            id: this.list[this.checkedKey].id,
            image_name: this.list[this.checkedKey].image_name,
            url: this.list[this.checkedKey].url,
            add_time: this.list[this.checkedKey].add_time,
          }
        }
      } else {
        console.log('哈哈哈哈哈哈哈哈');
        return false;
      }
    }
  },
  watch: {
    activeName(val) {
      if (val === "library") {
        this.empty();
        this.list = []
        this.getFileList();
      } else {
      }
    },
    async groupId() {
      this.empty();
      await this.getFileList();
    },
    checkedKey(val) {
      if (val !== null) {
        this.$set(this.ruleForm, "url", this.list[this.checkedKey].url);
        this.$set(this.ruleForm, "id", this.list[this.checkedKey].id);
        this.$set(this.ruleForm, "title", this.list[this.checkedKey].title);
        this.$set(this.ruleForm, "explain", this.list[this.checkedKey].explain);
        this.$set(
          this.ruleForm,
          "alternative_text",
          this.list[this.checkedKey].alternative_text
        );
        this.$set(
          this.ruleForm,
          "describe",
          this.list[this.checkedKey].describe
        );
      } else {
      }
    }
  },
  components: { dialogContainerFooter },
  mounted() {
    // this.getFileList();
  },
  methods: {
    async onHandleAdd() {
      let checkedList = this.list.filter(item => item.checked);
      if (checkedList.length > this.maxSelectNum) {
        this.$message.error("超过最大所选数量");
        return false;
      }

      if (this.limit === 1) {
        this.$emit("getPic", {
          url: this.list.filter(item => item.checked)[0].url
        });
      } else {
        this.$emit(
          "getPicD",
          this.list.filter(item => item.checked)
        );
      }
      await api.modifyInfo({
        id: this.list[this.checkedKey].id,
        groupId: this.groupId,
        title: this.ruleForm.title,
        explain: this.ruleForm.explain,
        alternative_text: this.ruleForm.alternative_text,
        describe: this.ruleForm.describe,
      }).then(res => {
      })
    },
    onHandleClear() {
      this.checkedKey = null;
      this.list = this.list.map(item => {
        item.checked = false;
        return item;
      });
    },
    queryFileList() {
      this.list = [];
      this.page = 1;
      this.checkedKey = null;
      this.getFileList();
    },
    onTimeChange(time) {
      let startTime = moment(time);
      let endTime = moment(time)
        .add(1, "M")
        .subtract(1, "d");

      this.$set(this.ruleForm2, "start_time", startTime.format("YYYY-MM-DD"));
      this.$set(this.ruleForm2, "end_time", endTime.format("YYYY-MM-DD"));
      this.queryFileList();
    },
    async editFileInfo() {
      this.$set(this.list[this.checkedKey], "title", this.ruleForm.title);
      this.$set(this.list[this.checkedKey], "explain", this.ruleForm.explain);
      this.$set(
        this.list[this.checkedKey],
        "alternative_text",
        this.ruleForm.alternative_text
      );
      this.$set(this.list[this.checkedKey], "describe", this.ruleForm.describe);

      let res = await api.editFileInfo(this.ruleForm);
    },
    // 移出分类
    async onMoveClassify(id) {
      await api.moveClassify(-1, [
        {
          id: id
        }
      ]);
      this.$message.success("移动成功");
      this.empty();
      await this.getFileList();
    },
    // 初始化清空数据
    empty() {
      this.list = [];
      this.page = 1;
      this.pageSize = 30
      this.$refs.ruleForm2.resetFields();
      this.checkedKey = null;
    },
    // 永久删除图片
    async onDeleteFile(id) {
      await api.deleteFile(id).then(res => {
        this.$message.success("删除成功");
        this.empty();
        this.getFileList();
      })
    },
    /**
     * 获取分类名称
     * @returns {Promise<null|*>}
     */
    async getClassifyName() {
      api.getClassify().then(res => {
        let key = res.data.list.findIndex(item => item.id == this.groupId)
        if (res.data.list[key].id === -1) {
          // 全部商品不显示移除分类按钮
          return null
        }
        return res.data.list[key].name
      })
      
    },
    async getMeta(url) {
      let img = new Image();
      img.src = url;
      return new Promise(resolve => {
        img.onload = function() {
          return resolve({
            width: this.width,
            height: this.height
          });
        };
      });
    },
    onHandleClickFileLi(index,item) {
      console.log(item);
      let checkedList = this.list.filter(item => item.checked);
      if (
        checkedList.length >= this.maxSelectNum &&
        !this.list[index].checked &&
        this.limit !== 1
      ) {
        this.$message.error("超过最大所选数量");
        return false;
      }
      if (this.limit === 1) {
        this.list = this.list.map(item => {
          item.checked = false;
          return item;
        });
        this.list[index].checked = true;
      } else {
        this.list[index].checked = !this.list[index].checked;
      }

      this.checkedKey = index;
    },
    load() {
      if (this.hasMore) {
        this.page++;
        this.getFileList();
      }
    },
    handleClick() {},
    changes() {
    },
    // 上传成功
    handleUploadSuccess(res) {
      console.log(res);
      this.activeName = "library";
    },
    // 上传之前的处理
    handleBeforeUpload(file) {
      if (this.maxSize) {
        if (file.size > this.maxSize) {
          this.$message.error("超出最大上传大小");
          return false;
        }
      }
    },
    // 获取文件列表
    async getFileList() {
      let that = this
      await api.getFileList({
        page: this.page,
        pageSize: this.pageSize,
        pid: this.groupId,
        start_time: this.ruleForm2.start_time,
        end_time: this.ruleForm2.end_time,
        title: this.ruleForm2.title,
        storeId: that.$route.path.split('/')[1] == 'Platform' ? 0 : getStorage('laike_admin_uaerInfo').storeId
      }).then(res => {
        if (res.data.code === '200') {
          res.data.data.list = res.data.data.list.map(item => {
            item["checked"] = false;
            return item;
          })
          this.list = this.list.concat(res.data.data.list);
          if (res.data.data.list.length) {
            this.hasMore = true;
          } else {
            this.hasMore = false;
          }
        } else {
          this.hasMore = false;
        }
      });
      
    }
  }
};
</script>

<style scoped lang="less">
@import "../style/dialog-container.less";
</style>
