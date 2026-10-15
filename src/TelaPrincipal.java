
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private JPanel painel;
    private JLabel labelNome;
    private JTextField campoNome;
    private JButton botaoCadastrar;

    public TelaPrincipal() {
        setTitle("Sistema de Projetos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel = new JPanel();

        labelNome = new JLabel("Nome:");
        campoNome = new JTextField(30);
        botaoCadastrar = new JButton("Cadastrar");

        painel.add(labelNome);
        painel.add(campoNome);
        painel.add(botaoCadastrar);

        add(painel);
        
        botaoCadastrar.addActionListener(e -> {
            String nome = campoNome.getText();

            JOptionPane.showMessageDialog(
                this,
                "Projeto: " + nome
            );
        });

    }

    public static void main(String[] args) {
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }
}
