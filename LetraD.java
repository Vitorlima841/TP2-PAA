import java.util.*;

public class LetraD {

    // Algoritmo exato MOCHILA-EXATO
    public static List<Integer> knapsackExato(int m, int n, int[] u, int[] w) {

        int sumU = 0;
        for (int x : u) sumU += x;

        int[] dp = new int[sumU + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        int[][] parent = new int[n][sumU + 1];
        for (int i = 0; i < n; i++)
            Arrays.fill(parent[i], -1);

        for (int i = 0; i < n; i++) {
            for (int v = sumU; v >= u[i]; v--) {
                if (dp[v - u[i]] + w[i] < dp[v]) {
                    dp[v] = dp[v - u[i]] + w[i];
                    parent[i][v] = v - u[i];
                }
            }
        }

        int bestV = 0;
        for (int v = 0; v <= sumU; v++) {
            if (dp[v] <= m) bestV = v;
        }

        List<Integer> items = new ArrayList<>();
        int v = bestV;
        for (int i = n - 1; i >= 0; i--) {
            if (parent[i][v] != -1) {
                items.add(i);
                v = parent[i][v];
            }
        }

        return items;
    }

    // Algoritmo aproximado MOCHILA-IKε
    public static List<Integer> mochilaIKE(int m, int n, int[] v, int[] w, double eps) {

        boolean algumCabe = false;
        for (int wi : w)
            if (wi <= m) algumCabe = true;

        if (!algumCabe)
            return new ArrayList<>();

        int theta = 0;
        for (int i = 0; i < n; i++)
            if (w[i] <= m)
                theta = Math.max(theta, v[i]);

        double lambda = (eps * theta) / n;

        int[] u = new int[n];
        for (int i = 0; i < n; i++)
            u[i] = (int) Math.floor(v[i] / lambda);

        return knapsackExato(m, n, u, w);
    }


    // Exemplo de uso (agora imprime valor final)
    public static void main(String[] args) {

        int m = 10;
        int[] w = {4, 8, 5, 3};
        int[] v = {5, 12, 8, 1};
        double eps = 0.1;

        List<Integer> sol = mochilaIKE(m, v.length, v, w, eps);

        int valorTotal = 0;
        int pesoTotal = 0;

        System.out.println("Itens escolhidos: " + sol);

        for (int i : sol) {
            valorTotal += v[i];
            pesoTotal += w[i];
        }

        System.out.println("Valor total da mochila = " + valorTotal);
        System.out.println("Peso total = " + pesoTotal + " / capacidade = " + m);
    }
}
