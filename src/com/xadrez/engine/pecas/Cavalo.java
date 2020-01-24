package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Quadrado;
import com.xadrez.engine.tabuleiro.Tabuleiro;
import com.xadrez.engine.tabuleiro.TabuleiroUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.xadrez.engine.tabuleiro.Movimento.*;

public class Cavalo extends Peca {

    private final static int[] MOVIMENTOS_POSSIVEIS = {-17, -15, -10, -6, 6,  10, 15, 17};

    public Cavalo(Cor corPeca, int posicaoPeca) {
        super(TipoDePeca.CAVALO, posicaoPeca, corPeca, true);
    }

    public Cavalo(Cor corPeca, int posicaoPeca, boolean isPrimeiroMovimento) {
        super(TipoDePeca.CAVALO, posicaoPeca, corPeca, isPrimeiroMovimento);
    }

    @Override
    public Collection<Movimento> calcularMovimentosLegais(final  Tabuleiro tabuleiro) {

        int movimentoCandidato;
        final List<Movimento> movimentosLegais = new ArrayList<>();

        for(final int movimentoAtual : MOVIMENTOS_POSSIVEIS) {
            movimentoCandidato = this.posicaoPeca + movimentoAtual;
            if(TabuleiroUtil.isQuadradoValido(movimentoCandidato)) {
                if(isExclusaoPrimeiraColuna(this.posicaoPeca, movimentoAtual) ||
                        isExclusaoSegundaColuna(this.posicaoPeca, movimentoAtual) ||
                        isExclusaoSetimaColuna(this.posicaoPeca, movimentoAtual) ||
                        isExclusaoOitavaColuna(this.posicaoPeca, movimentoAtual)) {
                    continue;
                }
                final Quadrado quadradoCandidato = tabuleiro.getQuadrado(movimentoCandidato);
                if(!quadradoCandidato.isOcupado()) {
                    movimentosLegais.add(new MovimentoSemCaptura(tabuleiro, this, movimentoCandidato));
                } else {
                    final Peca destinoPeca = quadradoCandidato.getPeca();
                    final Cor corPeca = destinoPeca.getCorPeca();
                    if(this.corPeca != corPeca) {
                        movimentosLegais.add(new MovimentoDeCaptura(tabuleiro, this, movimentoCandidato, destinoPeca));
                    }
                }
            }
        }

        return movimentosLegais;
    }

    @Override
    public Cavalo moverPeca(Movimento movimento) {
        return new Cavalo(movimento.getPecaMovida().getCorPeca(), movimento.getCoordenadaDeDestino());
    }

    @Override
    public String toString() {
        return TipoDePeca.CAVALO.toString();
    }

    private static boolean isExclusaoPrimeiraColuna(final int posicaoAtual, final int posicaoCandidata) {
        return TabuleiroUtil.PRIMEIRA_COLUNA[posicaoAtual] && (posicaoCandidata == -17 || posicaoCandidata == -10 || posicaoCandidata == 6 || posicaoCandidata == 15);
    }

    private static boolean isExclusaoSegundaColuna(final int posicaoAtual, final int posicaoCandidata) {
        return TabuleiroUtil.SEGUNDA_COLUNA[posicaoAtual] && (posicaoCandidata == -10 || posicaoCandidata == 6);
    }

    private static boolean isExclusaoSetimaColuna(final int posicaoAtual, final int posicaoCandidata) {
        return TabuleiroUtil.SETIMA_COLUNA[posicaoAtual] && (posicaoCandidata == -6 || posicaoCandidata == 10);
    }

    private static boolean isExclusaoOitavaColuna(final int posicaoAtual, final int posicaoCandidata) {
        return TabuleiroUtil.OITAVA_COLUNA[posicaoAtual] && (posicaoCandidata == 17 || posicaoCandidata == 10 || posicaoCandidata == -6 || posicaoCandidata == -15);
    }

}
