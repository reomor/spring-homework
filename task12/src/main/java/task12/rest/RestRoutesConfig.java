package task12.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class RestRoutesConfig {

    @Bean
    RouterFunction<?> routerFunction(
            RestAuthorRouterHandlers authorRouterHandlers,
            RestBookRouterHandlers bookRouterHandlers) {
        return RouterFunctions
                .route(RequestPredicates.GET("/rest/authors"), authorRouterHandlers::findAll)
                .andRoute(RequestPredicates.GET("/rest/authors/{id}"), authorRouterHandlers::findById)
                .andRoute(RequestPredicates.POST("/rest/authors"), authorRouterHandlers::save)
                .andRoute(RequestPredicates.PUT("/rest/authors/{id}"), authorRouterHandlers::update)
                .andRoute(RequestPredicates.DELETE("/rest/authors/{id}"), authorRouterHandlers::delete)
                .andRoute(RequestPredicates.GET("/rest/books"), bookRouterHandlers::findAll)
                .andRoute(RequestPredicates.GET("/rest/books/{id}/comments"), bookRouterHandlers::findComments);
    }
}
