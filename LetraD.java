import java.util.*;

public class LetraD {

    public static class Resultado {
        public int valorEscalado;
        public int valorReal;
        public List<Integer> itens;

        @Override
        public String toString() {
            return "Valor real = " + valorReal +
                   "\nValor escalado = " + valorEscalado +
                   "\nItens escolhidos = " + itens;
        }
    }

    // ------------------------------------------------------------
    // FPTAS IK-ε
    // ------------------------------------------------------------
    public static Resultado resolverAproximado(int[] v, int[] w, int m, double eps) {

        int n = v.length;

        //Descobrir θ = maior valor de item admissível pelo peso
        int theta = 0;
        for (int i = 0; i < n; i++) {
            if (w[i] <= m && v[i] > theta) {
                theta = v[i];
            }
        }

        //Calcular λ
        double lambda = (eps * theta) / n;

        // 3) Criar valores escalados u[i]
        int[] u = new int[n];
        for (int i = 0; i < n; i++) {
            u[i] = (int) Math.floor(v[i] / lambda);
        }

        //Descobrir soma máxima de u[i]
        int U = 0;
        for (int ui : u) U += ui;

        // DP[i][j] = menor peso para obter valor total j usando itens até i
        int INF = Integer.MAX_VALUE / 4;
        int[][] DP = new int[n + 1][U + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(DP[i], INF);
        }
        DP[0][0] = 0;

        // Preencher DP
        for (int i = 1; i <= n; i++) {
            for (int val = 0; val <= U; val++) {
                // não pegar item
                DP[i][val] = DP[i - 1][val];

                // pegar item
                if (u[i - 1] <= val) {
                    DP[i][val] = Math.min(DP[i][val],
                                          DP[i - 1][val - u[i - 1]] + w[i - 1]);
                }
            }
        }

        //O melhor valor escalado é o maior val com DP[n][val] ≤ m
        int melhorVal = 0;
        for (int val = U; val >= 0; val--) {
            if (DP[n][val] <= m) {
                melhorVal = val;
                break;
            }
        }

        //Reconstrução dos itens escolhidos
        List<Integer> itens = new ArrayList<>();
        int val = melhorVal;
        int i = n;

        while (i > 0 && val >= 0) {
            if (val >= u[i - 1] &&
                DP[i][val] == DP[i - 1][val - u[i - 1]] + w[i - 1]) {

                itens.add(i - 1);
                val -= u[i - 1];
                i--;

            } else {
                i--;
            }
        }

        //Calcular valor REAL dos itens escolhidos
        int valorReal = 0;
        for (int idx : itens) {
            valorReal += v[idx];
        }

        //Montar resultado
        Resultado r = new Resultado();
        r.valorEscalado = melhorVal;
        r.valorReal = valorReal;
        r.itens = itens;

        return r;
    }

    // ------------------------------------------------------------
    // MAIN para testes
    // ------------------------------------------------------------
    public static void main(String[] args) {

        int[] v = {5, 12, 8, 1};
        int[] w = {4, 8, 5, 3};
        int m = 5;
        double eps = 0.1;

        Resultado r = resolverAproximado(v, w, m, eps);

        System.out.println("===== RESULTADO FPTAS (Letra D) =====");
        System.out.println(r);
    }
}
