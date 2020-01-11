package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.Collection;

public abstract class Peca {

    protected final int posicaoPeca;
    protected final Cor corPeca;
    protected final boolean isPrimeiroMovimento;

    Peca(final int posicaoPeca, final Cor corPeca) {
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

    public abstract Collection<Movimento> calcularMovimentosLegais(final Tabuleiro tabuleiro);

}
