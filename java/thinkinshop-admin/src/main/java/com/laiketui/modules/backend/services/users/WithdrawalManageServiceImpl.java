package com.laiketui.modules.backend.services.users;

import com.laiketui.api.modules.backend.users.WithdrawalManageService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.home.SystemMessageModel;
import com.laiketui.domain.log.RecordModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.user.FinanceConfigModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.WithdrawModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.Tool.ExcelParamVo;
import com.laiketui.domain.vo.user.WalletVo;
import com.laiketui.domain.vo.user.WithdrawalVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.EasyPoiExcelUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 提现管理实现
 *
 * @author Trick
 * @date 2021/1/11 14:05
 */
@Service
public class WithdrawalManageServiceImpl implements WithdrawalManageService {
    private final Logger logger = LoggerFactory.getLogger(WithdrawalManageServiceImpl.class);

    @Override
    public Map<String, Object> getWithdrawalInfo(WithdrawalVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (vo.getStatus() != null) {
                parmaMap.put("status", vo.getStatus());
            }
            parmaMap.put("is_mch", 0);
            if (vo.getWid() != null && vo.getWid() > 0) {
                parmaMap.put("wid", vo.getWid());
            }
            if (StringUtils.isNotEmpty(vo.getMchName())) {
                parmaMap.put("like_name", vo.getMchName());
            }
            if (StringUtils.isNotEmpty(vo.getUserName())) {
                parmaMap.put("like_user_name", vo.getUserName());
            }
            if (StringUtils.isNotEmpty(vo.getPhone())) {
                parmaMap.put("like_mobile", vo.getPhone());
            }
            if (StringUtils.isNotEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
                if (!StringUtils.isEmpty(vo.getEndDate())) {
                    parmaMap.put("endDate", vo.getEndDate());
                }
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = withdrawModelMapper.countWithdrawLeftUserBank(parmaMap);
            List<Map<String, Object>> dataList = withdrawModelMapper.getWithdrawLeftUserBank(parmaMap);
            if (vo.getExportType() == 1) {
                exportWithdrawalData(dataList, false, response);
                return null;
            }

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取提现列表信息", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getWithdrawalInfo");
        }
        return resultMap;
    }

    //导出 提现审核列表
    private void exportWithdrawalData(List<Map<String, Object>> list, boolean flag, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"会员名称", "来源", "联系电话", "状态", "申请时间", "提现金额", "提现手续费", "持卡人姓名", "银行名称", "支行名称", "卡号"};
            //对应字段
            String[] kayList = new String[]{"userName", "sourceName", "mobile", "examineName", "add_date", "money", "s_charge", "Cardholder", "Bank_name", "branch", "Bank_card_number"};
            if (flag) {
                List<String> headers = new ArrayList<>();
                Collections.addAll(headers, headerList);
                headers.add("备注");
                headerList = headers.toArray(new String[0]);

                List<String> kay = new ArrayList<>();
                Collections.addAll(kay, kayList);
                kay.add("refuse");
                kayList = kay.toArray(new String[0]);
            }

            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("提现审核列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(true);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("提现审核列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportWithdrawalData");
        }
    }

    @Override
    public Map<String, Object> getWithdrawalRecord(WithdrawalVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("review_status", DictionaryConst.MchExameStatus.EXAME_PASS_STATUS);
            if (vo.getStatus() != null) {
                parmaMap.put("status", vo.getStatus());
            }
            parmaMap.put("is_mch", 0);

            if (vo.getStatus() != null) {
                parmaMap.put("status", vo.getStatus());
            }
            if (vo.getWid() != null && vo.getWid() > 0) {
                parmaMap.put("wid", vo.getWid());
            }
            if (StringUtils.isNotEmpty(vo.getUserName())) {
                parmaMap.put("like_name", vo.getUserName());
            }
            if (StringUtils.isNotEmpty(vo.getPhone())) {
                parmaMap.put("like_mobile", vo.getPhone());
            }
            if (StringUtils.isNotEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
                if (!StringUtils.isEmpty(vo.getEndDate())) {
                    parmaMap.put("endDate", vo.getEndDate());
                }
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            parmaMap.put("add_date_sort", DataUtils.Sort.DESC.toString());

            int total = withdrawModelMapper.countWithdrawLeftMchBank(parmaMap);
            List<Map<String, Object>> dataList = withdrawModelMapper.getWithdrawLeftMchBank(parmaMap);
            for (Map<String, Object> map : dataList) {
                int examine = MapUtils.getIntValue(map, "status");
                String examineName;
                switch (examine) {
                    //0：审核中 1：审核通过 2：拒绝
                    case 0:
                        examineName = "审核中";
                        break;
                    case 1:
                        examineName = "审核通过";
                        break;
                    case 2:
                        examineName = "拒绝";
                        break;
                    default:
                        examineName = "-";
                }
                map.put("examineName", examineName);
            }
            if (vo.getExportType() == 1) {
                exportWithdrawalData(dataList, true, response);
                return null;
            }

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取提现审核列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getWithdrawalExamineInfo");
        }
        return resultMap;
    }

    @Override
    public void withdrawalExamine(MainVo vo, int id, int status, String refuse) throws LaiKeApiException {
        try {
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            WithdrawModel withdrawModel = withdrawModelMapper.selectByPrimaryKey(id);
            if (withdrawModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "提现记录不存在");
            }
            //站内通知
            String sysNoticeTitle = "余额提现申请审批通过!";
            String sysNoticeText = "您的提现申请已通过审核，提现金额将会在1-3个工作日到账。";
            SystemMessageModel systemMessageSave = new SystemMessageModel();
            systemMessageSave.setStore_id(vo.getStoreId());
            systemMessageSave.setRecipientid(withdrawModel.getUser_id());
            systemMessageSave.setSenderid(adminModel.getName());
            systemMessageSave.setType(GloabConst.LktConfig.SYSMESSAGE_NOT_READ);
            //操作记录
            User user = new User();
            user.setStore_id(vo.getStoreId());
            user.setUser_id(withdrawModel.getUser_id());
            user = userBaseMapper.selectOne(user);
            RecordModel recordModel = new RecordModel();
            recordModel.setStore_id(vo.getStoreId());
            recordModel.setUser_id(withdrawModel.getUser_id());
            recordModel.setMoney(withdrawModel.getMoney());
            recordModel.setOldmoney(user.getMoney());
            recordModel.setEvent(String.format("%s提现了%s", user.getUser_id(), withdrawModel.getMoney()));
            recordModel.setType(21);

            //状态 0：审核中 1：审核通过 2：拒绝
            WithdrawModel withdrawUpdate = new WithdrawModel();
            withdrawUpdate.setId(withdrawModel.getId());
            if (DictionaryConst.ExameStatus.EXAME_NOT_PASS_STATUS.equals(status + "")) {
                sysNoticeTitle = "余额提现失败!";
                sysNoticeText = String.format("您申请的提现被驳回!驳回原因：%s", refuse);
                withdrawUpdate.setRefuse(refuse);
                //拒绝记录
                recordModel.setEvent(String.format("%s提现%s被拒绝", user.getUser_id(), withdrawModel.getMoney()));
                recordModel.setType(22);
                //退还至余额
                int count = userBaseMapper.rechargeUserPrice(user.getId(), withdrawModel.getMoney().abs());
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "服务器繁忙,请稍后重试");
                }
            }
            withdrawUpdate.setStatus(StringUtils.toString(status));
            int count = withdrawModelMapper.updateByPrimaryKeySelective(withdrawUpdate);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败");
            }
            systemMessageSave.setTitle(sysNoticeTitle);
            systemMessageSave.setContent(sysNoticeText);
            systemMessageSave.setTime(new Date());
            count = systemMessageModelMapper.insertSelective(systemMessageSave);

            recordModel.setAdd_date(new Date());
            count = recordModelMapper.insertSelective(recordModel);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "服务器繁忙,请稍后重试");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("审核提现", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "withdrawalExamine");
        }
    }

    @Override
    public Map<String, Object> getWalletInfo(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            FinanceConfigModel financeConfigModel = new FinanceConfigModel();
            financeConfigModel.setStore_id(vo.getStoreId());
            financeConfigModel = financeConfigModelMapper.selectOne(financeConfigModel);
            resultMap.put("data", financeConfigModel);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取钱包设置信息", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getWalletInfo");
        }
        return resultMap;
    }

    @Override
    public boolean setWalletInfo(WalletVo vo) throws LaiKeApiException {
        try {
            int count;
            if (vo.getMinMoney() == null || vo.getMinMoney().doubleValue() <= 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "最小充值金额不能小于等于0");
            }
            if (vo.getMinOutMoney() == null || vo.getMinOutMoney().doubleValue() <= 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "最小提现金额不能小于等于0");
            }
            if (vo.getMaxOutMoney() == null || vo.getMaxOutMoney().doubleValue() <= 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "最大提现金额不能小于等于0");
            }
            if (vo.getServiceMoney() == null || vo.getServiceMoney().doubleValue() >= 1 || vo.getServiceMoney().doubleValue() <= 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "手续费为大于0小于1的小数");
            }
            if (vo.getMinOutMoney().compareTo(vo.getServiceMoney()) == 0 || vo.getMinOutMoney().compareTo(vo.getServiceMoney()) < 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "最小提现金额不能小于等于手续费");
            }
            String unit = "元";
            if (!StringUtils.isEmpty(vo.getUnit())) {
                unit = vo.getUnit();
            }

            FinanceConfigModel financeConfigModel = new FinanceConfigModel();
            financeConfigModel.setStore_id(vo.getStoreId());
            financeConfigModel = financeConfigModelMapper.selectOne(financeConfigModel);

            FinanceConfigModel saveFinanceConfig = new FinanceConfigModel();
            saveFinanceConfig.setUnit(unit);
            saveFinanceConfig.setMin_cz(vo.getMinMoney());
            saveFinanceConfig.setMin_amount(vo.getMinOutMoney());
            saveFinanceConfig.setMax_amount(vo.getMaxOutMoney());
            saveFinanceConfig.setService_charge(vo.getServiceMoney());
            saveFinanceConfig.setModify_date(new Date());

            if (financeConfigModel != null) {
                saveFinanceConfig.setId(financeConfigModel.getId());
                count = financeConfigModelMapper.updateByPrimaryKeySelective(saveFinanceConfig);
            } else {
                saveFinanceConfig.setStore_id(vo.getStoreId());
                count = financeConfigModelMapper.insertSelective(saveFinanceConfig);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置钱包设置信息" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setWalletInfo");
        }
    }


    @Autowired
    private WithdrawModelMapper withdrawModelMapper;

    @Autowired
    private FinanceConfigModelMapper financeConfigModelMapper;

    @Autowired
    private SystemMessageModelMapper systemMessageModelMapper;

    @Autowired
    private RecordModelMapper recordModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private RedisUtil redisUtil;
}
