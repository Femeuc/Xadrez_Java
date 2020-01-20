package com.xadrez;

import com.xadrez.engine.tabuleiro.Tabuleiro;
import com.xadrez.gui.Tabela;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JXadrez {

    public static void main(String[] args) {
        Tabuleiro tabuleiro = Tabuleiro.criarTabuleiroPadrao();
        System.out.println(tabuleiro);
        Tabela tabela = new Tabela();
    }
}
