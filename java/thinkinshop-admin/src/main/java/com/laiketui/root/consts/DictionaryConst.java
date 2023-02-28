package com.laiketui.root.consts;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典映射
 *
 * @author Trick
 * @date 2020/9/28 13:52
 */
public interface DictionaryConst {
    /**
     * 短信模板类型
     */
    String SMS_TEMPLATE_TYPE = "短信模板类型";

    /**
     * 短信模板类别
     */
    String SMS_TEMPLATE_CATEGORY = "短信模板类别";

    /**
     * 新品
     */
    String LKT_SPLX_001 = "1";
    /**
     * 热销
     */
    String LKT_SPLX_002 = "2";
    /**
     * 推荐
     */
    String LKT_SPLX_003 = "3";


    /**
     * 来源
     */
    interface StoreSource {
        /**
         * 小程序
         */
        String LKT_LY_001 = "1";
        /**
         * APP
         */
        String LKT_LY_002 = "2";
        /**
         * 支付宝小程序
         */
        String LKT_LY_003 = "3";
        /**
         * 字节跳动小程序
         */
        String LKT_LY_004 = "4";
        /**
         * 百度小程序
         */
        String LKT_LY_005 = "5";
        /**
         * pc端
         */
        String LKT_LY_006 = "6";
        /**
         * H5
         */
        String LKT_LY_007 = "7";
    }

    /**
     * 引导图类型
     */
    interface GuideType {
        /**
         * 启动引导图
         */
        Integer GUIDETYPE_00 = 0;
        /**
         * 安装引导图
         */
        Integer GUIDETYPE_001 = 1;
    }

    /**
     * 订单类型
     *  用订单头部来区分订单类型,则需要按头部长度大小，从大到小判断
     */
    interface OrdersType {

        /**
         * 普通订单
         */
        String ORDERS_HEADER_GM = "GM";
        /**
         * 砍价头部
         */
        String ORDERS_HEADER_KJ = "KJ";

        /**
         * 拼团订单
         */
        String ORDERS_HEADER_PT = "PT";

        /**
         * 开团订单
         */
        String ORDERS_HEADER_KT = "KT";

        /**
         * 分销订单
         */
        String ORDERS_HEADER_FX = "FX";

        /**
         * 竞拍
         */
        String ORDERS_HEADER_JP = "JP";

        /**
         * 特惠
         */
        String ORDERS_HEADER_TH = "TH";

        /**
         * 秒杀
         */
        String ORDERS_HEADER_MS = "MS";

        /**
         * 竞拍
         */
        String ORDERS_HEADER_AC = "AC";

        /**
         * 充值
         */
        String ORDERS_HEADER_CZ = "CZ";

        /**
         * 积分商城
         */
        String ORDERS_HEADER_IN = "IN";

        /**
         * vip订单
         */
        String ORDERS_HEADER_DJ = "DJ";

        /**
         * 平台拼团订单
         */
        String PTHD_ORDER_PP = "PP";
        /**
         * 平台秒杀订单
         */
        String PTHD_ORDER_PM = "PM";
        /**
         * 虚拟订单
         */
        String ORDERS_HEADER_VI = "VI";

        /**
         * 店铺保证金 - 临时表+保证金表
         */
        String ORDERS_HEADER_MCH_PROMISE = "PR";

        /**
         * 平台标识
         */
        String PTHD_ORDER_HEADER = "pthd_";
    }

    /**
     * 支付类型
     */
    interface OrderPayType {
        /**
         * 优惠卷
         */
        String ORDERPAYTYPE_CONSUMER_PAY = "consumer_pay";

        /**
         * 头条支付宝APP支付
         */
        String ORDERPAYTYPE_TT_ALIPAY = "tt_alipay";

        /**
         * 中国银联手机支付
         */
        String ORDERPAYTYPE_WAP_UNIONPAY = "wap_unionpay";
        /**
         * 微信APP支付
         */
        String ORDERPAYTYPE_APP_WECHAT = "app_wechat";
        /**
         * 微信小程序支付
         */
        String ORDERPAYTYPE_MINI_WECHAT = "mini_wechat";
        /**
         * 微信公众号支付
         */
        String ORDERPAYTYPE_JSAPI_WECHAT = "jsapi_wechat";
        /**
         * 支付宝小程序
         */
        String ORDERPAYTYPE_ALIPAY_MINIPAY = "alipay_minipay";
        /**
         * 支付宝WAP
         */
        String ORDERPAYTYPE_ALIPAY_WAP = "alipay_mobile";
        /**
         * 微信二维码支付
         */
        String ORDERPAYTYPE_PC_WECHAT = "pc_wechat";
        /**
         * 支付宝二维码支付
         */
        String ORDERPAYTYPE_PC_ALIPAY = "pc_alipay";
        /**
         * 手机H5微信支付
         */
        String ORDERPAYTYPE_H5_WECHAT = "H5_wechat";
        /**
         * 支付宝手机支付
         */
        String ORDERPAYTYPE_ALIPAY = "alipay";

