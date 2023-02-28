package com.laiketui.modules.backend.services;

import com.laiketui.api.modules.backend.OverviewService;
import com.laiketui.common.mapper.AdminModelMapper;
import com.laiketui.common.mapper.CoreMenuModelMapper;
import com.laiketui.common.mapper.GuideModelMapper;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.role.CoreMenuModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.saas.OverviewVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.PinyinUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能导览
 *
 * @author Trick
 * @date 2021/1/26 11:30
 */
@Service
public class OverviewServiceImpl implements OverviewService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminModelMapper adminModelMapper;

    @Autowired
    private CoreMenuModelMapper coreMenuModelMapper;

    @Autowired
    private GuideModelMapper guideModelMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> index(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            List<Map<String, Object>> resultList = new ArrayList<>();
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);

            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("is_core", 1);
            parmaMap.put("s_id", 0);
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("is_display", 0);
            parmaMap.put("roleId", adminModel.getRole());
            //如果是系统管理员则无视商城
            if (adminModel.getAdmin_type() != 0) {
                parmaMap.put("store_id", vo.getStoreId());
            }

            parmaMap.put("guide_sort", DataUtils.Sort.DESC.toString());
            List<Map<String, Object>> coreMenuList = coreMenuModelMapper.getFunctionOverview(parmaMap);
            for (Map<String, Object> map : coreMenuList) {
                Map<String, Object> mainMap = new HashMap<>(16);
                int id = MapUtils.getIntValue(map, "id");
                String title = MapUtils.getString(map, "guide_name");
                int type = MapUtils.getIntValue(map, "type");
                if (type == 0) {
                    //平台不显示
                    continue;
                }
                //获取菜单下所有功能
                List<Map<String, Object>> subclassList = new ArrayList<>();
                findSubordinates(adminModel.getAdmin_type() == 0, vo.getStoreId(), id, subclassList, Integer.parseInt(adminModel.getRole()), null);
                mainMap.put("title", title);
                mainMap.put("list", subclassList);
                resultList.add(mainMap);
            }
            resultMap.put("list", resultList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("功能导览页面数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    /**
     * 获取权限下所有功能
     *
     * @param isAdmin      -
     * @param storeId      -
     * @param sid          -
     * @param subclassList -
     * @param roleId       -
     * @param goToUrl      -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/6/15 15:11
     */
    private void findSubordinates(boolean isAdmin, int storeId, int sid, List<Map<String, Object>> subclassList, int roleId, String goToUrl) throws LaiKeApiException {
        try {
            logger.debug("roleId={} sid={} 正在递归", roleId, sid);
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("s_id", sid);
            parmaMap.put("is_core", 1);
            parmaMap.put("guide_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);

            if (!isAdmin) {
                parmaMap.put("store_id", storeId);
            }

            parmaMap.put("is_display", 0);
            parmaMap.put("roleId", roleId);
            List<Map<String, Object>> list = coreMenuModelMapper.getFunctionOverview(parmaMap);
            for (Map<String, Object> map : list) {
                int id = MapUtils.getIntValue(map, "id");
                int level = MapUtils.getIntValue(map, "level");
                //跳转路径
                if (level == 2) {
                    goToUrl = MapUtils.getString(map, "url");
                }
                //只显示到二级
                if (level > 2) {
                    continue;
                }
                Map<String, Object> subclassMap = new HashMap<>(16);
                subclassMap.put("title", MapUtils.getString(map, "guide_name"));
                subclassMap.put("image", publiceService.getImgPath(MapUtils.getString(map, "image1"), 0));
                subclassMap.put("introduction", MapUtils.getString(map, "briefintroduction"));
                subclassMap.put("url", goToUrl);
                subclassList.add(subclassMap);
                findSubordinates(isAdmin, storeId, id, subclassList, roleId, goToUrl);
            }
        } catch (Exception e) {
            logger.error("获取权限下所有功能 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "findSubordinates");
        }
    }

    @Override
    public Map<String, Object> functionList(OverviewVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            List<Map<String, Object>> resultList = new ArrayList<>();
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);

            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("is_core", 1);
            parmaMap.put("s_id", 0);
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("is_display", 0);
            parmaMap.put("roleId", adminModel.getRole());
            //如果是系统管理员则无视商城
            if (adminModel.getAdmin_type() != 0) {
                parmaMap.put("store_id", vo.getStoreId());
            }

            parmaMap.put("guide_sort", DataUtils.Sort.DESC.toString());
            int total = coreMenuModelMapper.countFunctionOverview(parmaMap);
            List<Map<String, Object>> coreMenuList = new ArrayList<>();
            if (total > 0) {
                coreMenuList = coreMenuModelMapper.getFunctionOverview(parmaMap);
            }
            for (Map<String, Object> map : coreMenuList) {
                int sid = MapUtils.getIntValue(map, "s_id");
                String idStr = MapUtils.getString(map, "id");
                String sidStr = MapUtils.getString(map, "s_id");
                int type = MapUtils.getIntValue(map, "type");
                if (type == 0) {
                    //平台不显示
                    continue;
                }

                String py = "";
                String title = MapUtils.getString(map, "title");
                map.put("title", title);
                if (sid > 0) {
                    //获取上级菜单信息
                    CoreMenuModel coreMenuModel = new CoreMenuModel();
                    coreMenuModel.setId(sid);
                    coreMenuModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    coreMenuModel = coreMenuModelMapper.selectOne(coreMenuModel);
                    if (coreMenuModel != null) {
                        title = coreMenuModel.getTitle();
                        sidStr = coreMenuModel.getS_id().toString();
                    }
                }
                String format = "%s_%s";
                py = PinyinUtils.getPinYinHeadChar(title);
                idStr = String.format(format, py, idStr);
                sidStr = String.format(format, py, sidStr);
                map.put("id_id", idStr);
                map.put("sid", sidStr);
                if (sid > 0) {
                    //获取菜单下所有功能
                    List<Map<String, Object>> subclassList = new ArrayList<>();
                    findSubordinates(adminModel.getAdmin_type() == 0, vo.getStoreId(), sid, subclassList, Integer.parseInt(adminModel.getRole()), null);
                }
                resultList.add(map);
            }

            resultMap.put("list", resultList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("编辑商城导览列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "functionList");
        }
        return resultMap;
    }

    @Override
    public void rsort(MainVo vo) throws LaiKeApiException {
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("is_core", 0);
            parmaMap.put("s_id", 0);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("guide_sort", DataUtils.Sort.DESC.toString());

            List<Map<String, Object>> coreMenuList = coreMenuModelMapper.getFunctionOverview(parmaMap);
            int sort = 1;
            //子类从一万开始排
            int sort_sun = 10000;
            for (Map<String, Object> map : coreMenuList) {
                int id = MapUtils.getIntValue(map, "id");
                String title = MapUtils.getString(map, "title");
                //重新排序
                guideModelMapper.test(sort, MapUtils.getIntValue(map, "guidId"));
                //获取菜单下所有功能
                parmaMap.put("s_id", id);
                List<Map<String, Object>> subclassList = coreMenuModelMapper.getFunctionOverview(parmaMap);
                for (Map<String, Object> sunMap : subclassList) {
                    String sunTitle = MapUtils.getString(sunMap, "title");
                    //重新排序
                    guideModelMapper.test(sort_sun, MapUtils.getIntValue(sunMap, "guidId"));
                    sort_sun++;
                }
                sort++;
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("排序出现问题的时候使用 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "functionList");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sortTop(MainVo vo, int id, Integer sid) throws LaiKeApiException {
        try {
            if (sid == null) {
                sid = 0;
            }
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            int roleId = adminModelMapper.getStoreRole(vo.getStoreId());
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (sid > 0) {
                parmaMap.put("s_id", sid);
            } else {
                parmaMap.put("s_id", 0);
            }
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("roleId", roleId);
            parmaMap.put("guide_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", 0);
            parmaMap.put("pageEnd", 1);
            List<Map<String, Object>> coreMenuList = coreMenuModelMapper.getFunctionOverview(parmaMap);
            int maxSort = 0;
            for (Map<String, Object> map : coreMenuList) {
                maxSort = MapUtils.getIntValue(map, "guide_sort") + 1;
            }
            int row = guideModelMapper.sortMove(vo.getStoreId(), roleId, id, maxSort);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "置顶失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("置顶 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "sortTop");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void move(MainVo vo, int id, int id2) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            int roleId = adminModelMapper.getStoreRole(vo.getStoreId());
            int row = guideModelMapper.moveSort(vo.getStoreId(), roleId, id, id2);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "置顶异常");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("上下移动 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "move");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void isDisplaySwitch(MainVo vo, int id) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            int roleId = adminModelMapper.getStoreRole(vo.getStoreId());
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("id", id);
            parmaMap.put("roleId", roleId);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            List<Map<String, Object>> coreMenuList = coreMenuModelMapper.getFunctionOverview(parmaMap);
            if (coreMenuList == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "菜单不存在");
            }
            for (Map<String, Object> map : coreMenuList) {
                //是否显示
                int isDisplay = 1;
                if (MapUtils.getIntValue(map, "is_display") == 1) {
                    isDisplay = 0;
                }
                int row = guideModelMapper.displaySwitch(vo.getStoreId(), roleId, id, isDisplay);
                if (row < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "操作失败");
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("是否显示开关 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "isDisplaySwitch");
        }
    }

    @Autowired
    private PubliceService publiceService;
}
