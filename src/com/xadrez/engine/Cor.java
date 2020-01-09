package com.xadrez.engine;

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
    };

    public abstract int getDirecao();
    public abstract boolean isBranco();
    public abstract boolean isPreto();
}
