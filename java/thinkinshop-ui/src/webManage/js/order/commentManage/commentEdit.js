import {del, save, index} from "@/api/order/comment";
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'edit',
  //初始化数据
  data() {
    return {
      mainData: {}, replyText: null, star: 0,
      starGroup: ['差评', '失望', '一般', '满意', '好评'],
      isEdit: false,
      //评论图集
      commentImgList: null,
      //追评图集
      reviewImgList: null,
    }
  },
  //组装模板
  created() {
    this.loadData();
  },
  mounted() {
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'commentList') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },

  methods: {
    async loadData() {
      await index({
        api: 'admin.order.getCommentsDetailInfoById',
        cid: this.$route.params.id
      }).then(data => {
        if (!isEmpty(data)) {
          this.mainData = data.data.data.list[0];
          this.mainData.CommentType = Number(this.mainData.CommentType);
          this.star = this.mainData.CommentType
          //评价图数据处理
          this.commentImgList = this.mainData.images;
          if (!isEmpty(this.commentImgList)) {
            let imgList = [];
            this.commentImgList.forEach(function (item) {
              imgList.push(item.url)
            })
            this.commentImgList = imgList;
          }
          //追评图数据处理
          this.reviewImgList = this.mainData.review_images;
          if (!isEmpty(this.reviewImgList)) {
            let imgList = [];
            this.reviewImgList.forEach(function (item) {
              imgList.push(item.url)
            })
            this.reviewImgList = imgList;
          }
        } else {
          this.$router.go(-1);
        }
      });
    },
    async Save() {
      this.dialogVisible = true;
      let title = '修改评论';
      let param = {
        api: 'admin.order.updateCommentsDetailInfoById',
        cid: this.mainData.id,
        commentText: decodeURIComponent(this.mainData.content),
        commentType: this.mainData.CommentType,
        commentImgUrls: this.commentImgList.toString(),
        review: decodeURIComponent(this.mainData.review),
        reviewImgList: this.reviewImgList.toString(),
      }

      await save(param).then(res => {
        if (!isEmpty(res)) {
          this.$message({
            type: 'success',
            message: title + '成功!'
          })
        }
        this.dialogVisible = false;
        this.$router.go(-1)
      })
    },


  }

}
