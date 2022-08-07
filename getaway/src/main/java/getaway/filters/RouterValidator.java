package getaway.filters;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {
    public static final List<String> openApiEndpoints= List.of(
            "/register",
            "/register/verify",
            "/auth/login"
    );

    public static final List<String> closeApiEndpoints= List.of(
            "/local/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
    public Predicate<ServerHttpRequest> isClose =
            request -> closeApiEndpoints
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
}
