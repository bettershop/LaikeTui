package com.laiketui.root.consts;

/**
 * @description: 全局常量
 * @author: wx
 * @date: Created in 2019/10/26 14:30
 * @version: 1.0
 * @modified By:
 */
public interface GloabConst {

    /**
     * 协议
     */
    interface ProtocolConst {
        String DUBBO = "dubbo";
        String HTTP = "http";
    }

    /**
     * 分页
     */
    interface PageEnum {
        int TERMINAL_DEFAULT_PAGE = 0;
        int TERMINAL_DEFAULT_PAGESIZE = 10;
    }

    /**
     * 时间格式
     */
    interface TimePattern {
        String YMDHMS = "yyyy-MM-dd HH:mm:ss";
        String YMDHMS1 = "yyyyMMddHHmmss";
        String YMDHMS2 = "yyMMddHHmmss";
        String YMDHM = "yyyy-MM-dd HH:mm";
        String YMD = "yyyy-MM-dd";
        String YMD1 = "yyyyMMdd";
        String YMD2 = "yyyy-M-dd";
        String YMD3 = "yyyy.M.dd";
        String YMD4 = "yyyy-M-d";
        String YM = "yyyy-MM";
        String HMS = "HH:mm:ss";
        String HM = "HH:mm";
        String MD = "MMdd";
        String MD1 = "MM/dd";
        String MM = "MM";
        String D = "dd";
        String D1 = "d";
        /**
         * 1970-01-01
         */
        String TIME = "1970-01-01 ";
        /**
         * 年月日
         */
        String YMD_CN = "yyyy年MM月dd日";
    }

    /**
     * 缓存key头部
     */
    interface RedisHeaderKey {

        /**
         * 注册
         */
        String REGISTER_HEADER = "REGISTER_HEADER_";

        /**
         * 登陆
         */
        String LOGIN_HEADER = "LOGIN_HEADER_";

        /**
         * 修改登陆密码
         */
        String UPDATE_PASSWORDE = "UPDATE_PASSWORDE_";

        /**
         * 修改支付密码
         */
        String UPDATE_PWD_PAY_CODE = "UPDATE_PWD_PAY_CODE_";
        /**
         * 提现
         */
        String DRAWING_CODE = "DRAWING_CODE_";

        /**
         * 修改手机号
         */
        String UPDATE_PHOE_CODE = "UPDATE_PHOE_CODE_";

        /**
         * 通用短信模板key
         */
        String CURRENCY_CODE = "CURRENCY_CODE_";


        /**
         * 有效的商城id
         */
        String LKT_STOREID_EFFECTIVE_LIST = "LKT_STOREID_EFFECTIVE_LIST";


        /**
         * h5商城首页缓存
         * param = 商城Id
         */
        String JAVA_INDEX_CACHE = "JAVA_INDEX_CACHE_%s";

        /**
         * 商城首页缓存-单用户数据
         * param = 商城Id,登录token
         */
        String index_cache_condition_ = "index_cache_condition_%s_%s";

        /**
         * 微信AccessToken键名
         */
        String WEIXIN_ACCESS_TOKEN = "WEIXIN_ACCESS_TOKEN_";

        /**
         * 敏感词库
         */
        String LKT_SENSITIVE_WORDS = "LKT_SENSITIVE_WORDS";
        /**
         * 脱敏
         */
        String LKT_SENSITIVE_WORDS_SENSITIVE = "LKT_SENSITIVE_WORDS_SENSITIVE";
        /**
         * 店铺名称非法词汇库
         */
        String LKT_SHOPNAME_ILLEGAL_WORDS = "LKT_SHOPNAME_ILLEGAL_WORDS";

        /**
         * 银行卡所属库
         */
        String LKT_BANK_CARD_LIBRARY = "LKT_BANK_CARD_LIBRARY";


        /**
         * 微信 TICKET 键名
         */
        String WEIXIN_TICKET = "WEIXIN_ACCESS_TOKEN";


