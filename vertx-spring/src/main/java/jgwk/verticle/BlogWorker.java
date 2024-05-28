package jgwk.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import jgwk.document.Post;
import jgwk.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BlogWorker extends AbstractVerticle {
    private final BlogService blogService;

    public static final String POST_GET_ALL = "post.get.all";
    public static final String POST_ADD = "post.add";
    public static final String POST_UPDATE = "post.update";
    public static final String POST_DELETE = "post.delete";

    @Override
    public void start() {
        vertx.eventBus()
            .consumer(BlogWorker.POST_GET_ALL, message -> {
                List<Post> postList = blogService.getAllPosts();
                message.reply(Json.encode(postList));
            });

        vertx.eventBus()
            .consumer(BlogWorker.POST_ADD, message -> {
                JsonObject root = (JsonObject) message.body();
                JsonObject body = root.getJsonObject("body");

                Post post = new Post();
                post.setPostId(System.currentTimeMillis());
                post.setTitle(body.getString("title"));
                post.setContent(body.getString("content"));

                blogService.addPost(post);
                message.reply(Json.encode(post));
            });

        vertx.eventBus()
            .consumer(BlogWorker.POST_UPDATE, message -> {
                JsonObject root = (JsonObject) message.body();
                JsonObject param = root.getJsonObject("param");
                JsonObject body = root.getJsonObject("body");
                Long postId = param.getLong("postId");

                Post post = blogService.getPost(postId);
                post.setTitle(body.getString("title"));
                post.setContent(body.getString("content"));

                blogService.updatePost(post);
                message.reply(Json.encode(post));
            });

        vertx.eventBus()
            .consumer(BlogWorker.POST_DELETE, message -> {
                JsonObject root = (JsonObject) message.body();
                JsonObject param = root.getJsonObject("param");
                Long postId = param.getLong("postId");

                blogService.deletePost(postId);
                message.reply(null);
            });
    }
}
