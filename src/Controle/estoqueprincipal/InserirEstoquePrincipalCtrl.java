/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle.estoqueprincipal;

import Controle.ControleEstoquePrincipal;
import Controle.ControlePrincipal;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class InserirEstoquePrincipalCtrl {

    public InserirEstoquePrincipalCtrl(ControleEstoquePrincipal estoque) {        
        String query = "INSERT INTO estoque_principal (`id_estoque_p`, "
        + "`estado`, "
        + "`bloco`, "
        + "`municipio`, "
        + "`fazenda`, "
        + "`projeto`, "
        + "`upc`, "
        + "`talhao`, "
        + "`area`, "
        + "`material_genetico`, "
        + "`m3_ha`, "
        + "`talhadia`, "
                
        + "`ano_rotacao`, "
        + "`data_plantio`, "
        + "`data_rotacao_1`, "
        + "`data_rotacao_2`, "
        + "`data_rotacao_3`, "
        + "`idade_corte1`, "
        + "`idade_corte2`, "
        + "`idade_corte3`, "
        + "`idade_hoje`, "
        + "`conducao`, "
        + "`categoria`, "
        + "`situacao`, "
                
        + "`ima`, "
        + "`mdc_ha`, "
        + "`densidade_madeira`, "
        + "`densidade_carvao`, "
        + "`mad_ton_ha`, "
        + "`carv_ton_ha`,"
        + "`id_operario`, "
        + "`data_estoque`, "
                
        + "`vol_mad_estimado`, "
        + "`vol_mad_transp`, "
        + "`vol_mad_balanco`, "
        + "`mdc_estimado`, "
        + "`mdc_transp`, "
        + "`mdc_balanco`, "
        + "`mad_ton_estimado`, "
        + "`mad_ton_transp`, "
        + "`mad_ton_balanco`, "
        + "`carv_ton_estimado`, "
        + "`carv_ton_transp`, "
        + "`carv_ton_balanco`, "
                
        + "`madeira_praca`, "
        + "`madeira_forno`, "
        + "`rend_grav_estimado`, "
        + "`rend_grav_real`, "
        + "`fator_empilalhemto`) "
                
            + "VALUES (" + null
            + ", '" + ControlePrincipal.estado
            + "', '" + ControlePrincipal.bloco
            + "', '" + ControlePrincipal.municipio
            + "', '" + ControlePrincipal.fazenda
            + "', '" + ControlePrincipal.projeto
            + "', '" + estoque.getUpc()
            + "', '" + estoque.getTalhao()
            + "', '" + estoque.getArea()
            + "', '" + estoque.getMaterial_genetico()
            + "', '" + estoque.getM3_ha()
            + "', '0"       
                
            +"', '0000"
            +"', '00-00-0000"
            +"', '00-00-0000"
            +"', '00-00-0000"
            +"', '00-00-0000"
            +"', '0.0"
            +"', '0.0"
            +"', '0.0"
            +"', '0.0"
            +"', '---"
            +"', 'Silvicultura"
            +"', 'Plantio Futuro"
                
            +"', '0.0"
            +"', '0.0"
            +"', '"+estoque.getDensidade_madeira()
            +"', '"+estoque.getDensidade_carvao()
            +"', '0.0"
            +"', '0.0"                
            +"', '" + estoque.getId_operario()
            +"', '" + estoque.getData_estoque()  
                
            +"', '" + estoque.getVol_mad_estimado()
            +"', '0.00"
            +"', '" + estoque.getVol_mad_balanco()
            +"', '" + estoque.getMdc_estimado()
            +"', '0.00"
            +"', '" + estoque.getMdc_balanco()
            +"', '" + estoque.getMad_ton_estimado()
            +"', '0.00"
            +"', '" + estoque.getMad_ton_balanco()
            +"', '" + estoque.getCarv_ton_estimado()
            +"', '0.00"
            +"', '" + estoque.getCarv_ton_balanco()
                
            +"', '0.0"
            +"', '0.0"
            +"', '0.0"
            +"', '0.0"
            +"', '"+ estoque.getFator_empilalhemto()
            /*+ "VALUES (" + null
            + ", '" + estoque.getEstado()
            + "', '" + estoque.getBloco()
            + "', '" + estoque.getMunicipio()
            + "', '" + estoque.getFazenda()
            + "', '" + estoque.getProjeto()
            + "', '" + estoque.getUpc()
            + "', '" + estoque.getTalhao()
            + "', '" + estoque.getArea()
            + "', '" + estoque.getM3_ha()
            + "', '" + estoque.getMaterial_genetico()
            + "', '" + estoque.getTalhadia()
                
            +"', '" + estoque.getAno_rotacao()
            +"', '" + estoque.getData_plantio()
            +"', '" + estoque.getData_rotacao_1()
            +"', '" + estoque.getData_rotacao_2()
            +"', '" + estoque.getData_rotacao_3()
            +"', '" + estoque.getIdade()
            +"', '" + estoque.getCategoria()
            +"', '" + estoque.getSituacao()
                
            +"', '" + estoque.getIma()
            +"', '" + estoque.getMdc_ha()
            +"', '"+estoque.getDensidade_madeira()
            +"', '"+estoque.getDensidade_carvao()
            +"', '" + estoque.getMad_ton_ha()
            +"', '" + estoque.getCarv_ton_ha()
            +"', '" + estoque.getId_operario()
            +"', '" + estoque.getData_estoque()  
                
            +"', '" + estoque.getVol_mad_estimado()
            +"', '" + estoque.getVol_mad_transp()
            +"', '" + estoque.getVol_mad_balanco()
            +"', '" + estoque.getMdc_estimado()
            +"', '" + estoque.getMdc_transp()
            +"', '" + estoque.getMdc_balanco()
            +"', '" + estoque.getMad_ton_estimado()
            +"', '" + estoque.getMad_ton_transp()
            +"', '" + estoque.getMad_ton_balanco()
            +"', '" + estoque.getCarv_ton_estimado()
            +"', '" + estoque.getCarv_ton_transp()
            +"', '" + estoque.getCarv_ton_balanco()
                
            +"', '" + estoque.getMadeira_praca()
            +"', '" + estoque.getMadeira_forno()
            +"', '" + estoque.getMad_ton_tot()
            +"', '" + estoque.getCarv_ton_tot()
            +"', '" + estoque.getRend_grav_estimado()
            +"', '" + estoque.getRend_grav_real()
            +"', '"+ estoque.getFator_empilalhemto()*/
            +"')";
        
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }    
}