        /**
         * 单点登录标识
         */
        String LOGIN_ACCESS_FLAG = "LOGIN_ACCESS_FLAG_";
        /**
         * 单点登录标识-店铺后台
         */
        String LOGIN_ACCESS_FLAG_MCH = "LOGIN_ACCESS_FLAG_MCH_";
        /**
         * 登陆验授权令牌 会员
         */
        String LOGIN_ACCESS_TOKEN = "LOGIN_ACCESS_TOKEN_";
        /**
         * 登陆验授权令牌-店铺后台
         */
        String LOGIN_ACCESS_TOKEN_MCH = "LOGIN_ACCESS_TOKEN_MCH_";
        /**
         * 登陆验授权令牌 后台
         */
        String LOGIN_ACCESS_MANAGE_TOKEN = "LOGIN_ACCESS_MANAGE_TOKEN_";
        /**
         * 单点登录标识 后台
         */
        String LOGIN_ACCESS_MANAGE_FLAG = "LOGIN_ACCESS_MANAGE_FLAG_";
        /**
         * pc商城授权令牌
         */
        String LOGIN_ACCESS_PC_SHOP_TOKEN = "LOGIN_ACCESS_PC_SHOP_TOKEN_";
        /**
         * 单点登录标识 pc商城
         */
        String LOGIN_ACCESS_PC_SHOP_FLAG = "LOGIN_ACCESS_PC_SHOP_FLAG_";


        /**
         * url地址缓存key
         */
        String LKT_URLMODAL = "LKT_URLMODAL";

        /**
         * 微信直播api token
         */
        String WX_API_LIVE_BROADCAST_ACCESSTOKEN = "WX_API_LIVE_BROADCAST_ACCESSTOKEN";

        /**
         * 所有快递信息
         */
        String EXPRESS_ALL = "EXPRESS_ALL";

        /**
         * 售后图片上传
         */
        String RETURN_UPLOAD_KEY = "RETURN_UPLOAD_KEY_";
        /**
         * 评论图片上传
         */
        String COMMENTS_UPLOAD_KEY = "COMMENTS_UPLOAD_KEY_";
    }


    /**
     * 用户身份
     */
    interface UserIdentity {
        /**
         * 会员
         */
        Integer USER_VIP = 1;
        /**
         * 游客
         */
        Integer USER_YOUKE = 0;
    }

    /**
     * 字典目录
     */
    @Deprecated
    interface DictionaryName {

        /**
         * 短信模板类型
         */
        @Deprecated
        String SMS_TEMPLATE_TYPE = "短信模板类型";

        /**
         * 短信模板类别
         */
        @Deprecated
        String SMS_TEMPLATE_CATEGORY = "短信模板类别";

    }

    /**
     * 魔法值特殊处理
     */
    interface ManaValue {
        String MANA_VALUE_TRUE = "true";
        String MANA_VALUE_ROWS = "rows";

        String MANA_VALUE_SUCCESS = "Success";
        String MANA_VALUE_OK = "ok";
        String MANA_VALUE_FAIL = "Fail";
        String MANA_VALUE_ERRCODE = "errcode";
        String MANA_VALUE_OPPENID = "openid";
        String MANA_VALUE_SESSION_KEY = "session_key";
        String MANA_VALUE_UNDEFINDED = "undefinded";
        String MANA_VALUE_PICK = "pick";

    }

    /**
     * 系统全局配置
     */
    interface LktConfig {
        String LOGO_DEFAULT_URL = "https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/1550673836340.png";

        /**
         * 用户登陆有效时间/秒
         */
        Integer LOGIN_EXISTENCE_TIME = 60 * 60 * 2;

        /**
         * 图像验证码失效时间
         */
        Integer IMG_CODE_EXISTENCE_TIME = 60 * 60 / 2;


