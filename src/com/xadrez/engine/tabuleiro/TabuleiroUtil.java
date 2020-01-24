package com.xadrez.engine.tabuleiro;

import java.util.*;

public class    TabuleiroUtil {

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
    public static final int INDICE_DO_QUADRADO_INICIAL = 0;

    public final List<String> NOTACAO_ALGEBRICA = iniciarNotacaoAlgebrica();
    public final Map<String, Integer> POSICAO_PARA_COORDENADA = iniciarPosicaoParaCoordenadaMap();

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

    private Map<String, Integer> iniciarPosicaoParaCoordenadaMap() {
        final Map<String, Integer> posicaoParaCoordenada = new HashMap<>();
        for (int i = INDICE_DO_QUADRADO_INICIAL; i < QUANTIDADE_QUADRADOS; i++) {
            posicaoParaCoordenada.put(NOTACAO_ALGEBRICA.get(i), i);
        }
        return Collections.unmodifiableMap(posicaoParaCoordenada);
    }

    private static List<String> iniciarNotacaoAlgebrica() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

    public static boolean isQuadradoValido(final int coordenada) {
        return coordenada >= 0 && coordenada < QUANTIDADE_QUADRADOS;
    }

    public int getCoordenadaNaPosicao(final String posicao) {
        return POSICAO_PARA_COORDENADA.get(posicao);
    }

    public String getCoordenadaDePosicao(final int coordenada) {
        return NOTACAO_ALGEBRICA.get(coordenada);
    }
}
