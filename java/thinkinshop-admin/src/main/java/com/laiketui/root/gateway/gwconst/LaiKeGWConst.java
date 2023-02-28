package com.laiketui.root.gateway.gwconst;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/21 16:15
 * @version: 1.0
 * @modified By:
 */
public interface LaiKeGWConst {

    /**
     * token头
     */
    String HEARDER_TOKEN = "authorizations";
    /**
     * 版本默认
     */
    String API_VERSION = "1.0.0";

    /**
     * 接口默认方法名
     */
    String API_DEFAULT_METHOD = "index";

    /**
     * 发生错误
     */
    String GW_ERROR = "20001";

    /**
     * 失败
     */
    String GW_FAIL = "20002";

    /**
     * 异常请求
     */
    String GW_BAD_REQ = "5003";

    /**
     * api节点不可用
     */
    String API_OFFLINE = "5001";

    /**
     * api不存在
     */
    String API_NOTEXISTS = "5002";

    /**
     * 成功(有返回数据)
     */
    String RESCODE_SUCCESS_DATA = "1001";

    /**
     * 查询结果为空
     */
    String RESCODE_NOEXIST = "1004";

    /**
     * 异常带数据
     */
    String RESCODE_EXCEPTION_DATA = "1008";

    /**
     * 未登陆状态
     */
    String RESCODE_NOLOGIN = "1003";

    /**
     * 未登陆状态 无操作权限
     */
    String RESCODE_NOAUTH = "1005";

    /**
     * 登录过期
     */
    String RESCODE_LOGINEXPIRE = "1009";

    /**
     * token（暂时没有刷新自动token机制，通过重新登录获取）刷新TOKEN(有返回数据)
     */
    String RESCODE_REFTOKEN_MSG = "1006";

    /**
     * 刷新TOKEN
     */
    String RESCODE_REFTOKEN = "1007";

    /**
     * Token过期
     */
    String JWT_ERRCODE_EXPIRE = "4001";

    /**
     * 验证不通过
     */
    String JWT_ERRCODE_FAIL = "4002";

    /**
     * 导出请求标记 1 表示导出文件请求
     */
    String EXP_FLAG = "1";

    /**
     * 成功
     */
    String GW_SUCCESS = "200";


}
