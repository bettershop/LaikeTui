package com.laiketui.api.modules.backend.order;

import com.laiketui.domain.vo.main.RefundVo;
import com.laiketui.domain.vo.order.RefundQueryVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 退货管理
 *
 * @author Trick
 * @date 2021/1/5 15:55
 */
public interface RefundService {


    /**
     * 获取退货列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/5 16:17
     */
    Map<String, Object> getRefundList(RefundQueryVo vo, HttpServletResponse response) throws LaiKeApiException;

    /**
     * 售后审核 通过/拒绝
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/4 14:19
     */
    boolean examine(RefundVo vo) throws LaiKeApiException;
}
