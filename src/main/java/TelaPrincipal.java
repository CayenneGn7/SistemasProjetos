
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import client.ApiClient;
import model.Projeto;

public class TelaPrincipal extends JFrame {

    private JTextField campoNome;
    private JTextField campoDescricao;
    private JComboBox<String> comboCategoria;
    private JComboBox<String> comboStatus;
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton botaoCadastrar;
    private JButton botaoLimpar;
    private JButton botaoExcluir;
    private JButton botaoAlterar;

    private ApiClient apiClient;
    private int idProjetoEmEdicao = -1;

    public TelaPrincipal() {
        setTitle("Sistema de Projetos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        apiClient = new ApiClient();

        criarComponentes();
        criarEventos();
        carregarTabela();
    }

    private void criarComponentes() {
        campoNome = new JTextField(15);
        campoDescricao = new JTextField(15);

        String[] categorias = {
            "Web", "Software", "Mobile", "Outro"
        };

        String[] status = {
            "Planejado", "Em desenvolvimento", "Concluído"
        };

        comboCategoria = new JComboBox<>(categorias);
        comboStatus = new JComboBox<>(status);

        JPanel painelFormulario = new JPanel();

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Descrição:"));
        painelFormulario.add(campoDescricao);

        painelFormulario.add(new JLabel("Categoria:"));
        painelFormulario.add(comboCategoria);

        painelFormulario.add(new JLabel("Status:"));
        painelFormulario.add(comboStatus);

        botaoCadastrar = new JButton("Cadastrar");
        botaoLimpar = new JButton("Limpar");
        botaoExcluir = new JButton("Excluir selecionado");
        botaoAlterar = new JButton("Alterar");

        painelFormulario.add(botaoCadastrar);
        painelFormulario.add(botaoLimpar);
        painelFormulario.add(botaoExcluir);
        painelFormulario.add(botaoAlterar);

        modelo = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Categoria", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modelo);

        setLayout(new BorderLayout());
        add(painelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    private void criarEventos() {
        botaoCadastrar.addActionListener(e -> cadastrar());
        botaoLimpar.addActionListener(e -> limparFormulario());
        botaoExcluir.addActionListener(e -> excluirSelecionado());
        botaoAlterar.addActionListener(e -> alterarProjeto());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarFormularioSelecionado();
            }
        });
    }

    private void limparFormulario() {
        campoNome.setText("");
        campoDescricao.setText("");
        comboCategoria.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);
        idProjetoEmEdicao = -1;
        tabela.clearSelection();
        campoNome.requestFocus();
    }

    private void cadastrar() {
        String nome = campoNome.getText().trim();
        String descricao = campoDescricao.getText().trim();
        String categoria = (String) comboCategoria.getSelectedItem();
        String status = (String) comboStatus.getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Informe o nome do projeto."
            );
            return;
        }

        String entradaId = JOptionPane.showInputDialog(
            this, "Informe o ID do projeto:"
        );

        if (entradaId == null) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(entradaId.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "O ID deve ser um número inteiro."
            );
            return;
        }

        Projeto projeto = new Projeto(
            id, nome, descricao, categoria, status
        );

        try {
            apiClient.cadastrarProjeto(projeto);
            carregarTabela();
            limparFormulario();

            JOptionPane.showMessageDialog(
                this, "Projeto cadastrado com sucesso!"
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível cadastrar o projeto.\n"
                    + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void atualizarTabela(List<Projeto> projetos) {
        modelo.setRowCount(0);

        for (Projeto projeto : projetos) {
            modelo.addRow(new Object[]{
                projeto.getId(),
                projeto.getNome(),
                projeto.getCategoria(),
                projeto.getStatus()
            });
        }
    }

    private void carregarTabela() {
        new SwingWorker<List<Projeto>, Void>() {

            @Override
            protected List<Projeto> doInBackground() throws Exception {
                return apiClient.listarProjetos();
            }

            @Override
            protected void done() {
                try {
                    atualizarTabela(get());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        TelaPrincipal.this,
                        "Não foi possível carregar os projetos.\n"
                            + ex.getMessage(),
                        "Erro de comunicação com a API",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }.execute();
    }

    private void excluirSelecionado() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(
                this, "Selecione um projeto para excluir."
            );
            return;
        }

        int id = (int) modelo.getValueAt(linhaSelecionada, 0);

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir o projeto selecionado?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            apiClient.excluirProjeto(id);
            carregarTabela();
            limparFormulario();

            JOptionPane.showMessageDialog(
                this, "Projeto excluído com sucesso!"
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível excluir o projeto.\n"
                    + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void carregarFormularioSelecionado() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            return;
        }

        idProjetoEmEdicao =
            (int) modelo.getValueAt(linhaSelecionada, 0);

        try {
            Projeto projeto = apiClient.listarProjetos()
                .stream()
                .filter(p -> p.getId() == idProjetoEmEdicao)
                .findFirst()
                .orElse(null);

            if (projeto != null) {
                campoNome.setText(projeto.getNome());
                campoDescricao.setText(projeto.getDescricao());
                comboCategoria.setSelectedItem(projeto.getCategoria());
                comboStatus.setSelectedItem(projeto.getStatus());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível carregar os dados do projeto.\n"
                    + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void alterarProjeto() {
        if (idProjetoEmEdicao == -1) {
            JOptionPane.showMessageDialog(
                this, "Selecione um projeto para alterar."
            );
            return;
        }

        String nome = campoNome.getText().trim();
        String descricao = campoDescricao.getText().trim();
        String categoria = (String) comboCategoria.getSelectedItem();
        String status = (String) comboStatus.getSelectedItem();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Informe o nome do projeto."
            );
            return;
        }

        Projeto projeto = new Projeto(
            idProjetoEmEdicao,
            nome,
            descricao,
            categoria,
            status
        );

        try {
            apiClient.atualizarProjeto(projeto);
            carregarTabela();
            limparFormulario();

            JOptionPane.showMessageDialog(
                this, "Projeto alterado com sucesso!"
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível alterar o projeto.\n"
                    + ex.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}