        /**
         * 身份验证token时效
         */
        Integer AUTHENTICATION_PROCESS_EXISTENCE_TIME = 60 * 30;

        /**
         * 图片上传默认域名
         */
        String UPLOADIMG_DOMAIN = "https://xiaochengxu.laiketui.com/V2.7";

        /**
         * 小程序 使用 DictionaryConst.StoreSource
         */
        @Deprecated
        Integer STORETYPE_XIAOCHENXU = 1;
        /**
         * App
         */
        @Deprecated
        Integer STORETYPE_APP = 2;

        /**
         * 支付密码重试次数
         */
        Integer PASSWORD_VALIDATE_NUM = 5;


        /**
         * 0 = 平台
         */
        Integer LKT_CONFIG_TYPE_PT = 0;

        /**
         * 1=pc店铺
         */
        Integer LKT_CONFIG_TYPE_PC = 1;

        /**
         * 2=店铺/移动端
         */
        Integer LKT_CONFIG_TYPE_MCH = 2;

        /**
         * 注册
         */
        String REGISTER_TYPE = "1";
        /**
         * 免注册
         */
        String REGISTER_TYPE2 = "2";

        /**
         * 系统消息读取状态 未读
         */
        Integer SYSMESSAGE_NOT_READ = 1;

        /**
         * 系统消息读取状态 已读
         */
        Integer SYSMESSAGE_READ = 2;

        /**
         * 单个订单最多申请x次售后
         */
        Integer RETURN_ORDER_LIMIT = 2;

    }

    /**
     * 图片上传配置key
     */
    interface UploadConfigConst {
        /**
         * 图片上传方式 本地
         */
        String IMG_UPLOAD_LOCALHOST = "1";
        /**
         * 图片上传方式 阿里云
         */
        String IMG_UPLOAD_OSS = "2";
        /**
         * 图片上传方式 腾讯云
         */
        String IMG_UPLOAD_TXY = "3";
        /**
         * 图片上传方式 七牛云
         */
        String IMG_UPLOAD_QNY = "4";


        String IMG_GIF = "GIF";
        String IMG_PNG = "PNG";
        String IMG_JPG = "JPG";
        String IMG_JPEG = "JPEG";

        /**
         * oos 协议头
         */
        String OOS_HTTP_HEADER = "https://";

        /**
         * 七牛云 协议头
         */
        String QINIU_HTTP_HEADER = "https://";

        /**
         * 腾讯云 协议头
         */
        String TENXUN_HTTP_HEADER = "https://";

        /**
         * 上传文件夹 格式 xxx_店铺id
         */
        String IMG_PATH = "image_";

        /**
         * 公钥
         */
        String ACCESSKEYID = "AccessKeyID";

        /**
         * 私钥
         */
        String ACCESSKEYSECRET = "AccessKeySecret";

        /**
         * 自定义域名
         */
        String ENDPOINT = "Endpoint";

        /**
         * 仓库
         */
        String BUCKET = "Bucket";

        /**
         * 是否开启自定义域名
         */
        String ISOPENZDY = "isopenzdy";
        /**
         * 图片样式
         */
        String IMAGESTYLE = "imagestyle";

        /**
         * 本地上传地址
         */
        String UPLOADIMG_DOMAIN = "uploadImg_domain";

        /**
         * 本地上传路径
         */
        String UPLOADIMG = "uploadImg";
        /**
         * 自定义域名
         */
        String MYENDPOINT = "MyEndpoint";
    }

    /**
     * 验证码类别
     */
    interface VcodeCategory {

        /**
         * 验证码
         */
        int TYPE_VERIFICATION = 0;
        /**
         * 通知类型
         */
        int TYPE_NOTICE = 1;

