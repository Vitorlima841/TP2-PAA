public class LetraD {
    public static class Item {
        public int valor;
        public int peso;
        public boolean foiSelecionado;

        public Item(int valor, int peso, boolean foiSelecionado) {
            this.peso = peso;
            this.valor = valor;
            this.foiSelecionado = foiSelecionado;
        }
    }

    public static Item[] mochilaProgramacaoGulosa(Item[] itens, int capacidade) {
        int n = itens.length;
        int valorTotal = 0;
        int pesoTotal = 0;
        int capacidadeAtualizada = capacidade;

        Item[] itensSelecionadosTemp = new Item[n];
        int indiceSelecionado = 0;

        for (int i = 0; i < n; i++) {
            if (itens[i].peso <= capacidadeAtualizada) {
                capacidadeAtualizada -= itens[i].peso;
                valorTotal += itens[i].valor;
                pesoTotal += itens[i].peso;
                itens[i].foiSelecionado = true;

                itensSelecionadosTemp[indiceSelecionado] = itens[i];
                indiceSelecionado++;
            }
        }

        Item[] itensSelecionados = new Item[indiceSelecionado];
        for (int i = 0; i < indiceSelecionado; i++) {
            itensSelecionados[i] = itensSelecionadosTemp[i];
        }

        System.out.println("Valor total: " + valorTotal + " | Peso: " + pesoTotal + " | Capacidade: " + capacidade);

        return itensSelecionados;
    }

    static int partition(Item[] arr, int low, int high) {
        int pivot = arr[high].valor;
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            if (arr[j].valor > pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);  
        return i + 1;
    }

    static void swap(Item[] arr, int i, int j) {
        Item temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void quickSort(Item[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        int capacidade = 15;
        int[] pesos = {5, 4, 1, 2, 1, 2, 3, 4, 2, 3};
        int[] valores = {1, 8, 6, 6, 1, 5, 1, 5, 4, 4};

        Item[] itens = new Item[10];

        for(int i = 0; i < 10; i++){
            itens[i] = new Item(valores[i], pesos[i], false);
        }

        quickSort(itens, 0, valores.length - 1);

        long tempoInicial = System.currentTimeMillis();
        Item[] itensSelecionados = mochilaProgramacaoGulosa(itens, capacidade);
        long tempoFinal = System.currentTimeMillis();

        long tempo = tempoFinal - tempoInicial;

        for (Item itensSelecionado : itensSelecionados) {
            System.out.println("Itens Selecionados: " + itensSelecionado.valor + " | " + itensSelecionado.peso + " | " + itensSelecionado.foiSelecionado);
        }
        System.out.println("\nTempo = " + tempo + " ms");
    }
}