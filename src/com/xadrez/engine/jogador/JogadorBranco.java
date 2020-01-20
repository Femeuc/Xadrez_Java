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

public class JogadorBranco extends Jogador {
    public JogadorBranco(final Tabuleiro tabuleiro,
                         final Collection<Movimento> movimentosLegaisDoBranco,
                         final Collection<Movimento> movimentosLegaisDoPreto) {
        super(tabuleiro, movimentosLegaisDoBranco, movimentosLegaisDoPreto);
    }

    @Override
    public Collection<Peca> getPecasAtivas() {
        return this.tabuleiro.getPecasBrancas();
    }

    @Override
    public Cor getCor() {
        return Cor.BRANCO;
    }

    @Override
    public Jogador getOponente() {
        return this.tabuleiro.jogadorPreto();
    }

    @Override
    protected Collection<Movimento> calcularRoquesDoRei(final Collection<Movimento> movimentosDestejogador,
                                                        final Collection<Movimento> movimentosDoOponente) {
        final List<Movimento> roquesDoRei = new ArrayList<>();
        if(this.rei.isPrimeiroMovimento() && !this.isEmCheck()) {
            // Roque pequeno das brancas
            if(!this.tabuleiro.getQuadrado(61).isOcupado() &&
                    !this.tabuleiro.getQuadrado(62).isOcupado()) {
                final Quadrado quadradoDaTorre = this.tabuleiro.getQuadrado(63);
                if(quadradoDaTorre.isOcupado() && quadradoDaTorre.getPeca().isPrimeiroMovimento()) {
                    if(Jogador.calcularAtaquesEmQuadrado(61, movimentosDoOponente).isEmpty() &&
                            Jogador.calcularAtaquesEmQuadrado(62, movimentosDoOponente).isEmpty() &&
                                    quadradoDaTorre.getPeca().getTipoDePeca().isTorre())
                    roquesDoRei.add(new Movimento.MovimentoDeRoquePequeno(this.tabuleiro,
                                                                          this.rei, 62,
                                                                          (Torre) quadradoDaTorre.getPeca(),
                                                                          quadradoDaTorre.getCoordenadaDoQuadrado(), 61));
                }
            }
            if(!this.tabuleiro.getQuadrado(59).isOcupado() &&
            !this.tabuleiro.getQuadrado(58).isOcupado() &&
            !this.tabuleiro.getQuadrado(57).isOcupado()) {
                final Quadrado quadradoDaTorre = this.tabuleiro.getQuadrado(56);
                if(quadradoDaTorre.isOcupado() && quadradoDaTorre.getPeca().isPrimeiroMovimento()) {
                    roquesDoRei.add(new Movimento.MovimentoDeRoqueGrande(this.tabuleiro, this.rei, 68,
                                                                        (Torre) quadradoDaTorre.getPeca(),
                                                                        quadradoDaTorre.getCoordenadaDoQuadrado(), 59));
                }
            }
        }
        return roquesDoRei;
    }
}
