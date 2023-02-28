<template>
  <div class="container">
    <div class="iframe-content">
      <div class="title">{{notice.title}}</div>
      <div class="desc">{{notice.add_time | dateFormat}}</div>
      <div class="content" v-html="notice.content"></div>
    </div>
  </div>
</template>

<script>
import main from "@/webManage/js/mall/systemNotice/noticeDetail";

export default main
</script>

<style scoped lang="less">
  @import "../../../webManage/css/mall/systemNotice/noticeDetail";
</style>
