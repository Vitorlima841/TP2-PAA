import java.util.*;

public class LetraB {

    static class ItemComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            double a1 = (1.0 * a[0]) / a[1];
            double b1 = (1.0 * b[0]) / b[1];
            return Double.compare(b1, a1);
        }
    }

    static class ResultadoItem {
        int valor;
        int peso;
        double porcentagem;

        public ResultadoItem(int valor, int peso, double porcentagem) {
            this.valor = valor;
            this.peso = peso;
            this.porcentagem = porcentagem;
        }

        @Override
        public String toString() {
            return String.format(
                    "Valor: %d, Peso: %d, Porcentagem usada: %.2f%%",
                    valor, peso, porcentagem * 100
            );
        }
    }

    static double fractionalKnapsack(int[] val, int[] wt, int capacity, List<ResultadoItem> itensSelecionados) {
        int n = val.length;
        int[][] items = new int[n][2];

        for (int i = 0; i < n; i++) {
            items[i][0] = val[i];
            items[i][1] = wt[i];
        }

        Arrays.sort(items, new ItemComparator());

        double resultado = 0.0;
        int capacidadeAtual = capacity;

        for (int i = 0; i < n; i++) {
            int valor = items[i][0];
            int peso = items[i][1];

            if (peso <= capacidadeAtual) {
                resultado += valor;
                capacidadeAtual -= peso;
                itensSelecionados.add(new ResultadoItem(valor, peso, 1.0));
            } else {
                double porcentagem = (double) capacidadeAtual / peso;
                resultado += valor * porcentagem;
                itensSelecionados.add(new ResultadoItem(valor, peso, porcentagem));
                break;
            }
        }

        return resultado;
    }

    public static void main(String[] args) {
        int[] val = { 60, 100, 120 };
        int[] wt = { 10, 20, 30 };
        int capacidade = 50;

        List<ResultadoItem> escolhidos = new ArrayList<>();

        double resultado = fractionalKnapsack(val, wt, capacidade, escolhidos);

        System.out.println("Valor máximo = " + resultado);
        System.out.println("\nItens selecionados:");
        for (ResultadoItem ri : escolhidos) {
            System.out.println(ri);
        }
    }
}
