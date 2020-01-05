package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;
import com.xadrez.engine.tabuleiro.TabuleiroUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Peao extends Peca{
    private final static int[] MOVIMENTOS_POSSIVEIS = {8};

    Peao(int posicaoPeca, Cor corPeca) {
        super(posicaoPeca, corPeca);
    }

    @Override
    public Collection<Movimento> calcularMovimentosLegais(Tabuleiro tabuleiro) {
        final List<Movimento> movimentosLegais = new ArrayList<>();
        int movimentoCandidato;
        for(final int movimentoAtual : MOVIMENTOS_POSSIVEIS) {
            movimentoCandidato = this.posicaoPeca + (this.getCorPeca().getDirecao() * movimentoAtual);
            if(!TabuleiroUtil.isQuadradoValido(movimentoCandidato)) {
                continue;
            }
            if(movimentoAtual == 8 && !tabuleiro.getQuadrado(movimentoCandidato).isOcupado()) {
                movimentosLegais.add(new Movimento.MovimentoSemCaptura(tabuleiro, this, movimentoCandidato));
            }
        }
        return null;
    }
}
