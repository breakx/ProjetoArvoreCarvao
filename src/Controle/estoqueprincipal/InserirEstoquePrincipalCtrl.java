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
        String query = "INSERT INTO estoque_principal (`id_estoque_p`, `estado`, `bloco`, `municipio`, `fazenda`, `projeto`, `upc`, `talhao`, `area`, `m3_ha`, "
                + "`material_genetico`, `talhadia`, `ano_rotacao`, `data_plantio`, `data_rotacao_1`, `data_rotacao_2`, `data_rotacao_3`, `idade`, `categoria`, "
                + "`situacao`, `ima`, `mad_ton_tot`, `carv_ton_tot`, `mdc_ha`, `densidade_madeira`, `densidade_carvao`, `id_operario`, `data_estoque`, "
                + "`vol_mad_estimado`, `vol_mad_real`, `vol_mad_balanco`, `mdc_estimado`, `mdc_real`, `mdc_balanco`, `mad_ton_estimado`, `mad_ton_real`, "
                + "`mad_ton_balanco`, `carv_ton_estimado`, `carv_ton_real`, `carv_ton_balanco`, `madeira_talhao`, `madeira_praca`, `madeira_forno`, "
                + "`rend_grav_estimado`, `rend_grav_real`, `fator_empilalhemto`) "
              + "VALUES (" + null
              /*+ ", '" + estoque.getEstado()
              + "', '" + estoque.getBloco()
              + "', '" + estoque.getMunicipio()
              + "', '" + estoque.getFazenda()
              + "', '" + estoque.getProjeto()*/
              + ", '" + ControlePrincipal.estado
              + "', '" + ControlePrincipal.bloco
              + "', '" + ControlePrincipal.municipio
              + "', '" + ControlePrincipal.fazenda
              + "', '" + ControlePrincipal.projeto
              + "', '" + estoque.getUpc()
              + "', '" + estoque.getTalhao()
              + "', '" + estoque.getArea()
              + "', '" + estoque.getM3_ha()
              +"', '0"
              +"', '0"
              +"', '0000"
              +"', '00-00-0000 00:00:00"
              +"', '00-00-0000 00:00:00"
              +"', '00-00-0000 00:00:00"
              +"', '00-00-0000 00:00:00"
              +"', '0.0"
              +"', '---"
              +"', '---"
              +"', '0.0"
              +"', '0.00"
              +"', '0.00"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '" + estoque.getId_operario()
              +"', '" + estoque.getData_estoque()              
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"')";
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }    
}
/*NULL, 'mg', 'sul', 'aaass', 'faz-A', 'proj-A', '1', '1', '25.62', '11', '5544545', '0', '2016', '2016-02-01 00:00:00', '00-00-0000 00:00:00', '00-00-0000 00:00:00', '00-00-0000 00:00:00', '0', '---', '---', '0', '0', '0', '0', '0', '0', 'a.000', '11-03-2016 15:43:24', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');*/