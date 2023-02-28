package com.laiketui.api.modules.backend.terminal;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.mall.SaveTerminalAppVo;
import com.laiketui.domain.vo.admin.mall.SaveTerminalWeiXinVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 终端管理接口
 *
 * @author Trick
 * @date 2021/7/23 9:29
 */
public interface TerminalService {

    /**
     * 终端管理界面
     *
     * @param vo   -
     * @param type -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/23 9:30
     */
    Map<String, Object> index(MainVo vo, int type) throws LaiKeApiException;

    /**
    * 保存app配置
    *
    * @param vo -
    * @throws LaiKeApiException-
    * @author Trick
    * @date 2021/7/23 18:13
    */
    void saveApp(SaveTerminalAppVo vo) throws LaiKeApiException;

    /**
    * 保存小程序配置
    *
    * @param vo -
    * @throws LaiKeApiException-
    * @author Trick
    * @date 2021/7/26 15:17
    */
    void saveWeiXin(SaveTerminalWeiXinVo vo) throws LaiKeApiException;
}
