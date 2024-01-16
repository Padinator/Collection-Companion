package de.collectioncompanion.APIGateway;

import de.collectioncompanion.APIGateway.adapter.outbound.RestServerOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER;

@Configuration
public class APIGatewayConfig /* implements WebMvcConfigurer */ {

    /*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/collection/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
    */

    @Autowired
    private Environment environment;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, UserURLResolver userURLResolver) {
        String DATABASE_MS = "http://" + environment.getProperty("DATABASE_MS");
        // String DATABASE_MS = "http://" + environment.getProperty("DATABASE_MS") + ":"
        // + 8081;
        String RESULTS_MS = "http://" + environment.getProperty("RESULTS_MS");
        String TASKS_MS = "http://" + environment.getProperty("TASKS_MS");
        String STATISTICS_MS = "http://" + environment.getProperty("STATISTICS_MS");

        System.out.println("DATABASE_MS: " + DATABASE_MS);
        System.out.println("RESULTS_MS: " + RESULTS_MS);
        System.out.println("TASKS_MS: " + TASKS_MS);
        System.out.println("STATISTICS_MS: " + STATISTICS_MS);

        return builder.routes() // Iterate over all routes
                .route(r -> r.path("/collection/**") // Only routes starting with "/get/" will get through
                        .filters(f -> f.filter(userURLResolver, ROUTE_TO_URL_FILTER_ORDER + 1)) // Filter URLs and sort
                        .uri(DATABASE_MS + "/collection"))
                .build();
    }
}

@Component
class UserURLResolver implements GatewayFilter {

    /**
     * ID for requesting TASKS-MS and collecting result from RESULTS-MS
     */
    private static long id;

    @Autowired
    private Environment environment;

    @Autowired
    private RestServerOut restServerOut;

    /*
     * Will be called each time loading a site
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String starting = exchange.getRequest().getPath().toString();
        final String DATABASE_MS = "http://" + environment.getProperty("DATABASE_MS") + starting;
        // final String DATABASE_MS = "http://localhost:8081/collection";
        final String TASKS_MS = "http://" + environment.getProperty("TASKS_MS") + starting;
        // final String TASKS_MS = "http://localhost:8082/collection";
        final String RESULTS_MS = "http://" + environment.getProperty("RESULTS_MS") + starting;
        // final String RESULTS_MS = "http://localhost:8083/collection";
        final String STATISTICS_MS = "http://" + environment.getProperty("STATISTICS_MS") + starting;

        Map<String, String> queryParams = exchange.getRequest().getQueryParams().toSingleValueMap();
        StringBuilder params = new StringBuilder("?"); // parameters of the query
        // String category = queryParams.get("category"), searchTerm =
        // queryParams.get("searchTerm");
        // boolean webcrwalerRequest = true; // For gRPC (later)
        ResponseEntity<String> request = null;

        // Convert parameters into one variable
        for (Map.Entry<String, String> e : queryParams.entrySet())
            params.append(e.getKey()).append("=").append(e.getValue()).append("&");

        params = new StringBuilder(params.substring(0, params.length() - 1));
        System.out.println(params);

        if (params.toString().equals("?")) // If no params were used, remove "?"
            params = new StringBuilder();

        // Ask DB-MS to request again
        // Do call: webcrwalerRequest = localDatabaseMS.hasValidCollection(category,
        // searchTerm);
        if (exchange.getRequest().getPath().value().equals("/collection")) {
            request = restServerOut.doGetRequest(DATABASE_MS, params.toString());
            System.out.println("Status code of requesting DB-MS: " + request.getStatusCode());
        }

        // Route request
        try {
            exchange.getResponse().getHeaders().add("Content-Type", "application/json");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "*");
            exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
            System.out.println(exchange.getResponse().getHeaders());

            if (request == null || request.getStatusCode().value() == 200) // Route request to database microservice
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR,
                        new URI(DATABASE_MS + params.toString().replaceAll(" ", "%20")));
            else { // Ask Result-MS for result of call to Task-MS
                   // Extend parameters with ID
                if (params.toString().equals(""))
                    params.append("?id=").append(id);
                else
                    params.append("&id=").append(id);

                // Call Task-MS (later with gRPC)
                System.out.println("URI of requesting Task-MS: " + TASKS_MS + params);
                request = restServerOut.doPostRequest(TASKS_MS, params.toString());
                System.out.println("Status code of requesting Task-MS: " + request.getStatusCode());

                // Get result of requesting collection from Result-MS
                params = new StringBuilder("?id=").append(id++);
                System.out.println("URI of requesting Results-MS: " + RESULTS_MS + params);
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR,
                        new URI(RESULTS_MS + params));
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return chain.filter(exchange);
    }
}
