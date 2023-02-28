package com.laiketui.root.utils.weixin;


import com.alibaba.fastjson.JSONObject;
import com.laiketui.root.consts.GloabConst;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.spec.AlgorithmParameterSpec;

public final class AuthUtil {
    public static JSONObject getOpenIdAndToken(String appId, String appSecret, String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(GloabConst.WeiXinUrl.SESSION_KEY_GET_URL, appId, appSecret, code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String body = responseEntity.getBody();
        // 返回结果转换为json对象
        JSONObject jObject = JSONObject.parseObject(body);
        return jObject;
    }

    public static JSONObject getOpenId(String appId, String appSecret, String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code
                + "&grant_type=authorization_code";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String body = responseEntity.getBody();
        // 返回结果转换为json对象
        JSONObject jObject = JSONObject.parseObject(body);
        return jObject;
    }

    public static JSONObject getUserInfo(String accessToken, String openid) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid + "&lang=zh_CN";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String body = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
        // 返回结果转换为json对象
        JSONObject jObject = JSONObject.parseObject(body);
        return jObject;
    }

    public static JSONObject doGetJson(String URL) throws IOException {
        JSONObject jsonObject = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            //创建远程url连接对象
            java.net.URL url = new URL(URL);
            //通过远程url连接对象打开一个连接，强转成HTTPURLConnection类
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("Accept", "application/json");
            //发送请求
            conn.connect();
            //通过conn取得输入流，并使用Reader读取
            if (200 == conn.getResponseCode()) {
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                    System.out.println(line);
                }
            } else {
                System.out.println("ResponseCode is an error code:" + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        jsonObject = JSONObject.parseObject(result.toString());
        return jsonObject;
    }

    /**
     * AES解密
     *
     * @param data //密文，被加密的数据
     * @param key  //秘钥
     * @param iv   //偏移量
     * @return
     * @throws Exception
     */
    public static String decrypt1(String data, String key, String iv) {

        String decrypt = null;
        try {
            String decode = URLDecoder.decode(data, "utf-8");
            String decode1 = URLDecoder.decode(key, "utf-8");
            String decode2 = URLDecoder.decode(iv, "utf-8");
            byte[] encrypData = Base64.decodeBase64(decode);
            byte[] ivData = Base64.decodeBase64(decode2);
            byte[] sKey = Base64.decodeBase64(decode1);
            decrypt = decrypt(sKey, ivData, encrypData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decrypt;
    }

    public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        // 解析解密后的字符串
        return new String(cipher.doFinal(encData), "UTF-8");
    }
}
