package com.laiketui.root.utils.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * bean和map互转
 * @author wangxian
 */
public final class BeanUtils {

    /**
     * javabean转map
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Map bean2Map(T t){
        try {
            return  JSONObject.parseObject(JSON.toJSONString(t), Map.class);
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * map转javabean
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T map2Bean(Map map,Class<T> t){
        try {
            return  JSONObject.parseObject(JSON.toJSONString(map),t);
        }catch (Exception e){
            throw e;
        }
    }

    public static void main(String[] args) {

    }


}
