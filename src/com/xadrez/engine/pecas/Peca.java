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
    private final int cachedHashCode;


    Peca(final TipoDePeca tipoDePeca,
         final int posicaoPeca,
         final Cor corPeca) {
        this.tipoDePeca = tipoDePeca;
        this.posicaoPeca = posicaoPeca;
        this.corPeca = corPeca;
        this.isPrimeiroMovimento = false;
        this.cachedHashCode = computeHashCode();
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    private int computeHashCode() {
        int resultado = this.tipoDePeca.hashCode();
        resultado = 31 * resultado + this.corPeca.hashCode();
        resultado = 31 * resultado + this.posicaoPeca;
        resultado = 31 * resultado + (this.isPrimeiroMovimento ? 1 : 0);
        return resultado;
    }

    @Override
    public boolean equals(final Object outra) {
        if(this == outra) {
            return true;
        }
        if(!(outra instanceof Peca)) {
            return false;
        }
        final Peca outraPeca = (Peca) outra;
        return posicaoPeca == outraPeca.getPosicaoPeca() &&
                tipoDePeca == outraPeca.getTipoDePeca() &&
                corPeca == outraPeca.getCorPeca() &&
                isPrimeiroMovimento == outraPeca.isPrimeiroMovimento();
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

    public abstract Peca moverPeca(Movimento movimento);

    public enum TipoDePeca {
        PEAO("P") {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return false;
            }
        },
        CAVALO("C") {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return false;
            }
        },
        BISPO("B") {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return false;
            }
        },
        TORRE("T") {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return true;
            }
        },
        RAINHA("D") {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return false;
            }
        }, // D de "dama"
        REI("R") {
            @Override
            public boolean isRei() {
                return true;
            }

            @Override
            public boolean isTorre() {
                return false;
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

        public abstract boolean isTorre();
    }

}
