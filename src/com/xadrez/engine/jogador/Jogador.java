package com.xadrez.engine.jogador;

import com.xadrez.engine.Cor;
import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.pecas.Rei;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Jogador {
    protected final Tabuleiro tabuleiro;
    protected final Rei rei;
    protected final Collection<Movimento> movimentosLegais;
    private final boolean isEmCheck;

    Jogador(final Tabuleiro tabuleiro,
            final Collection<Movimento> movimentosLegais,
            final Collection<Movimento> movimentosDoOponente) {
        this.tabuleiro = tabuleiro;
        this.rei = determinarRei();
//      TODO this.movimentosLegais = Iterables.concat(movimentosLegais, calcularRoquesDoRei(movimentosLegais, movimentosDoOponente);
        this.movimentosLegais = movimentosLegais;
        this.isEmCheck = !Jogador.calcularAtaquesEmQuadrado(this.rei.getPosicaoPeca(), movimentosDoOponente).isEmpty();
    }

    public Rei getReiDoJogador() {
        return this.rei;
    }

    public Collection<Movimento> getMovimentosLegais() {
        return this.movimentosLegais;
    }

    protected static Collection<Movimento> calcularAtaquesEmQuadrado(int posicaoPeca, Collection<Movimento> movimentos) {
        final List<Movimento> movimentosDeAtaque = new ArrayList<>();
        for(final Movimento movimento : movimentos) {
            if(posicaoPeca == movimento.getCoordenadaDeDestino()) {
                movimentosDeAtaque.add(movimento);
            }
        }
        return movimentosDeAtaque;
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
        return this.isEmCheck;
    }

    public boolean isEmCheckMate() {
        return this.isEmCheck && !temMovimentosDeFuga();
    }

    public boolean IsEmStaleMate() {
        return !this.isEmCheck && !temMovimentosDeFuga();
    }

    protected boolean temMovimentosDeFuga() {
        for(final Movimento movimento : this.movimentosLegais) {
            final TransicaoDeMovimento transicao = fazerMovimento(movimento);
            if(transicao.getStatusDeMovimento().isFeito()) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmRoque() {
        return false;
    }

    public TransicaoDeMovimento fazerMovimento(final Movimento movimento) {
        if(!isMovimentoLegal(movimento)) {
            return new TransicaoDeMovimento(this.tabuleiro, movimento, StatusDeMovimento.MOVIMENTO_ILEGAL);
        }
        final Tabuleiro tabuleiroDeTransicao = movimento.executar();
        final Collection<Movimento> ataquesAoRei = Jogador.calcularAtaquesEmQuadrado(tabuleiroDeTransicao.jogadorAtual().getOponente().getReiDoJogador().getPosicaoPeca(),
                tabuleiroDeTransicao.jogadorAtual().getMovimentosLegais());
        if(!ataquesAoRei.isEmpty()) {
            return new TransicaoDeMovimento(this.tabuleiro, movimento, StatusDeMovimento.DEIXA_JOGADOR_EM_CHECK);
        }
        return new TransicaoDeMovimento(tabuleiroDeTransicao, movimento, StatusDeMovimento.FEITO);
    }

    public abstract Collection<Peca> getPecasAtivas();
    public abstract Cor getCor();
    public abstract Jogador getOponente();
    protected abstract Collection<Movimento> calcularRoquesDoRei(Collection<Movimento> jogador, Collection<Movimento> oponente);
}
