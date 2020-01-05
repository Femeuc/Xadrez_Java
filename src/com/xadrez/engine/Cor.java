package com.xadrez.engine;

public enum Cor {
    BRANCO {
        @Override
        public int getDirecao() {
            return -1;
        }
    },
    PRETO {
        @Override
        public int getDirecao() {
            return 1;
        }
    };

    public abstract int getDirecao();
}
