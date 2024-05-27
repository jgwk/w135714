package jgwk;

import io.vertx.core.Vertx;
import jgwk.verticle.BlogVerticle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@SpringBootApplication
public class VertxSpringApplication {
    private final BlogVerticle blogVerticle;

    public static void main(String[] args) {
        SpringApplication.run(VertxSpringApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(blogVerticle);
    }
}
