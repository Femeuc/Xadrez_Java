package com.xadrez.engine.jogador;

public enum StatusDeMovimento {
    FEITO{
        @Override
        boolean isFeito() {
            return true;
        }
    },
    MOVIMENTO_ILEGAL {
        @Override
        boolean isFeito() {
            return false;
        }
    },
    DEIXA_JOGADOR_EM_CHECK {
        @Override
        boolean isFeito() {
            return false;
        }
    };
    abstract boolean isFeito();
}
