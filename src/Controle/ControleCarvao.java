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
public class ControleCarvao {
    private String id_controle_carvao;
    private String id_operario;
    private String id_estoque;
    private String talhao;
    private String forno;
    private float volume_madeira;
    private String data_entrada_madeira_forno;
    private float volume_carvao;
    private String data_saida_carvao;
    private float fator;

    /**
     * @return the id_controle_carvao
     */
    public String getId_controle_carvao() {
        return id_controle_carvao;
    }

    /**
     * @param id_controle_carvao the id_controle_carvao to set
     */
    public void setId_controle_carvao(String id_controle_carvao) {
        this.id_controle_carvao = id_controle_carvao;
    }

    /**
     * @return the id_operario
     */
    public String getId_operario() {
        return id_operario;
    }

    /**
     * @param id_operario the id_operario to set
     */
    public void setId_operario(String id_operario) {
        this.id_operario = id_operario;
    }

    /**
     * @return the talhao
     */
    public String getTalhao() {
        return talhao;
    }

    /**
     * @param talhao the talhao to set
     */
    public void setTalhao(String talhao) {
        this.talhao = talhao;
    }

    /**
     * @return the forno
     */
    public String getForno() {
        return forno;
    }

    /**
     * @param forno the forno to set
     */
    public void setForno(String forno) {
        this.forno = forno;
    }

    /**
     * @return the volume_madeira
     */
    public float getVolume_madeira() {
        return volume_madeira;
    }

    /**
     * @param volume_madeira the volume_madeira to set
     */
    public void setVolume_madeira(float volume_madeira) {
        this.volume_madeira = volume_madeira;
    }

    /**
     * @return the data_entrada_madeira_forno
     */
    public String getData_entrada_madeira_forno() {
        return data_entrada_madeira_forno;
    }

    /**
     * @param data_entrada_madeira_forno the data_entrada_madeira_forno to set
     */
    public void setData_entrada_madeira_forno(String data_entrada_madeira_forno) {
        this.data_entrada_madeira_forno = data_entrada_madeira_forno;
    }

    /**
     * @return the volume_carvao
     */
    public float getVolume_carvao() {
        return volume_carvao;
    }

    /**
     * @param volume_carvao the volume_carvao to set
     */
    public void setVolume_carvao(float volume_carvao) {
        this.volume_carvao = volume_carvao;
    }

    /**
     * @return the data_saida_carvao
     */
    public String getData_saida_carvao() {
        return data_saida_carvao;
    }

    /**
     * @param data_saida_carvao the data_saida_carvao to set
     */
    public void setData_saida_carvao(String data_saida_carvao) {
        this.data_saida_carvao = data_saida_carvao;
    }

    /**
     * @return the fator
     */
    public float getFator() {
        return fator;
    }

    /**
     * @param fator the fator to set
     */
    public void setFator(float fator) {
        this.fator = fator;
    }

    /**
     * @return the id_estoque
     */
    public String getId_estoque() {
        return id_estoque;
    }

    /**
     * @param id_estoque the id_estoque to set
     */
    public void setId_estoque(String id_estoque) {
        this.id_estoque = id_estoque;
    }
}