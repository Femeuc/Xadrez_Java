package com.xadrez.gui;

import com.sun.scenario.effect.InvertMask;
import com.xadrez.engine.jogador.TransicaoDeMovimento;
import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Quadrado;
import com.xadrez.engine.tabuleiro.Tabuleiro;
import com.xadrez.engine.tabuleiro.TabuleiroUtil;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Tabela {

    private final JFrame gameFrame;
    private final PainelDoTabuleiro painelDoTabuleiro;
    private final Color quadradoCorClara = Color.decode("#FFFACD");
    private final Color quadradoCorEscura = Color.decode("#593E1A");
    private Tabuleiro tabuleiroDeXadrez;

    private final static Dimension DIMENSAO_DO_QUADRO_EXTERIOR = new Dimension(600, 600);
    private final static Dimension DIMENSAO_DO_PAINEL_DO_TABULEIRO = new Dimension(400, 350);
    private final static Dimension DIMENSAO_DO_PAINEL_DE_QUADRADO = new Dimension(10, 10);
    private static String caminhoPadraoDosIconesDasPecas = "arte/fancy/";

    private Quadrado quadradoDeOrigem;
    private Quadrado quadradoDeDestino;
    private Peca pecaMovidaPorHumano;
    private BoardDirection boardDirection;
    private boolean destacarMovimentosLegais;


    public Tabela() {
        this.gameFrame = new JFrame("JXadrez");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar barraDeMenuDaTabela = criarBarraDeMenuDaTabela();
        this.gameFrame.setJMenuBar(barraDeMenuDaTabela);
        this.gameFrame.setSize(DIMENSAO_DO_QUADRO_EXTERIOR);
        this.tabuleiroDeXadrez = Tabuleiro.criarTabuleiroPadrao();
        this.painelDoTabuleiro = new PainelDoTabuleiro();
        this.boardDirection = BoardDirection.NORMAL;
        this.destacarMovimentosLegais = false;
        this.gameFrame.add(this.painelDoTabuleiro, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private boolean getDestacarMovimentosLegais() {
        return this.destacarMovimentosLegais;
    }

    private JMenuBar criarBarraDeMenuDaTabela() {
        final JMenuBar barraDeMenuDaTabela = new JMenuBar();
        barraDeMenuDaTabela.add(criarMenuDeArquivo());
        barraDeMenuDaTabela.add(criarMenuDePreferencias());
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

    private JMenu criarMenuDePreferencias() {
        final JMenu menuDePreferencias = new JMenu("Preferencias");
        final JMenuItem inverterTabuleiroItem = new JMenuItem("Inverter Tabuleiro");
        inverterTabuleiroItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                boardDirection = boardDirection.opposite();
                painelDoTabuleiro.desenharTabuleiro(tabuleiroDeXadrez);
            }
        });
        menuDePreferencias.add(inverterTabuleiroItem);
        menuDePreferencias.addSeparator();
        final JCheckBoxMenuItem destaqueDeMovimentoLegalCheckbox = new JCheckBoxMenuItem("Destacar Movimentos Legais", false);
        destaqueDeMovimentoLegalCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                destacarMovimentosLegais = destaqueDeMovimentoLegalCheckbox.isSelected();
            }
        });
        menuDePreferencias.add(destaqueDeMovimentoLegalCheckbox);
        return menuDePreferencias;
    }

    enum BoardDirection {
        NORMAL {
            @Override
            List<PainelDeQuadrado> traverse(final List<PainelDeQuadrado> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<PainelDeQuadrado> traverse(final List<PainelDeQuadrado> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<PainelDeQuadrado> traverse(final List<PainelDeQuadrado> boardTiles);
        abstract BoardDirection opposite();

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

        public void desenharTabuleiro(final Tabuleiro tabuleiro) {
            removeAll();
            for(final PainelDeQuadrado painelDeQuadrado : boardDirection.traverse(quadradosDoTabuleiro)) {
                painelDeQuadrado.desenharQuadrado(tabuleiro);
                add(painelDeQuadrado);
            }
            validate();
            repaint();
        }
    }

    public static class LogDeMovimento {
        private final List<Movimento> movimentos;

        LogDeMovimento() {
            this.movimentos = new ArrayList<>();
        }

        public List<Movimento> getMovimentos() {
            return this.movimentos;
        }

        public void adicionarMovimento(final Movimento movimento) {
            this.movimentos.add(movimento);
        }

        public int tamanho() {
            return this.movimentos.size();
        }

        public void clear() {
            this.movimentos.clear();
        }

        public Movimento removerMovimento(int index) {
            return this.movimentos.remove(index);
        }

        public boolean removerMovimento(final Movimento movimento) {
            return this.movimentos.remove(movimento);
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
            atribuirPecaNoQuadrado(tabuleiroDeXadrez);
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isRightMouseButton(e)) {
                        quadradoDeOrigem = null;
                        quadradoDeDestino = null;
                        pecaMovidaPorHumano = null;
                    } else if(isLeftMouseButton(e)) {
                        if(quadradoDeOrigem == null) {
                            quadradoDeOrigem = tabuleiroDeXadrez.getQuadrado(quadradoID);
                            pecaMovidaPorHumano = quadradoDeOrigem.getPeca();
                            if(pecaMovidaPorHumano == null) {
                                quadradoDeOrigem = null;
                            }
                        } else {
                            quadradoDeDestino = tabuleiroDeXadrez.getQuadrado(quadradoID);
                            final Movimento movimento = Movimento.MovimentoFactory.criarMovimento(tabuleiroDeXadrez, quadradoDeOrigem.getCoordenadaDoQuadrado(), quadradoDeDestino.getCoordenadaDoQuadrado());
                            final TransicaoDeMovimento transicao = tabuleiroDeXadrez.jogadorAtual().fazerMovimento(movimento);
                            if(transicao.getStatusDeMovimento().isFeito()) {
                                tabuleiroDeXadrez = transicao.getTabuleiroEmTransicao();
                                //TODO add the move to the move log
                            }
                            quadradoDeOrigem = null;
                            quadradoDeDestino = null;
                            pecaMovidaPorHumano = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                painelDoTabuleiro.desenharTabuleiro(tabuleiroDeXadrez);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseReleased(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseEntered(final MouseEvent mouseEvent) {

                }

                @Override
                public void mouseExited(final MouseEvent mouseEvent) {

                }
            });
            validate();
        }

        public void desenharQuadrado(final Tabuleiro tabuleiro) {
            atribuirCorDoQuadrado();
            atribuirPecaNoQuadrado(tabuleiro);
            destacarMovimentosLegais(tabuleiro);
            validate();
            repaint();
        }

        private void atribuirPecaNoQuadrado(final Tabuleiro tabuleiro) {
            this.removeAll();
            if(tabuleiro.getQuadrado(this.quadradoID).isOcupado()) {
                try {
                    final BufferedImage imagem = ImageIO.read(new File(caminhoPadraoDosIconesDasPecas +
                            tabuleiro.getQuadrado(this.quadradoID).getPeca().getCorPeca().toString().substring(0, 1) +
                            tabuleiro.getQuadrado(this.quadradoID).getPeca().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(imagem)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void destacarMovimentosLegais(final Tabuleiro tabuleiro) {
            if(destacarMovimentosLegais) {
                for(final Movimento movimento : movimentosLegaisDaPeca(tabuleiro)) {
                    if(movimento.getCoordenadaDeDestino() == this.quadradoID) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("arte/misc/green_dot.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Movimento> movimentosLegaisDaPeca(final Tabuleiro tabuleiro) {
            if(pecaMovidaPorHumano != null && pecaMovidaPorHumano.getCorPeca() == tabuleiro.jogadorAtual().getCor()) {
                return pecaMovidaPorHumano.calcularMovimentosLegais(tabuleiro);
            }
            return Collections.emptyList();
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
