package com.xadrez.gui;

import com.xadrez.engine.tabuleiro.TabuleiroUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Tabela {

    private final JFrame gameFrame;
    private final PainelDoTabuleiro painelDoTabuleiro;
    private Color quadradoCorClara = Color.decode("#FFFACD");
    private Color quadradoCorEscura = Color.decode("#593E1A");

    private final static Dimension DIMENSAO_DO_QUADRO_EXTERIOR = new Dimension(600, 600);
    private final static Dimension DIMENSAO_DO_PAINEL_DO_TABULEIRO = new Dimension(400, 350);
    private final static Dimension DIMENSAO_DO_PAINEL_DE_QUADRADO = new Dimension(10, 10);

    public Tabela() {
        this.gameFrame = new JFrame("JXadrez");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar barraDeMenuDaTabela = criarBarraDeMenuDaTabela();
        this.gameFrame.setJMenuBar(barraDeMenuDaTabela);
        this.gameFrame.setSize(DIMENSAO_DO_QUADRO_EXTERIOR);
        this.painelDoTabuleiro = new PainelDoTabuleiro();
        this.gameFrame.add(this.painelDoTabuleiro, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar criarBarraDeMenuDaTabela() {
        final JMenuBar barraDeMenuDaTabela = new JMenuBar();
        barraDeMenuDaTabela.add(criarMenuDeArquivo());
        return barraDeMenuDaTabela;
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

        final JMenuItem menuFechar = new JMenuItem("Fechar");
        menuFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        menuDeArquivo.add(menuFechar);

        return menuDeArquivo;
    }

    private class PainelDoTabuleiro extends JPanel {
        final List<PainelDeQuadrado> quadradosDoTabuleiro;

        PainelDoTabuleiro() {
            super(new GridLayout(8,8));
            this.quadradosDoTabuleiro = new ArrayList<>();
            for(int i = 0; i < TabuleiroUtil.QUANTIDADE_QUADRADOS; i++) {
                final PainelDeQuadrado painelDeQuadrado = new PainelDeQuadrado(this, i);
                this.quadradosDoTabuleiro.add(painelDeQuadrado);
                add(painelDeQuadrado);
            }
            setPreferredSize(DIMENSAO_DO_PAINEL_DO_TABULEIRO);
            validate();
        }
    }

    private class PainelDeQuadrado extends JPanel {
        private final int quadradoID;

        PainelDeQuadrado(final PainelDoTabuleiro painelDoTabuleiro,
                         final int quadradoID) {
            super(new GridBagLayout());
            this.quadradoID = quadradoID;
            setPreferredSize(DIMENSAO_DO_PAINEL_DE_QUADRADO);
            atribuirCorDoQuadrado();
            validate();
        }

        private void atribuirCorDoQuadrado() {
            if(TabuleiroUtil.PRIMEIRA_LINHA[this.quadradoID] ||
                    TabuleiroUtil.TERCEIRA_LINHA[this.quadradoID] ||
                    TabuleiroUtil.QUINTA_LINHA[this.quadradoID] ||
                    TabuleiroUtil.SETIMA_LINHA[this.quadradoID]) {
                setBackground(this.quadradoID % 2 == 0 ? quadradoCorClara : quadradoCorEscura);
            } else if(TabuleiroUtil.SEGUNDA_LINHA[this.quadradoID] ||
                        TabuleiroUtil.QUARTA_LINHA[this.quadradoID] ||
                        TabuleiroUtil.SEXTA_LINHA[this.quadradoID] ||
                        TabuleiroUtil.OITAVA_LINHA[this.quadradoID]) {
                setBackground(this.quadradoID % 2 != 0 ? quadradoCorClara : quadradoCorEscura);
            }
        }
    }
}
