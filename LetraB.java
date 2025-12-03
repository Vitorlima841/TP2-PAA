public class LetraB {

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

    public static Resultado mochilaForcaBruta(int[] pesos, int[] valores, int capacidade) {
        int n = pesos.length;
        boolean[] selecionados = new boolean[n];
        boolean[] melhorEscolha = new boolean[n];

        int melhorValor = 0;
        int melhorPeso = 0;

        long total = 1L << n; // 2^n

        for (long mask = 0; mask < total; mask++) {
            int pesoAtual = 0;
            int valorAtual = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1L << i)) != 0) {
                    pesoAtual += pesos[i];
                    valorAtual += valores[i];
                    selecionados[i] = true;
                } else {
                    selecionados[i] = false;
                }
            }

            if (pesoAtual <= capacidade && valorAtual > melhorValor) {
                melhorValor = valorAtual;
                melhorPeso = pesoAtual;
                System.arraycopy(selecionados, 0, melhorEscolha, 0, n);
            }
        }

        return new Resultado(melhorValor, melhorPeso, melhorEscolha);
    }

    public static void main(String[] args) {

        for (int n = 1; n <= 60; n++) {
            int[] pesos = new int[n];
            int[] valores = new int[n];
            int capacidade = n * 2;

            for (int i = 0; i < n; i++) {
                pesos[i] = 1 + (int) (Math.random() * 5);
                valores[i] = 1 + (int) (Math.random() * 10);
            }

            long tempoInicial = System.currentTimeMillis();
            Resultado r = mochilaForcaBruta(pesos, valores, capacidade);
            long tempoFinal = System.currentTimeMillis();

            long tempo = tempoFinal - tempoInicial;

            System.out.println("n = " + n + " | tempo = " + tempo + " ms");
            System.out.println("Capacidade = " + capacidade);

            // Mostrar todos os pesos
            System.out.print("Todos os pesos: ");
            for (int p : pesos) System.out.print(p + " | ");
            System.out.println();

            // Mostrar todos os valores
            System.out.print("Todos os valores: ");
            for (int v : valores) System.out.print(v + " | ");
            System.out.println();

            // Mostrar itens escolhidos com seus respectivos pesos e valores
            System.out.println("\nItens selecionados:");
            for (int i = 0; i < n; i++) {
                if (r.selecionados[i]) {
                    System.out.println("peso = " + pesos[i] + " | valor = " + valores[i]);
                }
            }

            System.out.println("Melhor valor total = " + r.melhorValor);
            System.out.println("Melhor peso total = " + r.melhorPeso);
            System.out.println("\n=======================================\n");
        }
    }
}


/* 
# Resumo dos Resultados

O maior tamanho de entrada resolvido de forma viável foi n = 30, levando ≈ 60.829 ms.
Para n = 32, o tempo subiu para ≈ 249.197 ms, tornando a execução inviável.

Isso ocorre porque o algoritmo de força bruta possui complexidade exponencial ( O(2^n) ).
Cada aumento de 1 no valor de n dobra o número de combinações, e aumentar de 30 para 32 multiplica o tempo por aproximadamente 4 — comportamento típico de algoritmos exponenciais.

### Estimativa para uma entrada dez vezes maior (n = 300)

O tempo seria aproximadamente:

[T(300) ≈ 6 x 10^85ms ≈ 10^75 anos]

Esse valor é incomparavelmente maior que a idade do universo, demonstrando que a força bruta não escala para entradas maiores.

*/

/*  ------------------------- LETRA E ---------------------------------------------
 *  A complexidade do algoriomo da letra B é 2^N
 *  A complexidade da letra C é pseudo polinomia (n*W) onde cresce de forma exponencial baseado no W
 *  A complexidade da letra D é n logn (pois precisa orenar primeiro)
 */