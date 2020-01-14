package com.xadrez.engine.jogador;

import com.xadrez.engine.Cor;
import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.pecas.Rei;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.Collection;

public abstract class Jogador {
    protected final Tabuleiro tabuleiro;
    protected final Rei rei;
    protected final Collection<Movimento> movimentosLegais;

    Jogador(final Tabuleiro tabuleiro,
            final Collection<Movimento> movimentosLegais,
            final Collection<Movimento> movimentosDoOponente) {
        this.tabuleiro = tabuleiro;
        this.rei = determinarRei();
        this.movimentosLegais = movimentosLegais;
    }

    private Rei determinarRei() {
        for(final Peca peca : getPecasAtivas()) {
            if(peca.getTipoDePeca().isRei()) {
                return (Rei) peca;
            }
        }
        throw new RuntimeException("Não deve chegar até aqui! Tabuleiro inválido!");
    }

    public boolean isMovimentoLegal(final Movimento movimento) {
        return this.movimentosLegais.contains(movimento);
    }

    public boolean isEmCheck() {
        return false;
    }

    public boolean isEmCheckMate() {
        return false;
    }

    public boolean IsEmStaleMate() {
        return false;
    }

    public boolean isEmRoque() {
        return false;
    }

    public TransicaoDeMovimento fazerMovimento() {
        return null;
    }

    public abstract Collection<Peca> getPecasAtivas();

    public abstract Cor getCor();

    public abstract Jogador getOponente();
}