        /**
         * TODO 支付宝手机支付_临时
         */
        String ORDERPAYTYPE_ALIPAY_TMP = "aliPay";

        /**
         * 百度小程序支付
         */
        String ORDERPAYTYPE_BAIDU_PAY = "baidu_pay";

        /**
         * 钱包支付
         */
        String ORDERPAYTYPE_WALLET_PAY = "wallet_pay";
    }

    /**
     * 订单状态
     */
    interface OrdersStatus {
        /**
         * 待付款
         */
        int ORDERS_R_STATUS_UNPAID = 0;
        /**
         * 待发货
         */
        int ORDERS_R_STATUS_CONSIGNMENT = 1;
        /**
         * 待收货
         */
        int ORDERS_R_STATUS_DISPATCHED = 2;

        /**
         * 已完成
         */
        int ORDERS_R_STATUS_COMPLETE = 5;
        /**
         * 订单关闭
         */
        int ORDERS_R_STATUS_CLOSE = 7;
    }

    /**
     * 阿里接口错误代码
     */
    interface AliApiCode {
        /**
         * 卖家余额不足
         */
        String ALIPAY_ACQ_SELLER_BALANCE_NOT_ENOUGH = "ACQ.SELLER_BALANCE_NOT_ENOUGH";
    }

    /**
     * 售后类型 - re_type
     * 售后类型：1仅退款 2退货退款 3换货
     */
    interface ReturnRecordReType {
        /**
         * 1仅退款
         */
        Integer RETURNORDERTYPE_REFUSE_AMT = 1;
        /**
         * 2退货退款
         */
        Integer RETURNORDERSTATUS_REFUSE_REBACKGOODS_AMT = 2;
        /**
         * 3换货
         */
        Integer RETURNORDERSTATUS_GOODS_REBACK = 3;
    }


    /**
     * 售后订单状态 r_type
     * 100:不在退货退款状态
     * 0:审核中 1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递 4:收到寄回商品,同意并退款
     * 5：拒绝并退回商品 8:拒绝(退款) 9:同意并退款 10:拒绝(售后)11:同意并且寄回商品 12售后结束
     */
    interface ReturnOrderStatus {
        /**
         * 审核中
         */
        Integer RETURNORDERSTATUS_EXAMEWAIT_STATUS = 0;
        /**
         * 同意并让用户寄回
         */
        Integer RETURNORDERSTATUS_AGREE_REBACK = 1;
        /**
         * 拒绝 退货退款
         */
        Integer RETURNORDERSTATUS_REFUSE_REBACKGOODS_AMT = 2;
        /**
         * 用户已快递
         */
        Integer RETURNORDERSTATUS_USER_DELIVERED = 3;
        /**
         * 收到寄回商品 同意并退款
         */
        Integer RETURNORDERSTATUS_RECEIVED_REBAKGOODS = 4;
        /**
         * 拒绝并退回商品
         */
        Integer RETURNORDERSTATUS_REFUSE_REBACKGOODS = 5;
        /**
         * 拒绝 退款
         */
        Integer RETURNORDERSTATUS_REFUSE_AMT = 8;
        /**
         * 同意并退款
         */
        Integer RETURNORDERSTATUS_AGREE_REBACK_AMT = 9;
        /**
         * 拒绝 售后
         */
        Integer RETURNORDERSTATUS_REFUSE_AFTER_SALE = 10;
        /**
         * 同意并且寄回商品
         */
        Integer RETURNORDERSTATUS_AGREE_REBACK_GOODS = 11;
        /**
         * 售后结束
         */
        Integer RETURNORDERSTATUS_AFTER_SALE_END = 12;
    }

    /**
     * 类型 0:登录/退出 1:添加 2:修改 3:删除 4:导出
     * 5:启用/禁用 6:通过/拒绝 10删除订单
     */
    interface adminRecordType {
        /**
         * 登录/退出
         */
        int ADMINRECORDTYPE_OUT_OR_IN = 0;
        /**
         * 添加
         */
        int ADMINRECORDTYPE_ADD_DATA = 1;
        /**
         * 修改
         */
        int ADMINRECORDTYPE_UPDATE_DATA = 2;
        /**
         * 删除
         */
        int ADMINRECORDTYPE_DEL_DATA = 3;
        /**
         * 导出
         */
        int ADMINRECORDTYPE_OUT_DATA = 4;
        /**
         * 启用/禁用
         */
        int ADMINRECORDTYPE_OPEN_OR_STOP = 5;
        /**
         * 通过/拒绝
         */
        int ADMINRECORDTYPE_PASS_OR_REFUSE = 6;
        /**
         * 删除订单
         */
        int ADMINRECORDTYPE_DEL_ORDERNO = 7;
    }

