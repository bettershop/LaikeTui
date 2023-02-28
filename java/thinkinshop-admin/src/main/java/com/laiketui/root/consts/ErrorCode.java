package com.laiketui.root.consts;

public interface ErrorCode {

    /**
     * 系统级错误
     */
    interface SysErrorCode {
        // 1-99
        /**
         * 图片上传失败
         */
        String UPLOAD_FAIL = "1";
        /**
         * 短信发送失败
         */
        String SEND_SMS_FAIL = "10";
        /**
         * redis 保存失败
         */
        String SAVE_REDIS_FAIL = "11";

        /**
         * 方法不存在
         */
        String FUNCTION_NOT_EXISTS = "12";
        /**
         * 方法错误
         */
        String FUNCTION_ERROR = "13";
    }

    /**
     * 业务级错误
     */
    interface BizErrorCode {
        // 100 - 9999

        /**
         * 库存不足
         */
        String INSUFFICIENT_STOCK = "105";

        /**
         * 参数错误
         */
        String PARAMETER_VAL_ERROR = "109";

        /**
         * 商品已下架
         */
        String OFF_SHELF = "0105";

        /**
         * 业务异常
         */
        String ABNORMAL_BIZ = "110";

        /**
         * 非法入侵
         */
        String ILLEGAL_INVASION = "115";

        /**
         * 注册失败
         */
        String REGISTER_FAIL = "199";
        /**
         * 注册失败,userid生成失败
         */
        String REGISTER_USERID_FAIL = "201";

        /**
         * 已经发货
         */
        String ORDER_DELIVERYED = "202";

        /**
         * 未登录
         */
        String LOGIN_INVALID = "203";

        /**
         * 已经提醒过了,请稍后再试！
         */
        String ORDER_REMIND_ALREADY = "215";

        /**
         * 提醒发货失败
         */
        String ORDER_REMIND_FAILED = "216";

        /**
         * 账号已被注册
         */
        String REGISTER_ZH_FAIL = "300";
        /**
         * 手机号已被注册
         */
        String REGISTER_PHONE_FIAL = "301";
        /**
         * 验证码错误
         */
        String PCODE_FIAL = "302";
        /**
         * 验证码不存在或失效
         */
        String PCODE_INVALID = "303";

        /**
         * 密码不正确
         */
        String LOGIN_PWD_FAIL = "304";
        /**
         * 用户名或密码不正确
         */
        String LOGIN_NAME_FAIL = "305";
        /**
         * 参数异常
         */
        String PARAMATER_ERROR = "306";
        /**
         * 短信模板不存在
         */
        String SMS_TEMPLATE_NOT_EXIST = "307";
        /**
         * 验证码未失效
         */
        String REGISTER_PCODE_NOT_INVALID = "308";
        /**
         * 未注册
         */
        String LOGIN_NOT_REGISTER = "309";
        /**
         * 出生日期只能修改一次
         */
        String BIRTHDAY_LIMIT_ONE = "310";
        /**
         * 用户名格式不正确
         */
        String USERNAME_NOT_CHECK = "311";
        /**
         * 用户名格式不正确
         */
        String MIMA_NOT_CHECK = "312";
        /**
         * 支付密码格式不正确
         */
        String PAY_PASSWORD_NOT_CHECK = "313";
        /**
         * 账号 格式不正确
         */
        String ZHANGHAO_NOT_CHECK = "314";
        /**
         * 手机号 格式不正确
         */
        String PHONE_NOT_CHECK = "315";
        /**
         * 出生日期 格式不正确
         */
        String BIRTHDAY_NOT_CHECK = "316";
        /**
         * 操作失败
         */
        String OPERATION_FAILED = "317";

        /**
         * 未选择图片
         */
        String IMAGE_NOT_CHECKED = "320";
        /**
         * 图片文件类型为空
         */
        String IMAGE_TYPE_NULL = "321";
        /**
         * 图片文件类型有误
         */
        String IMAGE_TYPE_NOT_CHECK = "322";
        /**
         * 图片名不能为空
         */
        String IMAGE_NAME_NOT_NULL = "323";

        /**
         * 店铺名称 格式不正确
         */
        String MCH_NAME_NOT_CHECK = "324";
        /**
         * 店铺信息 格式不正确
         */
        String MCH_INFO_NOT_CHECK = "325";
        /**
         * 店铺经营范围 格式不正确
         */
        String MCH_RANGE_NOT_CHECK = "326";
        /**
         * 身份证 格式不正确
         */
        String MCH_IDCARD_NOT_CHECK = "327";
        /**
         * 联系电话 格式不正确
         */
        String MCH_TEL_NOT_CHECK = "328";
        /**
         * 联系地址 格式不正确
         */
        String MCH_ADDRESS_NOT_CHECK = "329";

