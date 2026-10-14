
import model.Projeto;

public class Main {

    public static void main(String[] args) {

        Projeto projeto1 = new Projeto(
            1,
            "Sistema Acadêmico",
            "Sistema para gerenciamento acadêmico",
            "Software",
            "Em desenvolvimento"
        );

        Projeto projeto2 = new Projeto(
            2,
            "Portfolio Pessoal",
            "Site para apresentar projetos e habilidades",
            "Web",
            "Em desenvolvimento"
        );

        Projeto projeto3 = new Projeto(
            3,
            "Sistema de Estoque",
            "Sistema para controle de produtos",
            "Software",
            "Planejado"
        );
        
        projeto1.setStatus("Concluído");
        projeto1.exibirDados();
        System.out.println();

        projeto2.exibirDados();
        System.out.println();

        projeto3.exibirDados();
    }
}

