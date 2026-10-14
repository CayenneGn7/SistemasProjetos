

import model.Projeto;

public class Main {

    public static void main(String[] args) {

        Projeto projeto = new Projeto(
            1,
            "Sistema Acadêmico",
            "Sistema para gerenciamento acadêmico",
            "Software",
            "Em desenvolvimento"
        );

        projeto.exibirDados();
    }
}

