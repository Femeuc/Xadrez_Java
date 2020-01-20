package com.xadrez.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tabela {

    private final JFrame gameFrame;

    private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

    public Tabela() {
        this.gameFrame = new JFrame("JXadrez");
        final JMenuBar barraDeMenuDaTabela = new JMenuBar();
        popularBarraDeMenu(barraDeMenuDaTabela);
        this.gameFrame.setJMenuBar(barraDeMenuDaTabela);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }

    private void popularBarraDeMenu(final JMenuBar barraDeMenuDaTabela) {
        barraDeMenuDaTabela.add(criarMenuDeArquivo());
    }

    private JMenu criarMenuDeArquivo() {
        final JMenu menuDeArquivo = new JMenu("Arquivo");
        final JMenuItem openPGN = new JMenuItem("Carregar Arquivo PGN");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Abrir esse arquivo PGN!");
            }
        });
        menuDeArquivo.add(openPGN);
        return menuDeArquivo;
    }
}
