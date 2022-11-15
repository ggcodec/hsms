package com.hsms.gateway.filter;

import com.hsms.common.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * @author haotchen
 * @time 2022/11/15-11:16
 */
@Order(0) // 指定优先级,值越小越优先 可以是负数
@Repository
public class CheckFilter implements GlobalFilter {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
        HttpCookie authToken = cookies.getFirst("authToken");
        if (Objects.isNull(authToken) || StringUtils.isEmpty(authToken)) {
            String requestPath = exchange.getRequest().getPath().toString();
            boolean b = checkLoginPath(requestPath);
            if (b) {
                return chain.filter(exchange);
            }
        } else {
            ValueOperations ops = redisTemplate.opsForValue();
            String token = authToken.getValue();

            Jws<Claims> claimsJws = JWTUtil.parseToken(token, "123123");
            String username = (String) claimsJws.getBody().get("username");
            String rt = (String) ops.get(username);
            if (!Objects.isNull(rt) && !StringUtils.isEmpty(rt)){
                return chain.filter(exchange);
            }

        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    public boolean checkLoginPath(String path){
        String[] paths = {
                "/api/v1/auth/loginUser",
                "/api/v1/auth/loginManager"
        };
        for (String p : paths) {
            if (p.equals(path)) {
                return true;
            }
        }
        return false;
    }
}