        /**
         * 登录
         */
        int LOGIN_CODE = 1;
        /**
         * 验证码注册
         */
        int REGISTER_CODE = 2;
        /**
         * 修改手机号
         */
        int UPDATE_PHOE_CODE = 3;
        /**
         * 修改登陆密码
         */
        int UPDATE_PWD_CODE = 4;
        /**
         * 修改支付密码
         */
        int UPDATE_PWD_PAY_CODE = 5;
        /**
         * 提现模板
         */
        int DRAWING_CODE = 6;
        /**
         * 通用模板
         */
        int CURRENCY_CODE = 7;
        /**
         * 支付成功通知
         */
        int PAY_SUCCESS_NOTICE = 8;
        /**
         * 退款
         */
        int PAY_REFUND_ORDER = 9;
    }

    /**
     * 插件code
     * 使用 DictionaryConst.Plugin.xx
     */
    @Deprecated
    interface PluginCode {
        /**
         * div插件名称
         */
        String DIV_CODE = "div";
    }


    /**
     * 内部跳转url
     */
    interface LaikeTuiUrl {
        /**
         * 分销商品分享 url
         * pro_id = 商品Id
         * fx_id = 分销id
         * fatherId = 上级id
         */
        String LAIKE_FX_GOODS_SHARE_URL = "pages/goods/goodsDetailed?isDistribution=true&pro_id=%s&fx_id=%s&isfx=true&fatherId=%s";
        /**
         * 拼团商品分享 url
         * pro_id = 商品Id
         * activity_no = 拼团活动id
         * fatherId = 上级id
         */
        String LAIKE_PT_GOODS_SHARE_URL = "pages/goods/goodsDetailed?pro_id=%s&activity_no=%s&isfx=true&fatherId=%s";
        /**
         * 拼团商品分享 url
         * pro_id = 商品Id
         * activity_no = 拼团活动id
         * platform_activities_id = [平台]拼团活动id
         * fatherId = 上级id
         */
        String LAIKE_PP_GOODS_SHARE_URL = "pagesA/group/groupDetailed?pro_id=%s&a_type=1&activity_no=%s&isfx=true&platform_activities_id=%s&fatherId=%s";

        /**
         * 拼团商品分享 url
         * sNo = 订单号
         * activity_no = 拼团活动id
         * fatherId = 上级id
         */
        String LAIKE_PT_END_GOODS_SHARE_URL = "pagesA/group/group_end?sNo=%s&friend=true&activity_no=%s&fatherId=%s";

        /**
         * 【平台】拼团商品分享 url
         * sNo = 订单号
         * activity_no = 拼团活动id
         * platform_activities_id = [平台]拼团活动id
         * fatherId = 上级id
         */
        String LAIKE_PP_END_GOODS_SHARE_URL = "pagesA/group/group_end?sNo=%s&friend=true&a_type=1&activity_no=%s&fatherId=%s";

        /**
         * 拼团商品分享 url
         * bindding_id = 竞拍id
         * fatherId = 上级id
         */
        String LAIKE_JP_GOODS_SHARE_URL = "pagesA/bidding/bidding_detailed?bindding_id=%s&type=1&isfx=true&fatherId=%s";
        /**
         * 砍价商品分享 url
         * proId = 商品id
         * attr_id = 规格id
         * order_no = 订单号
         * brStatus = 砍价状态
         * bargain_id = 砍价id
         * fatherId = 上级id
         */
        String LAIKE_KJ_GOODS_SHARE_URL = "pagesA/bargain/bargainIng?proId=%s&attr_id=%s&order_no=%s&brStatus=%s&friend=true&bargain_id=%s&fatherId=%s";

        //前端跳转地址
        enum JumpPath {
            //分类跳转
            GOODS_CLASS_APP("/pages/goods/goods", "cid=%s"),
            GOODS_CLASS_PC("index.html?module=homeList&action=homeList", "cid=%s"),
            //商品
            GOODS_APP("/pages/goods/goodsDetailed", "productId=%s"),
            GOODS_PC("index.html?module=homedetail&action=homedetail", "id=%s"),
            //店铺
            MCH_APP("/pagesA/store/store", "shop_id=%s"),
            ;
            //路径
            String path;
            //参数
            String parma;

