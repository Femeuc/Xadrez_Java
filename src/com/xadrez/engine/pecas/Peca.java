package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.Collection;

public abstract class Peca {

    protected final TipoDePeca tipoDePeca;
    protected final int posicaoPeca;
    protected final Cor corPeca;
    protected final boolean isPrimeiroMovimento;

    Peca(final TipoDePeca tipoDePeca,
         final int posicaoPeca,
         final Cor corPeca) {
        this.tipoDePeca = tipoDePeca;
        this.posicaoPeca = posicaoPeca;
        this.corPeca = corPeca;
        this.isPrimeiroMovimento = false;
    }

    public int getPosicaoPeca() {
        return this.posicaoPeca;
    }

    public Cor getCorPeca() {
        return corPeca;
    }

    public boolean isPrimeiroMovimento() {
        return this.isPrimeiroMovimento;
    }

    public TipoDePeca getTipoDePeca() {
        return this.tipoDePeca;
    }

    public abstract Collection<Movimento> calcularMovimentosLegais(final Tabuleiro tabuleiro);

    public enum TipoDePeca {
        PEAO("P") {
            @Override
            public boolean isRei() {
                return false;
            }
        },
        CAVALO("C") {
            @Override
            public boolean isRei() {
                return false;
            }
        },
        BISPO("B") {
            @Override
            public boolean isRei() {
                return false;
            }
        },
        TORRE("T") {
            @Override
            public boolean isRei() {
                return false;
            }
        },
        RAINHA("D") {
            @Override
            public boolean isRei() {
                return false;
            }
        }, // D de "dama"
        REI("R") {
            @Override
            public boolean isRei() {
                return true;
            }
        };

        private String nomeDaPeca;

        TipoDePeca(final String nomeDaPeca) {
            this.nomeDaPeca = nomeDaPeca;
        }

        @Override
        public String toString() {
            return this.nomeDaPeca;
        }

        public abstract boolean isRei();
    }

}
