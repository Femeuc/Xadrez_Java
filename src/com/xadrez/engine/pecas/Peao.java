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
    private final static int[] MOVIMENTOS_POSSIVEIS = {8, 7, 9, 16};

    public Peao(Cor corPeca, int posicaoPeca) {
        super(TipoDePeca.PEAO, posicaoPeca, corPeca);
    }

    @Override
    public Collection<Movimento> calcularMovimentosLegais(final Tabuleiro tabuleiro) {
        final List<Movimento> movimentosLegais = new ArrayList<>();
        int movimentoCandidato;
        for(final int movimentoAtual : MOVIMENTOS_POSSIVEIS) {
            movimentoCandidato = this.posicaoPeca + (this.corPeca.getDirecao() * movimentoAtual);
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
            } else if(movimentoAtual == 7 &&
                    !((TabuleiroUtil.OITAVA_COLUNA[this.posicaoPeca] && this.corPeca.isBranco()) ||
                      (TabuleiroUtil.PRIMEIRA_COLUNA[this.posicaoPeca] && this.corPeca.isPreto()) )) {
                if(tabuleiro.getQuadrado(movimentoCandidato).isOcupado()) {
                     final Peca pecaNoCandidato = tabuleiro.getQuadrado(movimentoCandidato).getPeca();
                     if(this.corPeca != pecaNoCandidato.getCorPeca()) {
                     }
                }

            } else if(movimentoAtual == 9 &&
                    !((TabuleiroUtil.PRIMEIRA_COLUNA[this.posicaoPeca] && this.corPeca.isBranco()) ||
                     (TabuleiroUtil.OITAVA_COLUNA[this.posicaoPeca] && this.corPeca.isPreto()) )) {
                if(tabuleiro.getQuadrado(movimentoCandidato).isOcupado()) {
                    final Peca pecaNoCandidato = tabuleiro.getQuadrado(movimentoCandidato).getPeca();
                    if(this.corPeca != pecaNoCandidato.getCorPeca()) {
                    }
                }
            }
        }
        return movimentosLegais;
    }

    @Override
    public Peao moverPeca(Movimento movimento) {
        return new Peao(movimento.getPecaMovida().getCorPeca(), movimento.getCoordenadaDeDestino());
    }

    @Override
    public String toString() {
        return TipoDePeca.PEAO.toString();
    }
}
