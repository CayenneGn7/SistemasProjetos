
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Projeto;
import service.ProjetoService;

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

    private JPanel painelFormulario;
    private ProjetoService service;

    public TelaPrincipal() {
        setTitle("Sistema de Projetos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        service = new ProjetoService();
        service.carregar();

        criarComponentes();
        criarEventos();
        carregarTabela();
    }

    private void criarComponentes() {
        campoNome = new JTextField(20);
        campoDescricao = new JTextField(20);

        comboCategoria = new JComboBox<>();
        comboCategoria.addItem("Web");
        comboCategoria.addItem("Software");
        comboCategoria.addItem("Mobile");
        comboCategoria.addItem("Outro");

        comboStatus = new JComboBox<>();
        comboStatus.addItem("Planejado");
        comboStatus.addItem("Em desenvolvimento");
        comboStatus.addItem("Concluído");

        botaoCadastrar = new JButton("Cadastrar");
        botaoLimpar = new JButton("Limpar");
        botaoExcluir = new JButton("Excluir selecionado");

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Categoria");
        modelo.addColumn("Status");

        tabela = new JTable(modelo);

        painelFormulario = new JPanel();

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Descrição:"));
        painelFormulario.add(campoDescricao);

        painelFormulario.add(new JLabel("Categoria:"));
        painelFormulario.add(comboCategoria);

        painelFormulario.add(new JLabel("Status:"));
        painelFormulario.add(comboStatus);

        painelFormulario.add(botaoCadastrar);
        painelFormulario.add(botaoLimpar);
        painelFormulario.add(botaoExcluir);

        setLayout(new BoxLayout(
            getContentPane(),
            BoxLayout.Y_AXIS
        ));

        add(painelFormulario);
        add(new JScrollPane(tabela));
    }

    private void criarEventos() {
        botaoLimpar.addActionListener(e -> limparFormulario());
        botaoCadastrar.addActionListener(e -> cadastrar());
        botaoExcluir.addActionListener(e -> excluirSelecionado());
    }

    private void limparFormulario() {
        campoNome.setText("");
        campoDescricao.setText("");

        comboCategoria.setSelectedIndex(0);
        comboStatus.setSelectedIndex(0);

        campoNome.requestFocus();
    }

    private void cadastrar() {
        String nome = campoNome.getText();
        String descricao = campoDescricao.getText();
        String categoria = comboCategoria.getSelectedItem().toString();
        String status = comboStatus.getSelectedItem().toString();

        if (nome.isBlank()) {
            JOptionPane.showMessageDialog(
                this,
                "Informe o nome do projeto."
            );
            return;
        }

        String entradaId = JOptionPane.showInputDialog(
            this,
            "Digite o ID do projeto:"
        );

        if (entradaId == null) {
            return;
        }

        int id;

        try {
            id = Integer.parseInt(entradaId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Digite um ID numérico válido."
            );
            return;
        }

        Projeto projeto = new Projeto(
            id,
            nome,
            descricao,
            categoria,
            status
        );

        if (service.adicionar(projeto)) {
            service.salvar();
            carregarTabela();
            limparFormulario();

            JOptionPane.showMessageDialog(
                this,
                "Projeto cadastrado com sucesso!"
            );
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível cadastrar. Verifique o nome e o ID."
            );
        }
    }

    private void excluirSelecionado() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Selecione um projeto na tabela."
            );
            return;
        }

        int id = (int) modelo.getValueAt(linhaSelecionada, 0);

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja excluir o projeto selecionado?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        if (service.removerPorId(id)) {
            service.salvar();
            carregarTabela();

            JOptionPane.showMessageDialog(
                this,
                "Projeto excluído com sucesso!"
            );
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível excluir o projeto."
            );
        }
    }

    private void carregarTabela() {
        modelo.setRowCount(0);

        for (Projeto projeto : service.listar()) {
            modelo.addRow(new Object[]{
                projeto.getId(),
                projeto.getNome(),
                projeto.getCategoria(),
                projeto.getStatus()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}

