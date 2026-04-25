package esd;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class ListaSequencial<T> implements Iterable<T>{
    // esta classe define um iterador da ListaSimples
    // foi definida como classe aninhada de ListaSimples, mas isso não é obrigatório !
    public class ListaIterator implements Iterator<T> {
        // Cada estrutura de dados terá atributos específicos para ser possível
        // iterá-la. No caso da ListaSimples, são estes:

        // A referência para o valor atual da iteração
        int pos = 0;
        ListaSequencial<T> q;

        // pelo construtor se passa a referência ao primeiro nodo da lista
        ListaIterator(ListaSequencial<T> q) {
            this.q = q;
        }

        @Override
        public boolean hasNext() {
            // a iteração não chegou ao fim se a referência ao nodo atual não for null
            return pos < q.comprimento();
        }

        @Override
        public T next() {
            // somente pode obter o próximo valor da iteração se ela não chegou ao fim !
            if (! hasNext()) {
                throw new NoSuchElementException("fim da iteração");
            }
            // retorna o próximo valor da iteração, e também avança para próximo nodo
            return q.obtem(pos++);
        }
    }

    T[] area;
    int len = 0;
    final int defcap = 8;

    @SuppressWarnings("unchecked")
    public ListaSequencial() {
        area = (T[])new Object[defcap];
    }

    public ListaSequencial(T... vals) {
        this();

        for (var x: vals) {
            adiciona(x);
        }
    }

    // este método da classe Lista cria um iterador, que possibilita iterar do primeiro ao último objeto
    // armazenado neste Deque
    @Override
    public Iterator<T> iterator()
    {
        // cria um iterador. Note que a referência ao primeiro nodo é passada como parãmetro na criação do iterador
        return new ListaIterator(this);
    }

    @SuppressWarnings("unchecked")
    void expande() {
        // isto será usado quando for necessário expandir a capacidade da lista
        T[] nova = (T[])new Object[2*len];

        System.arraycopy(area, 0, nova, 0, len);
        area = nova;
    }

    @Override
    public boolean equals(Object obj) {
        ListaSequencial<T> outra = (ListaSequencial<T>)obj;
        boolean iguais = len == outra.len;

        for (int pos = 0; iguais && pos < outra.comprimento(); pos++) {
            iguais = this.area[pos].equals(outra.area[pos]);
        }

        return iguais;
    }

    public int procura_reversa(T valor) {
        for (int pos=len-1; pos >= 0; pos--) {
            if (area[pos].equals(valor)) return pos;
        }
        return -1;
    }

    public boolean esta_vazia() {
        // retorna true se lista estiver vazia, ou false caso contrário
        return len == 0;
    }

    public int capacidade() {
        // retorna um inteiro que representa a capacidade da lista
        return area.length;
    }

    public void adiciona(T elemento) {
        // adiciona um valor ao final da lista
        if (len == area.length) {
            expande();
        }
        area[len++] = elemento;
    }

    public void insere(int indice, T elemento) {
        if (indice > len) {
            throw new IndexOutOfBoundsException("indice inválido");
        }
        if (len == area.length) {
            expande();
        }
        if (indice < len) {
            System.arraycopy(area, indice, area, indice+1, len-indice);
        }
        area[indice] = elemento;
        len++;
    }

    public T remove(int indice) {
        // remove um valor da posição indicada pelo parâmetro "indice"
        // desloca uma posição para trás os valores à frente de indice
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("pos inválida");
        }
        T val = area[indice];
        if (--len > 0) {
            System.arraycopy(area, indice+1, area, indice, len-indice);
        }

        area[len] = null;

        return val;
    }

    public T remove_rapido(int indice) {
        // remove um valor da posição indica pelo parãmetro índice
        // move o último dado da lista para essa posição
        // dispara IndexOutOfBoundsException se indice for inválido
        // retorna o valor que ofi removido da lista
        if (indice < 0 || indice >= len) {
            throw new IndexOutOfBoundsException("pos inválida");
        }
        T val = area[indice];
        if (--len > 0) {
            area[indice] = area[len];
        }

        area[len] = null;

        return val;
    }

    public T remove_ultimo() {
        // remove o último valor da lista
        // disparar uma exceção IndexOutOfBoundsException caso lista vazia
        // retorna o valor que foi removido da lista
        return remove(len-1);
    }

    public int procura(Predicate<T> pred) {
        // retorna um inteiro que representa aposição onde valor foi encontrado pela primeira vez (contando do início da lista)
        // retorna -1 se não o encontrar !
        for (int pos=0; pos < len; pos++) {
            if (pred.test(area[pos])) {
                return pos;
            }
        }
        return -1;
    }

    public int procura(T valor) {
        // retorna um inteiro que representa aposição onde valor foi encontrado pela primeira vez (contando do início da lista)
        // retorna -1 se não o encontrar !
        for (int pos=0; pos < len; pos++) {
            if (valor.equals(area[pos])) {
                return pos;
            }
        }
        return -1;
    }

    public T obtem(int indice) {
        // retorna o valor armazenado na posição indica pelo parâmetro "indice"
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if (indice >= len || indice < 0) {
            throw new IndexOutOfBoundsException("posição inválida");
        }
        return area[indice];
    }

    public void substitui(int indice, T valor) {
        // armazena o valor na posição indicada por "indice", substituindo o valor lá armazenado atualmente
        // disparar uma exceção IndexOutOfBoundsException caso posição seja inválida
        if (indice >= len || indice < 0) {
            throw new IndexOutOfBoundsException("posição inválida");
        }
        area[indice] = valor;
    }

    public int comprimento() {
        // retorna um inteiro que representa o comprimento da lista (quantos valores estão armazenados)
        return len;
    }

    public void limpa() {
        // esvazia a lista
        for (int pos=0; pos < len; pos++) {
            area[pos] = null;
        }
        len = 0;
    }

    // remove um valor supondo que a lista esteja ordenada
    public void remove(T valor) {
        int pos = busca_binaria((Comparable)valor);
        if (pos >= 0) {
            remove(pos);
        }
    }

    public void insere_ordenado(Comparable valor) {
        // insere o valor na lista, preservando seu ordenamento
        if (len == area.length) {
            expande();
        }

        // procura a posição onde o valor deve ser inserido
        int pos;
        for (pos=0; pos < len; pos++) {
            if (valor.compareTo(area[pos]) < 0) {
                // ao encontrá-la, usa o método insere usual para inserir o valor
                break;
            }
        }
        insere(pos, (T)valor);

        // se não encontrar a posição, então o novo valor é maior do que
        // o último da lista ... adiciona-o ao final
        adiciona((T)valor);
    }

    public int busca_binaria(Comparable valor) {
        // procura o valor dentro da lista usando busca binária
        // retorna a posição onde se encontra, ou -1 caso não exista

        // as posições do início e fim do trecho da lista onde fazer a busca
        int pos1 = 0;
        int pos2 = len;

        // continua enquanto o trecho tiver pelo menos um elemento
        while (pos1 < pos2) {
            // calcula a posição mediana do trecho
            int meio = pos1 + (pos2 - pos1)/2;
            // compara o valor procurado com o valor que está no meio do trecho
            int cmp = valor.compareTo(area[meio]);
            // se encontrou o valor, retorna a posição onde está
            if (cmp == 0) return meio;
            // se valor procurado for menor do que o do meio,
            // continua a busca na primeira metade
            if (cmp < 0) {
                pos2 = meio;
            } else {
                // senão, busca na segunda metade
                pos1 = meio+1;
            }
        }

        // não encontrou !
        return -1;
    }

    // ordenamento por seleção
    public void ordena() {
        if (len < 2) return;

        for (int j=0; j < len-1; j++) {
            // procura menor valor a partir da posição j
            int menor = j;
            for (int k=j+1; k < len; k++) {
                Comparable val = (Comparable)area[menor];
                if (val.compareTo(area[k]) > 0) {
                    menor = k;
                }
            }
            // move menor valor para a posição j
            if (j != menor) {
                T curr = area[menor];
                area[menor] = area[j];
                area[j] = curr;
            }
        }
    }

    public void ordena_bolha() {
        // não precisa ordenar se houver menos que dois valores na lista
        if (len < 2) return;

        // laço externo: indica a posição aonde mover o maior valor encontrado
        for (int j=len-1; j > 0; j--) {
            // laço interno: empurra o maior valor até a posição "j"
            for (int i=0; i < j; i++) {
                // precisa disso para poder comparar os valores
                Comparable val = (Comparable)area[i];
                // se valor na posição i maior que o da posição i+1,
                // faz a permuta
                if (val.compareTo(area[i+1]) > 0) {
                    T curr = area[i];
                    area[i] = area[i+1];
                    area[i+1] = curr;
                }
            }
        }
    }

    public void ordena_bolha_melhorado() {
        if (len < 2) return;
        boolean ok = true;

        for (int j=len-1; j > 0 && ok; j--) {
            ok = false;
            for (int i=0; i < j; i++) {
                Comparable val = (Comparable)area[i];
                if (val.compareTo(area[i+1]) > 0) {
                    T curr = area[i];
                    area[i] = area[i+1];
                    area[i+1] = curr;
                    ok = true;
                }
            }
        }
    }

    public boolean contem(ListaSequencial<T> outra) {
        for (int j=0; j < len-outra.len+1; j++) {
            boolean ok=true;
            for (int i=0; ok && i < outra.len; i++) {
                ok = area[i+j].equals(outra.area[i]);
            }
            if (ok) return true;
        }
        return false;
    }

//    // retorna uma lista sequencial contendo os próximos maiores valores desta lista sequencial
//    public ListaSequencial<T> proximos_maiores_valores() {
//        ListaSequencial<T> r = new ListaSequencial<>();
//        return r;
//    }
//
//    public int procura_reversa(T valor) {
//        return -1;
//    }
}
