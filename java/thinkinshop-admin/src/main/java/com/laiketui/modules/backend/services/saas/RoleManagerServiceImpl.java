package com.laiketui.modules.backend.services.saas;

import com.laiketui.api.common.admin.PublicRoleService;
import com.laiketui.api.modules.backend.saas.RoleManagerService;
import com.laiketui.common.mapper.AdminModelMapper;
import com.laiketui.common.mapper.CoreMenuModelMapper;
import com.laiketui.common.mapper.GuideMenuModelMapper;
import com.laiketui.common.mapper.RoleMenuModelMapper;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.role.CoreMenuModel;
import com.laiketui.domain.role.GuideMenuModel;
import com.laiketui.domain.role.RoleMenuModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.menu.AddMenuVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.ImgUploadUtils;
import com.laiketui.root.utils.tool.PinyinUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 权限管理
 *
 * @author Trick
 * @date 2021/1/28 16:16
 */
@Service
public class RoleManagerServiceImpl implements RoleManagerService {
    private final Logger logger = LoggerFactory.getLogger(ShopManageServiceImpl.class);

    @Override
    public Map<String, Object> getMenuInfo(MainVo vo, String name, Integer id, Integer sid) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("recycle", 0);
            if (id != null) {
                parmaMap.put("id", id);
                //获取所有上级权限
                List<CoreMenuModel> coreMenuModelList = new ArrayList<>();
                publicRoleService.getRoleFatherById(id, coreMenuModelList);
                if (coreMenuModelList.size() > 0) {
                    coreMenuModelList.remove(0);
                }
                resultMap.put("roleList", coreMenuModelList);
            }
            if (!StringUtils.isEmpty(name)) {
                parmaMap.put("title", name);
            }
            if (sid != null && sid > 0) {
                parmaMap.put("sid", sid);
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            parmaMap.put("sort_sort", DataUtils.Sort.DESC.toString());

            int total = coreMenuModelMapper.countDynamic(parmaMap);
            List<Map<String, Object>> dataList = coreMenuModelMapper.selectDynamic(parmaMap);
            for (Map<String, Object> map : dataList) {
                int fatherId = (int) map.get("s_id");
                if (fatherId > 0) {
                    //获取上级名称
                    CoreMenuModel coreMenuModel = coreMenuModelMapper.selectByPrimaryKey(fatherId);
                    map.put("fatherName", coreMenuModel.getTitle());
                }
            }

            resultMap.put("total", total);
            resultMap.put("dataList", dataList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取核心菜单信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getMenuInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getMenuLeveInfo(MainVo vo, String name, Integer id, Integer sid, Integer type, Integer isCore) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (id != null && id > 0) {
                parmaMap.put("id", id);
            } else {
                if (sid != null && sid > 0) {
                    parmaMap.put("sid", sid);
                } else {
                    parmaMap.put("sid", 0);
                }
            }
            if (type != null) {
                parmaMap.put("type", type);
            }
            if (isCore != null) {
                parmaMap.put("isCore", isCore);
            }
            if (!StringUtils.isEmpty(name)) {
                parmaMap.put("titleORid", name);
            }

            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("sort_sort", DataUtils.Sort.DESC.toString());
            List<Map<String, Object>> dataList = coreMenuModelMapper.selectDynamic(parmaMap);
            for (Map<String, Object> map : dataList) {
                int mid = MapUtils.getInteger(map, "id");
                String mname = MapUtils.getString(map, "title");
                mname = PinyinUtils.getPinYinHeadChar(mname);
                int fatherId = (int) map.get("s_id");
                map.put("id_id", mname + "_" + mid);
                map.put("sid", mname + "_" + fatherId);

                map.put("image", publiceService.getImgPath(MapUtils.getString(map, "image"), 0));
                map.put("image1", publiceService.getImgPath(MapUtils.getString(map, "image1"), 0));
                if (fatherId > 0) {
                    //获取上级名称
                    CoreMenuModel coreMenuModel = coreMenuModelMapper.selectByPrimaryKey(fatherId);
                    map.put("fatherName", coreMenuModel.getTitle());
                }
            }
            int total = coreMenuModelMapper.countDynamic(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
            resultMap.put("sid", sid);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取上级菜单信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getFatherMenuInfo");
        }
        return resultMap;
    }

    @Override
    public void moveMenuSort(MainVo vo, int id, Integer moveId, Integer type) throws LaiKeApiException {
        try {
            int row = 0;
            CoreMenuModel coreMenuModel = new CoreMenuModel();
            coreMenuModel.setId(id);
            coreMenuModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            coreMenuModel = coreMenuModelMapper.selectOne(coreMenuModel);
            if (coreMenuModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "菜单不存在");
            }
            //1=置顶 2=上移 下移
            switch (type) {
                case 1:
                    int sort = coreMenuModelMapper.maxSort(coreMenuModel.getLevel());
                    CoreMenuModel coreMenuUpdate = new CoreMenuModel();
                    coreMenuUpdate.setId(id);
                    coreMenuUpdate.setSort(sort + 1);
                    row = coreMenuModelMapper.updateByPrimaryKeySelective(coreMenuUpdate);
                    break;
                case 2:
                    row = coreMenuModelMapper.moveSort(id, moveId);
                    break;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "修改失败");
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("菜单顺序移动 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "moveMenuSort");
        }
    }

    @Override
    public void addMenuInfo(AddMenuVo vo) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(vo.getMenuName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "菜单名称不能为空");
            } else if (vo.getLevel() == 1 && vo.getMenuName().length() > 6) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "菜单名称不能超过六个字符");
            }
            if (vo.getLevel() == null || vo.getLevel() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "菜单级别不能为空");
            }
            if (vo.getLevel().equals(1)) {
                if (StringUtils.isEmpty(vo.getDefaultLogo())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择菜单默认图标");
                }
                if (StringUtils.isEmpty(vo.getChekedLogo())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择菜单选中图标");
                }
            }

            CoreMenuModel coreMenuModelSave = new CoreMenuModel();
            coreMenuModelSave.setTitle(vo.getMenuName());
            coreMenuModelSave.setLevel(vo.getLevel());
            coreMenuModelSave.setIs_core(vo.getIsCore());
            coreMenuModelSave.setIs_plug_in(0);
            coreMenuModelSave.setType(vo.getMenuClass());
            coreMenuModelSave.setGuide_name(vo.getGuideName());
            coreMenuModelSave.setUrl(vo.getMenuUrl());
            coreMenuModelSave.setModule(vo.getPath());
            if (StringUtils.isNotEmpty(vo.getDefaultLogo())) {
                coreMenuModelSave.setImage(ImgUploadUtils.getUrlImgByName(vo.getDefaultLogo(), true));
            }
            if (StringUtils.isNotEmpty(vo.getChekedLogo())) {
                coreMenuModelSave.setImage1(ImgUploadUtils.getUrlImgByName(vo.getChekedLogo(), true));
            }

            CoreMenuModel coreMenuModel = null;
            if (vo.getMid() != null) {
                coreMenuModel = coreMenuModelMapper.selectByPrimaryKey(vo.getMid());
                if (coreMenuModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "菜单不存在");
                }
            }

            if (coreMenuModel == null || !coreMenuModel.getTitle().equals(vo.getMenuName())) {
                Map<String, Object> parmaMap = new HashMap<>(16);
                parmaMap.put("title", vo.getMenuName());
                parmaMap.put("sid", vo.getFatherMenuId());
                parmaMap.put("type", vo.getMenuClass());
                parmaMap.put("isCore", vo.getIsCore());
                parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
                count = coreMenuModelMapper.countDynamic(parmaMap);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "菜单名称已存在");
                }
            }

            if (vo.getLevel() == 1) {
                //一级菜单
                if (StringUtils.isEmpty(vo.getDefaultLogo())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "默认图标不能为空");
                }
                if (StringUtils.isEmpty(vo.getChekedLogo())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "选中图标不能为空");
                }
                coreMenuModelSave.setS_id(0);
            } else {
                if (StringUtils.isEmpty(vo.getMenuUrl())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "路径不能为空");
                }
                if (vo.getFatherMenuId() == null || vo.getFatherMenuId() < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "上级菜单不能为空");
                }
                coreMenuModelSave.setS_id(vo.getFatherMenuId());
                coreMenuModelSave.setBriefintroduction(vo.getBriefintroduction());
            }

            if (coreMenuModel != null) {
                coreMenuModelSave.setId(coreMenuModel.getId());
                //如果是从别的菜单改过来的则重新获取序号
                if (!coreMenuModel.getS_id().equals(vo.getFatherMenuId())) {
                    int sort = coreMenuModelMapper.maxSortByLevel(vo.getLevel(), vo.getFatherMenuId(), vo.getMenuClass()) + 1;
                    logger.debug("菜单id{}从别的菜单改过来 重新获取序号{}", coreMenuModel.getId(), sort);
                    coreMenuModelSave.setSort(sort);
                }
                count = coreMenuModelMapper.updateByPrimaryKeySelective(coreMenuModelSave);
            } else {
                //获取当前级别最新序号
                int sort = coreMenuModelMapper.maxSortByLevel(vo.getLevel(), vo.getFatherMenuId(), vo.getMenuClass()) + 1;
                coreMenuModelSave.setSort(sort);
                coreMenuModelSave.setAdd_time(new Date());
                count = coreMenuModelMapper.insertSelective(coreMenuModelSave);

                //如果是控制台,则给系统管理员绑定权限
                if (vo.getMenuClass() == 1) {
                    AdminModel sysAdmin = adminModelMapper.getSystemAdmin();
                    if (sysAdmin != null) {
                        //保存功能导览
                        logger.debug("给系统管理员{} 绑定权限", sysAdmin.getName());
                        GuideMenuModel guideMenuModel = new GuideMenuModel();
                        guideMenuModel.setStore_id(vo.getStoreId());
                        guideMenuModel.setRole_id(Integer.parseInt(sysAdmin.getRole()));
                        guideMenuModel.setMenu_id(coreMenuModelSave.getId());
                        //排序
                        guideMenuModel.setGuide_sort(guideMenuModelMapper.maxSort(vo.getStoreId(), guideMenuModel.getRole_id()));
                        guideMenuModel.setAdd_date(new Date());
                        guideMenuModelMapper.insertSelective(guideMenuModel);
                        //权限菜单
                        RoleMenuModel roleMenuModel = new RoleMenuModel();
                        roleMenuModel.setRole_id(guideMenuModel.getRole_id());
                        roleMenuModel.setMenu_id(coreMenuModelSave.getId());
                        roleMenuModel.setAdd_date(new Date());
                        roleMenuModelMapper.insertSelective(roleMenuModel);

                    }
                }
            }

            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑菜单 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addMenuInfo");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delMenu(int menuId) throws LaiKeApiException {
        try {
            CoreMenuModel coreMenuModel = new CoreMenuModel();
            coreMenuModel.setId(menuId);
            coreMenuModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            CoreMenuModel coreMenuModelOld = coreMenuModelMapper.selectOne(coreMenuModel);
            if (coreMenuModelOld == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "菜单不存在");
            }
            coreMenuModel.setId(null);
            coreMenuModel.setS_id(coreMenuModelOld.getId());
            int count = coreMenuModelMapper.selectCount(coreMenuModel);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "删除失败,请先删除子菜单");
            }
            RoleMenuModel roleMenuCount = new RoleMenuModel();
            roleMenuCount.setMenu_id(coreMenuModelOld.getId());
            count = roleMenuModelMapper.selectCount(roleMenuCount);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "删除失败,菜单使用中");
            }
            count = coreMenuModelMapper.delCoreMenu(coreMenuModelOld.getId());

            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "菜单删除失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除菜单 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delMenu");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean bindRole(int roleId, List<Integer> adminIds) throws LaiKeApiException {
        try {
            for (Integer adminId : adminIds) {
                AdminModel adminModel = new AdminModel();
                adminModel.setId(adminId);
                adminModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                adminModel = adminModelMapper.selectOne(adminModel);
                if (adminModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "列表中有管理员不存在");
                }
                AdminModel adminModelUpdate = new AdminModel();
                adminModelUpdate.setId(adminModel.getId());
                adminModelUpdate.setStore_id(adminModel.getStore_id());
                //是否绑定,如果绑定则取消绑定,未绑定则绑定
                if (!StringUtils.isEmpty(adminModel.getRole()) && adminModel.getRole().equals(roleId + "")) {
                    //取消绑定
                    adminModelUpdate.setRole("");
                } else {
                    adminModelUpdate.setRole(String.valueOf(roleId));
                }
                int count = adminModelMapper.updateByPrimaryKeySelective(adminModelUpdate);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障");
                }
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("绑定/解绑角色 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "bindRole");
        }
    }

    @Override
    public Map<String, Object> getRoleMenu(MainVo vo, int roleId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("roleId", roleId);
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("levelList", new Integer[]{1, 2});
            parmaMap.put("sort_sort", DataUtils.Sort.DESC.toString());
            List<Integer> menuList = coreMenuModelMapper.getRoleMenuIds(parmaMap);

            resultMap.put("menuList", menuList);
            resultMap.put("btnIds", new ArrayList<>());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("根据角色获取菜单 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRoleMenu");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getAsyncRoutesByRoutes(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("roleId", user.getRole());
            parmaMap.put("levelList", new Integer[]{1});
            parmaMap.put("s_id", 0);
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("sort_sort", DataUtils.Sort.DESC.toString());
            List<Map<String, Object>> menuList = getRoleMenuList(parmaMap);

            resultMap.put("menu", menuList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取权限菜单---路由结构 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAsyncRoutesByRoutes");
        }
        return resultMap;
    }

    //获取当前角色拥有的菜单
    private List<Map<String, Object>> getRoleMenuList(Map<String, Object> parmaMap) {
        try {
            //获取某级菜单
            List<Map<String, Object>> menuList = coreMenuModelMapper.getRoleMenuInfos(parmaMap);
            for (Map<String, Object> menuMap : menuList) {
                int id = MapUtils.getIntValue(menuMap, "id");
                //当前菜单级别
                int menuLevel = MapUtils.getIntValue(menuMap, "level");
                //默认图片
                String image = publiceService.getImgPath(MapUtils.getString(menuMap, "image"), 0);
                String image1 = publiceService.getImgPath(MapUtils.getString(menuMap, "image1"), 0);
                menuMap.put("image", image);
                menuMap.put("image1", image1);

                //是否有下级菜单标识
                boolean isChildren = false;
                //获取当前级别子菜单
                parmaMap.put("levelList", new Integer[]{menuLevel + 1});
                parmaMap.put("s_id", id);
                List<Map<String, Object>> childrenList = getRoleMenuList(parmaMap);
                if (childrenList != null && childrenList.size() > 0) {
                    //只有有二级菜单需要路由配置
                    if (menuLevel < 3) {
                        isChildren = true;
                    }
                }
                menuMap.put("children", childrenList);
                menuMap.put("isChildren", isChildren);
            }
            return menuList;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取当前角色拥有的菜单 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAsyncRoutesByRoutes");
        }
    }

    @Autowired
    private PublicRoleService publicRoleService;

    @Autowired
    private CoreMenuModelMapper coreMenuModelMapper;

    @Autowired
    private AdminModelMapper adminModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private RoleMenuModelMapper roleMenuModelMapper;

    @Autowired
    private GuideMenuModelMapper guideMenuModelMapper;

    @Autowired
    private RedisUtil redisUtil;
}
