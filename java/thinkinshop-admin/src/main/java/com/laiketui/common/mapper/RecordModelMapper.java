package com.laiketui.common.mapper;

import com.laiketui.domain.log.RecordModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 操作记录 sql
 *
 * @author Trick
 * @date 2020/11/2 16:54
 */
public interface RecordModelMapper extends BaseMapper<RecordModel> {


    /**
     * 获取我的钱包
     *
     * @param recordModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/2 16:11
     */
    @Select("<script>" +
            " select money,add_date,type from lkt_record where store_id = #{store_id} and user_id = #{user_id} and is_mch = 0 " +
            " and (type =1 or type =2 or type =3 or type =4 or type =5 or type =11 or type =12 or type =13 or type =14 or type =19 " +
            " or type =20 or type =21 or type =22 or type =23 or type =24 or type = 26 or type = 27 or type =30) " +
            " order by add_date desc " +
            "<if test=\"page!=null\">" +
            " LIMIT #{page.pageNo},#{page.pageSize} " +
            "</if>" +
            "</script>")
    List<Map<String, Object>> getUserWallet(RecordModel recordModel) throws LaiKeApiException;

    /**
     * 获取用户操作记录
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 16:03
     */
    List<Map<String, Object>> getUserWalletRecordInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 统计用户操作记录
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 16:03
     */
    int countUserWalletRecordInfo(Map<String, Object> map) throws LaiKeApiException;
}