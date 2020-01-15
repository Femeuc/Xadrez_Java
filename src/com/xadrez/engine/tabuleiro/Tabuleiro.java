package com.xadrez.engine.tabuleiro;

import com.xadrez.engine.Cor;
import com.xadrez.engine.jogador.Jogador;
import com.xadrez.engine.jogador.JogadorBranco;
import com.xadrez.engine.jogador.JogadorPreto;
import com.xadrez.engine.pecas.*;

import java.util.*;

public class Tabuleiro {

    private final Quadrado[] tabuleiroDoJogo;
    private final Collection<Peca> pecasBrancas;
    private final Collection<Peca> pecasPretas;

    private final JogadorBranco jogadorBranco;
    private final JogadorPreto jogadorPreto;
    private final Jogador jogadorAtual;

    private Tabuleiro(final Builder builder) {
        this.tabuleiroDoJogo = criarTabuleiroDoJogo(builder);
        this.pecasBrancas = calcularPecasAtivas(this.tabuleiroDoJogo, Cor.BRANCO);
        this.pecasPretas = calcularPecasAtivas(this.tabuleiroDoJogo, Cor.PRETO);
        final Collection<Movimento> movimentosLegaisDoBranco = calcularMovimentosLegais(this.pecasBrancas);
        final Collection<Movimento> movimentosLegaisDoPreto = calcularMovimentosLegais(this.pecasPretas);
        this.jogadorBranco = new JogadorBranco(this, movimentosLegaisDoBranco, movimentosLegaisDoPreto);
        this.jogadorPreto = new JogadorPreto(this, movimentosLegaisDoBranco, movimentosLegaisDoPreto);
        this.jogadorAtual = builder.turnoDoJogador.escolherJogador(this.jogadorBranco, this.jogadorPreto);
    }

    @Override
    public String toString() {
        final StringBuilder construtor = new StringBuilder();
        for(int i = 0; i < TabuleiroUtil.QUANTIDADE_QUADRADOS; i++) {
            final String textoDoQuadrado = this.tabuleiroDoJogo[i].toString();
            construtor.append(String.format("%3s", textoDoQuadrado));
            if((i + 1) % TabuleiroUtil.QUADRADOS_POR_LINHA == 0) {
                construtor.append("\n");
            }
        }
        return construtor.toString();
    }

    public Jogador jogadorBranco() {
        return this.jogadorBranco;
    }

    public Jogador jogadorPreto() {
        return this.jogadorPreto;
    }

    public Jogador jogadorAtual() {
        return this.jogadorAtual;
    }

    public Collection<Peca> getPecasPretas() {
        return this.pecasPretas;
    }

    public Collection<Peca> getPecasBrancas() {
        return this.pecasBrancas;
    }

    private Collection<Movimento> calcularMovimentosLegais(final Collection<Peca> pecas) {
        final List<Movimento> movimentosLegais = new ArrayList<>();
        for(final Peca peca : pecas) {
            movimentosLegais.addAll(peca.calcularMovimentosLegais(this));
        }
        return movimentosLegais;
    }

    private static Collection<Peca> calcularPecasAtivas(Quadrado[] tabuleiroDoJogo, Cor cor) {
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

    public static Tabuleiro criarTabuleiroPadrao() {
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
            this.configuracaoDoTabuleiro = new HashMap<>();
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
