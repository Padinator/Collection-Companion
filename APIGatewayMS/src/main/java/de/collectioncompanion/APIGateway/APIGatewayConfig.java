package de.collectioncompanion.APIGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER;

@Configuration
public class APIGatewayConfig {
    @Autowired
    private Environment environment;
    public static final int port = 8080;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, UserURLResolver userURLResolver) {
        String DATABASE_MS = "http://" + environment.getProperty("DATABASE_MS") + ":" + port;
        String RESULTS_MS = "http://" + environment.getProperty("RESULTS_MS") + ":" + port;
        String TASKS_MS = "http://" + environment.getProperty("TASKS_MS") + ":" + port;
        String STATISTICS_MS = "http://" + environment.getProperty("STATISTICS_MS") + ":" + port;

        System.out.println("DATABASE_MS: " + DATABASE_MS);
        System.out.println("RESULTS_MS: " + RESULTS_MS);
        System.out.println("TASKS_MS: " + TASKS_MS);
        System.out.println("STATISTICS_MS: " + STATISTICS_MS);

        return builder.routes() // Iterate over all routes
                .route(r -> r.path("/collection/**") // Only routes starting with "/get/" will get through
                        .filters(f -> f.filter(userURLResolver, ROUTE_TO_URL_FILTER_ORDER + 1)) // Filter URLs and sort
                        .uri(DATABASE_MS))
                .build();
    }
}

@Component
class UserURLResolver implements GatewayFilter {
    @Autowired
    private Environment environment;

    public static final int port = 8080;

    /*
     * Will be called each time loading a site
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String starting = exchange.getRequest().getPath().toString();
        final String DATABASE_MS = "http://" + environment.getProperty("DATABASE_MS") + ":" + port + starting;
        final String RESULTS_MS = "http://" + environment.getProperty("RESULTS_MS") + ":" + port + starting;
        final String TASKS_MS = "http://" + environment.getProperty("TASKS_MS") + ":" + port + starting;
        final String STATISTICS_MS = "http://" + environment.getProperty("STATISTICS_MS") + ":" + port + starting;
        String params = "?"; // parameters of the query

        // Convert parameters into one variable
        for (Map.Entry<String, String> e : exchange.getRequest().getQueryParams().toSingleValueMap().entrySet())
            params += e.getKey() + "=" + e.getValue() + "&";
        params = params.substring(0, params.length() - 1);
        System.out.println(params);

        if (params.equals("?")) // If no params were used, remove "?"
            params = "";

        // Forward request to DB micro service
        try {
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, new URI(DATABASE_MS + params));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return chain.filter(exchange);
    }
}