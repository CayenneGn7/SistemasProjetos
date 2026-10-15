
import javax.swing.JFrame;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Projetos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }
}
