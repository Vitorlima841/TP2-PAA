import java.util.*;

public class LetraC {

    public static class Resultado {
        public double valorOtimo;
        public List<Integer> itens; 

        public Resultado(double valorOtimo, List<Integer> itens) {
            this.valorOtimo = valorOtimo;
            this.itens = itens;
        }
    }

    public static Resultado knapsack01(double[] weights, double[] values, double capacity) {
        int n = weights.length;
        int W = (int) capacity;

        double[][] dp = new double[n + 1][W + 1];

        // ------------------------------
        // Preenche tabela DP
        // ------------------------------
        for (int i = 1; i <= n; i++) {
            int w = (int) weights[i - 1];
            double v = values[i - 1];

            for (int cap = 0; cap <= W; cap++) {
                dp[i][cap] = dp[i - 1][cap]; 

                if (w <= cap) {
                    dp[i][cap] = Math.max(dp[i][cap], dp[i - 1][cap - w] + v);
                }
            }
        }

        double valorOtimo = dp[n][W];

        // ------------------------------
        // Backtracking — recupera itens
        // ------------------------------
        List<Integer> itensEscolhidos = new ArrayList<>();
        int cap = W;

        for (int i = n; i >= 1; i--) {
            if (dp[i][cap] != dp[i - 1][cap]) {
                itensEscolhidos.add(i - 1);
                cap -= (int) weights[i - 1];
            }
        }

        Collections.reverse(itensEscolhidos);

        return new Resultado(valorOtimo, itensEscolhidos);
    }

    public static void main(String[] args) {
        double[] pesos = {1, 4, 2, 3};
        double[] valores = {6, 4, 10, 12};
        double capacidade = 5;

        Resultado r = knapsack01(pesos, valores, capacidade);

        System.out.println("Valor ótimo: " + r.valorOtimo);
        System.out.println("Itens escolhidos:");
        for (int idx : r.itens) {
            System.out.println("Item " + idx);
        }
    }
}
