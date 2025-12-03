public class LetraD {
    public static class Resultado {
        public final int melhorValor;
        public final int melhorPeso;
        public final boolean[] selecionados;

        public Resultado(int melhorValor, int melhorPeso, boolean[] selecionados) {
            this.melhorValor = melhorValor;
            this.melhorPeso = melhorPeso;
            this.selecionados = selecionados;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Melhor valor total: ").append(melhorValor).append("\n");
            sb.append("Melhor peso total: ").append(melhorPeso).append("\n");
            sb.append("Itens escolhidos (índice, peso, valor):\n");

            for (int i = 0; i < selecionados.length; i++) {
                if (selecionados[i]) {
                    sb.append("  Item ").append(i).append("\n");
                }
            }
            return sb.toString();
        }
    }

    public static Resultado mochilaForcaBruta(int[] pesos, int[] valores, int capacidade) {
        int n = pesos.length;
        boolean[] selecionados = new boolean[n];
        boolean[] melhorEscolha = new boolean[n];

        int melhorValor = 0;
        int melhorPeso = 0;

        long total = 1L << n; // 2^n

        for (long mask = 0; mask < total; mask++) {
            int pesoAtual = 0;
            int valorAtual = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1L << i)) != 0) {
                    pesoAtual += pesos[i];
                    valorAtual += valores[i];
                    selecionados[i] = true;
                } else {
                    selecionados[i] = false;
                }
            }

            if (pesoAtual <= capacidade && valorAtual > melhorValor) {
                melhorValor = valorAtual;
                melhorPeso = pesoAtual;
                System.arraycopy(selecionados, 0, melhorEscolha, 0, n);
            }
        }

        return new Resultado(melhorValor, melhorPeso, melhorEscolha);
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] > pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);  
        return i + 1;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        int capacidade = 5;
        int[] pesos = {5, 4, 1, 2, 1, 2, 3, 4, 2, 3}; 
        int[] valores = {1, 8, 6, 6, 1, 5, 1, 5, 4, 4}; 
        
        quickSort(valores, 0, valores.length - 1);

        long tempoInicial = System.currentTimeMillis();
        Resultado r = mochilaForcaBruta(pesos, valores, capacidade);
        long tempoFinal = System.currentTimeMillis();

        for(int i = 0; i < valores.length; i++){
            System.out.println("aaaa " + valores[i]);
        }

        long tempo = tempoFinal - tempoInicial;

        System.out.println("\nTempo = " + tempo + " ms");

        System.out.println("\nItens selecionados:");
        for (int i = 0; i < 10; i++) {
            if (r.selecionados[i]) {
                System.out.println("Item " + i + " -> peso = " + pesos[i] + ", valor = " + valores[i]);
            }
        }

        System.out.println("\nMelhor valor total = " + r.melhorValor);
        System.out.println("Melhor peso total = " + r.melhorPeso + "\n");
    }
}