    /**
     * 审核状态
     */
    interface ExameStatus {
        /**
         * 审核等待状态
         */
        String EXAME_WAIT_STATUS = "0";
        /**
         * 审核通过状态
         */
        String EXAME_PASS_STATUS = "1";
        /**
         * 审核未通过状态
         */
        String EXAME_NOT_PASS_STATUS = "2";
    }

    /**
     * 商品活动类型
     * lkt_product_list.active
     */
    interface GoodsActive {
        /**
         * 正价商品
         */
        Integer GOODSACTIVE_POSITIVE_PRICE = 1;
        /**
         * 支持拼团
         */
        Integer GOODSACTIVE_SUPPORT_PT = 2;
        /**
         * 支持砍价
         */
        Integer GOODSACTIVE_POSITIVE_KJ = 3;
        /**
         * 支持竞拍
         */
        Integer GOODSACTIVE_POSITIVE_JP = 4;
        /**
         * 会员特惠
         */
        Integer GOODSACTIVE_VIP_DISCOUNT = 6;
        /**
         * 积分
         */
        Integer GOODSACTIVE_INTEGRAL = 7;
        /**
         * 秒杀
         */
        Integer GOODSACTIVE_SECONDS = 8;
        /**
         * 满减
         */
        Integer SUBTRACTION_SECONDS = 10;
    }

    /**
     * 商品展示位
     */
    interface GoodsShowAdr {
        /**
         * 全部商品
         */
        Integer GOODSSHOWADR_DEFAULT = 0;
        /**
         * 首页
         */
        Integer GOODSSHOWADR_INDEX = 1;

        /**
         * 购物车
         */
        Integer GOODSSHOWADR_CART = 2;
    }


    public static void main(String[] args) {
        List<String> s = new ArrayList<>();
    }

    /**
     * 商品审核状态
     * 1.待审核，2.审核通过，3.审核不通过，4.暂不审核
     */
    interface GoodsMchExameStatus {
        /**
         * 审核等待状态
         */
        Integer EXAME_WAIT_STATUS = 1;
        /**
         * 审核通过状态
         */
        Integer EXAME_PASS_STATUS = 2;
        /**
         * 审核未通过状态
         */
        Integer EXAME_NOT_PASS_STATUS = 3;
        /**
         * 暂不审核
         */
        Integer EXAME_STOP_STATUS = 4;
    }

    /**
     * 店铺审核状态
     */
    interface MchExameStatus {
        /**
         * 审核等待状态
         */
        Integer EXAME_WAIT_STATUS = 0;
        /**
         * 审核通过状态
         */
        Integer EXAME_PASS_STATUS = 1;
        /**
         * 审核未通过状态
         */
        Integer EXAME_NOT_PASS_STATUS = 2;
    }


    /**
     * 结算方式
     *
     * @author Trick
     * @date 2020/11/3 14:16
     */
    interface MchConfigSettlement {
        String SETTLEMENT_TYPE0 = "0";
        String SETTLEMENT_TYPE1 = "1";
        String SETTLEMENT_TYPE2 = "2";
    }

    /**
     * 收藏类型
     */
    interface UserCollectionType {
        /**
         * 普通收藏
         */
        Integer COLLECTIONTYPE1 = 1;
        /**
         * 积分商城收藏
         */
        Integer COLLECTIONTYPE2 = 2;
    }

    /**
     * 活动状态
     */
    interface ProductStatus {
        /**
         * 活动未开启
         */
        Integer NOT_OPEN_STATUS = 0;
        /**
         * 活动中
         */
        Integer PRODUCTSTATUS_SUCCESS_STATUS = 1;
        /**
         * 活动结束
         */
        Integer PRODUCTSTATUS_END_STATUS = 2;
        /**
         * 流拍
         */
        Integer UNSOLD_STATUS = 3;
    }

    /**
     * 回收站 0.不回收 1.回收
     */
    interface ProductRecycle {
        /**
         * 不回收
         */
        Integer NOT_STATUS = 0;
        /**
         * 回收-系统回收
         */
        Integer RECOVERY = 1;
        /**
         * 回收-用户回收
         */
        Integer RECOVERY_USER = 2;
        /**
         * 回收-店铺回收
         */
        Integer RECOVERY_MCH = 3;
    }

    /**
     * 商品状态
     */
    interface GoodsStatus {
        /**
         * 待上架
         */
        Integer NOT_GROUNDING = 1;

        /**
         * 已上架
         */
        Integer NEW_GROUNDING = 2;

        /**
         * 已下架
         */
        Integer OFFLINE_GROUNDING = 3;

    }

