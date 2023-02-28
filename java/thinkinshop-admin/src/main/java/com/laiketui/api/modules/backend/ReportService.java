package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.admin.MchReportVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 报表管理
 *
 * @author Trick
 * @date 2021/7/5 10:36
 */
public interface ReportService {
    /**
     * 店铺营业额报表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/5 10:37
     */
    Map<String, Object> mchTurnoverReport(MchReportVo vo) throws LaiKeApiException;

    /**
     * 商户新增用户报表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/5 14:33
     */
    Map<String, Object> mchTurnoverNewUserReport(MchReportVo vo) throws LaiKeApiException;

    /**
     * 商户订单报表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/5 15:05
     */
    Map<String, Object> mchTurnoverOrderReport(MchReportVo vo) throws LaiKeApiException;
}
