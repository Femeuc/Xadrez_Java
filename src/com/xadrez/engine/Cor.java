package com.xadrez.engine;

import com.xadrez.engine.jogador.Jogador;
import com.xadrez.engine.jogador.JogadorBranco;
import com.xadrez.engine.jogador.JogadorPreto;

public enum Cor {
    BRANCO {
        @Override
        public int getDirecao() {
            return -1;
        }

        @Override
        public boolean isBranco() {
            return true;
        }

        @Override
        public boolean isPreto() {
            return false;
        }

        @Override
        public Jogador escolherJogador(final JogadorBranco jogadorBranco,
                                       final JogadorPreto jogadorPreto) {
            return jogadorBranco;
        }
    },
    PRETO {
        @Override
        public int getDirecao() {
            return 1;
        }

        @Override
        public boolean isBranco() {
            return false;
        }

        @Override
        public boolean isPreto() {
            return true;
        }

        @Override
        public Jogador escolherJogador(final JogadorBranco jogadorBranco,
                                       final JogadorPreto jogadorPreto) {
            return jogadorPreto;
        }
    };

    public abstract int getDirecao();
    public abstract boolean isBranco();
    public abstract boolean isPreto();

    public abstract Jogador escolherJogador(JogadorBranco jogadorBranco, JogadorPreto jogadorPreto);
}
