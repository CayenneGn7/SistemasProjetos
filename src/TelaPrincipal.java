
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Projeto;
import service.ProjetoService;

public class TelaPrincipal extends JFrame {

    private JPanel painel;
    private JLabel labelNome;
    private JTextField campoNome;
    private JButton botaoCadastrar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private ProjetoService service;

    public TelaPrincipal() {
        setTitle("Sistema de Projetos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        service = new ProjetoService();
        service.carregar();

        painel = new JPanel();

        labelNome = new JLabel("Nome:");
        campoNome = new JTextField(20);
        botaoCadastrar = new JButton("Cadastrar");

        painel.add(labelNome);
        painel.add(campoNome);
        painel.add(botaoCadastrar);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Categoria");
        modelo.addColumn("Status");

        tabela = new JTable(modelo);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(painel);
        add(new JScrollPane(tabela));

        botaoCadastrar.addActionListener(e -> cadastrar());

        carregarTabela();
    }

    private void cadastrar() {
        String nome = campoNome.getText();

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
            id, nome, "", "Outro", "Planejado"
        );

        if (service.adicionar(projeto)) {
            service.salvar();
            carregarTabela();

            JOptionPane.showMessageDialog(
                this,
                "Projeto cadastrado com sucesso!"
            );

            campoNome.setText("");
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível cadastrar. Verifique o nome e o ID."
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

