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
public class ControleExpedirCarvao {
    private String id_expedir_carvao;    
    private String id_operario;
    private String data_envio;
    private String id_estoque_p;
    private int upc;
    private int talhao;
    private float peso_transportado;
    private float volume_transportado;
    private String destino_carvao;

    /**
     * @return the id_expedir_carvao
     */
    public String getId_expedir_carvao() {
        return id_expedir_carvao;
    }

    /**
     * @param id_expedir_carvao the id_expedir_carvao to set
     */
    public void setId_expedir_carvao(String id_expedir_carvao) {
        this.id_expedir_carvao = id_expedir_carvao;
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
     * @return the data_envio
     */
    public String getData_envio() {
        return data_envio;
    }

    /**
     * @param data_envio the data_envio to set
     */
    public void setData_envio(String data_envio) {
        this.data_envio = data_envio;
    }

    /**
     * @return the id_estoque_p
     */
    public String getId_estoque_p() {
        return id_estoque_p;
    }

    /**
     * @param id_estoque_p the id_estoque_p to set
     */
    public void setId_estoque_p(String id_estoque_p) {
        this.id_estoque_p = id_estoque_p;
    }

    /**
     * @return the upc
     */
    public int getUpc() {
        return upc;
    }

    /**
     * @param upc the upc to set
     */
    public void setUpc(int upc) {
        this.upc = upc;
    }

    /**
     * @return the talhao
     */
    public int getTalhao() {
        return talhao;
    }

    /**
     * @param talhao the talhao to set
     */
    public void setTalhao(int talhao) {
        this.talhao = talhao;
    }

    /**
     * @return the peso_transportado
     */
    public float getPeso_transportado() {
        return peso_transportado;
    }

    /**
     * @param peso_transportado the peso_transportado to set
     */
    public void setPeso_transportado(float peso_transportado) {
        this.peso_transportado = peso_transportado;
    }

    /**
     * @return the volume_transportado
     */
    public float getVolume_transportado() {
        return volume_transportado;
    }

    /**
     * @param volume_transportado the volume_transportado to set
     */
    public void setVolume_transportado(float volume_transportado) {
        this.volume_transportado = volume_transportado;
    }

    /**
     * @return the destino_carvao
     */
    public String getDestino_carvao() {
        return destino_carvao;
    }

    /**
     * @param destino_carvao the destino_carvao to set
     */
    public void setDestino_carvao(String destino_carvao) {
        this.destino_carvao = destino_carvao;
    }
    
}
