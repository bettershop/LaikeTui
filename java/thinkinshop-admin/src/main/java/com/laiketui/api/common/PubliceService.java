package com.laiketui.api.common;

import com.laiketui.domain.Page;
import com.laiketui.domain.dictionary.DictionaryListModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.message.MessageLoggingModal;
import com.laiketui.domain.user.User;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.Withdrawals1Vo;
import com.laiketui.domain.vo.files.UploadFileVo;
import com.laiketui.root.exception.LaiKeApiException;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 公共服务
 *
 * @author Trick
 * @date 2020/10/23 10:21
 */
public interface PubliceService {


    /**
     * 获取图片路径- 【已遗弃,后期将删除该方法】
     *
     * @param imgName -
     * @param storeId -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/23 10:22
     */
    @Deprecated
    String getImgPath(String imgName, String storeId) throws LaiKeApiException;

    /**
     * 获取图片路径 - 根据指定的商城id获取oss图片
     *
     * @param imgName -
     * @param storeId -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/28 11:05
     */
    String getImgPath(String imgName, Integer storeId) throws LaiKeApiException;

    /**
     * 上传文件
     *
     * @param vo -
     * @return List
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 11:50
     */
    List<String> uploadFiles(UploadFileVo vo) throws LaiKeApiException;

    /**
     * 上传图片
     *
     * @param multipartFiles - 图片集
     * @param uploadType     - 上传方式
     * @param storeType      - 来源
     * @param storeId        - 商城id
     * @param mchId          - 店铺id
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/24 9:58
     */
    List<String> uploadImage(List<MultipartFile> multipartFiles, String uploadType, int storeType, int storeId, Integer mchId) throws LaiKeApiException;

    /**
     * 上传图片
     *
     * @param multipartFiles -
     * @param uploadType     - 上传方式
     * @param storeType      - 来源
     * @param storeId        -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/4 10:15
     */
    List<String> uploadImage(List<MultipartFile> multipartFiles, String uploadType, int storeType, int storeId) throws LaiKeApiException;


    /**
     * 图片外链上传
     *
     * @param url        - 下载源
     * @param uploadType - 上传方式
     * @param storeType  - 来源
     * @param storeId    -
     * @return String - 返回图片名称
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/22 16:12
     */
    String uploadImage(String url, String uploadType, int storeType, int storeId) throws LaiKeApiException;


    /**
     * 添加商品轮播图
     *
     * @param imageNameList -
     * @param pid           - 商品id
     * @param isUpdate      - 是否更新轮播图 ，如果不跟新则删除之前的轮播图
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/22 17:16
     */
    void addRotationImage(List<String> imageNameList, int pid, boolean isUpdate) throws LaiKeApiException;

    /**
     * 验证token
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/23 10:22
     */
    Map<String, Object> verifyToken(MainVo vo) throws LaiKeApiException;


    /**
     * token是否过期
     *
     * @param token -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/15 17:03
     */
    boolean verifyToken(String token) throws LaiKeApiException;


    /**
     * 获取折扣
     *
     * @param storeId -
     * @param user    -
     * @return BigDecimal - 几折
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/23 10:26
     */
    BigDecimal getUserGradeRate(int storeId, User user) throws LaiKeApiException;

    /**
     * 获取折扣
     *
     * @param storeId   -
     * @param user      -
     * @param isLowRate - 是否获取最低折扣
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/26 11:20
     */
    BigDecimal getUserGradeRate(int storeId, User user, boolean isLowRate) throws LaiKeApiException;


    /**
     * 获取用户/游客信息
     *
     * @param accessId - 令牌
     * @return User
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/23 14:53
     */
    User getUserInfo(String accessId) throws LaiKeApiException;


    /**
     * 获取附近店铺
     *
     * @param tencentKey -
     * @param storeId    -
     * @param latitude   - 维度
     * @param longitude  - 经度
     * @param pageModel  -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/26 15:11
     */
    List<Map<String, Object>> getNearbyShops(String tencentKey, int storeId, String latitude, String longitude, Page pageModel) throws LaiKeApiException;


    /**
     * 区分购物车结算和立即购买---列出选购商品
     * <p>
     * php : tools.products_list
     *
     * @param goodsInfoList -[{"pid":"979"},{"cid":"5648"},{"num":1},{"sec_id":"6"}--秒杀id,{}] 商品信息
     * @param cartIds       - 购物车
     * @param buyType       - 购买类型 默认0 再次购买1
     * @param orderHead     - 订单头部
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/27 14:42
     */
    List<Map<String, Object>> productsList(List<Map<String, Object>> goodsInfoList, String cartIds, int buyType, String orderHead) throws LaiKeApiException;


    /**
     * 获取店铺商品统计信息
     * 返回:
     * [key:    quantity_on_sale、quantity_sold、collection_num]
     * [value:  在售商品数量、已售数量、收藏数量]
     * 【php: mch.commodity_information】
     *
     * @param storeId -
     * @param mchId   -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/29 10:40
     */
    Map<String, Object> commodityInformation(int storeId, int mchId) throws LaiKeApiException;


    /**
     * 获取商品评论信息
     *
     * @param parmaMap -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/29 17:37
     */
    List<Map<String, Object>> getGoodsCommentList(Map<String, Object> parmaMap) throws LaiKeApiException;


    /**
     * 校验密码
     *
     * @param header  -
     * @param phone   -
     * @param keyCode -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/3 9:17
     */
    boolean validatePhoneCode(String header, String phone, String keyCode) throws LaiKeApiException;


