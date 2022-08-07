package getaway.filters;

import getaway.model.InfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RefreshScope
@Component
public class AuthFilter implements GatewayFilter {
    private final RouterValidator routerValidator;
    private final RestTemplate restTemplate;

    @Value("${auth-location}")
    private String authUrl;

    public AuthFilter(RouterValidator routerValidator, RestTemplate restTemplate) {
        this.routerValidator = routerValidator;
        this.restTemplate = restTemplate;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isClose.test(request)){
            return this.onError(exchange, HttpStatus.NOT_FOUND);
        }

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request))
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            final String authHeader = this.getAuthHeader(request);
            try {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Authorization", authHeader);
                ResponseEntity<InfoDto> response = restTemplate.exchange(authUrl, HttpMethod.POST, new HttpEntity<>("body", httpHeaders), InfoDto.class);
                exchange.getRequest()
                        .mutate()
                        .header("X-auth-user-id", String.valueOf(Objects.requireNonNull(response.getBody()).getId()))
                        .header("X-auth-user-type", String.valueOf(Objects.requireNonNull(response.getBody()).getType()));
                return chain.filter(exchange);
            } catch (Exception e) {
                e.printStackTrace();
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return chain.filter(exchange);
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

}
