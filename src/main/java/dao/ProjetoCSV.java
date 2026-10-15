
package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Projeto;

public class ProjetoCSV {

    private static final String CAMINHO = "dados/projetos.csv";

    public static List<Projeto> carregar() {
        List<Projeto> projetos = new ArrayList<>();
        File arquivo = new File(CAMINHO);

        if (!arquivo.exists()) {
            return projetos;
        }

        try (BufferedReader leitor = new BufferedReader(
                new FileReader(arquivo))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(";", -1);

                System.out.println("Campos encontrados: " + dados.length);

                if (dados.length == 5) {
                    Projeto projeto = new Projeto(
                        Integer.parseInt(dados[0]),
                        dados[1],
                        dados[2],
                        dados[3],
                        dados[4]
                    );

                    projetos.add(projeto);

                    System.out.println("Projeto carregado: " + projeto.getNome());
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar projetos: " + e.getMessage());
        }

        return projetos;
    }

    public static void salvar(List<Projeto> projetos) {
        File arquivo = new File(CAMINHO);

        try {
            File pasta = arquivo.getParentFile();

            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            try (BufferedWriter escritor = new BufferedWriter(
                    new FileWriter(arquivo))) {

                for (Projeto projeto : projetos) {
                    escritor.write(
                        projeto.getId() + ";" +
                        projeto.getNome() + ";" +
                        projeto.getDescricao() + ";" +
                        projeto.getCategoria() + ";" +
                        projeto.getStatus()
                    );

                    escritor.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao salvar projetos: " + e.getMessage());
        }
    }
}