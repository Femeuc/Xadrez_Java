package com.xadrez.engine.tabuleiro;

import com.xadrez.engine.pecas.Peca;

import java.util.HashMap;
import java.util.Map;

public abstract class Quadrado {
    protected final int coordenada;

    private static final Map<Integer, QuadradoVazio> QUADRADOS_VAZIOS = criarQuadrados();

    private static Map<Integer, QuadradoVazio> criarQuadrados() {
        final Map<Integer, QuadradoVazio> mapaQuadradosVazios = new HashMap<>();

        for(int i = 0; i < TabuleiroUtil.QUANTIDADE_QUADRADOS; i++) {
            mapaQuadradosVazios.put(i, new QuadradoVazio(i));
        }

        return mapaQuadradosVazios; // ESSE RETORNO PODE SER MELHORADO com o uso de uma biblioteca: ImutableMap.copyOf()
    }

    public static Quadrado criarQuadrado(final int coordenada, final Peca peca) {
        return peca != null ? new Ocupado(coordenada, peca) : QUADRADOS_VAZIOS.get(coordenada);
    }

    private Quadrado (final int coord) {
        coordenada = coord;
    }

    public abstract boolean isOcupado();

    public abstract Peca getPeca();

    public static final class QuadradoVazio extends Quadrado {
        private QuadradoVazio(final int coordenada) {
            super(coordenada);
        }
        @Override
        public boolean isOcupado() {
            return false;
        }
        @Override
        public Peca getPeca() {
            return null;
        }
    }

    public static final class Ocupado extends Quadrado {
        private final Peca pecaQudrado;

        private Ocupado(int coordenada, final Peca pecaQudrado) {
            super(coordenada);
            this.pecaQudrado = pecaQudrado;
        }

        @Override
        public boolean isOcupado() {
            return true;
        }

        @Override
        public Peca getPeca() {
            return this.pecaQudrado;
        }
    }
}
