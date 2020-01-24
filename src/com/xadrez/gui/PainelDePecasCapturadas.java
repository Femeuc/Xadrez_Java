package com.xadrez.gui;

import com.google.common.primitives.Ints;
import com.xadrez.engine.pecas.Peca;
import com.xadrez.engine.tabuleiro.Movimento;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.xadrez.gui.Tabela.*;

public class PainelDePecasCapturadas extends JPanel {

    private final JPanel painelNorte;
    private final JPanel painelSul;

    private static final Color COR_DO_PAINEL = Color.decode("0xFDFE6");
    private static final Dimension DIMENSAO_DAS_ECAS_CAPTURAS = new Dimension(40, 80);
    private static final EtchedBorder BORDA_DO_PAINEL = new EtchedBorder(EtchedBorder.RAISED);

    public PainelDePecasCapturadas() {
        super(new BorderLayout());
        this.setBackground(COR_DO_PAINEL);
        this.setBorder(BORDA_DO_PAINEL);
        this.painelNorte = new JPanel(new GridLayout(8, 2));
        this.painelSul = new JPanel(new GridLayout(8, 2));
        this.painelNorte.setBackground(COR_DO_PAINEL);
        this.painelSul.setBackground(COR_DO_PAINEL);
        this.add(this.painelNorte, BorderLayout.NORTH);
        this.add(this.painelSul, BorderLayout.SOUTH);
        setPreferredSize(DIMENSAO_DAS_ECAS_CAPTURAS);
    }

    public void refazer(final LogDeMovimento logDeMovimento) {
        this.painelSul.removeAll();
        this.painelNorte.removeAll();

        final List<Peca> pecasBrancasCapturadas = new ArrayList<>();
        final List<Peca> pecasPretasCapturadas = new ArrayList<>();

        for(final Movimento movimento : logDeMovimento.getMovimentos()) {
            if(movimento.isCaptura()) {
                final Peca pecaCapturada = movimento.getPecaSobAtaque();
                if(pecaCapturada.getCorPeca().isBranco()) {
                    pecasBrancasCapturadas.add(pecaCapturada);
                } else if(pecaCapturada.getCorPeca().isPreto()) {
                    pecasPretasCapturadas.add(pecaCapturada);
                } else {
                    throw new RuntimeException("Não deveria chegar até aqui");
                }
            }
        }
        Collections.sort(pecasBrancasCapturadas, new Comparator<Peca>() {
            @Override
            public int compare(Peca o1, Peca o2) {
                return Ints.compare(o1.getValorDaPeca(), o2.getValorDaPeca());
            }
        });

        Collections.sort(pecasPretasCapturadas, new Comparator<Peca>() {
            @Override
            public int compare(Peca o1, Peca o2) {
                return Ints.compare(o1.getValorDaPeca(), o2.getValorDaPeca());
            }
        });

        for(final Peca pecaCapturada : pecasBrancasCapturadas) {
            try {
                final BufferedImage imagem = ImageIO.read(new File("arte/fancy" +
                         pecaCapturada.getCorPeca().toString().substring(0, 1) + "" + pecaCapturada.toString()));
                final ImageIcon icone = new ImageIcon(imagem);
                final JLabel imageLabel = new JLabel();
                this.painelSul.add(imageLabel);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        for(final Peca pecaCapturada : pecasPretasCapturadas) {
            try {
                final BufferedImage imagem = ImageIO.read(new File("arte/fancy" +
                        pecaCapturada.getCorPeca().toString().substring(0, 1) + "" + pecaCapturada.toString()));
                final ImageIcon icone = new ImageIcon(imagem);
                final JLabel imageLabel = new JLabel();
                this.painelSul.add(imageLabel);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        validate();
    }
}
