package com.laiketui.common.mapper;

import com.laiketui.domain.mch.AdminModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 管理员表
 *
 * @author Trick
 * @date 2020/11/11 9:15
 */
public interface AdminModelMapper extends BaseMapper<AdminModel> {

    /**
     * 获取商城自营店铺
     *
     * @param storeId -
     * @return CustomerModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/11 9:13
     */
    @Select("select * from lkt_admin where store_id = #{storeId} and type = 1")
    AdminModel getAdminCustomer(int storeId) throws LaiKeApiException;

    @Update("update lkt_admin set recycle = 1 where store_id = #{storeId}")
    int delAdminByStoreId(int storeId);

    /**
     * 登录失败
     */
    @Update("update lkt_admin set login_num = login_num+1 where id = #{id}")
    int adminLoginFail(int id);

    /**
     * 获取绑定角色的管理员
     *
     * @param roleId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 16:07
     */
    @Select("select b.name from lkt_admin as a left join lkt_customer as b on a.store_id = b.id where a.type = 1 and a.role = #{roleId} " +
            "and a.recycle = 0 and b.recycle = 0")
    List<String> getAdminBindNameList(int roleId) throws LaiKeApiException;


    /**
     * 获取绑定/非绑定商城
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 16:45
     */
    @Select("<script> select a.id,a.tel,b.name from lkt_admin as a left join lkt_customer as b on a.store_id = b.id " +
            "where a.recycle = 0 and b.recycle = 0" +
            "<if test='type != null '> and a.type = #{type} </if>" +
            "<if test='role != null '> and a.role = #{role} </if>" +
            "<if test='notRole != null '> and a.role != #{notRole} </if>" +
            "<if test='name != null '> and b.name like concat('%',#{name},'%') </if>" +
            "</script>")
    List<Map<String, Object>> getBindListInfo(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取管理员信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 15:00
     */
    List<Map<String, Object>> selectAdminListInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取管理员信息-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 15:00
     */
    int countAdminListInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取商城权限
     */
    @Select("select role from lkt_admin where store_id = #{storeId} and type = 0 and recycle = 0 limit 1")
    Integer getStoreRole(int storeId);


    /**
     * 验证商户是否绑定
     */
    @Select("select count(1) from lkt_admin a,lkt_role b where a.id=#{adminId} and a.recycle=0 and a.role=b.id")
    int verificationBind(int adminId);

    //获取系统管理员
    @Select("select * from lkt_admin where admin_type=0 and type=0 and store_id=0 and status=2 limit 1")
    AdminModel getSystemAdmin();

}