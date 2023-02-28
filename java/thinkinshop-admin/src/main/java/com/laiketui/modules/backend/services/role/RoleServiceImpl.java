package com.laiketui.modules.backend.services.role;

import com.laiketui.api.common.admin.PublicRoleService;
import com.laiketui.api.modules.backend.role.RoleService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.mch.CustomerModel;
import com.laiketui.domain.mch.RoleModel;
import com.laiketui.domain.role.CoreMenuModel;
import com.laiketui.domain.role.GuideMenuModel;
import com.laiketui.domain.role.RoleMenuModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.Tool.ExcelParamVo;
import com.laiketui.domain.vo.admin.role.AddAdminVo;
import com.laiketui.domain.vo.admin.role.LoggerAdminVo;
import com.laiketui.domain.vo.role.AddRoleVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataCheckTool;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.EasyPoiExcelUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 权限管理
 *
 * @author Trick
 * @date 2021/1/13 12:15
 */
@Service
public class RoleServiceImpl implements RoleService {
    private final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> getAdminInfo(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel admin = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (admin != null) {
                //判断是否是超级管理员(查看该商城所有管理员)
                boolean isSuperAdmin = admin.getType() == 0 || admin.getType() == 1;

                CustomerModel customerModel = customerModelMapper.selectByPrimaryKey(vo.getStoreId());
                if (customerModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
                }

                Map<String, Object> parmaMap = new HashMap<>(16);
                if (id != null && id > 0) {
                    parmaMap.put("id", id);
                }
                parmaMap.put("type", AdminModel.TYPE_STORE_ADMIN);
                parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
                parmaMap.put("add_date_sort", "desc");
                parmaMap.put("pageStart", vo.getPageNo());
                parmaMap.put("pageEnd", vo.getPageSize());
                if (isSuperAdmin) {
                    parmaMap.put("store_id", vo.getStoreId());
                } else {
                    //不是超级管理员则只能查看自己的
                    parmaMap.put("id", admin.getId());
                }
                int total = adminModelMapper.countAdminListInfo(parmaMap);
                List<Map<String, Object>> adminListInfo = adminModelMapper.selectAdminListInfo(parmaMap);

                resultMap.put("total", total);
                resultMap.put("list", adminListInfo);
                resultMap.put("customer_number", customerModel.getCustomer_number());
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取管理员列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAdminInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getRoleListInfo(MainVo vo, int status, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id_pt", vo.getStoreId());
            parmaMap.put("status", status);
            parmaMap.put("id", id);
            parmaMap.put("add_date_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = roleModelMapper.countRoleInfo(parmaMap);
            List<Map<String, Object>> roleModelList = roleModelMapper.selectRoleInfo(parmaMap);
            if (RoleModel.STATUS_CLIENT.equals(status)) {
                for (Map<String, Object> map : roleModelList) {
                    Map<String, Object> parmaMap1 = new HashMap<>(16);
                    parmaMap1.put("role", map.get("id"));
                    parmaMap1.put("type", AdminModel.TYPE_CLIENT);
                    //获取绑定的商户
                    List<Map<String, Object>> bindAdminList = adminModelMapper.getBindListInfo(parmaMap1);
                    map.put("bindAdminList", bindAdminList);
                }
            }

            resultMap.put("total", total);
            resultMap.put("list", roleModelList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取角色列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRoleListInfo");
        }
        return resultMap;
    }

    @Override
    public void addAdminInfo(AddAdminVo vo) throws LaiKeApiException {
        try {
            AdminModel admin = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            int count;
            //是否为修改
            boolean isUpdate = false;
            AdminModel adminModelOld = null;
            if (vo.getId() != null && vo.getId() > 0) {
                isUpdate = true;
                adminModelOld = new AdminModel();
                adminModelOld.setId(vo.getId());
                adminModelOld.setStore_id(vo.getStoreId());
                adminModelOld.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                adminModelOld = adminModelMapper.selectOne(adminModelOld);
                if (adminModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "管理员不存在", "addAdminInfo");
                }
            }
            AdminModel adminModelSave = new AdminModel();
            adminModelSave.setPassword(vo.getAdminPWD());
            adminModelSave.setRole(vo.getRoleId() + "");
            adminModelSave.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            //校验数据
            if (!isUpdate) {
                //添加
                AdminModel adminModel = new AdminModel();
                adminModel.setStore_id(vo.getStoreId());
                adminModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                adminModel.setName(vo.getAdminName());
                count = adminModelMapper.selectCount(adminModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "账号已存在", "addAdminInfo");
                }
                if (admin != null) {
                    adminModelSave.setSid(admin.getId());
                }
                adminModelSave.setType(AdminModel.TYPE_STORE_ADMIN);
                adminModelSave.setStore_id(vo.getStoreId());
                adminModelSave.setName(vo.getAdminName());
                adminModelSave.setAdd_date(new Date());
            } else {
                //修改
                adminModelSave.setId(adminModelOld.getId());
            }
            adminModelSave = DataCheckTool.checkAdminDataFormate(adminModelSave, isUpdate);
            if (isUpdate) {
                count = adminModelMapper.updateByPrimaryKeySelective(adminModelSave);
            } else {
                count = adminModelMapper.insertSelective(adminModelSave);
            }

            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/修改管理员信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addAdminInfo");
        }
    }

    @Override
    public void delAdminInfo(MainVo vo, int id) throws LaiKeApiException {
        try {
            AdminModel adminModel = new AdminModel();
            adminModel.setStore_id(vo.getStoreId());
            adminModel.setId(id);
            if (adminModelMapper.delete(adminModel) < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "删除失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除管理员信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delAdminInfo");
        }
    }

    @Override
    public void stopAdmin(MainVo vo, int id) throws LaiKeApiException {
        try {
            AdminModel adminModel = new AdminModel();
            adminModel.setId(id);
            adminModel.setStore_id(vo.getStoreId());
            adminModel = adminModelMapper.selectOne(adminModel);
            if (adminModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "管理员不存在");
            }
            AdminModel adminModelUpdate = new AdminModel();
            adminModelUpdate.setId(id);
            int status = adminModel.getStatus();
            if (status == 1) {
                status = 2;
            } else {
                status = 1;
            }
            adminModelUpdate.setStatus(status);

            if (adminModelMapper.updateByPrimaryKeySelective(adminModelUpdate) < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("禁用/启用管理员信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "stopAdmin");
        }
    }

    @Override
    public Map<String, Object> getAdminLoggerInfo(LoggerAdminVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("add_date_sort", DataUtils.Sort.DESC.toString());
            if (!StringUtils.isEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
                if (!StringUtils.isEmpty(vo.getEndDate())) {
                    parmaMap.put("endDate", vo.getEndDate());
                }
            }
            if (!StringUtils.isEmpty(vo.getAdminName())) {
                parmaMap.put("adminName_like", vo.getAdminName());
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            List<Map<String, Object>> dataList = adminRecordModelMapper.selectAdminLoggerInfo(parmaMap);
            if (vo.getExportType() == 1) {
                exportData(dataList, response);
                return null;
            }
            int total = adminRecordModelMapper.countAdminLoggerInfo(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取管理眼日志列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAdminLoggerInfo");
        }
        return resultMap;
    }

    /**
     * 导出管理员日志信息
     *
     * @param list     -
     * @param response -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/29 10:22
     */
    private void exportData(List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"管理员账号", "事件", "操作时间"};
            //对应字段
            String[] kayList = new String[]{"admin_name", "event", "add_date"};
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("管理员日志");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(true);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出管理员日志信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportData");
        }
    }

    @Override
    public boolean delAdminLogger(MainVo vo, String ids) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(ids)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "id不能为空");
            }
            List<String> idList = Arrays.asList(ids.split(","));

            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("idList", idList);

            return adminRecordModelMapper.delAdminLoggerInfo(parmaMap) > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除管理员日志信息 异常" + e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delAdminLogger");
        }
    }

    @Override
    public Map<String, Object> getUserRoleInfo(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            List<Map<String, Object>> menuList = publicRoleService.getRoleTreeList(id);
            resultMap.put("menuList", menuList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取权限列表信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUserRoleInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getUserRoles(MainVo vo, Integer roleId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (roleId != null) {
                resultMap.put("roleId", roleId);
            } else {
                //获取当前用户权限id
                resultMap.put("adminName", adminModel.getName());
                resultMap.put("roleId", adminModel.getRole());
            }

            //获取商城权限下拉
            RoleModel roleModel = new RoleModel();
            roleModel.setStore_id(vo.getStoreId());
            roleModel.setStatus(RoleModel.STATUS_ROLE);
            List<RoleModel> roleModelList = roleModelMapper.select(roleModel);

            resultMap.put("roleList", roleModelList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取权限下拉信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUserRoles");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getUserRoleMenuInfo(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            resultMap.put("menuList", publicRoleService.getMenuTreeList(null));
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取权限菜单信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUserRoleMenuInfo");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRoleMenu(AddRoleVo vo, Integer id) throws LaiKeApiException {
        try {
            int count;
            RoleModel roleModelOld = null;
            if (id != null && id > 0) {
                roleModelOld = roleModelMapper.selectByPrimaryKey(id);
                if (roleModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "角色不存在");
                }
            }
            if (StringUtils.isEmpty(vo.getRoleName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "角色名称不能为空");
            }
            if (vo.getRoleName().length() > 60) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "角色名称不能超过20个中文字长度");
            }
            if (vo.getPermissions() == null || vo.getPermissions().size() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择绑定权限");
            }
            if (StringUtils.isEmpty(vo.getDescribe())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "角色描述不能为空");
            }
            if (vo.getStatus() == null || vo.getStatus() < 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "状态不能为空");
            }
            if (roleModelOld == null || !roleModelOld.getName().equals(vo.getRoleName())) {
                //角色名称是否存在
                RoleModel roleModel = new RoleModel();
                roleModel.setStore_id(vo.getStoreId());
                roleModel.setStatus(vo.getStatus());
                roleModel.setName(vo.getRoleName());
                count = roleModelMapper.selectCount(roleModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "名称已存在");
                }
            }
            RoleModel roleModelSave = new RoleModel();
            roleModelSave.setName(vo.getRoleName());
            roleModelSave.setRole_describe(vo.getDescribe());
            if (vo.getStatus() != null && vo.getStatus() > 0) {
                roleModelSave.setStatus(vo.getStatus());
            }

            //获取平台菜单id
            CoreMenuModel coreMenuSystem = coreMenuModelMapper.getSystemMenu();
            if (coreMenuSystem == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ERROR, "菜单数据错误");
            }


            if (roleModelOld != null) {
                //修改
                roleModelSave.setId(roleModelOld.getId());
                //删除之前权限菜单
                count = roleMenuModelMapper.deleteMenu(roleModelOld.getId(), coreMenuSystem.getId());
                logger.debug("一共删除权限菜单 {} 个", count);
                //删除之前的权限导览
                count = guideMenuModelMapper.deleteGuidMenu(coreMenuSystem.getId(), roleModelOld.getId());
                logger.debug("一共删除权限菜单(功能导览) {} 个", count);

                count = roleModelMapper.updateByPrimaryKeySelective(roleModelSave);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙");
                }
            } else {
                //保存
                roleModelSave.setStore_id(vo.getStoreId());
                roleModelSave.setAdd_date(new Date());
                count = roleModelMapper.insertSelective(roleModelSave);

            }
            if (count < 1) {
                logger.info("修改/保存角色失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙");
            }
            //保存菜单信息
            int guideSort = 0;
            RoleMenuModel roleMenuModel = new RoleMenuModel();
            roleMenuModel.setRole_id(roleModelSave.getId());
            //保存功能导览
            GuideMenuModel guideMenuModel = new GuideMenuModel();
            guideMenuModel.setStore_id(vo.getStoreId());
            guideMenuModel.setRole_id(roleModelSave.getId());
            for (Integer menuId : vo.getPermissions()) {
                //权限菜单
                roleMenuModel.setMenu_id(menuId);
                roleMenuModel.setAdd_date(new Date());
                roleMenuModelMapper.insertSelective(roleMenuModel);
                //导览菜单
                guideMenuModel.setId(null);
                guideMenuModel.setMenu_id(menuId);
                //排序
                guideMenuModel.setGuide_sort(guideSort);
                guideMenuModel.setAdd_date(new Date());
                guideMenuModelMapper.insertSelective(guideMenuModel);
                guideSort++;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑角色信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addUserRoleMenu");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delUserRoleMenu(MainVo vo, int id) throws LaiKeApiException {
        try {
            //权限是否绑定
            AdminModel adminModel = new AdminModel();
            adminModel.setRole(id + "");
            adminModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            int count = adminModelMapper.selectCount(adminModel);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请先解除绑定关系,再进行删除操作！");
            }
            CoreMenuModel coreMenuSystem = coreMenuModelMapper.getSystemMenu();

            RoleModel roleModel = new RoleModel();
            roleModel.setStore_id(vo.getStoreId());
            roleModel.setId(id);
            count = roleModelMapper.delete(roleModel);
            if (count < 1) {
                logger.debug("角色删除失败 roleId:{}", id);
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙");
            }
            //删除之前权限菜单
            count = roleMenuModelMapper.deleteMenu(id, coreMenuSystem.getId());
            logger.info("一共删除权限菜单 {} 个", count);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙");
            }
            //删除之前的权限导览
            count = guideMenuModelMapper.deleteGuidMenu(coreMenuSystem.getId(), id);
            logger.info("一共删除权限菜单(功能导览) {} 个", count);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙");
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除角色信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delUserRoleMenu");
        }
    }


    @Autowired
    private PublicRoleService publicRoleService;

    @Autowired
    private CustomerModelMapper customerModelMapper;

    @Autowired
    private AdminModelMapper adminModelMapper;

    @Autowired
    private RoleModelMapper roleModelMapper;

    @Autowired
    private AdminRecordModelMapper adminRecordModelMapper;

    @Autowired
    private RoleMenuModelMapper roleMenuModelMapper;

    @Autowired
    private GuideMenuModelMapper guideMenuModelMapper;

    @Autowired
    private CoreMenuModelMapper coreMenuModelMapper;
}
