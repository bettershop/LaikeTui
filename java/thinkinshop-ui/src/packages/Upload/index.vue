<template>
  <div class="l-upload">
    <div
      class="img-item"
      v-for="(url, index) of fileList"
      :key="index"
      :style="{
        width: size + 'px',
        height: size + 'px',
        marginRight: marginRight
      }"
    >
      <img
        :src="url"
        alt=""
        :style="{ width: size + 'px', height: size + 'px' }"
      />
      <div class="actions" v-if="!disabled">
        <i class="el-icon-delete" @click="removeImg(index)"></i>
        <i class="set-main" v-if="index != 0 && mainImg" @click="setMain(index)">设为主图</i>
      </div>
      <i class="img-main" v-if="index == 0 && mainImg">主图</i>
    </div>
    <div
      class="upload"
      @click="dialogVisible = true"
      v-if="fileList.length < limit"
      :style="{
        width: size + 'px',
        height: size + 'px',
        marginRight: marginRight
      }"
    >
      <i class="el-icon-plus"></i>
    </div>
    <div class="desc">
      {{ text }}
    </div>

    <!--    <upload-dialog-->
    <!--      :isChoice="true"-->
    <!--      @getPic="handleGetPic"-->
    <!--      @getPicD="handleGetPicD"-->
    <!--      v-model="dialogVisible"-->
    <!--    ></upload-dialog>-->
    <uDialog
      v-if="dialogVisible"
      @getPicD="handleGetPicD"
      @getPic="handleGetPic"
      @close="dialogVisible = false"
    ></uDialog>
  </div>
</template>

<script>
import uDialog from "@/packages/Upload/dialog";

export default {
  name: "l-upload",
  provide() {
    return {
      indexCom: this
    };
  },
  props: {
    limit: {
      type: Number,
      default: 1
    },
    text: {
      type: String,
      default: "（最多上传一张，建议上传1：1尺寸的图片）"
    },
    value: {
      type: [Array, String],
      default: ""
    },
    list: {
      type: Array,
      default: () => {
        return [];
      }
    },

    mainImg: {
      // 是否显示主题
      type: Boolean,
      default: false
    },

    append_to_body: {
      type: Boolean,
      default: true
    },

    size: {
      type: Number,
      default: 80
    },
    disabled: {
      type: Boolean
    },
    maxSize: {
      type: Number,
      default: 0
    }
  },
  watch: {
    "value.length": {
      handler() {
        if (Array.isArray(this.value)) {
          this.fileList = this.value;
        } else {
          if (this.value) {
            this.fileList = [this.value];
          }
        }

        if (this.limit === 1) return 1;
        this.maxSelectNum = this.limit - this.fileList.length;
        console.log(this.maxSelectNum);
      },
      // 这里是关键，代表递归监听 demo 的变化
      deep: true,
      immediate: true
    },
    limit(val) {
      this.imgLimit = val;
      if (this.limit === 1) return 1;
      this.maxSelectNum = this.limit - this.fileList.length;
    }
  },
  computed: {
    isChoice() {
      return this.limit === 1;
    },
    marginRight() {
      if (this.text) {
        return "15px";
      }
      return "0";
    }
  },
  components: {
    // eslint-disable-next-line vue/no-unused-components
    uDialog
  },
  data() {
    return {
      dialogImageUrl: "",
      dialogVisible: false,
      imgNumber: 0,
      imgLimit: 1,
      fileList: [],
      maxSelectNum: 1
    };
  },
  methods: {
    getLimit() {
      return this.limit;
    },
    removeImg(index) {
      this.fileList.splice(index, 1);
      this.$emit("remove", index);
      if (this.limit === 1) {
        this.$emit("input", "");
      } else {
        this.$emit("input", this.fileList);
      }
    },

    setMain(index) {
      let imgUrl = this.fileList[index];
      this.fileList.splice(index, 1);
      this.fileList.unshift(imgUrl);
    },

    // 单选
    handleGetPic(data) {
      console.log(data);
      // debugger;
      this.fileList.push(data.url);
      this.$emit("select", data.url);
      this.$emit("input", data.url);
      this.dialogVisible = false;
    },
    // 多选
    handleGetPicD(data) {
      let list = [];
      for (let i = 0; i < data.length; i++) {
        list.push(data[i].url);
      }
      list = this.fileList.concat(list);
      this.fileList = list;
      this.$emit("select", list);
      this.$emit("input", list);
      this.dialogVisible = false;
    }
  }
};
</script>
