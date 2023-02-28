<template>
  <div class="navbar" ref="navHeader">
    <div class="left-control">
      <img :src="$store.getters.merchants_Logo" alt="">
      <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />
    </div>

    <div class="right-block">
      <ul class="Hui-userbar">
        <li class="headerLi" style="width: auto;">
            <a href="http://www.laiketui.com/"  target="_blank" >
                <font style="color: yellow;font-size: large;">{{ d }}</font>
            </a>
        </li>
        <li class="dropDown">
          <div class="top-dropDown">
            <img :src="$store.getters.head_img" alt="">
            <span>{{ username }}</span>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </div>
          <ul class="dropDown-menus">
            <li @click="dialogShow">
              <i class="el-icon-lock"></i>
              <span>{{ $t('topNav.changePassword') }}</span>
            </li>
            <li @click="dialogShow2">
              <i class="el-icon-user"></i>
              <span>{{ $t('topNav.basicInformation') }}</span>
            </li>
          </ul>
        </li>
        <li class="changeStore" v-if="type == 0">
          <div class="top-changeStore">
            <img class="shanghu" src="@/assets/imgs/shanghu.png" alt="">
            <span>{{ defaultVersion }}</span>
            <img class="qiehuan" src="@/assets/imgs/qiehuan.png" alt="">
          </div>
          <ul class="changeStore-menus">
            <li v-for="(item,index) in versionList" :key="index" @click="enterSystem(item)">
              <span>{{ item.name }}</span>
              <img v-show="item.id === showImg" src="../../assets/imgs/gouxuan.png" alt="">
            </li>
          </ul>
        </li>
        <li class="headerLi">
          <div class="top-headerLi">
            <el-badge :value="allNum" :max="99" class="totle-item">
            </el-badge>
            <img src="@/assets/imgs/xiaoxi.png" alt="">
          </div>
          <ul class="headerLi-menus">
            <li class="leveone">
              <div class="topmenus" @click="orderShow = !orderShow">
                <span class="order-remind">订单提醒</span>
                <div class="read">
                  <div class="operation">
                    <div v-if="orderInfo.total" class="operation-read" @click.stop="orderAllRead">
                      <i class="laiketui laiketui-biaojiweiyidu"></i>
                      <span @click="noticeReads('1,2,3,4,5,6')">一键已读</span>
                    </div>
                    <el-badge :value="orderInfo.total" :max="99" class="item">
                    </el-badge>
                    <i :class="[orderShow ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                  </div>
                </div>
              </div>
              <ul class="detail-menus" v-show="orderShow">
                <li class="levetwo">
                  <div class="submenu" @click="notificationDelivery = !notificationDelivery">
                    <span class="order-remind">待发货通知</span>
                    <div class="read">
                      <div class="operation">
                        <div class="operation-read" v-if="orderInfo.list[0].total">
                          <i class="laiketui laiketui-biaojiweiyidu"></i>
                          <span @click="noticeReads(1)">一键已读</span>
                        </div>
                        <span>（{{ orderInfo.list[0].total }}）</span>
                        <i :class="[notificationDelivery ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                      </div>
                    </div>
                  </div>
                  <ul class="order-details" v-if="orderInfo.list[0]" v-show="notificationDelivery">
                    <li v-for="(item,index) in orderInfo.list[0].list" :key="index" @click="goOrderList(item.parameter)">
                      <div class="left-icon">
                        <img src="@/assets/imgs/logo2.png" alt="">
                      </div>
                      <div class="right-info">
                        <div class="top-date">
                          <span class="orderInfo">订单消息</span>
                          <span class="date">{{ item.add_date }}</span>
                        </div>
                        <div class="bottom-detail">
                          <p>{{ item.content }}</p>
                          <a href="javascript:;">查看</a>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <li class="levetwo">
                  <div class="submenu" @click="afterSales = !afterSales">
                    <span class="order-remind">售后待处理通知</span>
                    <div class="read">
                      <div class="operation">
                        <div class="operation-read" v-if="orderInfo.list[1].total">
                          <i class="laiketui laiketui-biaojiweiyidu"></i>
                          <span @click.stop="noticeReads(2)">一键已读</span>
                        </div>
                        <span>（{{ orderInfo.list[1].total }}）</span>
                        <i :class="[afterSales ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                      </div>
                    </div>
                  </div>
                  <ul class="order-details" v-if="orderInfo.list[1]" v-show="afterSales">
                    <li v-for="(item,index) in orderInfo.list[1].list" :key="index" @click="salesReturnDetails(item.id)">
                      <div class="left-icon">
                        <img src="@/assets/imgs/logo2.png" alt="">
                      </div>
                      <div class="right-info">
                        <div class="top-date">
                          <span class="orderInfo">订单消息</span>
                          <span class="date">{{ item.add_date }}</span>
                        </div>
                        <div class="bottom-detail">
                          <p>{{ item.content }}</p>
                          <a href="javascript:;">查看</a>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <li class="levetwo">
                  <div class="submenu" @click="shipmentRemind = !shipmentRemind">
                    <span class="order-remind">发货提醒通知</span>
                    <div class="read">
                      <div class="operation">
                        <div class="operation-read" v-if="orderInfo.list[2].total">
                          <i class="laiketui laiketui-biaojiweiyidu"></i>
                          <span @click.stop="noticeReads(3)">一键已读</span>
                        </div>
                        <span>（{{ orderInfo.list[2].total }}）</span>
                        <i :class="[shipmentRemind ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                      </div>
                    </div>
                  </div>
                  <ul class="order-details" v-if="orderInfo.list[2]" v-show="shipmentRemind">
                    <li v-for="(item,index) in orderInfo.list[2].list" :key="index" @click="goOrderList(item.parameter)">
                      <div class="left-icon">
                        <img src="@/assets/imgs/logo2.png" alt="">
                      </div>
                      <div class="right-info">
                        <div class="top-date">
                          <span class="orderInfo">订单消息</span>
                          <span class="date">{{ item.add_date }}</span>
                        </div>
                        <div class="bottom-detail">
                          <p>{{ item.content }}</p>
                          <a href="javascript:;">查看</a>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <li class="levetwo">
                  <div class="submenu" @click="orderDown = !orderDown">
                    <span class="order-remind">订单关闭通知</span>
                    <div class="read">
                      <div class="operation">
                        <div class="operation-read" v-if="orderInfo.list[3].total">
                          <i class="laiketui laiketui-biaojiweiyidu"></i>
                          <span @click.stop="noticeReads(4)">一键已读</span>
                        </div>
                        <span>（{{ orderInfo.list[3].total }}）</span>
                        <i :class="[orderDown ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                      </div>
                    </div>
                  </div>
                  <ul class="order-details" v-if="orderInfo.list[3]" v-show="orderDown">
                    <li v-for="(item,index) in orderInfo.list[3].list" :key="index" @click="goOrderList(item.parameter)">
                      <div class="left-icon">
                        <img src="@/assets/imgs/logo2.png" alt="">
                      </div>
                      <div class="right-info">
                        <div class="top-date">
                          <span class="orderInfo">订单消息</span>
                          <span class="date">{{ item.add_date }}</span>
                        </div>
                        <div class="bottom-detail">
                          <p>{{ item.content }}</p>
                          <a href="javascript:;">查看</a>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <li class="levetwo">
                  <div class="submenu" @click="newOrder = !newOrder">
                    <span class="order-remind">新订单通知</span>
                    <div class="read">
                      <div class="operation">
                        <div class="operation-read" v-if="orderInfo.list[4].total">
                          <i class="laiketui laiketui-biaojiweiyidu"></i>
                          <span @click.stop="noticeReads(5)">一键已读</span>
                        </div>
                        <span>（{{ orderInfo.list[4].total }}）</span>
                        <i :class="[newOrder ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                      </div>
                    </div>
                  </div>
                  <ul class="order-details" v-if="orderInfo.list[4]" v-show="newOrder">
                    <li v-for="(item,index) in orderInfo.list[4].list" :key="index" @click="goOrderList(item.parameter)">
                      <div class="left-icon">
                        <img src="@/assets/imgs/logo2.png" alt="">
                      </div>
                      <div class="right-info">
                        <div class="top-date">
                          <span class="orderInfo">订单消息</span>
                          <span class="date">{{ item.add_date }}</span>
                        </div>
                        <div class="bottom-detail">
                          <p>{{ item.content }}</p>
                          <a href="javascript:;">查看</a>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <li class="levetwo">
                  <div class="submenu" @click="orderGoods = !orderGoods">
                    <span class="order-remind">订单收货通知</span>
                    <div class="read">
                      <div class="operation">
                        <div class="operation-read" v-if="orderInfo.list[5].total">
                          <i class="laiketui laiketui-biaojiweiyidu"></i>
                          <span @click.stop="noticeReads(6)">一键已读</span>
                        </div>
                        <span>（{{ orderInfo.list[5].total }}）</span>
                        <i :class="[orderGoods ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                      </div>
                    </div>
                  </div>
                  <ul class="order-details" v-if="orderInfo.list[5]" v-show="orderGoods">
                    <li v-for="(item,index) in orderInfo.list[5].list" :key="index" @click="goOrderList(item.parameter)">
                      <div class="left-icon">
                        <img src="@/assets/imgs/logo2.png" alt="">
                      </div>
                      <div class="right-info">
                        <div class="top-date">
                          <span class="orderInfo">订单消息</span>
                          <span class="date">{{ item.add_date }}</span>
                        </div>
                        <div class="bottom-detail">
                          <p>{{ item.content }}</p>
                          <a href="javascript:;">查看</a>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
              </ul>
            </li>
            <li class="leveone">
              <div class="topmenus" @click="goodsShow = !goodsShow">
                <span class="goods-remind">商品提醒</span>
                <div class="read">
                  <div class="operation">
                    <div class="operation-read" v-if="goodsInfo.total">
                      <i class="laiketui laiketui-biaojiweiyidu"></i>
                      <span @click.stop="noticeReads('7,9')">一键已读</span>
                    </div>
                    <el-badge :value="goodsInfo.total" :max="99" class="item">
                    </el-badge>
                    <i :class="[goodsShow ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                  </div>
                </div>
              </div>
              <ul class="detail-menus" v-show="goodsShow">
                <li class="levetwo">
                  <div class="submenu" @click="goodsAudit = !goodsAudit">
                    <span class="order-remind">商品审核通知</span>
                    <div class="read">
                      <div class="operation">
                        <div class="operation-read" v-if="goodsInfo.list[0].total">
                          <i class="laiketui laiketui-biaojiweiyidu"></i>
                          <span @click.stop="noticeReads(7)">一键已读</span>
                        </div>
                        <span>（{{ goodsInfo.list[0].total }}）</span>
                        <i :class="[goodsAudit ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                      </div>
                    </div>
                  </div>
                  <ul class="order-details" v-if="goodsInfo.list[0]" v-show="goodsAudit">
                    <li v-for="(item,index) in goodsInfo.list[0].list" :key="index" @click="goGoodsAudit(item.content)">
                      <div class="left-icon">
                        <img src="@/assets/imgs/logo2.png" alt="">
                      </div>
                      <div class="right-info">
                        <div class="top-date">
                          <span class="orderInfo">订单消息</span>
                          <span class="date">{{ item.add_date }}</span>
                        </div>
                        <div class="bottom-detail">
                          <p>{{ item.content }}</p>
                          <a href="javascript:;">查看</a>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
                <li class="levetwo">
                  <div class="submenu" @click="goodsReplenishment = !goodsReplenishment">
                    <span class="order-remind">商品补货通知</span>
                    <div class="read">
                      <div class="operation">
                        <div class="operation-read" v-if="goodsInfo.list[1].total">
                          <i class="laiketui laiketui-biaojiweiyidu"></i>
                          <span @click.stop="noticeReads(9)">一键已读</span>
                        </div>
                        <span>（{{ goodsInfo.list[1].total }}）</span>
                        <i :class="[goodsReplenishment ? 'el-icon-arrow-down' : 'el-icon-arrow-right']"></i>
                      </div>
                    </div>
                  </div>
                  <ul class="order-details" v-if="goodsInfo.list[1]" v-show="goodsReplenishment">
                    <li v-for="(item,index) in goodsInfo.list[1].list" :key="index" @click="goInventoryList(item.content)">
                      <div class="left-icon">
                        <img src="@/assets/imgs/logo2.png" alt="">
                      </div>
                      <div class="right-info">
                        <div class="top-date">
                          <span class="orderInfo">订单消息</span>
                          <span class="date">{{ item.add_date }}</span>
                        </div>
                        <div class="bottom-detail">
                          <p>{{ item.content }}</p>
                          <a href="javascript:;">查看</a>
                        </div>
                      </div>
                    </li>
                  </ul>
                </li>
              </ul>
            </li>
          </ul>
        </li>
        <li class="Hui-skin">
          <div class="topHui-skin">
            <img src="@/assets/imgs/huanfu.png" alt="">
          </div>
          <ul class="Huiskin-menus">
            <li
              ref="lis"
              v-for="(item,index) in skinList"
              :key="index"
              @click="changeTag(index)"
              @mouseenter="mouseenter(item)"
              @mouseleave="mouseleave(item)"
              :style="{'background': index == tag ? cNodeColor : '#fff','color': index == tag ? '#fff' : '#515a6e'}"
            >
              <span>{{ $t(item.name) }}</span>
              <i v-show="item.color == cNodeColor" class="el-icon-check"></i>
            </li>
          </ul>
        </li>
        <li class="sign-out" @click="signOut" v-bind:title="message">
          <img src="@/assets/imgs/tc.png" alt="">
        </li>
        <li class="Lang">
          <div class="top-lang">
            <img src="@/assets/imgs/lang.png" alt="">
          </div>
          <ul class="lang-menus">
            <li class="zh" @click="toggleZh('zh')">
              <span>中文</span>
            </li>
            <li class="en" @click="toggleEn('en')">
              <span>English</span>
            </li>
          </ul>
        </li>

      </ul>
    </div>

    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog
        title="修改密码"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
        :modal-append-to-body='false'
      >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <div class="pass-input">
            <el-form-item label="原密码：" prop="oldPassword">
              <el-input v-model="ruleForm.oldPassword" show-password></el-input>
            </el-form-item>
            <el-form-item label="新密码：" prop="newPassword">
              <el-input v-model="ruleForm.newPassword" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码：" prop="confirmPassword">
              <el-input v-model="ruleForm.confirmPassword" show-password></el-input>
            </el-form-item>
          </div>
          <div class="form-footer">
            <el-form-item>
              <el-button @click="dialogVisible = false" class="qxcolor">取 消</el-button>
              <el-button type="primary" @click="determine('ruleForm')" class="qdcolor">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

    <!-- a -->
    <div class="dialog-block" >
      <el-dialog
        title="提示"
        :visible.sync="dialogVisible1"
        :before-close="handleClose1"
        :modal-append-to-body='false'
      >
        <el-form label-width="100px" class="demo-ruleForm">
          <div class="pass-input" style="font-size: 1.239rem;margin-top:-0.5rem;" v-html="e">
          </div>
          <div class="form-footer">
            <el-form-item>
              <el-button @click="dialogVisible1 = false" class="qxcolor">取 消</el-button>
              <el-button type="primary" @click="handleClose1()" class="qdcolor">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

    <div class="dialog-info">
      <!-- 弹框组件 -->
      <el-dialog
        title="基本信息"
        :visible.sync="dialogVisible2"
        :before-close="handleClose2"
        :modal-append-to-body='false'
      >
        <el-form :model="ruleForm" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
          <div class="pass-input">
            <el-form-item class="upload-headimg" label="头像：">
              <el-upload
                class="avatar-uploader"
                :action="actionUrl"
                :data="uploadData"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="ruleForm2.headImg" :src="ruleForm2.headImg" class="avatar">
                <img :src="removeImg" @click.stop.prevent="removeImgs" v-if="ruleForm2.headImg" class="removeImg" alt="">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="昵称：">
              <el-input v-model="ruleForm2.nickname"></el-input>
            </el-form-item>
            <el-form-item label="生日：">
              <el-date-picker
                type="date"
                v-model="ruleForm2.birthday"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="性别：">
              <el-radio-group v-model="ruleForm2.gender">
                <el-radio v-for="item in genderList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="手机号码：">
              <el-input v-model="ruleForm2.phone"></el-input>
            </el-form-item>
          </div>
          <div class="form-footer">
            <el-form-item>
              <el-button @click="dialogVisible2 = false" class="qxcolor">取 消</el-button>
              <el-button type="primary" @click="determine2('ruleForm2')" class="qdcolor">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Hamburger from '@/components/Hamburger'
