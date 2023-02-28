package com.laiketui.root.utils.jwt;

import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeCommonException;
import com.laiketui.root.utils.tool.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt：令牌帮助类
 */
public final class JwtUtils {

    /**
     * token 过期时间, 单位: 毫秒. 这个值表示 30 天
     */
    public static final long TOKEN_EXPIRED_TIME = 86400L * 1000;

    private static final Map header = new HashMap();

    private static final String TOKEN_SECRET = "hello..laiketui.com";

    static {
        header.put("Type", "Jwt");
        header.put("alg", "HS256");
    }

    /**
     * 兼容php版本的jwttoken生成规则
     *
     * @param
     * @return
     */
    private static String createToken(Map data) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowLong = System.currentTimeMillis();
        // 设置过期时间
        Date expDate = new Date(nowLong + TOKEN_EXPIRED_TIME);
        Date now = new Date(nowLong);
        JwtBuilder builder = Jwts.builder()
                .setHeader(header)
                .setIssuedAt(now)
                .setExpiration(expDate)
                .signWith(signatureAlgorithm, TextCodec.BASE64.encode(TOKEN_SECRET));
        if (!CollectionUtils.isEmpty(data)) {
            builder.setClaims(data);
        }
        return builder.compact();
    }


    /**
     * 获取令牌
     *
     * @return
     */
    public static String getToken() {
        return createToken(null);
    }

    /**
     * 获取令牌带有数据
     *
     * @param data
     * @return
     */
    public static String getTokenWithData(Map data) {
        return createToken(data);
    }


    /**
     * 验证jwt
     */
    public static Claims verifyJwt(String token) throws LaiKeCommonException {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(TextCodec.BASE64.encode(TOKEN_SECRET)).parseClaimsJws(token).getBody();
            long validateTime = MapUtils.getLongValue(claims, "exp");
            if (DateUtil.getTime() > validateTime) {
                throw new LaiKeCommonException(ErrorCode.ContainerErrorCode.JWT_VERIFY_FAIL, "令牌已过期", "verifyJwt");
            }
        } catch (Exception e) {
            throw new LaiKeCommonException(ErrorCode.ContainerErrorCode.JWT_VERIFY_FAIL, "令牌验证出错" + e.getMessage(), "verifyJwt");
        }
        return claims;
    }

//    public static boolean verifyJwt(String token) throws LaiKeCommonException{
//        Claims claims = getClaims(token);
//        if(claims.get("login_time")){
//
//        }
//    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("admin_id", "1");
        map.put("admin_name", "admin");
        map.put("user_id", "user666");
        map.put("store_id", "1");
        map.put("store_type", "pc");
        map.put("mch_id", "80");
        map.put("login_time", DateUtil.dateFormateToDate("2021-11-03 16:23:25", GloabConst.TimePattern.YMDHMS));
        String phptoken = "";
        phptoken = getTokenWithData(map);

        Claims claims = verifyJwt(phptoken);
        long validateTime = MapUtils.getLongValue(claims, "login_time") + TOKEN_EXPIRED_TIME;
        System.out.println(MapUtils.getLongValue(claims, "login_time"));
        if (System.currentTimeMillis() > validateTime) {
            System.out.println("已过期");
        } else {
            System.out.println("未过期");
        }
    }

}
