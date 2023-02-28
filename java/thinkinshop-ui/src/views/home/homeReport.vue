<template>
  <div class="main">
    <!-- MAIN CONTENT -->
    <div class="main-content">
      <div class="container-fluid">
        <!-- OVERVIEW -->
        <div class="panel panel-headline">
          <div class="panel-body">
            <el-row :gutter="10">
              <el-col :span="4">
                <div class="grid-content bg-purple" @click="toOrderDetail('0')">
                  <div class="metric">
                    <div class="leftIcon" style="background: #68c8c7;"><img
                      src="../../assets/imgs/fukuan.png"/>
                    </div>
                    <div class="rightNum">
                      <div>
                        <span class="title">待付款</span>
                        <span class="number" style="color:#68c8c7 ;">{{dataMain.notPay}}</span>
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="grid-content bg-purple" @click="toOrderDetail('1')">
                  <div class="metric">
                    <div class="leftIcon" style="background: #ff6c60;"><img
                      src="../../assets/imgs/fahuo.png"/>
                    </div>
                    <div class="rightNum">
                      <div>
                        <span class="title">待发货</span>
                        <span class="number" style="color:#ff6c60 ;">{{dataMain.noSendOrderNum}}</span>
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="grid-content bg-purple" @click="toOrderDetail('2')">
                  <div class="metric">
                    <div class="leftIcon" style="background: #feb04c;"><img
                      src="../../assets/imgs/shouhuo.png"/>
                    </div>
                    <div class="rightNum">
                      <div>
                        <span class="title">待收货</span>
                        <span class="number" style="color:#feb04c ;">{{dataMain.notDeliverNum}}</span>
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="grid-content bg-purple" @click="toOrderDetail('5')">
                  <div class="metric">
                    <div class="leftIcon" style="background: #a6d867;"><img
                      src="../../assets/imgs/pingjia.png"/>
                    </div>
                    <div class="rightNum">
                      <div>
                        <span class="title">待评价</span>
                        <span class="number" style="color:#a6d867 ;">{{dataMain.orderNotCommentsNum}}</span>
                      </div>
                    </div>
                    <div class="clearfix"></div>
                  </div>
                </div>
              </el-col>
              <el-col :span="5">
                <div class="metric" @click="toOrderDetail('退货')">
                  <div class="leftIcon" style="background: #57c8f2;"><img
                    src="../../assets/imgs/tuihuo.png"/>
                  </div>
                  <div class="rightNum">
                    <div>
                      <span class="title">退货</span>
                      <span class="number" style="color:#57c8f2 ;">{{dataMain.orderReturnWaitNum}}</span>
                    </div>
                  </div>
                  <div class="clearfix"></div>
                </div>
              </el-col>
            </el-row>

            <el-row :gutter="5">
              <el-col :span="9">
                <div class="mainLeft">
                  <div class="realDiv">
                    <div class="realDivTitle">实时营业额</div>
                    <div class="realDivMainNum">
                      <span class="number">{{dataMain.toDaySaleTotal }}</span>
                      <span class="percentage">
                      <!-- el-icon-caret-top  el-icon-caret-bottom -->
                      <i class="el-icon-caret-bottom"></i>
                        <!-- upText downText  -->
                      <span class="downText">
                          {{dataMain.dayAmtProportion}}%
                      </span>
                    </span>
                    </div>
                    <div class="realDiv3Num">
                      <div class="realNum3">
                        <div class="numText_copy" style="position: relative;">
                          <p class="numText">昨日营业额
                          <el-tooltip class="item" effect="light" content="昨日的有效订单金额" placement="right-start">
                            <span class="my_mark" id="mark_img_1"></span>
                          </el-tooltip>
                          </p>
                          <div class="mark_div" id="mark_img_11"><span></span>昨日的有效订单金额</div>
                          <p class="num">{{dataMain.yesterdaySale}}</p>
                        </div>
                      </div>
                      <div class="realNum3">
                        <div class="numText_copy" style="position: relative;">
                          <p class="numText">本月营业额
                            <el-tooltip class="item" effect="light" content="本月的有效订单总金额" placement="right-start">
                              <span class="my_mark" id="mark_img_2"></span>
                            </el-tooltip>
                          </p>
                          <div class="mark_div" id="mark_img_22"><span></span>本月的有效订单总金额</div>
                          <p class="num">{{dataMain.monthAmtTotal}}</p>
                        </div>
                      </div>
                      <div class="realNum3">
                        <div class="numText_copy" style="position: relative;">
                          <p class="numText">累计营业额
                          <el-tooltip class="item" effect="light" content="自开店以来的累积营业额" placement="right-start">
                            <span class="my_mark" id="mark_img_3"></span>
                          </el-tooltip>
                          </p>
                          <div class="mark_div" id="mark_img_33"><span></span>自开店以来的累积营业额</div>
                          <p class="num">{{z_total}}</p>
                        </div>
                      </div>
                      <div class="clearfix"></div>
                    </div>
                  </div>
                  <div class="realDiv">
                    <div class="realDivTitle">实时订单数</div>
                    <div class="realDivMainNum">
                      <span class="number">{{dataMain.today_order_num}}</span>
                      <span class="percentage">
                      <!-- el-icon-caret-top  el-icon-caret-bottom -->
                      <i class="el-icon-caret-bottom"></i>
                        <!-- upText downText  -->
                      <span class="downText">
                          {{dataMain.yoy_order}}%
                      </span>
                    </span>
                    </div>
                    <div class="realDiv3Num">
                      <div class="realNum3">
                        <div>
                          <p class="numText">昨日订单</p>
                          <p class="num">{{dataMain.yesterdayOrderNum}}</p>
                        </div>
                      </div>
                      <div class="realNum3">
                        <div>
                          <p class="numText">本月订单</p>
                          <p class="num">{{dataMain.monthOrderNum}}</p>
                        </div>
                      </div>

                      <div class="realNum3" style="border:none">
                        <div>
                          <p class="numText">累计订单</p>
                          <p class="num">{{z_num}}</p>
                        </div>
                      </div>
                      <div class="clearfix"></div>
                    </div>
                  </div>
                  <div class="realDiv">
                    <div class="realDivTitle">实时访客数</div>
                    <div class="realDivMainNum">
                      <span class="number">{{dataMain.todayBrowseTotal}}</span>
                      <span class="percentage">
                      <!-- el-icon-caret-top  el-icon-caret-bottom -->
                      <i class="el-icon-caret-bottom"></i>
                        <!-- upText downText  -->
                      <!-- <span class="downText">
                          {{dataMain.daySale}}%
                      </span> -->
                    </span>
                    </div>
                    <div class="realDiv3Num">
                      <div class="realNum3">
                        <div>
                          <p class="numText">昨日访客</p>
                          <p class="num">{{dataMain.yesterdayBrowseTotal}}</p>
                        </div>
                      </div>
                      <div class="realNum3">
                        <div>
                          <p class="numText">本月访客</p>
                          <p class="num">{{dataMain.monthBrowseTotal}}</p>
                        </div>
                      </div>
                      <div class="realNum3" style="border:none">
                        <div>
                          <p class="numText">累计访客</p>
                          <p class="num">{{dataMain.coustomer_num_z}}</p>
                        </div>
                      </div>
                      <div class="clearfix"></div>
                    </div>
                  </div>
                </div>
              </el-col>
              <el-col :span="15">
                <div class="mainRight">
                  <div class="tjtDiv" style="height: 315px;background: transparent;">
                    <div class="member">
                      <div class="realDivTitle">会员统计</div>
                      <div class="title1" style="float: right; margin-right: 20px">累计会员: {{dataMain.userTotal}}人
                      </div>
                      <div class="tjtDivIn" id="memberTJT" style="height:240px;margin: 0 auto;">

                      </div>
                    </div>
                    <div class="news">
                      <div class="realDivTitle" style="border-bottom: 2px solid #eee;">公告</div>
                      <ul style="height: 240px;">
                        <li v-for="(item,index) in dataMain.sysNotice" :key="index" @click="toNoticeDetail(item.id)">
                          <div>
                            <span>{{item.title}}</span>
                            <div class="newsDate">{{item.add_time | dateFormat}}</div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="tjtDiv" style="height: 415px;">
                    <div class="realDivTitle">订单统计</div>
                    <div class="title1" style="float: right; margin-right: 20px">
                      <a href="javascript:;" @click="loadOrderReport(2)" :class='["type1",this.orderType===2 ? " activeTJTBtn" : ""]'>最近七天</a>
                      <a href="javascript:;" @click="loadOrderReport(3)" :class='["type2",this.orderType===3 ? " activeTJTBtn" : ""]'>本月</a>
                      <a href="javascript:;" @click="loadOrderReport(4)" :class='["type3",this.orderType===4 ? " activeTJTBtn" : ""]'>本年</a>
                    </div>
                    <div class="tjtDivIn" id="ddTJT" style="height:345px;"></div>
                  </div>
                </div>
              </el-col>
            </el-row>

          </div>
        </div>

      </div>
    </div>

  </div>
</template>

<script>
import main from '@/webManage/js/home/homeReport'

export default main
</script>

<style scoped lang="less">
  @import '../../webManage/css/home/homeReport';
</style>
