package com.laiketui.root.utils.tool;

import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期帮助类
 *
 * @author Trick
 * @date 2020/10/14 14:41
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);


    /**
     * 是否为日期类型
     *
     * @param str -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/11 11:01
     */
    public static boolean isDate(Object str) throws LaiKeApiException {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        try {
            FastDateFormat fdf = FastDateFormat.getInstance(GloabConst.TimePattern.YMD);
            System.out.println(fdf.format(fdf.parse(String.valueOf(str))));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的时间戳
     * @param format  格式 yyyy-MM-dd HH:mm:ss
     * @return static
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (StringUtils.isEmpty(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = GloabConst.TimePattern.YMDHMS;
        }
        FastDateFormat fdf = FastDateFormat.getInstance(format);
        return fdf.format(new Date(Long.parseLong(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return String
     */
    public static String date2TimeStamp(String dateStr, String format) {
        try {
            FastDateFormat fdf = FastDateFormat.getInstance(format);
            return String.valueOf(fdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 日期比较
     * date>parseDate
     * (!date>parseDate) 相当于 <=的效果
     *
     * @param date      - 日期
     * @param parseDate - 被比较的日期
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/7 14:12
     */
    public static boolean dateCompare(Date date, Date parseDate) throws LaiKeApiException {
        try {
            return date.after(parseDate);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("日期比较 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "日期比较失败", "dateCompare");
        }
    }

    /**
     * 日期比较
     * date>parseDate
     * (!date>parseDate) 相当于 <=的效果
     *
     * @param dateStr      - 日期
     * @param parseDateStr - 被比较的日期
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/7 14:12
     */
    public static boolean dateCompare(String dateStr, String parseDateStr) throws LaiKeApiException {
        try {
            FastDateFormat date = FastDateFormat.getInstance(GloabConst.TimePattern.YMDHMS);
            try {
                return date.parse(dateStr).after(date.parse(parseDateStr));
            } catch (ParseException parseException) {
                date = FastDateFormat.getInstance(GloabConst.TimePattern.YMD);
                return date.parse(dateStr).after(date.parse(parseDateStr));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("日期比较 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "日期比较失败", "dateCompare");
        }
    }

    /**
     * 日期比较
     * date>parseDate
     * (!date>parseDate) 相当于 <=的效果
     *
     * @param dateStr      - 日期
     * @param parseDateStr - 被比较的日期
     * @param dateFormate  - 字符格式
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/7 14:12
     */
    public static boolean dateCompare(String dateStr, String parseDateStr, String dateFormate) throws LaiKeApiException {
        try {
            FastDateFormat date = FastDateFormat.getInstance(dateFormate);
            return date.parse(dateStr).after(date.parse(parseDateStr));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("日期比较 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "日期比较失败", "dateCompare");
        }
    }

    /**
     * 字符串时间格式化
     *
     * @param dateStr -
     * @param format  -
     * @return String
     * @author Trick
     * @date 2020/11/25 14:01
     */
    public static String dateFormate(String dateStr, String format) {
        try {
            if (StringUtils.isNotEmpty(dateStr)) {
                if(dateStr.indexOf("T")!=-1){
                    dateStr = dateStr.replaceAll("T"," ");
                }
                int pos = dateStr.indexOf(":");
                if(dateStr.indexOf(":",pos+1) == -1){
                    dateStr = dateStr.concat(":00");
                }
                Date date = dateFormateToDate(dateStr, GloabConst.TimePattern.YMDHMS);
                return dateFormate(date, format);
            }
            return dateStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字符串时间格式化
     *
     * @param dateStr    -
     * @param format     - 最终返回的格式
     * @param dateFormat - 转换中途的格式
     * @return String
     * @author Trick
     * @date 2020/11/25 14:01
     */
    public static String dateFormate(String dateStr, String format, String dateFormat) {
        try {
            FastDateFormat fdf = FastDateFormat.getInstance(format);
            if(dateStr.indexOf("T")!=-1){
                dateStr = dateStr.replaceAll("T"," ");
            }
            int pos = dateStr.indexOf(":");
            if(dateStr.indexOf(":",pos+1) == -1){
                dateStr = dateStr.concat(":00");
            }
            return fdf.format(dateFormateToDate(dateStr, dateFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间时间格式化
     *
     * @param date   -
     * @param format -
     * @return java.lang.String
     * @author Trick
     * @date 2020/12/4 16:57
     */
    public static String dateFormate(Date date, String format) {
        try {
            if (date != null) {
                FastDateFormat fdf = FastDateFormat.getInstance(format);
                return fdf.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字符串时间格式化
     *
     * @param dateStr -
     * @param format  -
     * @return Date
     * @author Trick
     * @date 2020/11/25 14:04
     */
    public static Date dateFormateToDate(String dateStr, String format) {
        try {
            if (!StringUtils.isEmpty(dateStr)) {
                if(dateStr.indexOf("T")!=-1){
                    dateStr = dateStr.replaceAll("T"," ");
                }
                int pos = dateStr.indexOf(":");
                if(dateStr.indexOf(":",pos+1) == -1){
                    dateStr = dateStr.concat(":00");
                }
                FastDateFormat fdf = FastDateFormat.getInstance(format);
                return fdf.parse(dateStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期格式化
     *
     * @param date   -
     * @param format -
     * @return Date
     * @author Trick
     * @date 2020/11/25 14:04
     */
    public static Date dateFormateToDate(Date date, String format) {
        try {
            if (date != null) {
                FastDateFormat fdf = FastDateFormat.getInstance(format);
                return dateFormateToDate(fdf.format(date), format);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获得当天最小时间
     * 传昨天的日期则是昨天的,以此类推
     *
     * @param date -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/30 16:09
     */
    public static String getStartOfDay(Date date) throws LaiKeApiException {
        try {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
                    ZoneId.systemDefault());
            LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
            Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
            return FastDateFormat.getInstance(GloabConst.TimePattern.YMDHMS).format(startDate);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获得当天最小时间异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获得当天最小时间失败", "getStartOfDay");
        }
    }

    /**
     * 获得当天最大时间
     * 传昨天的日期则是昨天的,以此类推
     *
     * @param date -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/30 16:09
     */
    public static String getEndOfDay(Date date) throws LaiKeApiException {
        try {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
                    ZoneId.systemDefault());
            LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
            Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
            return FastDateFormat.getInstance(GloabConst.TimePattern.YMDHMS).format(endDate);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获得当天最小时间异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获得当天最大时间出错", "getEndOfDay");
        }
    }


    /**
     * 增减日期
     *
     * @param addDay -
     * @return Date
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/24 15:38
     */
    public static Date getAddDate(int addDay) throws LaiKeApiException {
        return getAddDate(new Date(), addDay);
    }

    /**
     * 增减日期
     *
     * @param addDay -
     * @return Date
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/24 15:38
     */
    public static Date getAddDate(Date time, int addDay) throws LaiKeApiException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.DAY_OF_MONTH, addDay);
            return calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("增加日期异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "增减日期失败", "getAddDate");
        }
    }

    /**
     * 日期 增/减秒数
     *
     * @param time      - 需要增加/减少的时间
     * @param addSecond - 秒
     * @return Date
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/24 15:38
     */
    public static Date getAddDateBySecond(Date time, int addSecond) throws LaiKeApiException {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            calendar.add(Calendar.SECOND, addSecond);
            return calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("增加日期秒数异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "增加日期秒数失败", "getAddDateBySecond");
        }
    }

    /**
     * 获得当月第一天
     *
     * @param date -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/30 16:09
     */
    public static String getMonthFirstDay(Date date) throws LaiKeApiException {
        try {
            Calendar cale = Calendar.getInstance();
            cale.setTime(date);
            cale.set(Calendar.DAY_OF_MONTH, cale.getActualMinimum(Calendar.DAY_OF_MONTH));
            String firstMonth = FastDateFormat.getInstance(GloabConst.TimePattern.YMD).format(cale.getTime());
            String endDay = "00:00:00";
            return firstMonth + " " + endDay;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获得当月最后一天异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获得当月最后一天出错", "getFirstMonthDay");
        }
    }

    /**
     * 获得当月最后一天
     *
     * @param date -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/30 16:09
     */
    public static String getMonthLastDay(Date date) throws LaiKeApiException {
        try {
            Calendar cale = Calendar.getInstance();
            cale.setTime(date);
            cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
            String firstMonth = FastDateFormat.getInstance(GloabConst.TimePattern.YMD).format(cale.getTime());
            String endDay = "23:59:59";
            return firstMonth + " " + endDay;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获得当月最后一天异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获得当月最后一天出错", "getFirstMonthDay");
        }
    }

    /**
     * 日期按月增加
     *
     * @param date -
     * @return Date
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/30 16:09
     */
    public static Date getAddMonth(Date date, int monthNum) throws LaiKeApiException {
        try {
            Calendar cale = Calendar.getInstance();
            cale.setTime(date);
            cale.add(Calendar.MONTH, monthNum);
            return cale.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("日期按月增加 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "日期处理失败", "getAddMonth");
        }
    }

    /**
     * 获取两个时间戳之间相差的几天
     *
     * @param time1 - 年月日 单位秒
     * @param time2 - 年月日 单位秒
     * @return int - 只算整数，多余的舍去
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/4 11:54
     */
    public static int getBetweenDate(long time1, long time2) throws LaiKeApiException {
        try {
            return getBetweenDate(time1, time2, false);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取两个时间戳之间相差的几天异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getBetweenDate");
        }
    }


    /**
     * 获取两个时间戳之间相差的几天
     *
     * @param time1 - 年月日 单位秒
     * @param time2 - 年月日 单位秒
     * @param isOut - 是否不足一天按一天计算
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/4 11:54
     */
    public static int getBetweenDate(long time1, long time2, boolean isOut) throws LaiKeApiException {
        try {
            long resultDay = (time1 - time2) / 86400;
            if (isOut) {
                return Math.abs(new BigDecimal(resultDay + "").setScale(0, BigDecimal.ROUND_UP).intValue());
            } else {
                return Math.abs(new BigDecimal(resultDay + "").intValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取两个时间戳之间相差的几天异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getBetweenDate");
        }
    }

    /**
     * 获取 年月周日 开始、结束时间
     *
     * @param type    - 1=今天 2= 本周 3=本月 4=本年
     * @param isStart - 是否获取开始时间,否则获取最后的时间
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/18 10:41
     */
    public static String getSpanDate(int type, boolean isStart) throws LaiKeApiException {
        try {
            Date resultDate = new Date();
            Calendar cal = Calendar.getInstance();
            switch (type) {
                case 1:
                    break;
                case 2:
                    if (isStart) {
                        cal.add(Calendar.WEEK_OF_MONTH, 0);
                        cal.set(Calendar.DAY_OF_WEEK, 2);
                    } else {
                        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    resultDate = cal.getTime();
                    break;
                case 3:
                    if (isStart) {
                        cal.add(Calendar.MONTH, 0);
                        cal.set(Calendar.DAY_OF_MONTH, 1);
                    } else {
                        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                    }
                    resultDate = cal.getTime();
                    break;
                case 4:
                    if (isStart) {
                        return new SimpleDateFormat("yyyy").format(new Date()) + "-01-01";
                    } else {
                        cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
                        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        resultDate = cal.getTime();
                    }
                    break;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            if (isStart) {
                return getStartOfDay(resultDate);
            } else {
                return getEndOfDay(resultDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取时间跨度 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSpanDate");
        }
    }


    /**
     * 时间单位枚举
     */
    public enum TimeType {
        /**
         * 年 月 日 时 分 秒
         */
        YEAR, MONTH, DAY, TIME, MINUTE, SECOND;
    }

    /**
     * 将秒转换成任意类型
     *
     * @param seconds - 秒
     * @return BigDecimal
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/10/20 11:54
     */
    public static BigDecimal dateConversionType(int seconds, TimeType type) throws LaiKeApiException {
        try {
            BigDecimal value;
            BigDecimal secondsTime = new BigDecimal(seconds);

            switch (type) {
                case DAY:
                    value = secondsTime.divide(new BigDecimal("24").multiply(new BigDecimal("3600")), 2, BigDecimal.ROUND_HALF_UP);
                    break;
                case TIME:
                    value = secondsTime.divide(new BigDecimal("3600"), 2, BigDecimal.ROUND_HALF_UP);
                    break;
                case MINUTE:
                    value = secondsTime.divide(new BigDecimal("60"), 2, BigDecimal.ROUND_HALF_UP);
                    break;
                case SECOND:
                    return secondsTime;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, String.format("暂不支持【%s】的转换", type));
            }
            return value;
        } catch (Exception e) {
            logger.error("时间换算 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "dateConversion");
        }
    }

    /**
     * 时间差换算
     *
     * @param time - 两个时间差的时间戳/单位秒
     * @param type -
     * @return long
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/22 17:18
     */
    public static long dateConversion(long time, TimeType type) throws LaiKeApiException {
        try {
            long value;

            switch (type) {
                case DAY:
                    value = time / (24 * 3600);
                    break;
                case TIME:
                    value = time % (24 * 3600) / 3600;
                    break;
                case MINUTE:
                    value = time % 3600 / 60;
                    break;
                case SECOND:
                    value = time % 60;
                    break;
                default:
                    return 0;
            }
            return value;
        } catch (Exception e) {
            logger.error("时间换算 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "dateConversion");
        }
    }


    public static BigDecimal dateConversion(BigDecimal time, TimeType type) throws LaiKeApiException {
        try {
            BigDecimal value;

            switch (type) {
                case DAY:
                    value = time.multiply(new BigDecimal("24")).multiply(new BigDecimal("60")).multiply(new BigDecimal("60"));
                    break;
                case TIME:
                    value = time.multiply(new BigDecimal("60")).multiply(new BigDecimal("60"));
                    break;
                case MINUTE:
                    value = time.multiply(new BigDecimal("60"));
                    break;
                case SECOND:
                    value = time;
                    break;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, String.format("暂不支持【%s】的转换", type));
            }
            return value;
        } catch (Exception e) {
            logger.error("时间换算 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "dateConversion");
        }
    }

    /**
     * 时间换算-秒
     *
     * @param num  -
     * @param type -
     * @return int -秒
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/6 15:15
     */
    public static int dateConversion(int num, TimeType type) throws LaiKeApiException {
        try {
            int value;

            switch (type) {
                case DAY:
                    value = num * 24 * 60 * 60;
                    break;
                case TIME:
                    value = num * 60 * 60;
                    break;
                case MINUTE:
                    value = num * 60;
                    break;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, String.format("暂不支持【%s】的转换", type));
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("时间换算 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "dateConversion");
        }
    }

    /**
     * 计算两个时间差多少 /单位
     *
     * @param time           - 精确到秒的时间戳
     * @param differenceTime - 差数
     * @param type           -
     * @return long - 返回相差秒
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/2 12:32
     */
    public static long dateConversion(long time, long differenceTime, TimeType type) throws LaiKeApiException {
        try {
            long value;

            long s = time - differenceTime;
            long day = s / (24 * 60 * 60);
            long hour = (s / (60 * 60) - day * 24);
            long min = ((s / (60)) - day * 24 * 60 - hour * 60);

            switch (type) {
                case DAY:
                    value = day;
                    break;
                case TIME:
                    value = hour;
                    break;
                case MINUTE:
                    value = min;
                    break;
                case SECOND:
                    value = s;
                    break;
                default:
                    return 0;
            }
            return value;
        } catch (Exception e) {
            logger.error("时间换算-计算两个时间差 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "dateConversion");
        }
    }

    /**
     * 计算两个时间相差的月份，不足一月按照一月计算
     *
     * @param gradeEndList - 拆分过后的开始时间 年月日
     * @param nowDateList  - 拆分过后的结束时间 年月日
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/16 11:50
     */
    public static int calculationMonthNum(String[] gradeEndList, String[] nowDateList) throws LaiKeApiException {
        int num;
        try {
            //根据要升级的等级计算费用
            if (Integer.parseInt(nowDateList[1]) < Integer.parseInt(gradeEndList[1])) {
                //当前月份小于过期月份
                num = Math.abs(Integer.parseInt(nowDateList[0]) - Integer.parseInt(gradeEndList[0])) * 12 +
                        Math.abs(Integer.parseInt(nowDateList[1]) - Integer.parseInt(gradeEndList[1]));
            } else {
                num = Math.abs(Integer.parseInt(nowDateList[0]) - Integer.parseInt(gradeEndList[0])) * 12 -
                        Math.abs(Integer.parseInt(nowDateList[1]) - Integer.parseInt(gradeEndList[1]));
            }
            if (num == 0) {
                //升级本月,至少一个月
                num = 1;
            } else {
                //不足一月按一月计算
                int day = Integer.parseInt(gradeEndList[2]) - Integer.parseInt(nowDateList[2]);
                if (day > 0) {
                    num += 1;
                }
            }
            return num;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("计算两个时间相差的月份，不足一月按照一月计算 异常 " + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "calculationMonthNum");
        }
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return String
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        return String.valueOf(time / 1000);
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return long
     */
    public static long getTime() {
        long time = System.currentTimeMillis();
        return time / 1000;
    }
}
