package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Movimento.MovimentoSemCaptura;
import com.xadrez.engine.tabuleiro.Tabuleiro;
import com.xadrez.engine.tabuleiro.TabuleiroUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Peao extends Peca{
    private final static int[] MOVIMENTOS_POSSIVEIS = {8, 16};

    Peao(final int posicaoPeca, final Cor corPeca) {
        super(posicaoPeca, corPeca);
    }

    @Override
    public Collection<Movimento> calcularMovimentosLegais(final Tabuleiro tabuleiro) {
        final List<Movimento> movimentosLegais = new ArrayList<>();
        final int movimentoCandidato;
        for(final int movimentoAtual : MOVIMENTOS_POSSIVEIS) {
            movimentoCandidato = this.posicaoPeca + (this.getCorPeca().getDirecao() * movimentoAtual);
            if(!TabuleiroUtil.isQuadradoValido(movimentoCandidato)) {
                continue;
            }
            if(movimentoAtual == 8 && !tabuleiro.getQuadrado(movimentoCandidato).isOcupado()) {
                movimentosLegais.add(new MovimentoSemCaptura(tabuleiro, this, movimentoCandidato));
            } else if(movimentoAtual == 16 && this.isPrimeiroMovimento() &&
                    (TabuleiroUtil.SEGUNDA_LINHA[this.posicaoPeca] && this.getCorPeca().isPreto()) ||
                    (TabuleiroUtil.SETIMA_LINHA[this.posicaoPeca] && this.getCorPeca().isBranco())) {
                final int atrasMovimentoCandidato = this.posicaoPeca + (this.corPeca.getDirecao() * 8 );
                if(!tabuleiro.getQuadrado(atrasMovimentoCandidato).isOcupado() && !tabuleiro.getQuadrado(movimentoCandidato).isOcupado()) {
                    movimentosLegais.add(new MovimentoSemCaptura(tabuleiro, this, movimentoCandidato));
                }
            }
        }
        return null;
    }
}
