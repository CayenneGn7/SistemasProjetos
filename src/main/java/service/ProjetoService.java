
package service;
import dao.ProjetoCSV;
import java.util.ArrayList;
import java.util.List;
import model.Projeto;

public class ProjetoService {

    private List<Projeto> projetos;

    public ProjetoService() {
        projetos = new ArrayList<>();
    }

public boolean adicionar(Projeto projeto) {
    if (projeto.getNome() == null ||
        projeto.getNome().isBlank()) {
        return false;
    }

    if (buscarPorId(projeto.getId()) != null) {
        return false;
    }

    projetos.add(projeto);
    return true;
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
    public boolean alterar(Projeto projetoAtualizado) {
    Projeto projeto = buscarPorId(projetoAtualizado.getId());

    if (projeto == null) {
        return false;
    }

    projeto.setNome(projetoAtualizado.getNome());
    projeto.setDescricao(projetoAtualizado.getDescricao());
    projeto.setCategoria(projetoAtualizado.getCategoria());
    projeto.setStatus(projetoAtualizado.getStatus());

    return true;
}

    public void carregar() {
        projetos = ProjetoCSV.carregar();
    }

    public void salvar() {
        ProjetoCSV.salvar(projetos);
    }

}
