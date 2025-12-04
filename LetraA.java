public class LetraA {

    // SubsetSum (decisão)
    public static boolean subsetSum(int[] nums, int T) {
        if (T < 0) return false;
        boolean[] dp = new boolean[T + 1];
        dp[0] = true;

        for (int num : nums) {
            if (num < 0) throw new IllegalArgumentException("Apenas inteiros positivos são permitidos.");
            for (int s = T; s >= num; s--) {
                if (dp[s - num]) dp[s] = true;
            }
        }
        return dp[T];
    }

    
    // Knapsack 0/1 (decisão)
    public static boolean knapsackDecision(int[] weights, int[] values, int W, int V) {
        if (weights.length != values.length)
            throw new IllegalArgumentException("Pesos e valores devem ter o mesmo tamanho.");

        int[] dp = new int[W + 1];
        int n = weights.length;

        for (int i = 0; i < n; i++) {
            int wi = weights[i];
            int vi = values[i];

            for (int c = W; c >= wi; c--) {
                dp[c] = Math.max(dp[c], dp[c - wi] + vi);
            }
        }

        return dp[W] >= V;  // decisão
    }

    // Demonstração da redução
    public static void main(String[] args) {

        // Instância de SubsetSum
        int[] nums = {3, 34, 4, 12, 5, 2};
        int T = 9;

        System.out.println("=== SUBSET SUM ===");
        boolean ss = subsetSum(nums, T);
        System.out.println("Resposta SubsetSum: " + ss);

        // Redução:
        int n = nums.length;
        int[] w = new int[n];
        int[] v = new int[n];

        for (int i = 0; i < n; i++) {
            w[i] = nums[i];
            v[i] = nums[i];
        }

        int W = T;
        int V = T;

        System.out.println("\n=== KNAPSACK (instância reduzida) ===");
        boolean ks = knapsackDecision(w, v, W, V);
        System.out.println("Resposta Knapsack: " + ks);

        System.out.println("\nAs respostas devem ser iguais. Redução SubsetSum -> Knapsack concluída.");
    }
}