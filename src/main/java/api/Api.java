
package api;

import io.javalin.Javalin;

public class Api {

    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.routes.get("/", ctx -> {
                ctx.result("API Sistema de Projetos");
            });
        }).start(7071);
    }
}