            JumpPath(String path, String parma) {
                this.path = path;
                this.parma = parma;
            }

            public String getPath() {
                return path;
            }

            public String getParma() {
                return parma;
            }
        }

    }

    /**
     * 微信各种接口url
     */
    interface WeiXinUrl {
        /**
         * 获取AccessToken
         */
        String ACCESSTOKEN_GET_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

        /**
         * token 生存时间
         */
        int ACCESSTOKEN_EXPIRES = 7000;


        /**
         * 获取Ticket
         */
        String TICKET_JSAPI_GET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=%s";


        /**
         * 获取会话密钥
         */
        String SESSION_KEY_GET_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

        /**
         * 微信直播列表
         * 适用于需要的码数量极多的业务场景
         */
        String LIVE_BROADCAST_LIST_ACCESSTOKEN_GET_URL = "https://api.weixin.qq.com/wxa/business/getliveinfo?access_token=%s";

        /**
         * 获取小程序码 A
         * 生成小程序码，可接受 path 参数较长，生成个数受限
         * 接口 A 加上接口 C，总共生成的码数量限制为 100,000，请谨慎调用。
         * 文档:
         * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/qr-code.html
         */
        String SHARE_A_GRCODE_GET_URL = "https://api.weixin.qq.com/wxa/getwxacode?access_token=%s";
        /**
         * 获取小程序码 B
         * 生成小程序码，可接受页面参数较短，生成个数不受限。
         * 文档:
         * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/qr-code.html
         */
        String SHARE_B_GRCODE_GET_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";
        /**
         * 获取小程序码 C
         * 生成二维码，可接受 path 参数较长，生成个数受限
         * 接口 A 加上接口 C，总共生成的码数量限制为 100,000，请谨慎调用。
         * 文档:
         * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/qr-code.html
         */
        String SHARE_C_GRCODE_GET_URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=%s";

        /**
         * 获取体验版二维码
         * access_token	String	是	第三方平台接口调用令牌authorizer_access_token
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/code/get_qrcode.html
         */
        String EXPERIENCE_QRCODE_GET_URL = "https://api.weixin.qq.com/wxa/get_qrcode?access_token=%s";

        /**
         * 授权给第三方平台
         * component_appid	是	第三方平台方 appid
         * pre_auth_code	是	预授权码
         * redirect_uri	是	回调 URI
         * auth_type	否	要授权的帐号类型， 1 则商户扫码后，手机端仅展示公众号、2 表示仅展示小程序，3 表示公众号和小程序都展示。如果为未指定，则默认小程序和公众号都展示。第三方平台开发者可以使用本字段来控制授权的帐号类型。
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Authorization_Process_Technical_Description.html
         */
        String AUTHORIZATION_TO_THIRD_PARTY = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=%s";

        /**
         * 获取 component_access_token 令牌
         * component_appid	string	是	第三方平台 appid
         * component_appsecret	string	是	第三方平台 appsecret
         * component_verify_ticket	string	是	微信后台推送的 ticket
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/api/component_access_token.html
         */
        String COMPONENT_ACCESS_TOKEN_POST = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";

        /**
         * 获取/刷新接口调用令牌
         * component_access_token	string	是	第三方平台component_access_token
         * component_appid	string	是	第三方平台 appid
         * authorizer_appid	string	是	授权方 appid
         * authorizer_refresh_token	string	是	刷新令牌，获取授权信息时得到
         * 文档:
         * https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=COMPONENT_ACCESS_TOKEN
         */
        String API_AUTHORIZER_TOKEN_POST = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token=%s";

        /**
         * 获取预授权码
         * component_access_token	string	是	第三方平台component_access_token，不是authorizer_access_token
         * component_appid	string	是	第三方平台 appid
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/api/pre_auth_code.html
         */
        String API_CREATE_PREAUTHCODE_POST = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=%s";

