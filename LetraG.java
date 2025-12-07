public class LetraG {

    public static void main(String[] args) {

        // ----------------------------------------------------
        // DADOS DE TESTE
        // (pode alterar conforme quiser)
        // ----------------------------------------------------
        int[] v = {5, 12, 8, 1};
        int[] w = {4, 8, 5, 3};
        double capacidade = 5;
        double eps = 0.1;

        // Conversão para double[] pois a LetraC usa double
        double[] wDouble = toDoubleArray(w);
        double[] vDouble = toDoubleArray(v);

        // ----------------------------------------------------
        // TESTE LETRA C (ÓTIMO)
        // ----------------------------------------------------
        long iniC = System.nanoTime();
        LetraC.Resultado otimo = LetraC.knapsack01(wDouble, vDouble, capacidade);
        long fimC = System.nanoTime();

        double tempoC = (fimC - iniC) / 1_000_000.0;

        System.out.println("========== LETRA C — SOLUÇÃO ÓTIMA ==========");
        System.out.println("Valor ótimo = " + otimo.valorOtimo);
        System.out.println("Itens = " + otimo.itens);
        System.out.println("Tempo (ms) = " + tempoC);
        System.out.println();

        // ----------------------------------------------------
        // TESTE LETRA D (APROXIMADO)
        // ----------------------------------------------------
        long iniD = System.nanoTime();
        LetraD.Resultado approx = LetraD.resolverAproximado(v, w, (int) capacidade, eps);
        long fimD = System.nanoTime();

        double tempoD = (fimD - iniD) / 1_000_000.0;

        System.out.println("========== LETRA D — APROXIMADO IK-e ==========");
        System.out.println("Valor REAL aproximado = " + approx.valorReal);
        System.out.println("Valor escalado (DP) = " + approx.valorEscalado);
        System.out.println("Itens = " + approx.itens);
        System.out.println("Tempo (ms) = " + tempoD);
        System.out.println();

        // ----------------------------------------------------
        // COMPARAÇÃO
        // ----------------------------------------------------
        double razao = approx.valorReal / otimo.valorOtimo;

        System.out.println("========== COMPARAÇÃO ==========");
        System.out.println("e = " + eps);
        System.out.println("Aproximação (valorReal / valorÓtimo) = " + razao);

        if (razao >= 1.0 - eps) {
            System.out.println("OK - A solução está dentro do limite teórico.");
        } else {
            System.out.println("Atenção — A solução ficou pior que o garantido.");
        }
    }

    // Conversor para double[]
    private static double[] toDoubleArray(int[] x) {
        double[] r = new double[x.length];
        for (int i = 0; i < x.length; i++) r[i] = x[i];
        return r;
    }
}
