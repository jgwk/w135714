package jgwk.rest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloVerticle.class);

    @Override
    public void start() {
        // curl -v http://localhost:8888
        // curl -v http://localhost:8888?name=olleh

        Router router = Router.router(vertx);

        router.route().handler(context -> {
            String address = context.request().connection().remoteAddress().toString();
            MultiMap queryParams = context.queryParams();
            String name = queryParams.contains("name")
                ? queryParams.get("name")
                : "unknown";

            context.json(
                new JsonObject()
                    .put("name", name)
                    .put("address", address)
                    .put("message", "Hello " + name + " connected from " + address)
            );
        });

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888)
            .onSuccess(server -> LOGGER.info("HTTP server started on port " + server.actualPort()));
    }
}


