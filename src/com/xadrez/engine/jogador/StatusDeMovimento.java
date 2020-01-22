package com.xadrez.engine.jogador;

public enum StatusDeMovimento {
    FEITO{
        @Override
        public boolean isFeito() {
            return true;
        }
    },
    MOVIMENTO_ILEGAL {
        @Override
        public boolean isFeito() {
            return false;
        }
    },
    DEIXA_JOGADOR_EM_CHECK {
        @Override
        public boolean isFeito() {
            return false;
        }
    };
    public abstract boolean isFeito();
}
