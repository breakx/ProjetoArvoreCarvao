/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 *
 * @author Cristiano GD
 */
public class ControleFazenda {
    private String id_fazenda;
    private int cod_estado; 
    private String estado;
    private int cod_bloco; 
    private String bloco; 
    private String municipio; 
    private String fazenda; 
    private String projeto; 

    /**
     * @return the id_fazenda
     */
    public String getId_fazenda() {
        return id_fazenda;
    }

    /**
     * @param id_fazenda the id_fazenda to set
     */
    public void setId_fazenda(String id_fazenda) {
        this.id_fazenda = id_fazenda;
    }

    /**
     * @return the cod_estado
     */
    public int getCod_estado() {
        return cod_estado;
    }

    /**
     * @param cod_estado the cod_estado to set
     */
    public void setCod_estado(int cod_estado) {
        this.cod_estado = cod_estado;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the cod_bloco
     */
    public int getCod_bloco() {
        return cod_bloco;
    }

    /**
     * @param cod_bloco the cod_bloco to set
     */
    public void setCod_bloco(int cod_bloco) {
        this.cod_bloco = cod_bloco;
    }

    /**
     * @return the bloco
     */
    public String getBloco() {
        return bloco;
    }

    /**
     * @param bloco the bloco to set
     */
    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    /**
     * @return the municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the fazenda
     */
    public String getFazenda() {
        return fazenda;
    }

    /**
     * @param fazenda the fazenda to set
     */
    public void setFazenda(String fazenda) {
        this.fazenda = fazenda;
    }

    /**
     * @return the projeto
     */
    public String getProjeto() {
        return projeto;
    }

    /**
     * @param projeto the projeto to set
     */
    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }
    
}
