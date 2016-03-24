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
public class ControleMadeira {
    private String id_controle_madeira;
    private String id_operario;
    private String talhao;
    private float saida_volume_talhao;
    private String data_talhao;
    private float altura1_t;
    private float altura2_t;
    private float altura3_t;
    private float comprimento_t;
    private float largura_t;
    private float peso_t;
    private float entrada_volume_praca;
    private String data_praca;
    private float altura1_p;
    private float altura2_p;
    private float altura3_p;
    private float comprimento_p;
    private float largura_p;
    private float peso_p;
    private float fator;
    private String id_estoque;

    /**
     * @return the id_controle_madeira
     */
    public String getId_controle_madeira() {
        return id_controle_madeira;
    }

    /**
     * @param id_controle_madeira the id_controle_madeira to set
     */
    public void setId_controle_madeira(String id_controle_madeira) {
        this.id_controle_madeira = id_controle_madeira;
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
     * @return the saida_volume_talhao
     */
    public float getSaida_volume_talhao() {
        return saida_volume_talhao;
    }

    /**
     * @param saida_volume_talhao the saida_volume_talhao to set
     */
    public void setSaida_volume_talhao(float saida_volume_talhao) {
        this.saida_volume_talhao = saida_volume_talhao;
    }

    /**
     * @return the data_talhao
     */
    public String getData_talhao() {
        return data_talhao;
    }

    /**
     * @param data_talhao the data_talhao to set
     */
    public void setData_talhao(String data_talhao) {
        this.data_talhao = data_talhao;
    }

    /**
     * @return the altura1_t
     */
    public float getAltura1_t() {
        return altura1_t;
    }

    /**
     * @param altura1_t the altura1_t to set
     */
    public void setAltura1_t(float altura1_t) {
        this.altura1_t = altura1_t;
    }

    /**
     * @return the altura2_t
     */
    public float getAltura2_t() {
        return altura2_t;
    }

    /**
     * @param altura2_t the altura2_t to set
     */
    public void setAltura2_t(float altura2_t) {
        this.altura2_t = altura2_t;
    }

    /**
     * @return the altura3_t
     */
    public float getAltura3_t() {
        return altura3_t;
    }

    /**
     * @param altura3_t the altura3_t to set
     */
    public void setAltura3_t(float altura3_t) {
        this.altura3_t = altura3_t;
    }

    /**
     * @return the comprimento_t
     */
    public float getComprimento_t() {
        return comprimento_t;
    }

    /**
     * @param comprimento_t the comprimento_t to set
     */
    public void setComprimento_t(float comprimento_t) {
        this.comprimento_t = comprimento_t;
    }

    /**
     * @return the largura_t
     */
    public float getLargura_t() {
        return largura_t;
    }

    /**
     * @param largura_t the largura_t to set
     */
    public void setLargura_t(float largura_t) {
        this.largura_t = largura_t;
    }

    /**
     * @return the entrada_volume_praca
     */
    public float getEntrada_volume_praca() {
        return entrada_volume_praca;
    }

    /**
     * @param entrada_volume_praca the entrada_volume_praca to set
     */
    public void setEntrada_volume_praca(float entrada_volume_praca) {
        this.entrada_volume_praca = entrada_volume_praca;
    }

    /**
     * @return the data_praca
     */
    public String getData_praca() {
        return data_praca;
    }

    /**
     * @param data_praca the data_praca to set
     */
    public void setData_praca(String data_praca) {
        this.data_praca = data_praca;
    }

    /**
     * @return the altura1_p
     */
    public float getAltura1_p() {
        return altura1_p;
    }

    /**
     * @param altura1_p the altura1_p to set
     */
    public void setAltura1_p(float altura1_p) {
        this.altura1_p = altura1_p;
    }

    /**
     * @return the altura2_p
     */
    public float getAltura2_p() {
        return altura2_p;
    }

    /**
     * @param altura2_p the altura2_p to set
     */
    public void setAltura2_p(float altura2_p) {
        this.altura2_p = altura2_p;
    }

    /**
     * @return the altura3_p
     */
    public float getAltura3_p() {
        return altura3_p;
    }

    /**
     * @param altura3_p the altura3_p to set
     */
    public void setAltura3_p(float altura3_p) {
        this.altura3_p = altura3_p;
    }

    /**
     * @return the comprimento_p
     */
    public float getComprimento_p() {
        return comprimento_p;
    }

    /**
     * @param comprimento_p the comprimento_p to set
     */
    public void setComprimento_p(float comprimento_p) {
        this.comprimento_p = comprimento_p;
    }

    /**
     * @return the largura_p
     */
    public float getLargura_p() {
        return largura_p;
    }

    /**
     * @param largura_p the largura_p to set
     */
    public void setLargura_p(float largura_p) {
        this.largura_p = largura_p;
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
     * @return the peso_t
     */
    public float getPeso_t() {
        return peso_t;
    }

    /**
     * @param peso_t the peso_t to set
     */
    public void setPeso_t(float peso_t) {
        this.peso_t = peso_t;
    }

    /**
     * @return the peso_p
     */
    public float getPeso_p() {
        return peso_p;
    }

    /**
     * @param peso_p the peso_p to set
     */
    public void setPeso_p(float peso_p) {
        this.peso_p = peso_p;
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
