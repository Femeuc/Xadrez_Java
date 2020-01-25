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
         final Cor corPeca,
         final boolean isPrimeiroMovimento) {
        this.tipoDePeca = tipoDePeca;
        this.posicaoPeca = posicaoPeca;
        this.corPeca = corPeca;
        this.isPrimeiroMovimento = isPrimeiroMovimento;
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

    public int getValorDaPeca() {
        return this.tipoDePeca.getValorDaPeca();
    }

    public abstract Collection<Movimento> calcularMovimentosLegais(final Tabuleiro tabuleiro);

    public abstract Peca moverPeca(Movimento movimento);

    public enum TipoDePeca {
        PEAO("P", 100) {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return false;
            }
        },
        CAVALO("C", 300) {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return false;
            }
        },
        BISPO("B", 300) {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return false;
            }
        },
        TORRE("T", 500) {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return true;
            }
        },
        RAINHA("D", 900) {
            @Override
            public boolean isRei() {
                return false;
            }

            @Override
            public boolean isTorre() {
                return false;
            }
        }, // D de "dama"
        REI("R", 10000) {
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
        private int valorDaPeca;

        TipoDePeca(final String nomeDaPeca, final int valorDaPeca) {
            this.nomeDaPeca = nomeDaPeca;
            this.valorDaPeca = valorDaPeca;
        }

        @Override
        public String toString() {
            return this.nomeDaPeca;
        }

        public int getValorDaPeca() {
            return this.valorDaPeca;
        }

        public abstract boolean isRei();

        public abstract boolean isTorre();
    }

}
