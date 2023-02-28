package com.laiketui.root.utils.okhttp;

import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 请求外部api
 *
 * @author Trick
 * @date 2020/11/28 11:29
 */
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String url) {
        String responseBody = "";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = builderHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 get 错误 >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    public static String post(String url) {
        return post(url, "{}");
    }

    public static String post(String url, Map<String, String[]> queries) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (queries != null && queries.keySet().size() > 0) {
            for (String key : queries.keySet()) {
                String[] vals = queries.get(key);
                if (vals != null && vals.length > 0) {
                    for (String val : vals) {
                        builder.add(key, val);
                    }
                }
            }
        }

        System.out.println(builder.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = builderHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 post 错误 >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    public static String post(String url, String json) {
        String responseBody = "";
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, String.valueOf(json));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = builderHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error("okhttp3 post json 错误 >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    public static InputStream getFile(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            OkHttpClient okHttpClient = builderHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().byteStream();
            }
        } catch (Exception e) {
            logger.error("okhttp3 getFile downloadFile 错误 >> ex = {}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    public static InputStream postFile(String url, String json) {
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, String.valueOf(json));

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response;
        try {
            OkHttpClient okHttpClient = builderHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().byteStream();
            }
        } catch (Exception e) {
            logger.error("okhttp3 postFile downloadFile 错误 >> ex = {}", ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    private static OkHttpClient builderHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    private static ConnectionPool pool() {
        return new ConnectionPool(200, 5, TimeUnit.MINUTES);
    }

}
