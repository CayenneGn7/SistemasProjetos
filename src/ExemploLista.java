
import java.util.ArrayList;

public class ExemploLista {

    public static void main(String[] args) {

        ArrayList<String> nomes = new ArrayList<>();

        nomes.add("Ana");
        nomes.add("Carlos");
        nomes.add("Mariana");

        System.out.println(nomes);
        System.out.println("Quantidade de nomes: " + nomes.size());
        System.out.println("Primeiro nome: " + nomes.get(0));
        nomes.remove("Carlos");
        System.out.println(nomes);
    }
}
