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

    public static void mochilaProgramacaoGulosa(int[] pesos, int[] valores, int capacidade) {
        int valorTotal = 0;
        int pesoTotal = 0;

        for(int i = 0; i < valores.length; i++){
            if(pesos[i] <= capacidade){
                System.out.println("Objeto escolhido : " + pesos[i] + "V " + valores[i]);
            }
        }

        return;
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
        mochilaProgramacaoGulosa(pesos, valores, capacidade);
        long tempoFinal = System.currentTimeMillis();

        long tempo = tempoFinal - tempoInicial;

        System.out.println("\nTempo = " + tempo + " ms");

        System.out.println("\nItens selecionados:");
    }
}