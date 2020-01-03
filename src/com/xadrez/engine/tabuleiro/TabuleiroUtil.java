package com.xadrez.engine.tabuleiro;

public class TabuleiroUtil {

    public static final boolean[] PRIMEIRA_COLUNA = null;
    public static final boolean[] SEGUNDA_COLUNA = null;
    public static final boolean[] SETIMA_COLUNA = null;
    public static final boolean[] OITAVA_COLUNA = null;

    private TabuleiroUtil() {
        throw new RuntimeException("Você não pode me instanciar!")
    }

    public static boolean isQuadradoValido(int coordenada) {
        return coordenada >= 0 && coordenada < 64;
    }
}
