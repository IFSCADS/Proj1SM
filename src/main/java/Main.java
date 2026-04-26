import esd.ListaSequencial;
import sm.Bistek;
import sm.Fort;
import sm.Giassi;
import sm.Produto;

import java.util.regex.Pattern;

public class Main {
    static void main() {

        // cria um acessador para o Fort
        var sm = new Fort();

//        var p = sm.obtem("143235");
//        IO.println(p);

        // procura todos produtos cujo nome contenha "tapioca"
        var res = sm.busca("arroz");

        if (res != null) {
            // itera com api streams
            res.stream()
                    .filter(x -> x.getNome().matches("^[aA]rroz\\s.*"))
                    .forEach(x -> IO.println(x));
            // ... ou com iteração usual
            for (var prod : res) {
                IO.println(prod);
            }
        }
    }
}
