package com.laiketui.root.utils.common;

/**
 *
 */
public interface LaiKeGlobleConst {

    interface RedisCacheKeyPre{
        String LAIKE_API = "LAIKE_API";
    }

    interface API_RET_CODE{

        /**
         * 错误 10000 开头
         *  系统错误
         *  业务错误
         *  ..
         */
        interface Error{

            String ERROR = "COMMON_10000";
            /**
             * 授权失败
             */
            String AUTH_SUCCESS = "AUTH_10000";
        }

        /**
         * 通用异常 30000
         */
        interface CommonError{

            /**
             * 参数异常码
             */
            String INVALIDATA_PARAMS = "PARAMS_30000";


        }

        /**
         * 异常结果 40000
         */
        interface BizError{

            /**
             * 授权失败
             */
            String AUTH_FAIL = "AUTH_40001";
            /**
             * 授权文件没找到
             */
            String AUTH_FILE_NOTFOUND = "AUTH_40002";

        }

    }

}