        /**
         * 用户未设置密码
         */
        String USER_NOT_CONFIG_PWD = "330";

        /**
         * 用户不存在
         */
        String USER_NOT_EXIST = "331";
        /**
         * 数据未找到
         */
        String DATA_NOT_EXIST = "332";
        /**
         * 库存不足
         */
        String STOCK_INSUFFICIENT = "333";
        /**
         * 商品未上架
         */
        String GOODS_OFF_SHELF = "334";
        /**
         * 商品数据不完整
         */
        String GOODS_DATA_INCOMPLETE = "335";
        /**
         * 数据处理失败
         */
        String DATA_HANDLE_FAIL = "336";
        /**
         * 数据错误
         */
        String DATA_ERROR = "337";
        /**
         * 配置不存在
         */
        String DATA_NOT_CONFIG = "338";

        /**
         * 登陆失败
         */
        String LOGIN_FAIL = "400";

        /**
         * 微信AccessToken获取失败
         */
        String GET_ACCESSTOKEN_FAIL = "401";

        /**
         * 微信Ticket获取失败
         */
        String GET_TICKET_FAIL = "402";
        /**
         * 数据非法
         */
        String DATA_ILLEGAL_FAIL = "412";
        /**
         * 数据已存在
         */
        String DATA_ALREADY_EXISTS = "413";
        /**
         * 权限不够
         */
        String INSUFFICIENT_PRIVILEGE = "414";
        /**
         * 密码未变化
         */
        String USER_PASSWORD_REPEAT = "415";
        /**
         * 密码重试次数过多
         */
        String USER_PASSWORD_OVER = "416";
        /**
         * 提现金额小于最低提现金额
         */
        String WITHDRAWAL_LESS_THAN_AMT = "417";
        /**
         * 提现金额大于最大提现金额
         */
        String WITHDRAWAL_GREATER_THAN_AMT = "418";
        /**
         * 提现金额大于店主金额
         */
        String WITHDRAWAL_GREATER_THAN_CASHABLE_AMT = "419";
        /**
         * 交易还未审核
         */
        String WITHDRAWAL_APPLY_NOT_REVIEWED = "420";
        /**
         * 交易还未审核
         */
        String WITHDRAWAL_EXCEED_UPPER_LIMIT = "421";
        /**
         * 持卡人不正确
         */
        String BANK_CARDHOLDER_ERROR = "422";
        /**
         * 卡号不正确
         */
        String BANKNO_ERROR = "423";
        /**
         * 卡号与银行不匹配
         */
        String BANKNO_NAME_ERROR = "424";
        /**
         * 请设置分佣
         */
        String PLEASE_SET_THE_FREIGHT_RATE_FIRST = "425";

        /**
         * 优惠券筛选失败
         */
        String COUPON_SCREENING_ERROR = "426";

        /**
         * 店铺优惠券筛选失败
         */
        String COUPON_MCH_SCREENING_ERROR = "427";

        /**
         * 平台优惠券筛选失败
         */
        String COUPON_PLAFORM_SCREENING_ERROR = "428";

        /**
         * 会员优惠计算失败
         */
        String MEMBER_YOUHUI_ERROR = "429";

        /**
         * 订单运费计算失败
         */
        String ORDER_JS_FREIGHT_ERROR = "430";

        /**
         * 订单满减计算失败
         */
        String ORDER_SUBSTRACTION_JS_ERROR = "431";

        /**
         * 下单失败,请稍后再试
         */
        String ORDER_FAILED_TRY_AGAIN_LATER = "432";


        /**
         * 更新兑换券使用状态失败
         */
        String ORDER_FAILED_UPDATE_COUPON_STATUS = "433";

        /**
         * 添加优惠券关联订单数据失败
         */
        String ORDER_FAILED_ADD_COUPON_ASSOSIATED_ORDERINFO = "434";

        /**
         * 创建订单号失败
         */
        String ORDER_NUMBER_FAILED = "435";

        /**
         * 添加满减记录失败
         */
        String ORDER_FAILED_ADD_SUBTRACTION_RECORDER = "436";

        /**
         * 订单列表获取失败
         */
        String ORDER_LIST_FAILED = "437";

        /**
         * 优惠券拆分订单失败
         */
        String ORDER_COUPON_CF_ERROR = "438";

        /**
         * 金额错误
         */
        String AMOUNT_ERROR = "439";

        /**
         * 余额不足
         */
        String INSUFFICIENT_BALANCE = "440";