    /**
     * 消息状态
     */
    interface SystemMessageType {

        /**
         * 未读
         */
        Integer MESSAGE_READE_NO = 1;
        /**
         * 已读
         */
        Integer MESSAGE_READE_OK = 2;

    }

    /**
     * 秒杀插件 常量池
     */
    interface Seckill {
        String SECKILL_NOTICE = "秒杀预告";

        /**
         * 未开始
         */
        String SECKILL_STATUS_NOT_START = "未开始";
        /**
         * 进行中
         */
        String SECKILL_STATUS_START = "进行中";
        /**
         * 秒杀结束
         */
        String SECKILL_STATUS_END = "秒杀结束";

    }

    /**
     * 产品值属性 1：新品,2：热销，3：推荐
     */
    interface GoodsStype {
        /**
         * 新品
         */
        Integer NEW_PRODUCT = 1;

        /**
         * 热销
         */
        Integer HOT_GROUNDING = 2;

        /**
         * 推荐
         */
        Integer TOP_GROUNDING = 3;

    }

    /**
     * 收支状态
     * lkt_mch_account_log.status
     */
    interface MchAccountLogStatus {
        /**
         * 收入
         */
        Integer MCHACCOUNTLOG_STATUS_INCOME = 1;

        /**
         * 支出
         */
        Integer MCHACCOUNTLOG_STATUS_EXPENDITURE = 2;
    }

    /**
     * 收支状态
     * 类型：1.订单 2.退款 3.提现
     * lkt_mch_account_log.type
     */
    interface MchAccountLogType {
        /**
         * 订单
         */
        Integer MCHACCOUNTLOG_TYPE_ORDER = 1;

        /**
         * 退款
         */
        Integer MCHACCOUNTLOG_TYPE_REFUND = 2;

        /**
         * 提现
         */
        Integer MCHACCOUNTLOG_TYPE_WITHDRAWAL = 3;
    }

    /**
     * 库存记录类型
     */
    interface StockType {
        /**
         * 入库
         */
        Integer STOCKTYPE_WAREHOUSING = 0;

        /**
         * 出库
         */
        Integer AGREEMENTTYPE_WAREHOUSING_OUT = 1;

        /**
         * 预警
         */
        Integer AGREEMENTTYPE_WAREHOUSING_WARNING = 2;
    }

    /**
     * 属性类型
     */
    interface SkuType {
        /**
         * 属性名称
         */
        Integer SKUTYPE_NAME = 1;

        /**
         * 属性值
         */
        Integer SKUTYPE_VALUE = 2;

    }

    /**
     * 地理位置
     */
    interface Position {
        /**
         * 省
         */
        Integer LEVEL_2 = 2;

        /**
         * 市
         */
        Integer LEVEL_3 = 3;

        /**
         * 县/区
         */
        Integer LEVEL_4 = 4;
    }

    /**
     * 协议类型
     */
    interface AgreementType {
        /**
         * 注册协议
         */
        Integer AGREEMENTTYPE_REGISTER = 0;

        /**
         * 店铺协议
         */
        Integer AGREEMENTTYPE_MCH = 1;

        /**
         * 隐私协议
         */
        Integer AGREEMENTTYPE_PRIVACY = 2;
    }

    /**
     * 售后类型
     */
    interface ServiceAddressType {
        /**
         * 发货地址
         */
        Integer SERVICEADDRESSTYPE_DELIVER = 1;
        /**
         * 售后地址
         */
        Integer ServiceAddressType_AfterSale = 2;
    }

    /**
     * 字典目录
     */
    interface DicName {
        /**
         * 商品活动类型
         */
        String DIC_GOODS_ACTIVE = "商品活动类型";
    }

    /**
     * 插件
     */
    interface Plugin {
        /**
         * 优惠卷
         */
        String COUPON = "coupon";
        /**
         * 店铺
         */
        String MCH = "mch";
        /**
         * 会员
         */
        String MEMBER = "member";
        /**
         * 平台活动
         */
        String PLATFORMACTIVITIES = "platform_activities";
        /**
         * 秒杀
         */
        String SECONDS = "seconds";
        /**
         * 签到
         */
        String SIGN = "sign";
        /**
         * 拼团
         */
        String GOGROUP = "go_group";
        /**
         * 砍价
         */
        String BARGAIN = "bargain";
        /**
         * 分销
         */
        String DISTRIBUTION = "distribution";
        /**
         * 积分商城
         */
        String INTEGRAL = "integral";
        /**
         * 满减
         */
        String SUBTRACTION = "subtraction";
        /**
         * diy
         */
        String DIY = "diy";
        /**
         * 竞拍
         */
        String AUCTION = "auction";

        /**
         * 钱包
         */
        String WALLET = "wallet";

    }

}
