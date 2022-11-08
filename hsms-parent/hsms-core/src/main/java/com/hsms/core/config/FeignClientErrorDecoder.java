package com.hsms.core.config;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static feign.FeignException.errorStatus;

/**
 * Fegin的异常拦截器
 *
 * @author haotchen
 * @time 2022/11/8-13:47
 */
@Configuration
@Slf4j
public class FeignClientErrorDecoder
        implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("feign client response:", response);
        String body = null;
        try {
            body = Util.toString(response.body().asReader());
        } catch (IOException e) {
            log.error("feign.IOException", e);
        }
        if (response.status() >= 400 && response.status() <= 500) {
            log.error(
                    "Fegin远程调用失败."
            );
        }
        return errorStatus(methodKey, response);
    }
}


