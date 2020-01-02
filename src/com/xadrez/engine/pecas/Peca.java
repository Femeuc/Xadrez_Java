package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.List;

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

    public abstract List<Movimento> calcularMovimentosLegais(final Tabuleiro tabuleiro);

}
