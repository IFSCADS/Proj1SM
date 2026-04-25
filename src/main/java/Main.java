import esd.ListaSequencial;
import sm.Giassi;
import sm.Produto;

import java.util.regex.Pattern;

public class Main {
    static void main() {
//        final Pattern re_resources = Pattern.compile("(\\d+)-(\\d+)/(\\d+)", Pattern.CASE_INSENSITIVE);
//
//        String res = "0-9/135";
//        var m = re_resources.matcher(res);
//        if (m.find()) {
//            for (int i=0; i <= m.groupCount(); i++) {
//                IO.println(m.group(i));
//            }
//        }
        // cria um acessador para o Giassi
        Giassi sm = new Giassi();

        // procura todos produtos cujo nome contenha "tapioca"
        var res = sm.busca("arroz");

        if (res != null) {
            // Mostra cada um dos produtos encontrados
            ListaSequencial<Produto> produtos = res.produtos();

            for (int pos = 0; pos < produtos.comprimento(); pos++) {
                IO.println(produtos.obtem(pos));
            }
            if (res.restantes() > 0) {
                res = sm.busca_proximo(res);
                produtos = res.produtos();

                for (int pos = 0; pos < produtos.comprimento(); pos++) {
                    IO.println(produtos.obtem(pos));
                }

            }

        }

    }
}
