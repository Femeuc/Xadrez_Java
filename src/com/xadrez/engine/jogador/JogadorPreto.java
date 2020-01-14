package com.xadrez.engine.jogador;

import com.xadrez.engine.Cor;
import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.Collection;

public class JogadorPreto extends Jogador {
    public JogadorPreto(Tabuleiro tabuleiro,
                        Collection<Movimento> movimentosLegaisDoBranco,
                        Collection<Movimento> movimentosLegaisDoPreto) {
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
}
