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
public class ControlePrincipal {
    //Dados de usuario
    public static String nome;
    public static String tipo_u;
    public static String id_op;
    public static int upc_u;
    //public static String tela_anterior;
         
    //Dados do estoque
    public static float densidade_madeira;
    public static float densidade_carvao;    
    public static float vol_mad_estimado;
    public static float vol_mad_transp;
    public static float vol_mad_balanco;
    public static float mdc_estimado;
    public static float mdc_prod;
    public static float mdc_balanco;
    public static float mad_ton_estimado;
    public static float mad_ton_transp;
    public static float mad_ton_balanco;
    public static float carv_ton_estimado;
    public static float carv_ton_prod;
    public static float carv_ton_balanco;
    public static float madeira_praca;
    public static float madeira_forno;
    public static float carvao_praca;
    public static float mdc_transp;
    public static float carv_ton_transp;   
    public static float rend_grav_real;
    public static float fator_empilalhemto; 
    public static float mad_ton_tot;
    public static float carv_ton_tot;  
    
    //Dados atuais  
    public static String id_estoque_principal;    
    public static String estado;
    public static String municipio;
    public static String fazenda;
    public static String projeto;
    public static String bloco;  
    public static String situacao; 
    public static String material_genetico;
    public static String data_inicio;
    public static String data_fim;
    public static int talhao;
    public static int upc;
    
    //Dados de controle
    public static boolean excel_cmd;
    public static String atualizarDados;
    public static String validade;    
    public static String condicao_carvao;
    public static String condicao_forno;
    public static String id_forno_usado;
    public static String desbrota;
    public static String tipo_estoque;
    public static float vol_carv_fornos;
    public static float rend_grav_fornos;
    
    //Dados para grafico
    public static float valor_grafico[];
    public static String info_grafico[];
    public static String tipo_grafico;
    public static String descricao;
}
