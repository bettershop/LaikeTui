package com.laiketui.root.utils.tool;

import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.user.User;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存辅助类
 *
 * @author Trick
 * @date 2020/9/30 15:38
 */
public class RedisDataTool {

    private static Logger logger = LoggerFactory.getLogger(RedisDataTool.class);

    /**
     * 根据token获取用户-app
     *
     * @param accessId -
     * @return User
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 9:52
     */
    public static User getRedisUserCache(String accessId, RedisUtil redisUtil) throws LaiKeApiException {
        return getRedisUserCache(accessId, redisUtil, GloabConst.RedisHeaderKey.LOGIN_ACCESS_TOKEN);
    }

    public static User getRedisUserCache(String accessId, RedisUtil redisUtil, String tokenKey) throws LaiKeApiException {
        if (StringUtils.isEmpty(tokenKey)) {
            tokenKey = GloabConst.RedisHeaderKey.LOGIN_ACCESS_TOKEN;
        }
        //获取当前登录用户数据
        Object cacheValue = redisUtil.get(tokenKey + accessId);
        User userCache = null;
        if (cacheValue != null) {
            try {
                userCache = (User) cacheValue;
            } catch (Exception e) {
                e.printStackTrace();
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙,请稍后再试", "getRedisUserCache");
            }
        }

        return userCache;
    }


