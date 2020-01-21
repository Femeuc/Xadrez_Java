package com.xadrez.engine.tabuleiro;

public class TabuleiroUtil {

    public static final boolean[] PRIMEIRA_COLUNA = iniciarColuna(0);
    public static final boolean[] SEGUNDA_COLUNA = iniciarColuna(1);
    public static final boolean[] SETIMA_COLUNA = iniciarColuna(6);
    public static final boolean[] OITAVA_COLUNA = iniciarColuna(7);

    public static final boolean[] PRIMEIRA_LINHA = iniciarLinha(0);
    public static final boolean[] SEGUNDA_LINHA = iniciarLinha(8);   // ID do quadrado em que a linha começa
    public static final boolean[] TERCEIRA_LINHA = iniciarLinha(16);
    public static final boolean[] QUARTA_LINHA = iniciarLinha(24);
    public static final boolean[] QUINTA_LINHA = iniciarLinha(32);
    public static final boolean[] SEXTA_LINHA = iniciarLinha(40);
    public static final boolean[] SETIMA_LINHA = iniciarLinha(48);
    public static final boolean[] OITAVA_LINHA = iniciarLinha(56);

    public static final int QUANTIDADE_QUADRADOS = 64;
    public static final int QUADRADOS_POR_LINHA = 8;

    private TabuleiroUtil() {
        throw new RuntimeException("Você não pode me instanciar!");
    }

    private static boolean[] iniciarColuna(int numeroColuna) {
        final boolean[] coluna = new boolean[QUANTIDADE_QUADRADOS];
        do {
            coluna[numeroColuna] = true;
            numeroColuna += QUADRADOS_POR_LINHA;
        } while(numeroColuna < QUANTIDADE_QUADRADOS);
        return coluna;
    }

    private static boolean[] iniciarLinha(int numeroDaLinha) {
        final boolean[] linha = new boolean[QUANTIDADE_QUADRADOS];
        do {
            linha[numeroDaLinha] = true;
            numeroDaLinha++;
        } while(numeroDaLinha % QUADRADOS_POR_LINHA != 0);
        return linha;
    }

    public static boolean isQuadradoValido(final int coordenada) {
        return coordenada >= 0 && coordenada < QUANTIDADE_QUADRADOS;
    }
}
