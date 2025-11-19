import java.util.*;
import java.lang.Math;

public class LetraC {

    public static class Result {
        public double valorAprox;
        public List<ItemFrac> itens;

        public Result(double valorAprox, List<ItemFrac> itens) {
            this.valorAprox = valorAprox;
            this.itens = itens;
        }
    }

    public static class ItemFrac {
        public int index;
        public double fracao;

        public ItemFrac(int index, double fracao) {
            this.index = index;
            this.fracao = fracao;
        }

        @Override
        public String toString() {
            return "(" + index + ", " + fracao + ")";
        }
    }

    public static Result fractionalKnapsackDpApprox(
            double[] weights, double[] values, double capacity, int precision) {

        int n = weights.length;

        List<Integer> itemIdx = new ArrayList<>();
        List<Double> wUnits = new ArrayList<>();
        List<Double> vUnits = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (weights[i] <= 0) continue;

            int pieces = Math.max(1, (int) Math.ceil(weights[i] * precision));
            double wUnit = weights[i] / pieces;
            double vUnit = values[i] / pieces;

            for (int p = 0; p < pieces; p++) {
                itemIdx.add(i);
                wUnits.add(wUnit);
                vUnits.add(vUnit);
            }
        }

        int m = itemIdx.size();
        int capInt = (int) Math.floor(capacity * precision + 1e-9);

        double[] dp = new double[capInt + 1];
        boolean[][] pick = new boolean[m][capInt + 1];

        int[] intWeights = new int[m];
        for (int i = 0; i < m; i++) {
            intWeights[i] = (int) Math.round(wUnits.get(i) * precision);
        }

        for (int i = 0; i < m; i++) {
            int w = intWeights[i];
            double v = vUnits.get(i);

            if (w == 0) continue;

            for (int cap = capInt; cap >= w; cap--) {
                double newValue = dp[cap - w] + v;
                if (newValue > dp[cap]) {
                    dp[cap] = newValue;
                    pick[i][cap] = true;
                }
            }
        }

        double valorAprox = dp[capInt];

        int cap = capInt;
        int[] unitsTaken = new int[n];

        for (int i = m - 1; i >= 0; i--) {
            if (cap >= 0 && pick[i][cap]) {
                int originalIdx = itemIdx.get(i);
                unitsTaken[originalIdx]++;
                cap -= intWeights[i];
            }
        }

        int[] piecesPerItem = new int[n];
        for (int i = 0; i < n; i++) {
            piecesPerItem[i] = Math.max(1, (int) Math.ceil(weights[i] * precision));
        }

        List<ItemFrac> resultadoItems = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double frac = (double) unitsTaken[i] / piecesPerItem[i];
            if (frac > 0) {
                resultadoItems.add(new ItemFrac(i, Math.min(1.0, frac)));
            }
        }

        return new Result(valorAprox, resultadoItems);
    }

    public static void main(String[] args) {
        double[] pesos = {10, 40, 20, 30};
        double[] valores = {60, 40, 100, 120};
        double capacidade = 50;

        Result r = fractionalKnapsackDpApprox(pesos, valores, capacidade, 10);

        System.out.println("Valor aprox (DP): " + r.valorAprox);
        System.out.println("Itens escolhidos:");
        for (ItemFrac it : r.itens) {
            System.out.println(it);
        }
    }
}