    /**
     * 获取用户信息
     *
     * @param accessId  -
     * @param redisUtil -
     * @param isLogin   - 是否需要登录
     * @return User
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/24 15:41
     */
    public static User getRedisUserCache(String accessId, RedisUtil redisUtil, boolean isLogin) throws LaiKeApiException {
        User user;
        if (isLogin) {
            //获取当前登录用户数据
            user = getRedisUserCache(accessId, redisUtil);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.LOGIN_INVALID, "请重新登录");
            }
        } else {
            user = getRedisUserCache(accessId, redisUtil);
        }
        return user;
    }

    public static User getRedisUserCache(String accessId, RedisUtil redisUtil, String tokenKey, boolean isLogin) throws LaiKeApiException {
        User user;
        if (isLogin) {
            //获取当前登录用户数据
            user = getRedisUserCache(accessId, redisUtil, tokenKey);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.LOGIN_INVALID, "请重新登录");
            }
        } else {
            user = getRedisUserCache(accessId, redisUtil, tokenKey);
        }
        return user;
    }


    /**
     * 获取后台用户信息
     *
     * @param accessId  -
     * @param redisUtil -
     * @return AdminModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/31 15:30
     */
    public static AdminModel getRedisAdminUserCache(String accessId, RedisUtil redisUtil) throws LaiKeApiException {
        //获取当前登录用户数据
        Object cacheValue = redisUtil.get(GloabConst.RedisHeaderKey.LOGIN_ACCESS_MANAGE_TOKEN + accessId);
        AdminModel userCache = null;
        if (cacheValue != null) {
            try {
                userCache = (AdminModel) cacheValue;
            } catch (Exception e) {
                e.printStackTrace();
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙,请稍后再试", "getRedisUserCache");
            }
        } else {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.LOGIN_INVALID, "请重新登录");
        }

        return userCache;
    }

    /**
     * 是否登录-任意模块
     *
     * @param accessId  -
     * @param redisUtil -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 11:07
     */
    public static void isLogin(String accessId, RedisUtil redisUtil) throws LaiKeApiException {
        Object cacheValue = redisUtil.get(GloabConst.RedisHeaderKey.LOGIN_ACCESS_MANAGE_TOKEN + accessId);
        if (cacheValue == null) {
            cacheValue = redisUtil.get(GloabConst.RedisHeaderKey.LOGIN_ACCESS_TOKEN_MCH + accessId);
            if (cacheValue == null) {
                cacheValue = redisUtil.get(GloabConst.RedisHeaderKey.LOGIN_ACCESS_TOKEN + accessId);
                if (cacheValue == null) {
                    cacheValue = redisUtil.get(GloabConst.RedisHeaderKey.LOGIN_ACCESS_PC_SHOP_TOKEN + accessId);
                    if (cacheValue == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.LOGIN_INVALID, "请重新登录");
                    }
                }
            }
        }
    }
    /**
    * redis 更新缓存【管理员】
    *
    * @param user -
    * @throws LaiKeApiException-
    * @author Trick
    * @date 2021/8/17 16:39
    */
    public static void refreshRedisAdminCache(String accessId, AdminModel user, String tokenKey, RedisUtil redisUtil) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(tokenKey)) {
                tokenKey = GloabConst.RedisHeaderKey.LOGIN_ACCESS_MANAGE_TOKEN;
            }
            Object cacheValue = redisUtil.get(tokenKey + accessId);
            if (cacheValue != null) {
                //时间不变
                long outTime = redisUtil.getExpire(tokenKey + accessId);
                boolean flag = redisUtil.set(tokenKey + accessId, user, outTime);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙,请稍后再试", "refreshRedisUserCache");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "请重新登录", "refreshRedisUserCache");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙,请稍后再试", "refreshRedisUserCache");
        }
    }
    /**
     * redis 更新用户信息
     *
     * @param accessId -
     * @param user     -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 9:52
     */
    public static void refreshRedisUserCache(String accessId, User user, String tokenKey, RedisUtil redisUtil) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(tokenKey)) {
                tokenKey = GloabConst.RedisHeaderKey.LOGIN_ACCESS_TOKEN;
            }
            Object cacheValue = redisUtil.get(tokenKey + accessId);
            if (cacheValue != null) {
                //时间不变
                long outTime = redisUtil.getExpire(tokenKey + accessId);
                boolean flag = redisUtil.set(tokenKey + accessId, user, outTime);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙,请稍后再试", "refreshRedisUserCache");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "请重新登录", "refreshRedisUserCache");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙,请稍后再试", "refreshRedisUserCache");
        }
    }

    public static void refreshRedisUserCache(String accessId, User user, RedisUtil redisUtil) throws LaiKeApiException {
        try {
            refreshRedisUserCache(accessId, user, GloabConst.RedisHeaderKey.LOGIN_ACCESS_TOKEN, redisUtil);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙,请稍后再试", "refreshRedisUserCache");
        }
    }

    /**
     * 验证短信
     *
     * @param phone     - 手机号
     * @param smsType   - 短信类别
     * @param pcode     - 验证码
     * @param redisUtil -
     * @return AdminModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/31 15:30
     */
    public static boolean verificationPcode(int smsType, String phone, String pcode, RedisUtil redisUtil) throws LaiKeApiException {
        try {
            Object cacheValue = redisUtil.get(RedisDataTool.getSmsHeade(smsType) + phone);
            if (cacheValue != null) {
                String rCode = cacheValue.toString();
                if (rCode.equals(pcode)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.READ_PHONE_CODE_NOT_DATA, "请重新获取验证码");
            }
        } catch (LaiKeApiException l) {
            throw l;
        }
    }

    /**
     * 获取短信缓存头部
     *
     * @param smsType -
     * @return User
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 9:52
     */
    public static String getSmsHeade(int smsType) throws LaiKeApiException {
        try {
            String header = "";
            switch (smsType) {
                case GloabConst.VcodeCategory.LOGIN_CODE:
                    header = GloabConst.RedisHeaderKey.LOGIN_HEADER;
                    break;
                case GloabConst.VcodeCategory.REGISTER_CODE:
                    header = GloabConst.RedisHeaderKey.REGISTER_HEADER;
                    break;
                case GloabConst.VcodeCategory.UPDATE_PHOE_CODE:
                    //修改手机号
                    header = GloabConst.RedisHeaderKey.UPDATE_PHOE_CODE;
                    break;
                case GloabConst.VcodeCategory.UPDATE_PWD_CODE:
                    header = GloabConst.RedisHeaderKey.UPDATE_PASSWORDE;
                    break;
                case GloabConst.VcodeCategory.UPDATE_PWD_PAY_CODE:
                    header = GloabConst.RedisHeaderKey.UPDATE_PWD_PAY_CODE;
                    break;
                case GloabConst.VcodeCategory.DRAWING_CODE:
                    header = GloabConst.RedisHeaderKey.DRAWING_CODE;
                    break;
                case GloabConst.VcodeCategory.CURRENCY_CODE:
                case GloabConst.VcodeCategory.PAY_REFUND_ORDER:
                    header = GloabConst.RedisHeaderKey.CURRENCY_CODE;
                    break;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信类型参数不存在!", "sendSms");
            }
            return header;
        } catch (LaiKeApiException l) {
            throw l;
        }
    }

}
