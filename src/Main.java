
import java.util.List;
import java.util.Scanner;
import model.Projeto;
import service.ProjetoService;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProjetoService service = new ProjetoService();

        service.carregar();

        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE PROJETOS ===");
            System.out.println("1 - Cadastrar projeto");
            System.out.println("2 - Listar projetos");
            System.out.println("3 - Buscar projeto por ID");
            System.out.println("4 - Alterar projeto");
            System.out.println("5 - Remover projeto");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Digite um número válido.");
                scanner.next();
                System.out.print("Escolha uma opção: ");
            }

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();

                    System.out.print("Status: ");
                    String status = scanner.nextLine();

                    Projeto novoProjeto = new Projeto(
                        id, nome, descricao, categoria, status
                    );

                    if (service.adicionar(novoProjeto)) {
                        service.salvar();
                        System.out.println("Projeto cadastrado com sucesso!");
                    } else {
                        System.out.println(
                            "Não foi possível cadastrar. Verifique o nome e o ID."
                        );
                    }
                    break;

                case 2:
                    List<Projeto> projetos = service.listar();

                    if (projetos.isEmpty()) {
                        System.out.println("Nenhum projeto cadastrado.");
                    } else {
                        for (Projeto projeto : projetos) {
                            System.out.println();
                            projeto.exibirDados();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o ID do projeto: ");
                    int idBusca = scanner.nextInt();
                    scanner.nextLine();

                    Projeto encontrado = service.buscarPorId(idBusca);

                    if (encontrado != null) {
                        encontrado.exibirDados();
                    } else {
                        System.out.println("Projeto não encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("ID do projeto que deseja alterar: ");
                    int idAlterar = scanner.nextInt();
                    scanner.nextLine();

                    Projeto existente = service.buscarPorId(idAlterar);

                    if (existente == null) {
                        System.out.println("Projeto não encontrado.");
                        break;
                    }

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();

                    System.out.print("Nova descrição: ");
                    String novaDescricao = scanner.nextLine();

                    System.out.print("Nova categoria: ");
                    String novaCategoria = scanner.nextLine();

                    System.out.print("Novo status: ");
                    String novoStatus = scanner.nextLine();

                    Projeto atualizado = new Projeto(
                        idAlterar, novoNome, novaDescricao,
                        novaCategoria, novoStatus
                    );

                    if (service.alterar(atualizado)) {
                        service.salvar();
                        System.out.println("Projeto alterado com sucesso!");
                    } else {
                        System.out.println("Não foi possível alterar o projeto.");
                    }
                    break;

                case 5:
                    System.out.print("ID do projeto que deseja remover: ");
                    int idRemover = scanner.nextInt();
                    scanner.nextLine();

                    if (service.removerPorId(idRemover)) {
                        service.salvar();
                        System.out.println("Projeto removido com sucesso!");
                    } else {
                        System.out.println("Projeto não encontrado.");
                    }
                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}


