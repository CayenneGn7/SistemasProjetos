
package service;

import java.util.ArrayList;
import java.util.List;
import model.Projeto;

public class ProjetoService {

    private List<Projeto> projetos;

    public ProjetoService() {
        projetos = new ArrayList<>();
    }

    public void adicionar(Projeto projeto) {
        if (projeto.getNome() == null || projeto.getNome().trim().isEmpty()) {
            System.out.println("O nome do projeto não pode ser vazio.");
            return;
        }

        if (buscarPorId(projeto.getId()) != null) {
            System.out.println("Já existe um projeto com esse ID.");
            return;
        }

        projetos.add(projeto);
    }

    public List<Projeto> listar() {
        return projetos;
    }

    public Projeto buscarPorId(int id) {
        for (Projeto projeto : projetos) {
            if (projeto.getId() == id) {
                return projeto;
            }
        }
        return null;
    }

    public List<Projeto> buscarPorCategoria(String categoria) {
        List<Projeto> resultado = new ArrayList<>();

        for (Projeto projeto : projetos) {
            if (projeto.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(projeto);
            }
        }

        return resultado;
    }

    public List<Projeto> buscarPorStatus(String status) {
        List<Projeto> resultado = new ArrayList<>();

        for (Projeto projeto : projetos) {
            if (projeto.getStatus().equalsIgnoreCase(status)) {
                resultado.add(projeto);
            }
        }

        return resultado;
    }

    public boolean removerPorId(int id) {
        Projeto projeto = buscarPorId(id);

        if (projeto != null) {
            projetos.remove(projeto);
            return true;
        }

        return false;
    }
}
