package api;
import java.util.List;

import io.javalin.Javalin;
import model.Projeto;
import service.ProjetoService;

public class Api {

    public static void main(String[] args) {

        ProjetoService service = new ProjetoService();

        var app = Javalin.create(config -> {config.staticFiles.add("/public");

            config.routes.get("/", ctx -> {
                ctx.result("API Sistema de Projetos");
            });

            config.routes.get("/api/projetos", ctx -> {
                List<Projeto> projetos = service.listar();
                ctx.json(projetos);
            });

            config.routes.get("/api/projetos/{id}", ctx -> {
                int id;

                try {
                    id = Integer.parseInt(ctx.pathParam("id"));
                } catch (NumberFormatException e) {
                    ctx.status(400);
                    ctx.json(new ErrorResponse(
                        "ID inválido. Informe um número inteiro."
                    ));
                    return;
                }

                Projeto projeto = service.buscarPorId(id);

                if (projeto == null) {
                    ctx.status(404);
                    ctx.json(new ErrorResponse("Projeto não encontrado"));
                    return;
                }

                ctx.json(projeto);
            });

            config.routes.post("/api/projetos", ctx -> {
                Projeto projeto = ctx.bodyAsClass(Projeto.class);

                if (!validarProjeto(projeto)) {
                    ctx.status(400);
                    ctx.json(new ErrorResponse(
                        "Nome, categoria e status são obrigatórios."
                    ));
                    return;
                }

                boolean adicionado = service.adicionar(projeto);

                if (!adicionado) {
                    ctx.status(400);
                    ctx.json(new ErrorResponse(
                        "Não foi possível cadastrar o projeto. Verifique o ID."
                    ));
                    return;
                }

                service.salvar();

                ctx.status(201);
                ctx.json(projeto);
            });

            config.routes.put("/api/projetos/{id}", ctx -> {
                int id;

                try {
                    id = Integer.parseInt(ctx.pathParam("id"));
                } catch (NumberFormatException e) {
                    ctx.status(400);
                    ctx.json(new ErrorResponse(
                        "ID inválido. Informe um número inteiro."
                    ));
                    return;
                }

                Projeto projeto = ctx.bodyAsClass(Projeto.class);

                if (!validarProjeto(projeto)) {
                    ctx.status(400);
                    ctx.json(new ErrorResponse(
                        "Nome, categoria e status são obrigatórios."
                    ));
                    return;
                }

                projeto.setId(id);

                boolean alterado = service.alterar(projeto);

                if (!alterado) {
                    ctx.status(404);
                    ctx.json(new ErrorResponse("Projeto não encontrado"));
                    return;
                }

                service.salvar();
                ctx.json(projeto);
            });

            config.routes.delete("/api/projetos/{id}", ctx -> {
                int id;

                try {
                    id = Integer.parseInt(ctx.pathParam("id"));
                } catch (NumberFormatException e) {
                    ctx.status(400);
                    ctx.json(new ErrorResponse(
                        "ID inválido. Informe um número inteiro."
                    ));
                    return;
                }

                Projeto projeto = service.buscarPorId(id);

                if (projeto == null) {
                    ctx.status(404);
                    ctx.json(new ErrorResponse("Projeto não encontrado"));
                    return;
                }

                service.removerPorId(id);
                service.salvar();

                ctx.status(204);
            });

        }).start(7071);
    }

    private static boolean validarProjeto(Projeto projeto) {
        return projeto != null
            && projeto.getNome() != null
            && !projeto.getNome().isBlank()
            && projeto.getCategoria() != null
            && !projeto.getCategoria().isBlank()
            && projeto.getStatus() != null
            && !projeto.getStatus().isBlank();
    }
}