    /**
     * 会员签到
     * [签到插件 php]sign.test
     *
     * @param user -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/4 10:07
     */
    Map<String, Integer> sign(User user) throws LaiKeApiException;


    /**
     * 判断插件是否开启
     *
     * @param pluginCode - 插件代码
     * @param pluginMap  - 返回结果引用
     * @param storeId    -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/4 10:00
     */
    boolean frontPlugin(int storeId, String pluginCode, Map<String, Integer> pluginMap) throws LaiKeApiException;


    /**
     * 获取商品支持的活动类型
     *
     * @param storeId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/1 15:46
     */
    List<Map<String, Object>> getGoodsActive(int storeId) throws LaiKeApiException;

    /**
     * 获取商品支持的活动类型
     *
     * @param storeId -
     * @param active  -
     * @return List
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/26 16:28
     */
    List<Map<String, Object>> getGoodsActive(int storeId, Integer active) throws LaiKeApiException;


    /**
     * 整理用户购物车数据
     * 场景:
     * 防止未登录前加入商品到购物车登陆后不见;
     * 处理:
     * 获取未登录购物车信息合并到登陆后用户购物车
     * [php loginAction.updateUser]
     *
     * @param vo     -
     * @param userid -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/6 15:03
     */
    void arrangeUserCart(MainVo vo, String userid) throws LaiKeApiException;


    /**
     * 获取协议
     *
     * @param storeId -
     * @param type    - 协议类型
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/10 16:10
     */
    Map<String, Object> getAgreement(int storeId, int type) throws LaiKeApiException;

    /**
     * 订单确认页面获取商品信息
     *
     * @param products    -
     * @param storeId     -
     * @param productType -
     * @return List
     * @throws LaiKeApiException -
     * @author wangxian
     * @date 2020/11/11 10:17
     */
    Map<String, Object> settlementProductsInfo(List<Map<String, Object>> products, int storeId, String productType) throws LaiKeApiException;

    /**
     * 剔除已自选商品id集
     *
     * @param storeId -
     * @param shopId  -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/11 9:30
     */
    List<Integer> getCheckedZiXuanGoodsList(int storeId, int shopId) throws LaiKeApiException;

    /**
     * 计算会员折扣
     *
     * @param params
     * @param storeId
     * @param userId
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> getMemberPrice(List<Map<String, Object>> params, String userId, int storeId) throws LaiKeApiException;


    /**
     * 发送短信
     * 【php Tool.generate_code】
     *
     * @param storeId     -
     * @param phone       -
     * @param type        - 短信类型
     * @param smsType     - 短信模式  0:验证码 1:短信通知
     * @param smsParmaMap - 短信参数 ,只有通知类型的短信才传
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/3 16:20
     */
    boolean sendSms(int storeId, String phone, int type, int smsType, Map<String, String> smsParmaMap) throws LaiKeApiException;


    /**
     * 验证短信模板
     *
     * @param storeId     -
     * @param phone       -
     * @param code        -
     * @param template    - 模板名称
     * @param smsParmaMap - 模板参数
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/18 15:51
     */
    boolean sendValidatorSmsTemplate(int storeId, String phone, String code, String template, Map<String, String> smsParmaMap) throws LaiKeApiException;


    /**
     * 获取微信token
     *
     * @param storeId -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/17 9:13
     */
    String getWeiXinToken(int storeId) throws LaiKeApiException;


    /**
     * 获取小程序二维码
     *
     * @param storeId   -
     * @param storeType -
     * @param path      -
     * @param width     -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/25 11:23
     */
    String getWeiXinAppQrcode(int storeId, int storeType, String path, int width) throws LaiKeApiException;


    /**
     * 获取当前商户logo
     *
     * @param storeId   -
     * @param storeType -
     * @param qrCodeUrl -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/25 11:36
     */
    String getStoreQrcode(int storeId, int storeType, String qrCodeUrl) throws LaiKeApiException;


    /**
     * 添加操作记录
     *
     * @param storeId -
     * @param userId  -
     * @param text    -
     * @param type    -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 17:40
     */
    boolean addAdminRecord(int storeId, String userId, String text, int type) throws LaiKeApiException;

    /**
     * @param val
     * @return
     */
    List<DictionaryListModel> getDictionaryList(String val);

    /**
     * 省市级联
     *
     * @param level   - 省市县等级
     * @param groupId - 上级id
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/5 11:45
     */
    List<Map<String, Object>> getJoinCityCounty(int level, int groupId) throws LaiKeApiException;


    /**
     * 修改后台消息表信息
     *
     * @param storeId              -
     * @param mchId                -
     * @param type                 -
     * @param parma                -
     * @param messageLoggingUpdate - 需要修改的值
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/4 10:08
     */
    void messageUpdate(int storeId, int mchId, int type, String parma, MessageLoggingModal messageLoggingUpdate) throws LaiKeApiException;


    /**
     * 申请提现
     *
     * @param vo   -
     * @param user - 提现人
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/3 12:09
     */
    boolean withdrawals(Withdrawals1Vo vo, User user) throws LaiKeApiException;

    /**
     * 获取后台权限按钮
     * 【php dbAction.Jurisdiction】
     *
     * @param storeId -
     * @param admin   - 当前登录人
     * @param url     - 需要判断权限的url
     * @return boolean
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/3 15:28
     */
    boolean jurisdiction(int storeId, AdminModel admin, String url) throws LaiKeApiException;
}