        /**
         * 查询指定发布审核单的审核状态
         * access_token	String	是	第三方平台接口调用令牌authorizer_access_token
         * auditid	String	是	提交审核时获得的审核 id
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/code/get_auditstatus.html#
         */
        String GET_AUDITSTATUS_POST = "https://api.weixin.qq.com/wxa/get_auditstatus?access_token=%s";

        /**
         * 小程序审核撤回
         * access_token	String	是	第三方平台接口调用令牌authorizer_access_token
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/code/undocodeaudit.html
         */
        String UNDOCODEAUDIT_GET = "https://api.weixin.qq.com/wxa/undocodeaudit?access_token=%s";

        /**
         * 上传小程序代码
         * access_token	String	是	第三方平台接口调用令牌authorizer_access_token
         * template_id	String	是	代码库中的代码模板 ID
         * ext_json	String	是	第三方自定义的配置
         * user_version	String	是	代码版本号，开发者可自定义（长度不要超过 64 个字符）
         * user_desc	String	是	代码描述，开发者可自定义
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/code/commit.html
         */
        String CODE_COMMIT_POST = "https://api.weixin.qq.com/wxa/commit?access_token=%s";

        /**
         * 设置服务器域名
         * access_token	string	是	第三方平台接口调用令牌authorizer_access_token
         * action	string	是	操作类型
         * requestdomain	string array	 是	request 合法域名；当 action 是 get 时不需要此字段
         * wsrequestdomain	string array	 是	socket 合法域名；当 action 是 get 时不需要此字段
         * uploaddomain	string array	 是	uploadFile 合法域名；当 action 是 get 时不需要此字段
         * downloaddomain	string array	 是	downloadFile 合法域名；当 action 是 get 时不需要此字段
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/Server_Address_Configuration.html
         */
        String MODIFY_DOMAIN_POST = "https://api.weixin.qq.com/wxa/modify_domain?access_token=%s";

        /**
         * 获取审核时可填写的类目信息
         * access_token	string	是	第三方平台接口调用令牌
         * 文档:
         * https://api.weixin.qq.com/wxa/get_category?access_token=ACCESS_TOKEN
         */
        String GET_EXAMINE_CATEGORY = "https://api.weixin.qq.com/wxa/get_category?access_token=%s";

        /**
         * 获取已上传的代码的页面列表
         * access_token	String	是	第三方平台接口调用令牌authorizer_access_token
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/code/get_page.html
         */
        String GET_UPLOAD_CODE_PAGE_LIST = "https://api.weixin.qq.com/wxa/get_page?access_token=%s";

        /**
         * 提交审核
         * access_token	String	是	第三方平台接口调用令牌authorizer_access_token
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/code/submit_audit.html
         */
        String SUBMIT_AUDIT_POST = "https://api.weixin.qq.com/wxa/submit_audit?access_token=%s";

        /**
         * 发布已通过审核的小程序
         * 文档:
         * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/code/release.html
         */
        String RELEASE_APP_POST = "https://api.weixin.qq.com/wxa/release?access_token=%s";
    }

    /**
     * 支付宝各种接口url
     */
    interface AlibabaApiUrl {
        /**
         * 支付宝网关
         */
        String ALIPAY_GET_URL = "https://openapi.alipay.com/gateway.do";


        /**
         * 支付宝沙箱网关
         */
        String ALIPAY_DEV_GET_URL = "https://openapi.alipaydev.com/gateway.do";

    }

    /**
     * 腾讯各种接口url
     */
    interface TengXunUrl {
        /**
         * 根据经纬度获取位置信息 url
         * 结构:
         *  https://apis.map.qq.com/ws/geocoder/v1/?location=39.984154,116.307490&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&get_poi=1
         */
        String TX_GET_ADDRESS = "http://apis.map.qq.com/ws/geocoder/v1/?location=%s&key=%s&get_poi=1";

