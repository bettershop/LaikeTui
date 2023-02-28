package com.laiketui.common.services.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.common.PublicDictionaryService;
import com.laiketui.api.common.admin.PublicRoleService;
import com.laiketui.common.mapper.AdminModelMapper;
import com.laiketui.common.mapper.CoreMenuModelMapper;
import com.laiketui.common.mapper.RoleMenuModelMapper;
import com.laiketui.domain.dictionary.DictionaryListModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.role.CoreMenuModel;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.exception.LaiKeApiException;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关于菜单、权限
 *
 * @author Trick
 * @date 2021/1/29 9:41
 */
@Service
public class PublicRoleServiceImpl implements PublicRoleService {
    private final Logger logger = LoggerFactory.getLogger(PublicRoleServiceImpl.class);


    @Autowired
    private CoreMenuModelMapper coreMenuModelMapper;

    @Autowired
    private PublicDictionaryService publicDictionaryService;

    @Autowired
    private RoleMenuModelMapper roleMenuModelMapper;

    @Override
    public List<Map<String, Object>> getMenuTreeList(Integer roleId) throws LaiKeApiException {
        List<Map<String, Object>> menuTreeMap = new ArrayList<>();
        try {
            //获取所有平台
            Map<String, Object> headerMap = publicDictionaryService.getDictionaryByName("导航栏");
            String json = JSON.toJSONString(headerMap.get("value"));
            List<DictionaryListModel> dictionaryListModelList = JSON.parseObject(json, new TypeReference<List<DictionaryListModel>>() {
            });
            //头部索引
            Map<String, Map<String, Object>> headerIndexTempMap = new HashMap<>(16);
            //构造树形头部信息
            for (DictionaryListModel dictionary : dictionaryListModelList) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("title", dictionary.getText());
                map.put("id", dictionary.getValue());
                map.put("checked", false);
                //添加索引
                headerIndexTempMap.put(dictionary.getValue(), map);
            }
            //获取当前角色拥有的权限
            List<Integer> currentMenus = new ArrayList<>();
            if (roleId != null) {
                currentMenus = roleMenuModelMapper.getUserRoleMenuInfoToId(roleId);
            }

            //获取所有菜单树形结构
            List<Map<String, Object>> dataList = coreMenuModelMapper.getCoreMenuInfoBySid(0);
            for (Map<String, Object> map : dataList) {
                int fatherId = Integer.parseInt(map.get("id").toString());
                //所属平台
                String type = String.valueOf(map.get("type"));
                //获取当前平台
                Map<String, Object> currentHeaderMap = headerIndexTempMap.get(type);
                if (currentHeaderMap.isEmpty()) {
                    //如果没有所属则跳过当前菜单
                    logger.debug("菜单【{}】没有所属平台", map.get("title"));
                    continue;
                }
                //选中标识
                map.put("checked", false);
                //获取子菜单结构
                List<Map<String, Object>> childrenList = getRoleChildrenAll(fatherId, currentMenus);
                map.put("children", childrenList);
                if (childrenList != null && childrenList.size() > 0) {
                    //选中
                    map.put("checked", true);
                    currentHeaderMap.put("checked", true);
                    //归类
                    currentHeaderMap.put("children", childrenList);
                }
            }
            //整合数据
            for (String key : headerIndexTempMap.keySet()) {
                menuTreeMap.add(headerIndexTempMap.get(key));
            }
            return menuTreeMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("更具菜单id获取树形结构 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getMenuTreeList");
        }
    }

    @Override
    public List<Map<String, Object>> getRoleTreeList(Integer roleId) throws LaiKeApiException {
        List<Map<String, Object>> menuTreeMap = new ArrayList<>();
        try {
            List<Integer> currentMenus = new ArrayList<>();
            if (roleId != null) {
                //获取当前角色拥有的权限
                currentMenus = roleMenuModelMapper.getUserRoleMenuInfoToId(roleId);
            }

            //获取所有菜单树形结构
            List<Map<String, Object>> resultMenuTreeMap = coreMenuModelMapper.getCoreMenuInfoBySid(0);
            for (Map<String, Object> map : resultMenuTreeMap) {
                int type = MapUtils.getIntValue(map, "type");
                //不显示平台
                if (type == 0) {
                    continue;
                }

                int fatherId = Integer.parseInt(map.get("id").toString());
                //选中标识
                map.put("checked", false);
                //获取子菜单结构
                List<Map<String, Object>> childrenList = getRoleChildrenAll(fatherId, currentMenus);
                if (childrenList != null && childrenList.size() > 0 && childrenList.get(0) != null) {
                    //选中
                    map.put("checked", true);
                } else if (childrenList != null && childrenList.size() > 0 && childrenList.get(0) == null) {
                    //去除之前的标识
                    childrenList.remove(0);
                }
                map.put("children", childrenList);
                menuTreeMap.add(map);
            }
            return menuTreeMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("更具权限id获取树形结构 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRoleTreeList");
        }
    }

    /**
     * 获取所有子权限菜单
     *
     * @param fatherId   - 上级id
     * @param roleMenuId - 当前角色拥有的菜单,用于选中
     * @return List -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 12:00
     */
    private List<Map<String, Object>> getRoleChildrenAll(int fatherId, List<Integer> roleMenuId) throws LaiKeApiException {
        try {
            //是否选中上级标识
            boolean isShow = false;
            List<Map<String, Object>> dataList = coreMenuModelMapper.getCoreMenuInfoBySid(fatherId);
            for (Map<String, Object> map : dataList) {
                int id = Integer.parseInt(map.get("id").toString());
                //选中标识
                map.put("checked", false);
                if (roleMenuId != null) {
                    //是否选中
                    if (roleMenuId.contains(id)) {
                        isShow = true;
                        map.put("checked", true);
                    }
                }
                List<Map<String, Object>> temp = getRoleChildrenAll(id, roleMenuId);
                if (temp != null && temp.size() > 0 && temp.get(0) == null) {
                    temp.remove(0);
                }
                map.put("children", temp);
            }
            if (!isShow) {
                dataList.add(0, null);
            }
            return dataList;
        } catch (Exception e) {
            logger.error("获取所有子权限菜单 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRoleChildrenAll");
        }
    }

    @Override
    public void getRoleFatherById(int fatherId, List<CoreMenuModel> list) throws LaiKeApiException {
        try {
            CoreMenuModel coreMenuModel = coreMenuModelMapper.selectByPrimaryKey(fatherId);
            if (coreMenuModel != null) {
                list.add(coreMenuModel);
                getRoleFatherById(coreMenuModel.getS_id(), list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取当前权限id获取上级菜单信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRoleFatherById");
        }
    }

    @Override
    public Map<String, Object> getBindListInfo(int roleId, String name, int isBind) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap1 = new HashMap<>(16);
            parmaMap1.put("type", AdminModel.TYPE_CLIENT);
            if (isBind == 1) {
                parmaMap1.put("role", roleId);
            } else {
                parmaMap1.put("notRole", roleId);
            }
            parmaMap1.put("name", name);
            //获取绑定的商户
            List<Map<String, Object>> bindAdminList = adminModelMapper.getBindListInfo(parmaMap1);

            resultMap.put("bindAdminList", bindAdminList);
        } catch (Exception e) {
            logger.error("获取所有商户 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getBindListInfo");
        }
        return resultMap;
    }

    @Override
    public boolean verificationBind(List<Integer> adminIds) throws LaiKeApiException {
        try {
            for (Integer adminId : adminIds) {
                int count = adminModelMapper.verificationBind(adminId);
                if (count > 0) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("验证商户是否绑定角色 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "verificationBind");
        }
    }

    @Autowired
    private AdminModelMapper adminModelMapper;
}
