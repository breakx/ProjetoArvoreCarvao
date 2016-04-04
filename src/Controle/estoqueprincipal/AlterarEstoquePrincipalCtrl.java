/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle.estoqueprincipal;

import Controle.ControleEstoquePrincipal;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class AlterarEstoquePrincipalCtrl {

    public AlterarEstoquePrincipalCtrl(ControleEstoquePrincipal estoque) {
        String query = "UPDATE `estoque_principal` SET "
                //+ "`estado` = 'mg"
                //+ "', `bloco` = 'sul"
                //+ "', `municipio` = 'rio branco"
                //+ "', `fazenda` = 'colonia"
                //+ "', `projeto` = '1"
                + "`upc` = '"+estoque.getUpc()
                + "', `area` = '"+estoque.getArea()
                + "', `m3_ha` = '"+estoque.getM3_ha()
                + "', `material_genetico` = '"+estoque.getMaterial_genetico()
                + "', `talhadia` = '"+estoque.getTalhadia()
                + "', `ano_rotacao` = '"+estoque.getAno_rotacao()
                + "', `data_plantio` = '"+estoque.getData_plantio()
                + "', `data_rotacao_1` = '"+estoque.getData_rotacao_1()
                + "', `data_rotacao_2` = '"+estoque.getData_rotacao_2()
                + "', `data_rotacao_3` = '"+estoque.getData_rotacao_3()
                + "', `idade` = '"+estoque.getIdade()
                + "', `categoria` = '"+estoque.getCategoria()
                + "', `situacao` = '"+ estoque.getSituacao()
                + "', `ima` = '"+estoque.getIma()
                + "', `mdc_ha` = '"+estoque.getMdc_ha()
                + "', `densidade_madeira` = '"+estoque.getDensidade_madeira()
                + "', `densidade_carvao` = '"+estoque.getDensidade_carvao()
                + "', `mad_ton_ha` = '"+estoque.getMad_ton_ha()
                + "', `carv_ton_ha` = '"+estoque.getCarv_ton_ha()
                + "', `vol_mad_estimado` = '"+estoque.getVol_mad_estimado()
                //+ "', `vol_mad_real` = '100.0"
                //+ "', `vol_mad_balanco` = '-100.0"
                + "', `mdc_estimado` = '"+estoque.getMdc_estimado()
                //+ "', `mdc_real` = '1.0"
                //+ "', `mdc_balanco` = '-9.0"
                + "', `mad_ton_estimado` = '"+estoque.getMad_ton_estimado()
                //+ "', `mad_ton_real` = '10.0"
                //+ "', `mad_ton_balanco` = '-1234.5"
                + "', `carv_ton_estimado` = '"+estoque.getCarv_ton_estimado()
                //+ "', `carv_ton_real` = '12.3"
                //+ "', `carv_ton_balanco` = '-120.3"
                //+ "', `madeira_praca` = '"+estoque.getMadeira_praca()
                //+ "', `madeira_forno` = '"+estoque.getMadeira_forno()
                //+ "', `mad_ton_tot` = '1792.69"
                //+ "', `carv_ton_tot` = '91"
                + "', `rend_grav_estimado` = '"+estoque.getRend_grav_estimado()
                + "', `rend_grav_real` = '"+estoque.getRend_grav_real()
                + "', `fator_empilalhemto` = '"+estoque.getFator_empilalhemto()
                + "' WHERE `id_estoque_p` = "+estoque.getId_estoque_p(); 
        
                /*"UPDATE estoque_principal SET "
                + "`ano_rotacao` = '"+estoque.getAno_rotacao()
                + "', `talhao` = '"+estoque.getTalhao()
                + "', `area` = '"+estoque.getArea()
                + "', `m3_ha` = '"+estoque.getM3_ha()
                + "', `data_plantio` = '"+estoque.getData_plantio()
                + "', `material_genetico` = '"+estoque.getMaterial_genetico()
                + "', `talhadia` = '"+estoque.getTalhadia()
                + "', `data_rotacao_1` = '"+estoque.getData_rotacao_1()
                + "', `data_rotacao_2` = '"+estoque.getData_rotacao_2()
                + "', `idade` = '"+estoque.getIdade()
                + "', `categoria` = '"+estoque.getCategoria()
                + "', `situacao` = '"+estoque.getSituacao()
                + "', `ima` = '"+estoque.getIma()
                + "', `mdc_ha` = '"+estoque.getMdc_ha()
                + "', `mdc` = '"+estoque.getMdc()
                + "', `densidade_carvao` = '"+estoque.getDensidade_carvao()
                + "', `densidade_madeira` = '"+estoque.getDensidade_madeira()
                + "', `id_operario` = '"+estoque.getId_operario()
                + "', `data_estoque` = '"+estoque.getData_estoque()
                + "', `volume_estimado` = '"+estoque.getVolume_estimado()
                + "', `madeira_praca` = '"+estoque.getMadeira_praca()
                + "', `madeira_forno` = '"+estoque.getMadeira_forno()
                + "', `mad_ton_tot` = '"+estoque.getMad_ton_tot()
                + "', `carv_ton_tot` = '"+estoque.getCarv_ton_tot()
                + "' WHERE id_estoque_p = "+estoque.getId_estoque_p();*/
        
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }

}