        /**
         * 距离矩阵 批量获取
         * to 参数用';'隔开
         */
        String TX_GET_DISTANCEMATRIX = "https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from=%s&to=%s&key=%s";

        /**
         * 根据地址获取经纬度
         */
        String TX_GET_LONGITUDE_LATITUDE = "https://apis.map.qq.com/ws/geocoder/v1/?address=%s&key=%s";
    }

    /**
     * 其它外部接口
     */
    interface OtherUrl {
        /**
         * 快递100免费查询接口 url
         */
        String API_KUAIDI100_URL = "http://www.kuaidi100.com/query?type=%s&postid=%s";


        /**
         * 淘宝商品抓取 url
         */
        String API_TAOBAO_GOODSDATA_URL = "https://item.taobao.com/item.htm?id=%s";


        /**
         * 淘宝商品详情抓取 url
         */
        String API_TAOBAO_GOODSDATA_DETAIL_URL = "https://world.taobao.com/item/%s.html";
    }

    /**
     * 编码集体
     */
    interface Chartset {
        /**
         * UTF8
         */
        String UTF8 = "UTF8";
        /**
         * UTF-8
         */
        String UTF_8 = "UTF-8";
        /**
         * GBK
         */
        String GBK = "GBK";
        /**
         * GB2312
         */
        String GB2312 = "GB2312";
    }

    /**
     * 语言
     */
    interface Lang {
        /**
         * 中文
         */
        String PHP_ZH_CN = "zh_cn";
        String JAVA_CN = "zh";
        /**
         * 英文
         */
        String PHP_EN_GB = "en-gb";
        String JAVA_EN = "en";
    }

    /**
     * 请求方法
     */
    interface RequestMethod {

        /**
         * post方法
         */
        String POST = "POST";

        /**
         * post-文件上传方法
         */
        String UPLOAD_POST = "UPLOAD_POST";

        /**
         * get方法
         */
        String GET = "GET";
    }

    /**
     * schema
     */
    interface HttpProtocol {
        /**
         * https
         */
        String HTTPS = "https";
        /**
         * http
         */
        String HTTP = "http";
        /**
         * wss
         */
        String WSS = "wss";

        /**
         * 协议符号
         */
        String SYMBOL = "://";
    }

    /**
     * 本机
     */
    String LOCALHOST = "localhost";

    /**
     * 本机
     */
    String LOCAL_IP = "127.0.0.1";

    /**
     * 时间单位
     */
    interface TimeUnit {
        /**
         * 日期
         */
        long DAY = 1000L * 60 * 60 * 24;

        /**
         * 小时
         */
        long HOUR = 1000L * 60 * 60;

        /**
         * 分
         */
        long MINUTE = 1000L * 60;

        /**
         *
         */
        long MINUTE_10 = 1000L * 60 * 10;

        long HALF_HOUR = 1000L * 60 * 30;

        long SECOND = 1000L;
    }

    /**
     * 端类型
     */
    interface StoreType {
        /**
         * app
         */
        int STORE_TYPE_APP = 2;
        /**
         * h5
         */
        int STORE_TYPE_H5 = 2;
        /**
         * 微信小程序
         */
        int STORE_TYPE_WX_MP = 1;
        /**
         * pc店铺
         */
        int STORE_TYPE_PC_MCH = 7;
        /**
         * pc商城
         */
        int STORE_TYPE_PC_MALL = 6;
        /**
         * pc管理后台
         */
        int STORE_TYPE_PC_ADMIN = 8;
        /**
         * 支付宝小程序
         */
        int STORE_TYPE_ALI_MP = 3;
        /**
         * 字节跳动小程序
         */
        int STORE_TYPE_BYTEDANCE_MP = 4;
        /**
         * 百度小程序
         */
        int STORE_TYPE_BAIDU_MP = 5;
    }


}
