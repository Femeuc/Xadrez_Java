package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Quadrado;
import com.xadrez.engine.tabuleiro.Tabuleiro;
import com.xadrez.engine.tabuleiro.TabuleiroUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Torre extends Peca{
    private final static int[] MOVIMENTOS_POSSIVEIS = {-8, -1, 1, 8};

    public Torre(Cor corPeca, int posicaoPeca) {
        super(TipoDePeca.TORRE, posicaoPeca, corPeca);
    }

    @Override
    public Collection<Movimento> calcularMovimentosLegais(final Tabuleiro tabuleiro) {
        final List<Movimento> movimentosLegais = new ArrayList<>();

        for(final int movimentoAtual : MOVIMENTOS_POSSIVEIS) {
            int movimentoCandidato = this.posicaoPeca;
            while(TabuleiroUtil.isQuadradoValido(movimentoCandidato)) {
                if(isExclusaoPrimeiraColuna(movimentoCandidato, movimentoAtual) ||
                        isExclusaoOitavaColuna(movimentoCandidato, movimentoAtual)) {
                    break;
                }
                movimentoCandidato += movimentoAtual;
                if(TabuleiroUtil.isQuadradoValido(movimentoCandidato)) {
                    final Quadrado quadradoCandidato = tabuleiro.getQuadrado(movimentoCandidato);
                    if(!quadradoCandidato.isOcupado()) {
                        movimentosLegais.add(new Movimento.MovimentoSemCaptura(tabuleiro, this, movimentoCandidato));
                    } else {
                        final Peca destinoPeca = quadradoCandidato.getPeca();
                        final Cor corPeca = destinoPeca.getCorPeca();
                        if(this.corPeca != corPeca) {
                            movimentosLegais.add(new Movimento.MovimentoDeCaptura(tabuleiro, this, movimentoCandidato, destinoPeca));
                        }
                        break; // Esse break é ativado quando alguma peça bloqueia movimentos do bispo, ficando no seu caminho
                    }
                }
            }
        }

        return movimentosLegais;
    }

    @Override
    public String toString() {
        return TipoDePeca.TORRE.toString();
    }

    private static boolean isExclusaoPrimeiraColuna(final int posicaoAtual, final int posicaoCandidata) {
        return TabuleiroUtil.PRIMEIRA_COLUNA[posicaoAtual] && (posicaoCandidata == -1);
    }
    private static boolean isExclusaoOitavaColuna(final int posicaoAtual, final int posicaoCandidata) {
        return TabuleiroUtil.OITAVA_COLUNA[posicaoAtual] && (posicaoCandidata == 1);
    }
}
