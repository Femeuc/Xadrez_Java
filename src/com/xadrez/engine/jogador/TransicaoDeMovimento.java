package com.xadrez.engine.jogador;

import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

public class TransicaoDeMovimento {
    private final Tabuleiro tabuleiroEmTransicao;
    private final Movimento movimento;
    private final StatusDeMovimento statusDeMovimento;

    public TransicaoDeMovimento(final Tabuleiro tabuleiroEmTransicao,
                                final Movimento movimento,
                                final StatusDeMovimento statusDeMovimento) {
        this.tabuleiroEmTransicao = tabuleiroEmTransicao;
        this.movimento = movimento;
        this.statusDeMovimento = statusDeMovimento;
    }
}
