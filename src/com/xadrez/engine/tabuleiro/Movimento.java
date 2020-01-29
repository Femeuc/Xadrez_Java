package com.xadrez.engine.tabuleiro;

import com.xadrez.engine.pecas.Peao;
import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.pecas.Torre;
import com.xadrez.engine.tabuleiro.Tabuleiro.Builder;

public abstract class Movimento {
    protected final Tabuleiro tabuleiro;
    protected final Peca pecaMovida;
    protected final int coordenadaDestino;
    protected final boolean isPrimeiroMovimento;

    public static final Movimento MOVIMENTO_NULO = new MovimentoNulo();

    private Movimento(final Tabuleiro tabuleiro,
              final Peca pecaMovida,
              final int coordenadaDestino) {
        this.tabuleiro = tabuleiro;
        this.pecaMovida = pecaMovida;
        this.coordenadaDestino = coordenadaDestino;
        this.isPrimeiroMovimento = pecaMovida.isPrimeiroMovimento();
    }

    private Movimento(final Tabuleiro tabuleiro,
                      final int coordenadaDestino) {
        this.tabuleiro = tabuleiro;
        this.coordenadaDestino = coordenadaDestino;
        this.pecaMovida = null;
        this.isPrimeiroMovimento = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.coordenadaDestino;
        result = prime * result + this.pecaMovida.hashCode();
        result = prime * result + this.pecaMovida.getPosicaoPeca();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Movimento)) {
            return false;
        }
        final Movimento outroMovimento = (Movimento) o;
        return getCoordenadaAtual() == outroMovimento.getCoordenadaAtual() &&
                getCoordenadaDeDestino() == outroMovimento.getCoordenadaDeDestino() &&
                getPecaMovida().equals(outroMovimento.getPecaMovida());
    }

    public int getCoordenadaAtual() {
        return this.getPecaMovida().getPosicaoPeca();
    }

    public int getCoordenadaDeDestino() {
        return this.coordenadaDestino;
    }

    public Peca getPecaMovida() {
        return this.pecaMovida;
    }

    public boolean isCaptura() {
        return false;
    }

    public boolean isMovimentoDeRoque() {
        return false;
    }

    public Peca getPecaSobAtaque() {
        return null;
    }

    public Tabuleiro executar() {
        final Builder builder = new Builder();
        for(final Peca peca : this.tabuleiro.jogadorAtual().getPecasAtivas()) {
            if(!this.pecaMovida.equals(peca)) {
                builder.setPeca(peca);
            }
        }
        for(final Peca peca : this.tabuleiro.jogadorAtual().getOponente().getPecasAtivas()) {
            builder.setPeca(peca);
        }
        builder.setPeca(this.pecaMovida.moverPeca(this));
        builder.setTurnoDoJogador(this.tabuleiro.jogadorAtual().getOponente().getCor());
        return builder.build();
    }

    public static class MovimentoDeCapturaMajor extends MovimentoDeCaptura {
        public MovimentoDeCapturaMajor(final Tabuleiro tabuleiro,
                                       final Peca pecaMovida,
                                       final int coordenadaDeDestino,
                                       final Peca pecaAtacada) {
            super(tabuleiro, pecaMovida, coordenadaDeDestino, pecaAtacada);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MovimentoDeCapturaMajor && super.equals(other);
        }

        @Override
        public String toString() {
            return pecaMovida.getTipoDePeca() + TabuleiroUtil.getCoordenadaDePosicao(this.coordenadaDestino);
        }
    }

    public static final class MovimentoSemCaptura extends Movimento {
        public MovimentoSemCaptura(final Tabuleiro tabuleiro,
                            final Peca pecaMovida,
                            final int coordenadaDestino) {
            super(tabuleiro, pecaMovida, coordenadaDestino);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MovimentoSemCaptura && super.equals(other);
        }

        @Override
        public String toString() {
            return pecaMovida.getTipoDePeca().toString() + TabuleiroUtil.getCoordenadaDePosicao(this.coordenadaDestino);
        }

    }

    public static class MovimentoDeCaptura extends Movimento {
        final Peca pecaCapturada;

        public MovimentoDeCaptura(final Tabuleiro tabuleiro,
                                  final Peca pecaMovida,
                                  final int coordenadaDestino,
                                  final Peca pecaCapturada) {
            super(tabuleiro, pecaMovida, coordenadaDestino);
            this.pecaCapturada = pecaCapturada;
        }

        @Override
        public int hashCode() {
            return this.pecaCapturada.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) {
                return true;
            }
            if(!(o instanceof MovimentoDeCaptura)) {
                return false;
            }
            final MovimentoDeCaptura outroMovimentoDeCaptura = (MovimentoDeCaptura) o;
            return super.equals(outroMovimentoDeCaptura) &&
                    getPecaSobAtaque().equals(outroMovimentoDeCaptura.getPecaSobAtaque());
        }

        @Override
        public boolean isCaptura() {
            return true;
        }

        @Override
        public Peca getPecaSobAtaque() {
            return this.pecaCapturada;
        }
    }

    public static final class MovimentoDePeao extends Movimento {

        public MovimentoDePeao(final Tabuleiro tabuleiro,
                                final Peca pecaMovida,
                                int coordenadaDestino) {
            super(tabuleiro, pecaMovida, coordenadaDestino);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MovimentoDePeao && super.equals(other);
        }

        @Override
        public String toString() {
            return TabuleiroUtil.getCoordenadaDePosicao(this.coordenadaDestino);
        }
    }

    public static class MovimentoDeCapturaParaPeao extends MovimentoDeCaptura {

        public MovimentoDeCapturaParaPeao(final Tabuleiro tabuleiro,
                                           final Peca pecaMovida,
                                           int coordenadaDestino,
                                           final Peca pecaCapturada) {
            super(tabuleiro, pecaMovida, coordenadaDestino, pecaCapturada);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MovimentoDeCapturaParaPeao && super.equals(other);
        }

        @Override
        public String toString() {
            return TabuleiroUtil.getCoordenadaDePosicao(this.pecaMovida.getPosicaoPeca()).substring(0, 1) + "x" +
                    TabuleiroUtil.getCoordenadaDePosicao(this.coordenadaDestino);
        }
    }

    public static final class  MovimentoDeCapturaParaPeaoEnPassant  extends MovimentoDeCapturaParaPeao {

        public MovimentoDeCapturaParaPeaoEnPassant(final Tabuleiro tabuleiro,
                                           final Peca pecaMovida,
                                           int coordenadaDestino,
                                           final Peca pecaCapturada) {
            super(tabuleiro, pecaMovida, coordenadaDestino, pecaCapturada);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MovimentoDeCapturaParaPeaoEnPassant && super.equals(other);
        }

        @Override
        public Tabuleiro executar() {
            final Builder builder = new Builder();
            for(final Peca peca : this.tabuleiro.jogadorAtual().getPecasAtivas()) {
                if(!this.pecaMovida.equals(peca)) {
                    builder.setPeca(peca);
                }
            }
            for(final Peca peca : this.tabuleiro.jogadorAtual().getOponente().getPecasAtivas()) {
                if(!peca.equals(this.getPecaSobAtaque())) {
                    builder.setPeca(peca);
                }
            }
            builder.setPeca(this.pecaMovida.moverPeca(this));
            builder.setTurnoDoJogador(this.tabuleiro.jogadorAtual().getOponente().getCor());
            return builder.build();
        }
    }

    public static final class SaltoDePeao extends Movimento {

        public SaltoDePeao(final Tabuleiro tabuleiro,
                            final Peca pecaMovida,
                            int coordenadaDestino) {
            super(tabuleiro, pecaMovida, coordenadaDestino);
        }

        @Override
        public Tabuleiro executar() {
            final Builder builder = new Builder();
            for(final Peca peca : this.tabuleiro.jogadorAtual().getPecasAtivas()) {
                if(!this.pecaMovida.equals(peca)) {
                    builder.setPeca(peca);
                }
            }
            for(final Peca peca : this.tabuleiro.jogadorAtual().getOponente().getPecasAtivas()) {
                builder.setPeca(peca);
            }
            final Peao peaoMovido = (Peao) this.pecaMovida.moverPeca(this);
            builder.setPeca(peaoMovido);
            builder.setPeaoEnPassant(peaoMovido);
            builder.setTurnoDoJogador(this.tabuleiro.jogadorAtual().getOponente().getCor());
            return builder.build();
        }

        @Override
        public String toString() {
            return TabuleiroUtil.getCoordenadaDePosicao(this.coordenadaDestino);
        }
    }

    static abstract class MovimentoDeRoque extends Movimento {
        protected final Torre torreDeRoque;
        protected final int posicaoInicialDaTorreDeRoque;
        protected final int posicaoDeDestinoDaTorreDeRoque;

        public MovimentoDeRoque(final Tabuleiro tabuleiro,
                                final Peca pecaMovida,
                                final int coordenadaDestino,
                                final Torre torreDeRoque,
                                final int posicaoInicialDaTorreDeRoque,
                                final int posicaoDeDestinoDaTorreDeRoque) {
            super(tabuleiro, pecaMovida, coordenadaDestino);
            this.torreDeRoque = torreDeRoque;
            this.posicaoInicialDaTorreDeRoque = posicaoInicialDaTorreDeRoque;
            this.posicaoDeDestinoDaTorreDeRoque = posicaoDeDestinoDaTorreDeRoque;
        }

        public Torre getTorreDeRoque() {
            return this.torreDeRoque;
        }

        @Override
        public boolean isMovimentoDeRoque() {
            return true;
        }

        @Override
        public Tabuleiro executar() {
            final Builder builder = new Builder();
            for(final Peca peca : this.tabuleiro.jogadorAtual().getPecasAtivas()) {
                if(!this.pecaMovida.equals(peca) && !this.torreDeRoque.equals(peca)) {
                    builder.setPeca(peca);
                }
            }
            for(final Peca peca : this.tabuleiro.jogadorAtual().getOponente().getPecasAtivas()) {
                builder.setPeca(peca);
            }
            builder.setPeca(this.pecaMovida.moverPeca(this));
            //TODO algumas coisas precisam ser acertadas aqui
            builder.setPeca(new Torre(this.torreDeRoque.getCorPeca(), this.posicaoDeDestinoDaTorreDeRoque));
            builder.setTurnoDoJogador(this.tabuleiro.jogadorAtual().getOponente().getCor());
            return builder.build();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.torreDeRoque.hashCode();
            result = prime * result + this.posicaoDeDestinoDaTorreDeRoque;
            return result;
        }

        @Override
        public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }
            if(!(other instanceof MovimentoDeRoque)) {
                return false;
            }
            final MovimentoDeRoque outroMovimentoDeRoque = (MovimentoDeRoque) other;
            return super.equals(outroMovimentoDeRoque) && this.torreDeRoque.equals(outroMovimentoDeRoque.getTorreDeRoque());
        }

    }

    public static final class MovimentoDeRoquePequeno extends MovimentoDeRoque {

        public MovimentoDeRoquePequeno(final Tabuleiro tabuleiro,
                                       final Peca pecaMovida,
                                       int coordenadaDestino,
                                       final Torre torreDeRoque,
                                       final int posicaoInicialDaTorreDeRoque,
                                       final int posicaoDeDestinoDaTorreDeRoque) {
            super(tabuleiro, pecaMovida, coordenadaDestino, torreDeRoque, posicaoInicialDaTorreDeRoque, posicaoDeDestinoDaTorreDeRoque);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MovimentoDeRoquePequeno && super.equals(other);
        }

        @Override
        public String toString() {
            return "0-0";
        }
    }

    public static final class MovimentoDeRoqueGrande extends MovimentoDeRoque {

        public MovimentoDeRoqueGrande(final Tabuleiro tabuleiro,
                                      final Peca pecaMovida,
                                      int coordenadaDestino,
                                      final Torre torreDeRoque,
                                      final int posicaoInicialDaTorreDeRoque,
                                      final int posicaoDeDestinoDaTorreDeRoque) {
            super(tabuleiro, pecaMovida, coordenadaDestino, torreDeRoque, posicaoInicialDaTorreDeRoque, posicaoDeDestinoDaTorreDeRoque);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MovimentoDeRoqueGrande && super.equals(other);
        }

        @Override
        public String toString() {
            return "0-0-0";
        }
    }

    public static final class MovimentoNulo extends Movimento {

        public MovimentoNulo() {
            super(null, -1);
        }

        @Override
        public Tabuleiro executar() {
            throw new RuntimeException("Não é possível executar o movimento nulo");
        }

        @Override
        public int getCoordenadaAtual() {
            return -1;
        }
    }

    public static class MovimentoFactory {
        private static final Movimento MOVIMENTO_NULO = new MovimentoNulo();

        private MovimentoFactory() {
            throw new RuntimeException("Não instanciável");
        }

        public static Movimento getMovimentoNulo() {
            return MOVIMENTO_NULO;
        }

        public static Movimento criarMovimento(final Tabuleiro tabuleiro,
                                               final int coordenadaAtual,
                                               final int coordenadaDeDestino) {
            for(final Movimento movimento : tabuleiro.getTodosOsMovimentosLegais()) {
                if(movimento.getCoordenadaAtual() == coordenadaAtual &&
                   movimento.getCoordenadaDeDestino() == coordenadaDeDestino) {
                    return movimento;
                }
            }
            return MOVIMENTO_NULO;
        }
    }

}

