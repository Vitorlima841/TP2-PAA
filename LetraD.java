import java.util.*;

public class LetraD {

    // ------------------------------------------------------------
    // Estrutura de resultado
    // ------------------------------------------------------------
    public static class Resultado {
        public int valorTotal;
        public int pesoTotal;
        public List<Integer> itens;

        public Resultado(int valorTotal, int pesoTotal, List<Integer> itens) {
            this.valorTotal = valorTotal;
            this.pesoTotal = pesoTotal;
            this.itens = itens;
        }
    }

    // ------------------------------------------------------------
    // ALGORITMO MOCHILA-EXATO
    // ------------------------------------------------------------
    public static Resultado mochilaExato(int m, int n, int[] v, int[] w) {

        int Vsum = Arrays.stream(v).sum();

        // DP[i][j] = menor peso possível usando itens até i para valor total j
        int[][] DP = new int[n + 1][Vsum + 1];

        // inicialização
        for (int i = 0; i <= n; i++) {
            DP[i][0] = 0;
        }
        for (int j = 1; j <= Vsum; j++) {
            DP[0][j] = Integer.MAX_VALUE / 4; // infinito
        }

        // preenche DP
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= Vsum; j++) {

                if (v[i - 1] > j) {
                    DP[i][j] = DP[i - 1][j];
                } else {
                    DP[i][j] = Math.min(
                            DP[i - 1][j],
                            w[i - 1] + DP[i - 1][j - v[i - 1]]
                    );
                }
            }
        }

        // encontra maior valor j com peso <= m
        int melhorValor = 0;
        for (int j = Vsum; j >= 0; j--) {
            if (DP[n][j] <= m) {
                melhorValor = j;
                break;
            }
        }

        // reconstrói solução
        List<Integer> itens = new ArrayList<>();
        int j = melhorValor;

        for (int i = n; i > 0; i--) {
            if (DP[i][j] != DP[i - 1][j]) {
                itens.add(i - 1);
                j -= v[i - 1];
            }
        }

        int pesoTotal = itens.stream().mapToInt(k -> w[k]).sum();

        return new Resultado(melhorValor, pesoTotal, itens);
    }

    // ------------------------------------------------------------
    // ALGORITMO MOCHILA-IKɛ
    // ------------------------------------------------------------
    public static Resultado mochilaIK(int m, int n, int[] v, int[] w, double epsilon) {

        // PASSO 1: se todos os pesos > m, retorno vazio
        boolean impossivel = true;
        for (int peso : w) {
            if (peso <= m) {
                impossivel = false;
                break;
            }
        }
        if (impossivel) {
            return new Resultado(0, 0, new ArrayList<>());
        }

        // PASSO 2: θ = maior valor de item admissível
        int theta = 0;
        for (int i = 0; i < n; i++) {
            if (w[i] <= m && v[i] > theta) {
                theta = v[i];
            }
        }

        // PASSO 3: λ = (epsilon * θ) / n
        double lambda = (epsilon * theta) / n;

        // PASSO 4: u_i = floor(v_i / λ)
        int[] u = new int[n];
        for (int i = 0; i < n; i++) {
            u[i] = (int) Math.floor(v[i] / lambda);
        }

        // PASSO 5: chama mochila-exata com valores escalados
        return mochilaExato(m, n, u, w);
    }

    // ------------------------------------------------------------
    // MAIN PARA TESTES
    // ------------------------------------------------------------
    public static void main(String[] args) {
        int m = 10;
        int[] w = {4, 8, 5, 3};
        int[] v = {5, 12, 8, 1};
        int n = v.length;

        double epsilon = 0.1;

        Resultado r = mochilaIK(m, n, v, w, epsilon);

        System.out.println("Valor aproximado (u_i): " + r.valorTotal);
        System.out.println("Peso total: " + r.pesoTotal);
        System.out.println("Itens escolhidos: " + r.itens);
    }
}
