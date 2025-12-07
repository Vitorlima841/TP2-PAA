import java.util.*;

public class LetraG {

    public static void main(String[] args) {

        // ============================================================
        // PARÂMETROS DO TESTE
        // ============================================================
        int n = 30;              // quantidade de itens
        int minValor = 10;        // valor mínimo gerado
        int maxValor = 1000;      // valor máximo gerado
        int minPeso  = 1;         // peso mínimo
        int maxPeso  = 50;        // peso máximo
        int capacidade = 500;     // capacidade da mochila
        double eps = 0.1;        // epsilon do FPTAS

        // ============================================================
        // GERANDO DADOS ALEATÓRIOS
        // ============================================================
        int[] v = gerarArray(n, minValor, maxValor);
        int[] w = gerarArray(n, minPeso,  maxPeso);

        double[] vDouble = toDoubleArray(v);
        double[] wDouble = toDoubleArray(w);

        // ============================================================
        // TESTE LETRA C (ÓTIMO)
        // ============================================================
        long iniC = System.nanoTime();
        LetraC.Resultado otimo = LetraC.knapsack01(wDouble, vDouble, capacidade);
        long fimC = System.nanoTime();

        double tempoC = (fimC - iniC) / 1_000_000.0;

        System.out.println("===============================================");
        System.out.println("========== LETRA C - SOLUÇÃO ÓTIMA ==========");
        System.out.println("Valor ótimo = " + otimo.valorOtimo);
        System.out.println("Itens escolhidos = " + otimo.itens);
        System.out.println("Tempo (ms) = " + tempoC);
        System.out.println();

        // ============================================================
        // TESTE LETRA D (APROXIMADO)
        // ============================================================
        long iniD = System.nanoTime();
        LetraD.Resultado approx = LetraD.resolverAproximado(v, w, capacidade, eps);
        long fimD = System.nanoTime();

        double tempoD = (fimD - iniD) / 1_000_000.0;

        System.out.println("===============================================");
        System.out.println("========== LETRA D - APROXIMADO ==========");
        System.out.println("Valor REAL aproximado = " + approx.valorReal);
        System.out.println("Valor escalado (DP) = " + approx.valorEscalado);
        System.out.println("Itens escolhidos = " + approx.itens);
        System.out.println("Tempo (ms) = " + tempoD);
        System.out.println();

        // ============================================================
        // COMPARAÇÃO
        // ============================================================
        double razao = approx.valorReal / otimo.valorOtimo;

        System.out.println("===============================================");
        System.out.println("========== COMPARAÇÃO FINAL ==========");
        System.out.println("e = " + eps);
        System.out.println("Aproximação = valorAproxReal / valorÓtimo = " + razao);

        if (razao >= 1.0 - eps) {
            System.out.println("OK - A solução cumpre a garantia (1 - e).");
        } else {
            System.out.println("ALERTA - A solução ficou fora do esperado!");
        }
    }

    // ============================================================
    // Funções auxiliares
    // ============================================================

    // Gera array de inteiros aleatórios
    public static int[] gerarArray(int n, int min, int max) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = rand.nextInt(max - min + 1) + min;
        return arr;
    }

    // Converte int[] para double[]
    private static double[] toDoubleArray(int[] x) {
        double[] r = new double[x.length];
        for (int i = 0; i < x.length; i++) r[i] = x[i];
        return r;
    }
}