import { getStorage, setStorage, removeStorage } from '@/utils/storage'
import { getShopInfo } from '@/api/Platform/merchants'
import { getRoleMenu } from '@/api/Platform/permissions'
import {a} from '@/api/Platform/acopyright.js'
import { updateAdminInfo, noticeList, noticeRead } from '@/api/layout/information'
import Config from "@/packages/apis/Config";
import { setUserAdmin } from '@/api/Platform/merchants'
export default {
  components: {
    Hamburger
  },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.confirmPassword !== '') {
          this.$refs.ruleForm.validateField('confirmPassword');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      skinList: [
        { name:'topNav.Blue', icon:0 , color:'#2d6dcc', key:0},
        { name:'topNav.Black', icon:1, color:'#222222', key:1},
        { name:'topNav.Green', icon:1, color:'#4cbe8b', key:2},
        { name:'topNav.Yellow', icon:1, color:'#f45d5d', key:3},
        { name:'topNav.Red', icon:1, color:'#f2a61f', key:4},
        { name:'topNav.Orange', icon:1, color:'#f87235', key:5}
      ],
      cNodeColor: '#2d6dcc',
      d:"未授权版来客推商城",
      e:"未经<a href='https://www.laiketui.com/' target='_blank' >来客推电商</a>系统授权的用户，仅供从事学习研究之用，不具备商业运作的合法性，如果未获取授权而从事商业行为，<a href='https://www.laiketui.com/' target='_blank' >来客推</a>保留对其使用系统停止升级、关闭、甚至对其商业运作行为媒体曝光和追究法律责任的起诉权利，授权请前往<a href='https://www.laiketui.com/' target='_blank' >www.laiketui.com</a>咨询。",
      message: '退出系统',

      versionList: [],
      defaultVersion: '',
      showImg: '',

      // 弹框数据
      dialogVisible: false,
      ruleForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      },
      rules: {
        oldPassword: [
          { required: true, message: '请填写原密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请填写密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur', required: true }
        ],
      },

      // 基本信息弹框数据
      dialogVisible2: false,
      ruleForm2: {
        headImg: '',
        nickname: '',
        birthday: '',
        gender: '',
        phone: ''
      },

      genderList: [
        {
          value: 1,
          name: '男'
        },
        {
          value: 0,
          name: '女'
        },
      ],

      actionUrl: Config.baseUrl,
      removeImg: require('../../assets/imgs/sha.png'),

      // 订单通知数据
      allNum: 0,
      orderInfo: {},
      goodsInfo: {},
      orderShow: false,
      notificationDelivery: false,
      afterSales: false,
      shipmentRemind: false,
      orderDown: false,
      newOrder: false,
      orderGoods: false,

      goodsShow: false,
      goodsAudit: false,
      goodsReplenishment: false,
      dialogVisible1:false
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
    ]),

    tag() {
      return this.$store.state.skinPeeler.tag
    },

    username() {
      return getStorage('laike_admin_uaerInfo').nickname
    },

    type() {
      return getStorage('laike_admin_uaerInfo').type
    },

    uploadData() {
      {
        return {
          api: 'resources.file.uploadFiles',
          storeId: getStorage('laike_admin_uaerInfo').storeId,
          groupId: -1,
          uploadType: 2,
          accessId: this.$store.getters.token
        }
      }
    }
  },
  watch: {
    dialogVisible1(){
      if(this.dialogVisible1 == false){
          this.amsg();
      }
    },
    tag() {
      if(this.tag === 0) {
        this.$refs.navHeader.style.backgroundColor = '#2d6dcc'
        this.cNodeColor = '#2d6dcc'
      } else if(this.tag === 1) {
        this.$refs.navHeader.style.backgroundColor = '#222222'
        this.cNodeColor = '#222222'
      } else if(this.tag === 2) {
        this.$refs.navHeader.style.backgroundColor = '#4cbe8b'
        this.cNodeColor = '#4cbe8b'
      } else if(this.tag === 3) {
        this.$refs.navHeader.style.backgroundColor = '#f45d5d'
        this.cNodeColor = '#f45d5d'
      } else if(this.tag === 4) {
        this.$refs.navHeader.style.backgroundColor = '#f2a61f'
        this.cNodeColor = '#f2a61f'
      } else {
        this.$refs.navHeader.style.backgroundColor = '#f87235'
        this.cNodeColor = '#f87235'
      }
    }
  },

  created() {

    this.getShopInfos().then(() => {
      this.showImg = getStorage('laike_admin_uaerInfo').storeId
      for (let i = 0; i < this.versionList.length; i++) {
        if(this.showImg === this.versionList[i].id) {
          this.defaultVersion = this.versionList[i].name
          break
        }
      }
    }),

    this.noticeList()
    let me = this;
    a({
      api:"SaaS.b.c"
    }).then(res => {
        console.log(res);
        if(res.data.code == 200){
          me.d = res.data.data.d ;
          me.e = res.data.data.e ;
          window.localStorage.setItem("d",me.d);
          window.localStorage.setItem("e",me.e);
        }
    })
    setTimeout(() => {
      this.dialogVisible1 = true;
    },200)

  },

  methods: {
    amsg() {
      this.$notify.error({
        title: '提示',
        message: this.e,
        dangerouslyUseHTMLString: true,
        type: 'warning',
        duration: 0
      });
    },
    async getShopInfos() {
      const res = await getShopInfo({
        api: 'saas.shop.getShopInfo',
        storeType: 8,
        storeId: null
      })
      console.log(res.data.data.dataList);
      this.versionList = res.data.data.dataList
      this.defaultVersion = res.data.data.dataList[0].name
      this.showImg = res.data.data.dataList[0].id
    },

    //切换商城
    enterSystem(value) {
      this.showImg = value.id
      this.defaultVersion = value.name
      const laike_admin_uaerInfo = getStorage('laike_admin_uaerInfo')
      const rolesInfo = getStorage('rolesInfo')
      getShopInfo({
        api: 'saas.shop.getShopInfo',
        storeType: 8,
        storeId: value.id,
      }).then(res => {
        const info = res.data.data.dataList[0]
        console.log(info);
        laike_admin_uaerInfo.storeId = info.id
        rolesInfo.storeId = info.id
        setStorage('laike_admin_uaerInfo',laike_admin_uaerInfo)
        setStorage('rolesInfo',rolesInfo)
        this.$store.commit('user/SET_MERCHANTSLOGO', info.merchant_logo)
        setStorage('laike_head_img', info.merchant_logo)
        setUserAdmin({
          api: 'admin.saas.user.setUserAdmin',
        })
        .then(response => {
          const laike_admin_uaerInfo = getStorage('laike_admin_uaerInfo')
          laike_admin_uaerInfo.mchId = response.data.data.mchId
          setStorage('laike_admin_uaerInfo',laike_admin_uaerInfo)
          resolve(response)
        })
        getRoleMenu({
          api: 'saas.role.getRoleMenu',
          storeId: info.id,
          roleId: parseInt(info.roleId)
        }).then(res => {
          this.$router.go(0)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 订单通知
    async noticeList() {
      const res = await noticeList({
        api: 'admin.notice.noticeList'
      })
      let orderInfo = res.data.data.list
      this.allNum = orderInfo[0].total + orderInfo[1].total
      this.ordertotal = orderInfo[0].total
      this.goodstotal = orderInfo[1].total

      this.orderInfo = orderInfo[0]
      this.goodsInfo = orderInfo[1]
    },

    async noticeReads(type) {
      this.$confirm('确定将该类型通知标记为已读?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        noticeRead({
          api: 'admin.notice.noticeRead',
          types: type
        }).then(res => {
          console.log(res);
          if(res.data.code == '200') {
            this.$message({
              type: 'success',
              message: '成功!',
              offset: 100
            })
            this.noticeList()
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });          
      });
    },

    goOrderList(value) {
      this.$router.push({
        path: '/order/orderList/orderLists',
        query: {
          no: value
        }
      })
    },

    salesReturnDetails(value) {
      this.$router.push({
        path: '/order/salesReturn/salesReturnDetails',
        query: {
          id: value
        }
      })
    },

    goGoodsAudit(value) {
      this.$router.push({
        path: '/plug_ins/stores/goodsAudit',
        query: {
          name: value
        }
      })
    },

    goInventoryList(value) {
      this.$router.push({
        path: '/goods/inventoryManagement/inventoryList',
        query: {
          name: value
        }
      })
    },

    orderAllRead() {
      this.ordertotle = 0
    },

    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },

    changeTag(index) {
      this.$store.dispatch("toggleTag", index)
      this.$refs.navHeader.style.background = this.cNodeColor
      console.log(index);
    },

    mouseenter(item) {
      this.$refs.lis[item.key].style.background = item.color
      this.$refs.lis[item.key].style.color = "#fff"
    },

    mouseleave(item) {
      if(this.cNodeColor !== item .color) {
        this.$refs.lis[item.key].style.background = "#fff"
        this.$refs.lis[item.key].style.color = "#515a6e"
      }
    },

    toggleZh(lang) {
      this.$i18n.locale = lang
      this.$store.dispatch('lang/setLanguage', lang)
    },

    toggleEn(lang) {
      this.$i18n.locale = lang
      this.$store.dispatch('lang/setLanguage', lang)
    },

    // 退出登录
    signOut() {
      window.localStorage.clear()
      this.$router.push('/login')
      location.reload();
    },

    // 弹框方法
    dialogShow(value) {
      this.ruleForm.oldPassword = ''
      this.ruleForm.newPassword = ''
      this.ruleForm.confirmPassword = ''
      this.dialogVisible = true
      this.$nextTick(() => {
        this.$refs.ruleForm.clearValidate()
      })
    },
    
    handleClose(done) {
      this.dialogVisible = false
    },

    handleClose1(done) {
      this.dialogVisible1 = false
    },

    // 修改密码
    determine(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            updateAdminInfo({
              api: 'admin.saas.user.updateAdminInfo',
              passwordOld: this.ruleForm.oldPassword,
              password: this.ruleForm.newPassword,
              storeId: null
            }).then(res => {
              console.log(res);
              if(res.data.code == '200') {
                this.$message({
                  message: '修改成功',
                  type: 'success',
                  offset: 100
                })
                this.dialogVisible = false
              }
            })
          } catch (error) {
            this.$message({
              message: '密码不能为空',
              type: 'error',
              offset: 100
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    // 基本信息弹框方法
    dialogShow2(value) {
      const info = getStorage('laike_admin_uaerInfo')
      this.ruleForm2.headImg = info.portrait
      this.ruleForm2.nickname = info.nickname
      this.ruleForm2.birthday = info.birthday
      this.ruleForm2.gender = info.sex
      this.ruleForm2.phone = info.phone
      this.dialogVisible2 = true
    },
    
    handleClose2(done) {
      this.dialogVisible2 = false
    },

    handleAvatarSuccess(res, file) {
      console.log(res);
      this.ruleForm2.headImg = res.data.imgUrls[0]
    },

    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!');
      // }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isLt2M;
    },

    removeImgs() {
      this.ruleForm2.headImg = ''
    },

    // 修改密码
    determine2(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            updateAdminInfo({
              api: 'admin.saas.user.updateAdminInfo',
              portrait: this.ruleForm2.headImg,
              nickname: this.ruleForm2.nickname,
              birthday: this.ruleForm2.birthday,
              sex: this.ruleForm2.gender,
              phone: this.ruleForm2.phone,
              storeId: null
            }).then(res => {
              console.log(res);
              if(res.data.code == '200') {
                const infos = getStorage('laike_admin_uaerInfo')
                infos.portrait = this.ruleForm2.headImg
                infos.nickname = this.ruleForm2.nickname
                infos.birthday = this.ruleForm2.birthday
                infos.sex = this.ruleForm2.gender
                infos.phone = this.ruleForm2.phone
                setStorage('laike_admin_uaerInfo',infos)
                // setStorage('laike_head_img',this.ruleForm2.headImg)
                this.$message({
                  message: '修改成功',
                  type: 'success',
                  offset: 100
                })
                this.$router.go(0)
                this.dialogVisible2 = false
              }
            })
          } catch (error) {
            this.$message({
              message: '密码不能为空',
              type: 'error',
              offset: 100
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  }
}
</script>

<style lang="less" scoped>
.navbar {
  height: 60px;
  position: relative;
  background-image: linear-gradient(to right, #0077FF , #00C0FF);
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  justify-content: space-between;

  .left-control {
    width: 220px;
    height: 60px;
    display: flex;
    justify-content: space-between;
    align-items: center;
      img {
        margin-left: 26px;
        width: 121px;
        height: 40px;
      }
      .hamburger-container {
        height: 60px;
        line-height: 60px;
        cursor: pointer;
        transition: background .3s;
        -webkit-tap-highlight-color:transparent;
        padding: 0 !important;
        &:hover {
          background: rgba(0, 0, 0, .025);
        }
      }
  }

  .right-block {
    height: 60px;
    flex: 1;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding-right: 40px;
    .Hui-userbar {
      list-style: none;
      padding: 0;
      margin: 0;
      display: flex;
      .dropDown {
        // width: 135px;
        height: 60px;
        line-height: 60px;
        position: relative;
        display: flex;
        justify-content: center;
        // margin-right: 21.5px;
        margin-left: 40px;
        &:hover .dropDown-menus  {
          display: block;
        }
        .top-dropDown {
          cursor: pointer;
          color: #fff;
          display: flex;
          align-items: center;
          img {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            margin-right: 5px;
          }
        }
        .dropDown-menus {
          list-style: none;
          padding: 0;
          margin: 0;
          position: absolute;
          left: 50%;
          transform: translateX(-50%);
          top: 60px;
          width: 134px;
          display: none;
          background-color: #fff;
          border: 1px solid #E9ECEF;
          box-shadow: 0px 0px 14px 0px rgba(0, 0, 0, 0.1);
          border-radius: 4px;
          li {
            cursor: pointer;
            width: 100%;
            height: 40px;
            text-align: center;
            line-height: 40px;
            font-size: 14px;
            i {
              margin-right: 6px;
              font-size: 18px;
            }
            &:hover {
              color: #2d8cf0;
              background-color: #f6f7f8;
            }
          }
        }
      }

      .changeStore {
        width: auto;
        height: 60px;
        line-height: 60px;
        position: relative;
        display: flex;
        justify-content: center;
        // margin-right: 20px;
        margin-left: 40px;
        &:hover .changeStore-menus  {
          display: block;
        }
        .top-changeStore {
          cursor: pointer;
          color: #fff;
          display: flex;
          align-items: center;
          span {
            margin: 0 6px;
          }
          .shanghu {
            width: 20px;
            height: 20px;
          }
          .qiehuan {
            width: 14px;
            height: 14px;
          }
        }
        .changeStore-menus {
          list-style: none;
          padding: 0;
          margin: 0;
          position: absolute;
          left: 50%;
          transform: translateX(-50%);
          top: 60px;
          width: 240px;
          display: none;
          background-color: #fff;
          border: 1px solid #E9ECEF;
          box-shadow: 0px 0px 14px 0px rgba(0, 0, 0, 0.1);
          border-radius: 4px;
          li {
            cursor: pointer;
            width: 100%;
            height: 40px;
            font-size: 14px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 10px;
            &:hover {
              color: #2d8cf0;
              background-color: #f4f7f9;
            }
          }
        }
      }

      .headerLi {
        // width: 60px;
        height: 60px;
        line-height: 60px;
        position: relative;
        display: flex;
        justify-content: center;
        margin-left: 40px;
        &:hover .headerLi-menus  {
          display: block;
        }
        .top-headerLi {
          cursor: pointer;
          color: #fff;
          display: flex;
          align-items: center;
          position: relative;
          img {
            width: 20px;
            height: 20px;
          }

          .totle-item {
            top: -3px;
            left: 6px;
            font-size: 12px;
            position: absolute;
          }
        }
        .headerLi-menus {
          list-style: none;
          padding: 0;
          margin: 0;
          position: absolute;
          right: -200px;
          top: 60px;
          width: 500px;
          display: none;
          background-color:transparent;
          .leveone {
            cursor: pointer;
            width: 100%;
            // height: 80px;
            font-size: 14px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 10px 30px;
            background-color: #fff;
            margin: 10px 0;
            border-radius: 10px;
            border: 1px solid rgba(99, 61, 61, 0.1);
            box-shadow: 0px 3px 18px 0px rgba(173, 173, 173 , 0.8);
            .topmenus {
              width: 100%;
              display: flex;
              justify-content: space-between;
              align-items: center;
              .order-remind,.goods-remind {
                font-size: 16px;
                font-weight: bold;
                color: #333;
              }

              .operation {
                display: flex;
                justify-content: center;
                align-items: center;
                .operation-read {
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  color: #0077ff;
                  margin-right: 10px;
                  font-size: 12px;
                }
                .item {
                  margin-top: 12px;
                }
              }
            }

            .detail-menus {
              width: 100%;
              display: flex;
              flex-direction: column;
              justify-content: center;
              align-items: center;
              .levetwo {
                width: 100%;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                .submenu {
                  width: 100%;
                  display: flex;
                  justify-content: space-between;
                  align-items: center;
                  height: 50px;
                  border-bottom: 1px solid #f3f3f3;
                  .order-remind {
                    font-size: 14px;
                    font-weight: bold;
                    color: #666;
                  }

                  .operation {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    .operation-read {
                      display: flex;
                      justify-content: center;
                      align-items: center;
                      color: #0077ff;
                      margin-right: 10px;
                      font-size: 12px
                    }
                  }
                }

                .order-details {
                  width: 100%;
                  max-height: 260px;
                  overflow: hidden;
                  overflow-y: auto;
                  li {
                    width: 100%;
                    height: 78px;
                    display: flex;
                    margin: 10px 0;
                    font-size: 12px;
                    .left-icon {
                      width: 60px;
                      height: 100%;
                      background-color: #0880ff;
                      display: flex;
                      justify-content: center;
                      align-items: center;
                      border-top-left-radius: 8px;
                      border-bottom-left-radius: 8px;
                    }
                    .right-info {
                      flex: 1;
                      height: 78px;
                      border: 1px solid rgba(0, 0, 0, 0.1);
                      border-top-right-radius: 8px;
                      border-bottom-right-radius: 8px;
                      display: flex;
                      flex-direction: column;
                      padding: 10px 20px;
                      .top-date {
                        height: 40%;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        .orderInfo {
                          font-weight: bold;
                        }
                        .date {
                          color: #cecece;
                        }
                      }

                      .bottom-detail {
                        height: 60%;
                        display: flex;
                        align-items: center;
                        margin-top: 5px;
                        p {
                          width: 90%;
                          line-height: 16px;
                        }
                        a {
                          margin-left: 5px;
                        }
                      }
                    }
                  }
                }
                
              }
            }
          }
        }
      }

      .Hui-skin {
        // width: 60px;
        height: 60px;
        line-height: 70px;
        position: relative;
        display: flex;
        justify-content: center;
        margin-left: 40px;
        &:hover .Huiskin-menus  {
          display: block;
        }
        .top-Huiskin {
          cursor: pointer;
          color: #fff;
          display: flex;
          align-items: center;
          img {
            width: 20px;
            height: 20px;
          }
        }
        .Huiskin-menus {
          list-style: none;
          padding: 0;
          margin: 0;
          position: absolute;
          left: 50%;
          transform: translateX(-50%);
          top: 60px;
          width: 134px;
          display: none;
          background-color: #fff;
          border: 1px solid #E9ECEF;
          box-shadow: 0px 0px 14px 0px rgba(0, 0, 0, 0.1);
          border-radius: 4px;
          li {
            cursor: pointer;
            width: 100%;
            height: 40px;
            font-size: 14px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 10px;
            &:hover {
              color: #2d8cf0;
            }
            i {
              color: #ffffff !important;
              font-weight: bold;
            }
          }
        }
      }

      .sign-out {
        // width: 60px;
        height: 60px;
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-left: 40px;
        img {
          width: 20px;
          height: 20px;
        }
      }

      .Lang {
        // width: 60px;
        height: 60px;
        line-height: 60px;
        position: relative;
        display: flex;
        justify-content: center;
        // margin-left: 10px;
        margin-left: 40px;
        &:hover .lang-menus  {
          display: block;
        }
        .top-lang {
          cursor: pointer;
          color: #fff;
          display: flex;
          align-items: center;
          img {
            width: 30px;
            height: 30px;
          }
        }
        .lang-menus {
          list-style: none;
          padding: 0;
          margin: 0;
          position: absolute;
          left: 50%;
          transform: translateX(-50%);
          top: 60px;
          width: 134px;
          display: none;
          background-color: #fff;
          border: 1px solid #E9ECEF;
          box-shadow: 0px 0px 14px 0px rgba(0, 0, 0, 0.1);
          border-radius: 4px;
          li {
            cursor: pointer;
            width: 100%;
            height: 40px;
            text-align: center;
            line-height: 40px;
            font-size: 14px;
            &:hover {
              color: #2d8cf0;
            }
          }
        }  
      }

    }
  }

  .dialog-block {
    /deep/.el-dialog {
      width: 580px;
      height: 366px;
    }
  }
  .dialog-info {
    /deep/.el-dialog {
      width: 500px;
      height: 500px;
      .el-dialog__body {
        .upload-headimg {
          .el-form-item__content {
            display: flex;
            align-items: center;
            .avatar-uploader .el-upload {
              border: 1px dashed #d9d9d9;
              border-radius: 6px;
              cursor: pointer;
              position: relative;
              overflow: hidden;
              border-radius: 50px;
            }
            .avatar-uploader .el-upload:hover {
              border-color: #409EFF;
            }
            .avatar-uploader-icon {
              font-size: 16px;
              color: #8c939d;
              width: 40px;
              height: 40px;
              line-height: 40px;
              text-align: center;
            }
            .avatar {
              width: 40px;
              height: 40px;
              display: block;
              border-radius: 50px;
            }
            .removeImg {
              position: absolute;
              right: 0;
              top: 0;
            }
          }
        }
        .el-date-editor {
          width: 280px;
          input {
            width: 280px;
          }
        }
      }
    }
  }
  .dialog-block,.dialog-info {
    z-index: 2001;
    /deep/.el-dialog {
      // width: 580px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
      margin: 0 !important;
      .el-dialog__header {
        width: 100%;
        height: 58px;
        line-height: 58px;
        font-size: 16px;
        margin-left: 19px;
        font-weight: bold;
        border-bottom: 1px solid #E9ECEF;
        box-sizing: border-box;
        margin: 0;
        padding: 0 0 0 19px;
        .el-dialog__headerbtn {
          font-size: 18px;
          top: 0 !important;
        }
        .el-dialog__title {
          font-weight: normal;
          font-size: 16px;
          color: #414658;
        }
        
      }

      .el-dialog__body {
        // border-bottom: 1px solid #E9ECEF;
        padding: 41px 60px 16px 60px !important;
        .pass-input {
          .el-form {
            width: 340px;
            .el-form-item {
              width: 340px;
              height: 40px;
              .el-form-item__content {
                .el-input {
                  width: 340px;
                  height: 40px;
                  input {
                    width: 340px;
                    height: 40px;
                  }
                }
              }
            }
          }
        }
        .form-footer {
          width: 100%;
          height: 72px;
          position: absolute;
          bottom: 0;
          right: 0;
          border-top: 1px solid #E9ECEF;
          .el-form-item {
            padding: 0 !important;
            height: 100%;
            display: flex;
            justify-content: flex-end;
            margin-right: 17px;
            .el-form-item__content {
              height: 100%;
              line-height: 72px;
              margin: 0 !important;
            }
          }

          .qxcolor {
            color: #6a7076;
            border: 1px solid #d5dbc6;
          }
          .qdcolor {
            background-color: #2890FF;
          }
          .qdcolor {
            background-color: #2890ff;
          }
          .qdcolor:hover {
            opacity: 0.8;
          }
          .qxcolor {
            color: #6a7076;
            border: 1px solid #d5dbe8;
          }
          .qxcolor:hover {
            color: #2890ff;
            border: 1px solid #2890ff;
            background-color: #fff;
          }
        }
      }
    }
    /deep/.el-form-item__label {
      font-weight: normal;
      color: #414658;
    }
  }

  /deep/.el-badge__content {
    border: none;
  }
}
</style>
