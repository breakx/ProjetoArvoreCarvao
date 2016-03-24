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
public class ControleEstoquePrincipal {
    private String id_estoque_p;
    private String estado; 
    private String upc;
    private String bloco; 
    private String municipio; 
    private String fazenda; 
    private String projeto; 
    private int ano_rotacao; 
    private String talhao; 
    private float area; 
    private float m3_ha; 
    private String data_plantio;
    private int material_genetico;
    private String talhadia;
    private String data_rotacao_1;
    private String data_rotacao_2;
    private float idade;
    private String categoria;
    private String situacao;
    private float ima; 
    private float mdc_ha;
    private float mdc; 
    private float densidade_carvao; 
    private float densidade_madeira; 
    private String id_operario;
    private String data_estoque;
    private float volume_estimado;
    private float madeira_talhao; 
    private float madeira_praca;
    private float madeira_forno; 
    private float mad_ton_tot;
    private float carv_ton_tot;

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
     * @return the upc
     */
    public String getUpc() {
        return upc;
    }

    /**
     * @param upc the upc to set
     */
    public void setUpc(String upc) {
        this.upc = upc;
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

    /**
     * @return the ano_rotacao
     */
    public int getAno_rotacao() {
        return ano_rotacao;
    }

    /**
     * @param ano_rotacao the ano_rotacao to set
     */
    public void setAno_rotacao(int ano_rotacao) {
        this.ano_rotacao = ano_rotacao;
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
     * @return the area
     */
    public float getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(float area) {
        this.area = area;
    }

    /**
     * @return the talhadia
     */
    public String getTalhadia() {
        return talhadia;
    }

    /**
     * @param talhadia the talhadia to set
     */
    public void setTalhadia(String talhadia) {
        this.talhadia = talhadia;
    }

    /**
     * @return the material_genetico
     */
    public int getMaterial_genetico() {
        return material_genetico;
    }

    /**
     * @param material_genetico the material_genetico to set
     */
    public void setMaterial_genetico(int material_genetico) {
        this.material_genetico = material_genetico;
    }

    /**
     * @return the data_plantio
     */
    public String getData_plantio() {
        return data_plantio;
    }

    /**
     * @param data_plantio the data_plantio to set
     */
    public void setData_plantio(String data_plantio) {
        this.data_plantio = data_plantio;
    }

    /**
     * @return the data_rotacao_1
     */
    public String getData_rotacao_1() {
        return data_rotacao_1;
    }

    /**
     * @param data_rotacao_1 the data_rotacao_1 to set
     */
    public void setData_rotacao_1(String data_rotacao_1) {
        this.data_rotacao_1 = data_rotacao_1;
    }

    /**
     * @return the data_rotacao_2
     */
    public String getData_rotacao_2() {
        return data_rotacao_2;
    }

    /**
     * @param data_rotacao_2 the data_rotacao_2 to set
     */
    public void setData_rotacao_2(String data_rotacao_2) {
        this.data_rotacao_2 = data_rotacao_2;
    }

    /**
     * @return the idade
     */
    public float getIdade() {
        return idade;
    }

    /**
     * @param idade the idade to set
     */
    public void setIdade(float idade) {
        this.idade = idade;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the situacao
     */
    public String getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    /**
     * @return the volume_estimado
     */
    public float getVolume_estimado() {
        return volume_estimado;
    }

    /**
     * @param volume_estimado the volume_estimado to set
     */
    public void setVolume_estimado(float volume_estimado) {
        this.volume_estimado = volume_estimado;
    }

    /**
     * @return the ima
     */
    public float getIma() {
        return ima;
    }

    /**
     * @param ima the ima to set
     */
    public void setIma(float ima) {
        this.ima = ima;
    }

    /**
     * @return the mdc_ha
     */
    public float getMdc_ha() {
        return mdc_ha;
    }

    /**
     * @param mdc_ha the mdc_ha to set
     */
    public void setMdc_ha(float mdc_ha) {
        this.mdc_ha = mdc_ha;
    }

    /**
     * @return the densidade_carvao
     */
    public float getDensidade_carvao() {
        return densidade_carvao;
    }

    /**
     * @param densidade_carvao the densidade_carvao to set
     */
    public void setDensidade_carvao(float densidade_carvao) {
        this.densidade_carvao = densidade_carvao;
    }

    /**
     * @return the densidade_madeira
     */
    public float getDensidade_madeira() {
        return densidade_madeira;
    }

    /**
     * @param densidade_madeira the densidade_madeira to set
     */
    public void setDensidade_madeira(float densidade_madeira) {
        this.densidade_madeira = densidade_madeira;
    }

    /**
     * @return the madeira_talhao
     */
    public float getMadeira_talhao() {
        return madeira_talhao;
    }

    /**
     * @param madeira_talhao the madeira_talhao to set
     */
    public void setMadeira_talhao(float madeira_talhao) {
        this.madeira_talhao = madeira_talhao;
    }

    /**
     * @return the madeira_praca
     */
    public float getMadeira_praca() {
        return madeira_praca;
    }

    /**
     * @param madeira_praca the madeira_praca to set
     */
    public void setMadeira_praca(float madeira_praca) {
        this.madeira_praca = madeira_praca;
    }

    /**
     * @return the madeira_forno
     */
    public float getMadeira_forno() {
        return madeira_forno;
    }

    /**
     * @param madeira_forno the madeira_forno to set
     */
    public void setMadeira_forno(float madeira_forno) {
        this.madeira_forno = madeira_forno;
    }

    /**
     * @return the mdc
     */
    public float getMdc() {
        return mdc;
    }

    /**
     * @param mdc the mdc to set
     */
    public void setMdc(float mdc) {
        this.mdc = mdc;
    }

    /**
     * @return the mad_ton_tot
     */
    public float getMad_ton_tot() {
        return mad_ton_tot;
    }

    /**
     * @param mad_ton_tot the mad_ton_tot to set
     */
    public void setMad_ton_tot(float mad_ton_tot) {
        this.mad_ton_tot = mad_ton_tot;
    }

    /**
     * @return the carv_ton_tot
     */
    public float getCarv_ton_tot() {
        return carv_ton_tot;
    }

    /**
     * @param carv_ton_tot the carv_ton_tot to set
     */
    public void setCarv_ton_tot(float carv_ton_tot) {
        this.carv_ton_tot = carv_ton_tot;
    }

    /**
     * @return the data_estoque
     */
    public String getData_estoque() {
        return data_estoque;
    }

    /**
     * @param data_estoque the data_estoque to set
     */
    public void setData_estoque(String data_estoque) {
        this.data_estoque = data_estoque;
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
     * @return the m3_ha
     */
    public float getM3_ha() {
        return m3_ha;
    }

    /**
     * @param m3_ha the m3_ha to set
     */
    public void setM3_ha(float m3_ha) {
        this.m3_ha = m3_ha;
    }
    
}
