
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private JPanel painel;

    public TelaPrincipal() {
        setTitle("Sistema de Projetos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        painel = new JPanel();

        add(painel);
    }

    public static void main(String[] args) {
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }
}
