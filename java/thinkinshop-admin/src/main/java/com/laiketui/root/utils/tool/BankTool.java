package com.laiketui.root.utils.tool;

import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 银行卡工具类
 *
 * @author Trick
 * @date 2020/11/3 11:18
 */
@Component
public class BankTool {

    private static final Logger logger = LoggerFactory.getLogger(BankTool.class);
    @Autowired
    RedisUtil redisUtil;

    private static final String FILE_PATH = "txt/BankCard.txt";

    /**
     * 初始化银行卡所属库
     *
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/28 13:48
     */
    @PostConstruct
    public void filterInfoAfter() throws LaiKeApiException {
        BufferedReader bufferedReader = null;
        Map<String, String> bankMap = new HashMap<>(16);
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_PATH);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            for (String txt; (txt = bufferedReader.readLine()) != null; ) {
                String[] banks = txt.split("=>");
                bankMap.put(banks[0], banks[1]);
            }

            logger.info("正在初始化【银行卡所属库】库");
            Object wordsObj = redisUtil.get(GloabConst.RedisHeaderKey.LKT_BANK_CARD_LIBRARY);
            if (wordsObj == null) {
                logger.info("正在生成【银行卡所属库】库");
                redisUtil.set(GloabConst.RedisHeaderKey.LKT_BANK_CARD_LIBRARY, bankMap, -1);
                logger.info("已生成【银行卡所属库】库");
            } else {
                logger.info("已存在【银行卡所属库】库");
            }
        } catch (Exception e) {
            logger.error("初始化【银行卡所属库】库异常 " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据尾号获取银行所属行
     *
     * @param bankNo -
     * @return boolean
     * @author Trick
     * @date 2020/10/28 14:10
     */
    @SuppressWarnings("unchecked")
    public String getBankNameByNo(String bankNo) throws LaiKeApiException {
        try {
            Object badwordObj = redisUtil.get(GloabConst.RedisHeaderKey.LKT_BANK_CARD_LIBRARY);
            if (badwordObj != null) {
                if (bankNo.length() < 4) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "银行卡格式错误");
                }
                Map<String, String> bankMap = (Map<String, String>) badwordObj;
                List<String> bankNoList = new ArrayList<>();
                if (bankNo.length() >= 8) {
                    bankNoList.add(bankNo.substring(0, 8));
                }
                if (bankNo.length() >= 6) {
                    bankNoList.add(bankNo.substring(0, 6));
                }
                if (bankNo.length() >= 5) {
                    bankNoList.add(bankNo.substring(0, 5));
                }
                bankNoList.add(bankNo.substring(0, 4));
                for (String bankNum : bankNoList) {
                    if (bankMap.containsKey(bankNum)) {
                        return bankMap.get(bankNum);
                    }
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.READ_NOT_DATA, "未获取到数据", "getBankNameByNo");
            }
        } catch (StringIndexOutOfBoundsException se) {
            logger.error("银行卡格式错误", se);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "银行卡格式错误");
        } catch (Exception e) {
            logger.error("根据尾号获取银行所属行异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "根据尾号获取银行所属行失败", "getBankNameByNo");
        }
        return null;
    }

    /**
     * 银行卡脱敏处理
     *
     * @param idNumber - 银行卡号
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/5 9:55
     */
    public static String desensitizedBankNumber(String idNumber) throws LaiKeApiException {
        try {
            if (!StringUtils.isEmpty(idNumber)) {
                idNumber = idNumber.replaceAll("(\\w{0})\\w*(\\w{4})", "$1******$2");
            }
            return idNumber;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据格式不正确", "desensitizedBankNumber");
        }
    }

    /**
     * 校验卡号是否未纯数字
     *
     * @param bankNo-
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/3 16:40
     */
    public boolean isNumber(String bankNo) throws LaiKeApiException {
        try {
            for (int i = bankNo.length(); --i >= 0; ) {
                if (!Character.isDigit(bankNo.charAt(i))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("校验卡号是否未纯数字异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "校验卡号是否未纯数字失败", "getBankNameByNo");
        }
    }

}
