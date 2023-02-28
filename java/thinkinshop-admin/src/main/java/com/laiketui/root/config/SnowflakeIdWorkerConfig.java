package com.laiketui.root.config;

import com.laiketui.root.utils.common.LKTSnowflakeIdWorker;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/18 15:09
 * @version: 1.0
 * @modified By:
 */
@Configuration
public class SnowflakeIdWorkerConfig {

    private  static final Logger logger = LogManager.getLogger(SnowflakeIdWorkerConfig.class);

    @Value("${id.work:noWorkId}")
    private String workId;

    @Value("${id.dateSource:noDateSource}")
    private String dateSource;

    @Bean
    public LKTSnowflakeIdWorker snowflakeIdWorker() {
        return new LKTSnowflakeIdWorker(getWorkFromConfig(), getDateFromConfig());
    }

    private Long getWorkFromConfig() {
        if ("noWorkId".equals(workId)) {
            return getWorkId();
        } else {
            return Long.valueOf(workId);
        }
    }

    private Long getDateFromConfig() {
        if ("noDateSource".equals(dateSource)) {
            return getDataCenterId();
        } else {
            return Long.valueOf(dateSource);
        }
    }

    private Long getWorkId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] ints = StringUtils.toCodePoints(hostAddress);
            int sums = 0;
            for (int b : ints) {
                sums += b;
            }
            return (long) (sums % 32);
        } catch (UnknownHostException e) {
            return RandomUtils.nextLong(0, 31);
        }
    }

    private Long getDataCenterId() {
        String hostname = SystemUtils.getHostName() ;
        hostname = hostname==null?"localhost":hostname;
        int[] ints = StringUtils.toCodePoints(hostname);
        int sums = 0;
        for (int i : ints) {
            sums += i;
        }
        return (long) (sums % 32);
    }

}
