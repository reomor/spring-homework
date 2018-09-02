package task12.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class RestRoutesConfig {

    @Bean
    RouterFunction<?> routerFunction(RestAuthorRouterHandlers handlers) {
        return RouterFunctions
                .route(RequestPredicates.GET("/rest/authors"), handlers::getAll)
                .andRoute(RequestPredicates.GET("/rest/authors/{id}"), handlers::findById)
                .andRoute(RequestPredicates.POST("/rest/authors"), handlers::save)
                .andRoute(RequestPredicates.PUT("/rest/authors/{id}"), handlers::update)
                .andRoute(RequestPredicates.DELETE("/rest/authors/{id}"), handlers::delete);
    }
}
