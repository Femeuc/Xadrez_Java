package com.xadrez.engine.pecas;

import com.xadrez.engine.Cor;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Quadrado;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.List;

public class Cavalo extends Peca {

    private final static int[] MOVIMENTOS_POSSIVEIS = {-17, -15, -10, -6, 6,  10, 15, 17};

    Cavalo(final int posicaoPeca, final Cor corPeca) {
        super(posicaoPeca, corPeca);
    }

    @Override
    public List<Movimento> calcularMovimentosLegais(Tabuleiro tabuleiro) {

        int movimentoCandidato;
        final List<Movimento> movimentosLegais = new ArrayList<>();

        for(final int movimentoAtual : MOVIMENTOS_POSSIVEIS) {
            movimentoCandidato = this.posicaoPeca + movimentoAtual;
            if(true) {
                final Quadrado quadradoCandidato = tabuleiro.getQuadrado(movimentoCandidato);
                if(!quadradoCandidato.isOcupado()) {
                    movimentosLegais.add(new Movimento());
                } else {
                    final Peca destinoPeca = quadradoCandidato.getPeca();
                    final Cor corPeca = destinoPeca.getCorPeca();
                    if(this.corPeca != corPeca) {
                        movimentosLegais.add(new Movimento());
                    }
                }
            }
        }

        return movimentosLegais;
    }
}
