package com.laiketui.root.utils.tool;

import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 店铺工具类
 *
 * @author Trick
 * @date 2020/11/10 11:11
 */
@Component
public class ShopUtils {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordTool.class);

    @Autowired
    RedisUtil redisUtil;

    private static final String FILE_PATH = "txt/ShopWords.txt";

    /**
     * 初始化店铺非法字符串
     *
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/28 13:48
     */
    @PostConstruct
    public void filterInfoAfter() throws LaiKeApiException {
        BufferedReader bufferedReader = null;
        List<String> arrayList = new ArrayList<>();
        try {
            logger.info("获取词库文件地址");
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_PATH);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            for (String txt; (txt = bufferedReader.readLine()) != null; ) {
                if (!arrayList.contains(txt)) {
                    arrayList = Arrays.asList(txt.split(","));
                }
            }

            logger.info("正在初始化【店铺名称非法词汇】库");
            Object wordsObj = redisUtil.get(GloabConst.RedisHeaderKey.LKT_SHOPNAME_ILLEGAL_WORDS);
            if (wordsObj == null) {
                logger.info("正在生成【店铺名称非法词汇】库");
                redisUtil.set(GloabConst.RedisHeaderKey.LKT_SHOPNAME_ILLEGAL_WORDS, arrayList, -1);
                logger.info("已生成【店铺名称非法词汇】库");
            } else {
                logger.info("已存在【店铺名称非法词汇】库");
            }
        } catch (Exception e) {
            logger.error("初始化【店铺名称非法词汇】库 异常 " + e.getMessage());
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "初始化词库失败", "filterInfoAfter");
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
     * 断是否有非法词汇
     * true=非法
     *
     * @param str -
     * @return boolean
     * @author Trick
     * @date 2020/10/28 14:10
     */
    @SuppressWarnings("unchecked")
    public boolean checkSenstiveWord(String str) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(str.trim())) {
                return true;
            }
            Object badwordObj = redisUtil.get(GloabConst.RedisHeaderKey.LKT_SHOPNAME_ILLEGAL_WORDS);
            if (badwordObj != null) {
                List<String> arrayList = (List<String>) badwordObj;
                for (String word : arrayList) {
                    int index = str.indexOf(word);
                    if (index > -1) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("断是否有非法词汇 异常 " + e.getMessage());
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "checkSenstiveWord");
        }
        return false;
    }

}
