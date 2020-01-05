package com.xadrez.engine.tabuleiro;

import com.xadrez.engine.pecas.Peca;

public abstract class Movimento {
    final Tabuleiro tabuleiro;
    final Peca pecaMovida;
    final int coordenadaDestino;

    private Movimento(final Tabuleiro tabuleiro,
              final Peca pecaMovida,
              final int coordenadaDestino) {
        this.tabuleiro = tabuleiro;
        this.pecaMovida = pecaMovida;
        this.coordenadaDestino = coordenadaDestino;
    }

    public static final class MovimentoSemCaptura extends Movimento {
        public MovimentoSemCaptura(final Tabuleiro tabuleiro,
                            final Peca pecaMovida,
                            final int coordenadaDestino) {
            super(tabuleiro, pecaMovida, coordenadaDestino);
        }
    }

    public static final class MovimentoDeCaptura extends Movimento {
        final Peca pecaCapturada;

        public MovimentoDeCaptura(final Tabuleiro tabuleiro,
                                  final Peca pecaMovida,
                                  final int coordenadaDestino,
                                  final Peca pecaCapturada) {
            super(tabuleiro, pecaMovida, coordenadaDestino);
            this.pecaCapturada = pecaCapturada;
        }
    }

}