        /**
         * 订单数据错误
         */
        String ORDER_DATA_ERROR = "441";

        /**
         * 收货失败
         */
        String RECEIVING_FAILED = "500";

        /**
         * 网络繁忙
         */
        String BUSY_NETWORK = "502";
        /**
         * 操作失败
         */
        String API_OPERATION_FAILED = "503";

        /**
         * 未授权域名
         */
        String NO_LICENSE_DOMAIN = "504";

        /**
         * 未配置定位服务
         */
        String GPS_NOT_CONFIG = "101";
        /**
         * 未获取到数据
         */
        String READ_NOT_DATA = "102";
        /**
         * 请重新获取验证码
         */
        String READ_PHONE_CODE_NOT_DATA = "103";

        /**
         * 订单确认信息获取失败
         */
        String ORDER_SETTLEMENT_ERROR = "600";

        /**
         * 腾讯定位api错误
         */
        String TENGXUN_API_ERROR = "1001";

        /**
         * 下单失败
         */
        String PAYMENT_BUILDER_RQCODE_ERROR = "1002";
        /**
         * 退款失败
         */
        String PAYMENT_NOT_REFUND_ERROR = "1101";

        /**
         * 支付密码不正确
         */
        String PAYMENT_PASSWORD_ERROR = "1102";

        /**
         * 支付失败
         */
        String PAYMENT_FAIL = "1103";
        /**
         * 您已生成待付款订单，请前往支付！
         */
        String PLASE_GO_TO_PAYMENT = "1104";


        /**
         * 签到插件未启用
         */
        String PLUGIN_SIGIN_NOT_OPEN = "3000";
        /**
         * 已经签到过了
         */
        String PLUGIN_DONE_DID_SIGIN = "3001";
        /**
         * 签到活动还未开启
         */
        String PLUGIN_SIGN_NOT_START = "3002";
        /**
         * 签到活动已经结束
         */
        String PLUGIN_SIGN_END = "3003";
        /**
         * 签到失败
         */
        String PLUGIN_SIGN_ERROR = "3004";


        /**
         * 秒杀-数据异常
         */
        String PLUGIN_SECONDS_DATA_ERROR = "3100";
        /**
         * 秒杀-秒杀商品不存在
         */
        String PLUGIN_SECONDS_GOODS_NOT_EXIST = "3101";
        /**
         * 秒杀-库存不足
         */
        String PLUGIN_SECONDS_STOCK_OUT = "3102";


        /**
         * 积分商城-积分商品不存在
         */
        String PLUGIN_INTEGRAL_GOODS_NOT_EXIST = "3200";

        /**
         * 积分订单下单失败
         */
        String PLUGIN_INTEGRAL_BUILDE_ORDER_ERROR = "3201";
        /**
         * 下单失败-余额不足
         */
        String PLUGIN_INTEGRAL_BALANCE_UNABLE_TO_EXCHANGE = "3202";
        /**
         * 下单失败-积分不足
         */
        String PLUGIN_INTEGRAL_SCORE_UNABLE_TO_EXCHANGE = "3203";
        /**
         * 下单失败-库存不足
         */
        String PLUGIN_INTEGRAL_STOCK_NOT_NUM = "3204";


        /**
         * 砍价订单不存在
         */
        String PLUGIN_BARGAIN_ORDER_NOT_EXIST = "3300";
        /**
         * 砍价商品不存在
         */
        String PLUGIN_BARGAIN_GOODS_NOT_EXIST = "3301";
        /**
         * 砍价商品属性不存在
         */
        String PLUGIN_BARGAIN_GOODS_ATTR_NOT_EXIST = "3302";
        /**
         * 砍价商品库存不足
         */
        String PLUGIN_BARGAIN_GOODS_NOT_STOCK = "3303";
        /**
         * 砍价失败
         */
        String PLUGIN_BARGAIN_PRICE_ERROR = "3304";

        /**
         * 您的店铺账户尚有进行中订单,无法退还保证金
         */
        String MCH_RETURN_PROMISE_PRICE_ERROR = "3401";

        /**
         * EXCEL文件导出失败
         */
        String FILE_EXCEL_ERROR = "5001";
    }

    /**
     * 容器类错误
     */
    interface ContainerErrorCode {
        // 10000- 99999
        /**
         * redis中 相同key 对应不同对象
         */
        String REDIS_MUTIVALS = "10000";

        /**
         * 验证码错误
         */
        String JWT_VERIFY_FAIL = "10001";

        /**
         * 网关参数错误
         */
        String WG_ERROR_PARAMS = "10002";

        /**
         * 接口异常结果
         */
        String GW_API_FAIL = "10003";

    }


}
