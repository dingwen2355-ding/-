package cn.wxgis.jc.gateway.handler;

import cn.wxgis.jc.common.enums.global.GlobalStatusEnum;
import cn.wxgis.jc.common.util.ServletUtils;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理
 */
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GatewayExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex)  {
        ServerHttpResponse response = exchange.getResponse();

        if (exchange.getResponse().isCommitted())  {
            return Mono.error(ex);
        }

        String msg = GlobalStatusEnum.STATUS_ERROR.getMsg();
        String code = GlobalStatusEnum.STATUS_ERROR.getCode();

        if (ex instanceof NotFoundException)  {
            msg = "服务未找到";
        }
        else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            msg = responseStatusException.getMessage();
        } else if (ex instanceof JwtException) {
            code = GlobalStatusEnum.UNAUTHORIZED.getCode();
            msg = GlobalStatusEnum.UNAUTHORIZED.getMsg();
        }
        log.error("[网关异常处理]请求路径:{},异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());

        return ServletUtils.webFluxResponseWriter(response, msg, code);
    }
}
