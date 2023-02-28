package com.laiketui.common.mapper;

import com.laiketui.domain.mch.BankCardModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 银行卡表
 *
 * @author Trick
 */
public interface BankCardModelMapper extends BaseMapper<BankCardModel> {

    @Select("select id,Bank_name,Bank_card_number from lkt_bank_card where store_id = #{stroeId} and mch_id = #{mchId} and recycle = 0 order by is_default desc")
    List<Map<String, Object>> selectBankcardByMch(int stroeId, int mchId);

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Update("UPDATE lkt_bank_card SET is_default=0 WHERE user_id=#{userId} and store_id=#{storeId} and recycle=0")
    int clearDefault(int storeId, String userId);
}