package com.xadrez.engine;

import com.xadrez.engine.tabuleiro.Tabuleiro;

public class JXadrez {

    public static void main(String[] args) {
        Tabuleiro tabuleiro = Tabuleiro.criarTabuleiroPadrao();
        System.out.println(tabuleiro);
    }
}
