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
    private String id_forno;
    private int upc_c;
    private int talhao;
    private String forno;
    private float volume_madeira;
    private String data_entrada_madeira_forno;
    private float volume_carvao;
    private String data_saida_carvao;
    private float rend_grav_forno;
    private String material_gen;
    private float umidade;
    private String data_ignicao;
    private String data_fim_carbonizacao;
    private String estimativa_mdc;

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
     * @return the rend_grav_forno
     */
    public float getRend_grav_forno() {
        return rend_grav_forno;
    }

    /**
     * @param rend_grav_forno the rend_grav_forno to set
     */
    public void setRend_grav_forno(float rend_grav_forno) {
        this.rend_grav_forno = rend_grav_forno;
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

    /**
     * @return the upc_c
     */
    public int getUpc_c() {
        return upc_c;
    }

    /**
     * @param upc_c the upc_c to set
     */
    public void setUpc_c(int upc_c) {
        this.upc_c = upc_c;
    }

    /**
     * @return the material_gen
     */
    public String getMaterial_gen() {
        return material_gen;
    }

    /**
     * @param material_gen the material_gen to set
     */
    public void setMaterial_gen(String material_gen) {
        this.material_gen = material_gen;
    }

    /**
     * @return the umidade
     */
    public float getUmidade() {
        return umidade;
    }

    /**
     * @param umidade the umidade to set
     */
    public void setUmidade(float umidade) {
        this.umidade = umidade;
    }

    /**
     * @return the data_ignicao
     */
    public String getData_ignicao() {
        return data_ignicao;
    }

    /**
     * @param data_ignicao the data_ignicao to set
     */
    public void setData_ignicao(String data_ignicao) {
        this.data_ignicao = data_ignicao;
    }

    /**
     * @return the data_fim_carbonizacao
     */
    public String getData_fim_carbonizacao() {
        return data_fim_carbonizacao;
    }

    /**
     * @param data_fim_carbonizacao the data_fim_carbonizacao to set
     */
    public void setData_fim_carbonizacao(String data_fim_carbonizacao) {
        this.data_fim_carbonizacao = data_fim_carbonizacao;
    }

    /**
     * @return the estimativa_mdc
     */
    public String getEstimativa_mdc() {
        return estimativa_mdc;
    }

    /**
     * @param estimativa_mdc the estimativa_mdc to set
     */
    public void setEstimativa_mdc(String estimativa_mdc) {
        this.estimativa_mdc = estimativa_mdc;
    }

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
}
