package com.laiketui.common.mapper;

import com.laiketui.domain.user.UserFirstModal;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface UserFirstModalMapper extends BaseMapper<UserFirstModal> {
    /**
     * @param sNo
     * @param storeId
     * @param sNo
     * @param storeId
     * @param gradeLeave
     * @return int
     */
    @Update("update lkt_user_first set is_use = 1,sNo =  #{sNo} where store_id = #{storeId} and user_id = #{userId} and level = #{gradeLeave})")
    int updateUserGiveRecord(String sNo, int storeId, String userId, int gradeLeave);


    /**
     * 获取等级会员首次开通表信息 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/7 15:08
     */
    List<Map<String, Object>> getUserFirstInfoDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 取消订单
     * @param sNo
     * @param storeId
     * @return
     */
    @Update("update lkt_user_first set is_use = 0 , sNo = '' where sNo = #{sNo} and store_id = #{storeId}")
    int cancleUserFirstRecord(String sNo,int storeId);

}