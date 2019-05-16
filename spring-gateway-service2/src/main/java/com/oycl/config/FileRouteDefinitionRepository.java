package com.oycl.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.oycl.filiter.JwtGatewayFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.synchronizedMap;


/**
 * 从自定义json文件读取路径配置
 */
@Component
public class FileRouteDefinitionRepository implements RouteDefinitionRepository {

    @Value("${route.routeFile}")
    private String routeFile;

    private final Map<String, RouteDefinition> routes = synchronizedMap(new LinkedHashMap<String, RouteDefinition>());

    @Autowired
    private Gson gson;

    private static final Logger logger = LoggerFactory.getLogger(FileRouteDefinitionRepository.class);

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        logger.debug("调用路由FileRouteDefinitionRepository");
        return Flux.fromIterable(routes.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap( r -> {
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routes.containsKey(id)) {
                routes.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: "+routeId)));
        });
    }

    /**
     * 启动时从文件加载route
     */
    @PostConstruct
    public void initRoute() {
        logger.debug("设置路由FileRouteDefinitionRepository");
        List<RouteDefinition> routeDefinitions = null;
        try {
            final JsonReader jsonReader = new JsonReader(new FileReader(routeFile));
            final Type type = new TypeToken<List<RouteDefinition>>() {}.getType();
            routeDefinitions = gson.fromJson(jsonReader, type);
            if (routeDefinitions.size() > 0) {
                routeDefinitions.forEach(item -> routes.put(item.getId(), item));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.debug("文件不存在，加载自定义路径失败。");
        }

    }
}
