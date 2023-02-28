<template>
  <div class="container">
    <div class="notice">
      <header>公告列表</header>
      <div class="list">
        <div class="item" @click="detail(item.id)" v-for="(item,index) in noticeList" :key="index">
          <p class="title">{{item.title}}</p>
          <div class="date">{{item.startdate | dateFormat}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import main from "@/webManage/js/mall/systemNotice/noticeList";

export default main
</script>

<style scoped lang="less">
  @import "../../../webManage/css/mall/systemNotice/noticeList";
</style>
