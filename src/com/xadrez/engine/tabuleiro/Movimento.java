package com.xadrez.engine.tabuleiro;

import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.tabuleiro.Tabuleiro.Builder;

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

    public int getCoordenadaDeDestino() {
        return this.coordenadaDestino;
    }

    public abstract Tabuleiro executar();

    public static final class MovimentoSemCaptura extends Movimento {
        public MovimentoSemCaptura(final Tabuleiro tabuleiro,
                            final Peca pecaMovida,
                            final int coordenadaDestino) {
            super(tabuleiro, pecaMovida, coordenadaDestino);
        }

        @Override
        public Tabuleiro executar() {
            final Builder builder = new Builder();
            for(final Peca peca : this.tabuleiro.jogadorAtual().getPecasAtivas()) {
                if(!this.pecaMovida.equals(peca)) {
                    builder.setPeca(peca);
                }
            }
            for(final Peca peca : this.tabuleiro.jogadorAtual().getOponente().getPecasAtivas()) {
                builder.setPeca(peca);
            }
            builder.setPeca(null);
            builder.setTurnoDoJogador(this.tabuleiro.jogadorAtual().getOponente().getCor());
            return builder.build();
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

        @Override
        public Tabuleiro executar() {
            return null;
        }
    }

}

