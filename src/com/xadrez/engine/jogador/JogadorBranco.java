package com.xadrez.engine.jogador;

import com.xadrez.engine.Cor;
import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.Collection;

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
}
