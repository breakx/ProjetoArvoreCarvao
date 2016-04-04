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
        + "`m3_ha`, "
        + "`material_genetico`, "
        + "`talhadia`, "
                
        + "`ano_rotacao`, "
        + "`data_plantio`, "
        + "`data_rotacao_1`, "
        + "`data_rotacao_2`, "
        + "`data_rotacao_3`, "
        + "`idade`, "
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
        + "`vol_mad_real`, "
        + "`vol_mad_balanco`, "
        + "`mdc_estimado`, "
        + "`mdc_real`, "
        + "`mdc_balanco`, "
        + "`mad_ton_estimado`, "
        + "`mad_ton_real`, "
        + "`mad_ton_balanco`, "
        + "`carv_ton_estimado`, "
        + "`carv_ton_real`, "
        + "`carv_ton_balanco`, "
                
        + "`madeira_praca`, "
        + "`madeira_forno`, "
        + "`mad_ton_tot`, "
        + "`carv_ton_tot`, "
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
            + "', '" + estoque.getM3_ha()
            + "', '" + estoque.getMaterial_genetico()
            + "', '0"       
                
            +"', '0000"
            +"', '00-00-0000"
            +"', '00-00-0000"
            +"', '00-00-0000"
            +"', '00-00-0000"
            +"', '0.0"
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
            +"', '0.0"
            +"', '0.0"
            +"', '"+ estoque.getFator_empilalhemto()
            +"')";
        
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }    
}