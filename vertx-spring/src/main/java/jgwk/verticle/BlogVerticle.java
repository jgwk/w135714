package jgwk.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BlogVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        // 글 전체 조회
        router
            .get("/api/post")
            .handler(rc ->
                vertx.eventBus()
                    .request(BlogWorker.POST_GET_ALL, null, defaultHandler(rc))
            );

        // 글 등록
        router
            .post("/api/post")
            .handler(rc -> {
                JsonObject root = new JsonObject();
                JsonObject body = rc.getBodyAsJson();
                root.put("body", body);

                vertx.eventBus()
                    .request(BlogWorker.POST_ADD, root, defaultHandler(rc));
            });

        // 글 수정
        router
            .put("/api/post/:postId")
            .handler(rc -> {
                JsonObject root = new JsonObject();
                JsonObject param = new JsonObject();
                root.put("param", param);

                Long postId = Long.valueOf(rc.request().getParam("postId"));
                param.put("postId", postId);

                JsonObject body = rc.getBodyAsJson();
                root.put("body", body);

                vertx.eventBus()
                    .request(BlogWorker.POST_UPDATE, root, defaultHandler(rc));
            });

        // 글 삭제
        router
            .delete("/api/post/:postId")
            .handler(rc -> {
                JsonObject root = new JsonObject();
                JsonObject param = new JsonObject();
                root.put("param", param);

                Long postId = Long.valueOf(rc.request().getParam("postId"));
                param.put("postId", postId);

                vertx.eventBus()
                    .request(BlogWorker.POST_DELETE, root, defaultHandler(rc));
            });

        vertx.createHttpServer()
            .requestHandler(router)
            .listen();
    }

    private Handler<AsyncResult<Message<String>>> defaultHandler(RoutingContext rc) {
        return message -> {
            if (message.succeeded()) {
                if (message.result().body() != null) {
                    rc.response().end(message.result().body());
                }
                else {
                    rc.response().end();
                }
            }
            else {
                rc.response().setStatusCode(500).end();
            }
        };
    }
}
