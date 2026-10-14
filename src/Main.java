
import model.Projeto;
import service.ProjetoService;

public class Main {

    public static void main(String[] args) {

        ProjetoService service = new ProjetoService();

        Projeto p1 = new Projeto(1, "Site pessoal",
                "Portfólio profissional", "Web", "Em andamento");

        Projeto p2 = new Projeto(2, "Sistema de estoque",
                "Controle de produtos", "Java", "Concluído");

        Projeto p3 = new Projeto(3, "Aplicativo Iris",
                "Aplicativo de segurança", "Mobile", "Em andamento");

        service.adicionar(p1);
        service.adicionar(p2);
        service.adicionar(p3);

        // Teste de ID duplicado
        Projeto p4 = new Projeto(1, "Projeto duplicado",
                "Teste", "Web", "Pendente");
        service.adicionar(p4);

        // Teste de nome vazio
        Projeto p5 = new Projeto(4, " ",
                "Teste", "Java", "Pendente");
        service.adicionar(p5);
        
        System.out.println("=== TODOS OS PROJETOS ===");
        for (Projeto projeto : service.listar()) {
            projeto.exibirDados();
            System.out.println();
        }

        System.out.println("=== BUSCAR POR ID ===");
        Projeto encontrado = service.buscarPorId(2);

        if (encontrado != null) {
            encontrado.exibirDados();
        }

        System.out.println("\n=== BUSCAR POR CATEGORIA ===");
        for (Projeto projeto : service.buscarPorCategoria("Web")) {
            projeto.exibirDados();
        }

        System.out.println("\n=== BUSCAR POR STATUS ===");
        for (Projeto projeto : service.buscarPorStatus("Em andamento")) {
            projeto.exibirDados();
        }

        System.out.println("\n=== REMOVER PROJETO ===");
        System.out.println(service.removerPorId(3)
                ? "Projeto removido."
                : "Projeto não encontrado.");

        System.out.println("\n=== LISTA ATUALIZADA ===");
        for (Projeto projeto : service.listar()) {
            projeto.exibirDados();
            System.out.println();
        }
    }
}


