package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Movimento.MovimentoDeCaptura;
import com.xadrez.engine.tabuleiro.Movimento.MovimentoSemCaptura;
import com.xadrez.engine.tabuleiro.Quadrado;
import com.xadrez.engine.tabuleiro.Tabuleiro;
import com.xadrez.engine.tabuleiro.TabuleiroUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.xadrez.engine.tabuleiro.Movimento.*;

public class Rei extends Peca{
    private final static int[] MOVIMENTOS_POSSIVEIS = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Rei(final Cor corPeca, final int posicaoPeca) {
        super(TipoDePeca.REI, posicaoPeca, corPeca, true);
    }

    public Rei(final Cor corPeca, final int posicaoPeca, boolean isPrimeiroMovimento) {
        super(TipoDePeca.REI, posicaoPeca, corPeca, isPrimeiroMovimento);
    }

    @Override
    public Collection<Movimento> calcularMovimentosLegais(Tabuleiro tabuleiro) {
        final List<Movimento> movimentosLegais = new ArrayList<>();
        int movimentoCandidato;
        for(final int movimentoAtual : MOVIMENTOS_POSSIVEIS) {
            movimentoCandidato = this.posicaoPeca + movimentoAtual;
            if(TabuleiroUtil.isQuadradoValido(movimentoCandidato)) {
                if(isExclusaoPrimeiraColuna(this.posicaoPeca, movimentoAtual) ||
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
                        movimentosLegais.add(new MovimentoDeCapturaMajor(tabuleiro, this, movimentoCandidato, destinoPeca));
                    }
                }
            }
        }

        return movimentosLegais;
    }

    @Override
    public Rei moverPeca(Movimento movimento) {
        return new Rei(movimento.getPecaMovida().getCorPeca(), movimento.getCoordenadaDeDestino());
    }

    @Override
    public String toString() {
        return TipoDePeca.REI.toString();
    }

    private static boolean isExclusaoPrimeiraColuna(final int posicaoAtual, final int posicaoCandidata) {
        return TabuleiroUtil.PRIMEIRA_COLUNA[posicaoAtual] && (posicaoCandidata == -9 || posicaoCandidata == -1 || posicaoCandidata == 7);
    }

    private static boolean isExclusaoOitavaColuna(final int posicaoAtual, final int posicaoCandidata) {
        return TabuleiroUtil.OITAVA_COLUNA[posicaoAtual] && (posicaoCandidata == 9 || posicaoCandidata == 1 || posicaoCandidata == -7);
    }
}
