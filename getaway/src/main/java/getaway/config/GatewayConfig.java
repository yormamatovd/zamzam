package getaway.config;

import getaway.filters.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

   private final AuthFilter filter;

    public GatewayConfig(AuthFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("register", r -> r.path("/register/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8085/"))

                .route("auth", r -> r.path("/auth/login")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8083/"))

                .route("client", r -> r.path("/client/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8084/"))

                .route("info", r -> r.path("/info/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8082/"))

                .route("attachmentMS", r -> r.path("/file/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8081/"))

                .route("systemMs", r -> r.path("/system/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8086/"))

                .route("sellerMs", r -> r.path("/seller/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8087/"))

                .route("productMs", r -> r.path("/product/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8088/"))

                .route("orderMs", r -> r.path("/order/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8089/"))
                .build();
    }

}

