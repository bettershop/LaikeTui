package com.laiketui.api.modules.backend.systems;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.AddressVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 地址管理接口
 *
 * @author Trick
 * @date 2021/1/15 9:32
 */
public interface AddressService {


    /**
     * 获取地址列表
     *
     * @param vo   -
     * @param id   - 可选
     * @param name - 联系人
     * @param type - 类型（1发货地址 2售后地址） 默认2
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 9:33
     */
    Map<String, Object> getAddressInfo(MainVo vo, Integer id, String name, Integer type) throws LaiKeApiException;


    /**
     * 添加/修改地址信息
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 10:49
     */
    boolean addAddressInfo(AddressVo vo) throws LaiKeApiException;

    /**
     * 设置默认
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/14 9:28
     */
    void setDefaultAddress(MainVo vo, int id) throws LaiKeApiException;

    /**
     * 删除地址
     *
     * @param vo   -
     * @param id   -
     * @param type -类型（1发货地址 2售后地址） 默认2
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 11:28
     */
    boolean delAddress(MainVo vo, int id, int type) throws LaiKeApiException;
}
