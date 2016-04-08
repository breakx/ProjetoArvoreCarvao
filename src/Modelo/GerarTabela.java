/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Cristiano GD
 */
public class GerarTabela extends AbstractTableModel{
    private ArrayList linhas;
    private String[] colunas;
    
    public GerarTabela(ArrayList lin, String[] col){
        setLinhas(lin);
        setColunas(col);
    }

    /**
     * @return the linhas
     */
    public ArrayList getLinhas() {
        return linhas;
    }

    /**
     * @param linhas the linhas to set
     */
    private void setLinhas(ArrayList linhas) {
        this.linhas = linhas;
    }

    /**
     * @return the colunas
     */
    public String[] getColunas() {
        return colunas;
    }

    /**
     * @param colunas the colunas to set
     */
    private void setColunas(String[] colunas) {
        this.colunas = colunas;
    }   

    /**
     * 
     * @return quantidade de linhas
     */
    public int getRowCount() {
        return linhas.size();
    }

    /**
     * 
     * @return quantidade de colunas
     */
    public int getColumnCount() {
        return colunas.length;
    }
    
    /**
     * 
     * @param columnIndex identifica indice da coluna
     * @return coluna especificada por columnIndex
     */
    @Override
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object[] linha = (Object[])getLinhas().get(rowIndex);
        return linha[columnIndex];
    }    
}
