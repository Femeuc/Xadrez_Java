package com.xadrez.engine.tabuleiro;

public class TabuleiroUtil {

    public static final boolean[] PRIMEIRA_COLUNA = iniciarColuna(0);
    public static final boolean[] SEGUNDA_COLUNA = iniciarColuna(1);
    public static final boolean[] SETIMA_COLUNA = iniciarColuna(6);
    public static final boolean[] OITAVA_COLUNA = iniciarColuna(7);

    public static final int QUANTIDADE_QUADRADOS = 64;
    public static final int QUADRADOS_POR_LINHA = 8;

    private TabuleiroUtil() {
        throw new RuntimeException("Você não pode me instanciar!")
    }

    private static boolean[] iniciarColuna(int numeroColuna) {
        final boolean[] coluna = new boolean[QUANTIDADE_QUADRADOS];
        do {
            coluna[numeroColuna] = true;
            numeroColuna += QUADRADOS_POR_LINHA;
        } while(numeroColuna < QUANTIDADE_QUADRADOS);
        return coluna;
    }

    public static boolean isQuadradoValido(final int coordenada) {
        return coordenada >= 0 && coordenada < QUANTIDADE_QUADRADOS;
    }
}
