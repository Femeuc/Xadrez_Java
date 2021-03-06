package com.xadrez.engine.jogador;

import com.xadrez.engine.Cor;
import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.pecas.Torre;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Quadrado;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JogadorPreto extends Jogador {
    public JogadorPreto(final Tabuleiro tabuleiro,
                        final Collection<Movimento> movimentosLegaisDoBranco,
                        final Collection<Movimento> movimentosLegaisDoPreto) {
        super(tabuleiro, movimentosLegaisDoPreto, movimentosLegaisDoBranco);
    }

    @Override
    public Collection<Peca> getPecasAtivas() {
        return this.tabuleiro.getPecasPretas();
    }

    @Override
    public Cor getCor() {
        return Cor.PRETO;
    }

    @Override
    public Jogador getOponente() {
        return this.tabuleiro.jogadorBranco();
    }

    @Override
    protected Collection<Movimento> calcularRoquesDoRei(final Collection<Movimento> movimentosDestejogador,
                                                        final Collection<Movimento> movimentosDoOponente) {
        final List<Movimento> roquesDoRei = new ArrayList<>();
        if(this.rei.isPrimeiroMovimento() && !this.isEmCheck()) {
            // Roque pequeno das negras
            if(!this.tabuleiro.getQuadrado(5).isOcupado() &&
                    !this.tabuleiro.getQuadrado(6).isOcupado()) {
                final Quadrado quadradoDaTorre = this.tabuleiro.getQuadrado(7);
                if(quadradoDaTorre.isOcupado() && quadradoDaTorre.getPeca().isPrimeiroMovimento()) {
                    if(Jogador.calcularAtaquesEmQuadrado(5, movimentosDoOponente).isEmpty() &&
                            Jogador.calcularAtaquesEmQuadrado(6, movimentosDoOponente).isEmpty() &&
                            quadradoDaTorre.getPeca().getTipoDePeca().isTorre())
                        roquesDoRei.add(new Movimento.MovimentoDeRoquePequeno(this.tabuleiro, this.rei, 6,
                                                                            (Torre) quadradoDaTorre.getPeca(),
                                                                            quadradoDaTorre.getCoordenadaDoQuadrado(), 5));
                }
            }
            if(!this.tabuleiro.getQuadrado(1).isOcupado() &&
                    !this.tabuleiro.getQuadrado(2).isOcupado() &&
                    !this.tabuleiro.getQuadrado(3).isOcupado()) {
                final Quadrado quadradoDaTorre = this.tabuleiro.getQuadrado(0);
                if(quadradoDaTorre.isOcupado() && quadradoDaTorre.getPeca().isPrimeiroMovimento() &&
                    Jogador.calcularAtaquesEmQuadrado(2, movimentosDoOponente).isEmpty() &&
                    Jogador.calcularAtaquesEmQuadrado(3, movimentosDestejogador).isEmpty() &&
                    quadradoDaTorre.getPeca().getTipoDePeca().isTorre()) {
                    roquesDoRei.add(new Movimento.MovimentoDeRoqueGrande(this.tabuleiro, this.rei, 2,
                                                                        (Torre) quadradoDaTorre.getPeca(),
                                                                        quadradoDaTorre.getCoordenadaDoQuadrado(), 3));
                }
            }
        }
        return roquesDoRei;
    }
}
