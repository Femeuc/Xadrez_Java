package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.Collection;

public abstract class Peca {

    protected final int posicaoPeca;
    protected final Cor corPeca;

    Peca(final int posicaoPeca, final Cor corPeca) {
        this.posicaoPeca = posicaoPeca;
        this.corPeca = corPeca;
    }

    public Cor getCorPeca() {
        return corPeca;
    }

    public abstract Collection<Movimento> calcularMovimentosLegais(final Tabuleiro tabuleiro);

}
