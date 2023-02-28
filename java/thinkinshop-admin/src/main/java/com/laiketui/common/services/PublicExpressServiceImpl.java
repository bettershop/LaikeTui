package com.laiketui.common.services;

import com.laiketui.api.common.PublicExpressService;
import com.laiketui.common.mapper.ExpressModelMapper;
import com.laiketui.domain.config.ExpressModel;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.consts.GloabConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物流公司信息服务接口
 *return
 * @author wangxian
 */
@Service("publicExpressService")
public class PublicExpressServiceImpl implements PublicExpressService {

    private final Logger logger = LoggerFactory.getLogger(PublicExpressServiceImpl.class);

    @Override
    public List<ExpressModel> getExpressInfo() {
        try{
            List<ExpressModel> cacheExpress = (List<ExpressModel>) redisUtil.get(GloabConst.RedisHeaderKey.EXPRESS_ALL);
            if( cacheExpress != null){
                return cacheExpress;
            }
            return expressModelMapper.selectAll();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取物流公司信息失败：{}",e.getMessage());
        }

        return null;
    }

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ExpressModelMapper expressModelMapper;

}
