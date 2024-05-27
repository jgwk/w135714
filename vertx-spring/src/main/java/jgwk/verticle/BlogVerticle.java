package jgwk.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jgwk.document.Post;
import jgwk.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BlogVerticle extends AbstractVerticle {
    private final BlogService blogService;

//    private static final String POST_GET_ALL = "post.get.all";
//    private static final String POST_ADD = "post.add";
//    private static final String POST_UPDATE = "post.update";
//    private static final String POST_DELETE = "post.delete";

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        // 글 전체 조회
        router
            .get("/api/post")
            .handler(context -> {
                List<Post> posts = blogService.getAllPosts();

                context.json(posts);
            });

        // TODO 이벤트 모델로 변경
        // TODO 다큐먼트가 직접 노출되지 않도록, 매퍼 추가

        // 글 등록
        router
            .post("/api/post")
            .handler(context -> {
                JsonObject postJson = context.getBodyAsJson();

                Post post = new Post();
                post.setPostId(System.currentTimeMillis());
                post.setTitle(postJson.getString("title"));
                post.setContent(postJson.getString("content"));

                blogService.addPost(post);

                context.json(post);
            });

        // 글 수정
        router
            .put("/api/post/:postId")
            .handler(context -> {
                Long postId = Long.valueOf(context.request().getParam("postId"));
                JsonObject postJson = context.getBodyAsJson();

                Post post = blogService.getPost(postId);
                post.setTitle(postJson.getString("title"));
                post.setContent(postJson.getString("content"));

                blogService.updatePost(post);

                context.json(post);
            });

        // 글 삭제
        router
            .delete("/api/post/:postId")
            .handler(context -> {
                Long postId = Long.valueOf(context.request().getParam("postId"));

                blogService.deletePost(postId);
            });

        vertx.createHttpServer()
            .requestHandler(router)
            .listen();
    }

}
