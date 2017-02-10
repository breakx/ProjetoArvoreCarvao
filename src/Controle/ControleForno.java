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
public class ControleForno {
    private String id_forno;
    private String nome_forno;
    private int volume_maximo_forno;
    private String situacao_forno;
    private int upc_forno;
    private String data_alteracao;
    private String usuario_alt;

    /**
     * @return the id_forno
     */
    public String getId_forno() {
        return id_forno;
    }

    /**
     * @param id_forno the id_forno to set
     */
    public void setId_forno(String id_forno) {
        this.id_forno = id_forno;
    }

    /**
     * @return the nome_forno
     */
    public String getNome_forno() {
        return nome_forno;
    }

    /**
     * @param nome_forno the nome_forno to set
     */
    public void setNome_forno(String nome_forno) {
        this.nome_forno = nome_forno;
    }

    /**
     * @return the volume_maximo_forno
     */
    public int getVolume_maximo_forno() {
        return volume_maximo_forno;
    }

    /**
     * @param volume_maximo_forno the volume_maximo_forno to set
     */
    public void setVolume_maximo_forno(int volume_maximo_forno) {
        this.volume_maximo_forno = volume_maximo_forno;
    }

    /**
     * @return the situacao_forno
     */
    public String getSituacao_forno() {
        return situacao_forno;
    }

    /**
     * @param situacao_forno the situacao_forno to set
     */
    public void setSituacao_forno(String situacao_forno) {
        this.situacao_forno = situacao_forno;
    }

    /**
     * @return the upc_forno
     */
    public int getUpc_forno() {
        return upc_forno;
    }

    /**
     * @param upc_forno the upc_forno to set
     */
    public void setUpc_forno(int upc_forno) {
        this.upc_forno = upc_forno;
    }

    /**
     * @return the data_alteracao
     */
    public String getData_alteracao() {
        return data_alteracao;
    }

    /**
     * @param data_alteracao the data_alteracao to set
     */
    public void setData_alteracao(String data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    /**
     * @return the usuario_alt
     */
    public String getUsuario_alt() {
        return usuario_alt;
    }

    /**
     * @param usuario_alt the usuario_alt to set
     */
    public void setUsuario_alt(String usuario_alt) {
        this.usuario_alt = usuario_alt;
    }
}
