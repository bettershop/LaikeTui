package com.laiketui.root.utils.common;

import java.util.Random;
import java.util.UUID;

/**
 * 生成各种id工具类
 *
 * @author Trick
 * @date 2020/11/20 15:55
 */
public class BuilderIDTool {
    /**
     * id类型
     *
     * @author Trick
     * @date 2020/11/20 15:55
     */
    public enum Type {
        /**
         * 字母组合
         */
        LETTER,
        /**
         * 数字组合
         */
        NUMBER,
        /**
         * 字母+数字 组合
         */
        ALPHA
    }

    /**
     * ID  Ϣ
     *
     * @param type   -
     * @param length -
     */
    public static String getNext(Type type, int length) {
        String base;
        switch (type) {
            case LETTER:
                base = LETTER;
                break;
            case NUMBER:
                base = NUMBER;
                break;
            default:
                base = ALPHA;
                break;
        }
        StringBuilder buffer = new StringBuilder();
        Random random = new Random();
        for (int i = 0, maxLength = base.length(); i < length; i++) {
            int idx = random.nextInt(maxLength);
            buffer.append(base.charAt(idx));
        }
        return buffer.toString();
    }


    /**
     *  生成GUID数据
     *
     * @author Trick
     * @date 2020/11/20 16:00
     * @return      * @param String -
     */
    public static String getGuid() {
        return UUID.randomUUID().toString();
    }

    private static final String LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmnopqrstuvwxzy";
    private static final String NUMBER = "0123456789";
    private static final String ALPHA = "ABCDEFGHIJKLMNPQRSTUVWXZYabcdefghijklmnpqrstuvwxzy0123456789";
    private static final LKTSnowflakeIdWorker SNOWFLAKE_ID_WORKER = new LKTSnowflakeIdWorker(0, 0);

    /**
     * * 获取snowFlakeId
     *
     * @return long
     */
    public static long getSnowflakeId() {
        return SNOWFLAKE_ID_WORKER.nextId();
    }

}
