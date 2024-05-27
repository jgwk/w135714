package jgwk;

import io.vertx.core.Vertx;
import jgwk.rest.HelloVerticle;

public class Boot {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HelloVerticle());
    }
}
