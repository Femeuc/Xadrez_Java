package com.xadrez.gui;

import com.xadrez.engine.tabuleiro.Movimento;
import com.xadrez.engine.tabuleiro.Tabuleiro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.xadrez.gui.Tabela.*;

public class PainelDeHistoricoDoJogo extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension DIMENSAO_DO_PAINEL_DE_HISTORICO = new Dimension(100, 400);

    PainelDeHistoricoDoJogo() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(DIMENSAO_DO_PAINEL_DE_HISTORICO);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    void redo(final Tabuleiro tabuleiro,
              final LogDeMovimento logDeMovimento) {
        int linhaAtual = 0;
        this.model.clear();
        for(final Movimento movimento : logDeMovimento.getMovimentos()) {
            final String textoDoMovimento = movimento.toString();
            if(movimento.getPecaMovida().getCorPeca().isBranco()) {
                this.model.setValueAt(textoDoMovimento, linhaAtual, 0);
            } else if(movimento.getPecaMovida().getCorPeca().isPreto()) {
                this.model.setValueAt(textoDoMovimento, linhaAtual, 1);
                linhaAtual++;
            }
        }

        if(logDeMovimento.getMovimentos().size() > 0) {
            final Movimento ultimoMovimento = logDeMovimento.getMovimentos().get(logDeMovimento.tamanho() - 1);
            final String textoDoMovimento = ultimoMovimento.toString();
            if(ultimoMovimento.getPecaMovida().getCorPeca().isBranco()) {
                this.model.setValueAt(textoDoMovimento + calcularCheckECheckmateHash(tabuleiro), linhaAtual, 0);
            } else if(ultimoMovimento.getPecaMovida().getCorPeca().isPreto()) {
                this.model.setValueAt(textoDoMovimento + calcularCheckECheckmateHash(tabuleiro), linhaAtual - 1, 1);
            }
        }
        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private String calcularCheckECheckmateHash(final Tabuleiro tabuleiro) {
        if(tabuleiro.jogadorAtual().isEmCheckMate()) {
            return "#";
        } else if(tabuleiro.jogadorAtual().isEmCheck()) {
            return "+";
        }
        return "";
    }

    private static class DataModel extends DefaultTableModel {
        private final List<Linha> valores;
        private static final String[] NOMES = {"Branco", "Preto"};

        DataModel() {
            this.valores = new ArrayList<>();
        }

        public void clear() {
            this.valores.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if(this.valores == null) {
                return 0;
            }
            return this.valores.size();
        }

        @Override
        public int getColumnCount() {
            return NOMES.length;
        }

        @Override
        public Object getValueAt(final int linha, final int coluna) {
            final Linha linhaAtual = this.valores.get(linha);
            if(coluna == 0) {
                return linhaAtual.getMovimentoDoBranco();
            } else if(coluna == 1) {
                return linhaAtual.getGetMovimentoDoPreto();
            }
            return null;
        }

        @Override
        public void setValueAt(final Object aValue,
                               final int linha,
                               final int coluna) {
            final Linha linhaAtual;
            if(this.valores.size() <= linha) {
                linhaAtual = new Linha();
                this.valores.add(linhaAtual);
            } else {
                linhaAtual = this.valores.get(linha);
            }
            if(coluna == 0 ) {
                linhaAtual.setMovimentoDoBranco((String) aValue);
                fireTableRowsInserted(linha, linha);
            } else if(coluna == 1) {
                linhaAtual.setMovimentoDoPreto((String) aValue);
                fireTableCellUpdated(linha, coluna);
            }
        }

        @Override
        public Class<?> getColumnClass(final int coluna) {
            return Movimento.class;
        }

        @Override
        public String getColumnName(final int coluna) {
            return NOMES[coluna];
        }

    }

    private static class Linha {
        private String movimentoDoBranco;
        private String movimentoDoPreto;

        Linha() {
        }

        public String getMovimentoDoBranco() {
            return this.movimentoDoBranco;
        }

        public String getGetMovimentoDoPreto() {
            return this.movimentoDoPreto;
        }

        public void setMovimentoDoBranco(final String movimento) {
            this.movimentoDoBranco = movimento;
        }

        public void setMovimentoDoPreto(final String movimento) {
            this.movimentoDoPreto = movimento;
        }
    }

}
