package com.xadrez.engine.tabuleiro;

import com.xadrez.engine.Cor;
import com.xadrez.engine.pecas.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Tabuleiro {

    private final Quadrado[] tabuleiroDoJogo;
    private final Collection<Peca> pecasBrancas;
    private final Collection<Peca> pecasPretas;

    private Tabuleiro(Builder builder) {
        this.tabuleiroDoJogo = criarTabuleiroDoJogo(builder);
        this.pecasBrancas = calcularPecasAtivas(this.tabuleiroDoJogo, Cor.BRANCO);
        this.pecasPretas = calcularPecasAtivas(this.tabuleiroDoJogo, Cor.PRETO);
    }

    private Collection<Peca> calcularPecasAtivas(Quadrado[] tabuleiroDoJogo, Cor cor) {
        final List<Peca> pecasAtivas = new ArrayList<>();
        for(final Quadrado quadrado : tabuleiroDoJogo) {
            if(quadrado.isOcupado()) {
                final Peca peca = quadrado.getPeca();
                if(peca.getCorPeca() == cor) {
                    pecasAtivas.add(peca);
                }
            }
        }
        return pecasAtivas;
    }

    public Quadrado getQuadrado(final int coordenada) {
        return tabuleiroDoJogo[coordenada];
    }

    private static Quadrado[] criarTabuleiroDoJogo(final Builder builder) {
        final Quadrado[] quadrados = new Quadrado[TabuleiroUtil.QUANTIDADE_QUADRADOS];
        for(int i = 0; i < TabuleiroUtil.QUANTIDADE_QUADRADOS; i++) {
            quadrados[i] = Quadrado.criarQuadrado(i, builder.configuracaoDoTabuleiro.get(i));
        }
        return quadrados;
    }

    private static Tabuleiro criarTabuleiroPadrao() {
        final Builder builder = new Builder();
        // Layout das Peças Pretas
        builder.setPeca(new Torre(Cor.PRETO, 0));
        builder.setPeca(new Cavalo(Cor.PRETO, 1));
        builder.setPeca(new Bispo(Cor.PRETO, 2));
        builder.setPeca(new Rainha(Cor.PRETO, 3));
        builder.setPeca(new Rei(Cor.PRETO, 4));
        builder.setPeca(new Bispo(Cor.PRETO, 5));
        builder.setPeca(new Cavalo(Cor.PRETO, 6));
        builder.setPeca(new Torre(Cor.PRETO, 7));
        builder.setPeca(new Peao(Cor.PRETO, 8));
        builder.setPeca(new Peao(Cor.PRETO, 9));
        builder.setPeca(new Peao(Cor.PRETO, 10));
        builder.setPeca(new Peao(Cor.PRETO, 11));
        builder.setPeca(new Peao(Cor.PRETO, 12));
        builder.setPeca(new Peao(Cor.PRETO, 13));
        builder.setPeca(new Peao(Cor.PRETO, 14));
        builder.setPeca(new Peao(Cor.PRETO, 15));
        // Layout das Peças Brancas
        builder.setPeca(new Peao(Cor.BRANCO, 48));
        builder.setPeca(new Peao(Cor.BRANCO, 49));
        builder.setPeca(new Peao(Cor.BRANCO, 50));
        builder.setPeca(new Peao(Cor.BRANCO, 51));
        builder.setPeca(new Peao(Cor.BRANCO, 52));
        builder.setPeca(new Peao(Cor.BRANCO, 53));
        builder.setPeca(new Peao(Cor.BRANCO, 54));
        builder.setPeca(new Peao(Cor.BRANCO, 55));
        builder.setPeca(new Torre(Cor.BRANCO, 56));
        builder.setPeca(new Cavalo(Cor.BRANCO, 57));
        builder.setPeca(new Bispo(Cor.BRANCO, 58));
        builder.setPeca(new Rainha(Cor.BRANCO, 59));
        builder.setPeca(new Rei(Cor.BRANCO, 60));
        builder.setPeca(new Bispo(Cor.BRANCO, 61));
        builder.setPeca(new Cavalo(Cor.BRANCO, 62));
        builder.setPeca(new Torre(Cor.BRANCO, 63));
        //Turno do Branco
        builder.setTurnoDoJogador(Cor.BRANCO);
        //Constrói o Tabuleiro
        return builder.build();
    }

    public static class Builder {
        Map<Integer, Peca> configuracaoDoTabuleiro;
        Cor turnoDoJogador;

        public Builder() {
        }

        public Builder setPeca(final Peca peca) {
            this.configuracaoDoTabuleiro.put(peca.getPosicaoPeca(), peca);
            return this;
        }

        public Builder setTurnoDoJogador(final Cor turnoDoJogador) {
            this.turnoDoJogador = turnoDoJogador;
            return this;
        }

        public Tabuleiro build() {
            return new Tabuleiro(this);
        }
    }
}
