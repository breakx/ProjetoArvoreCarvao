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
        String query = "UPDATE estoque_principal SET "
                + "`estado` = '"+estoque.getEstado()
                + "', `upc` = '"+estoque.getUpc()
                + "', `bloco` = '"+estoque.getBloco()
                + "', `municipio` = '"+estoque.getMunicipio()
                + "', `fazenda` = '"+estoque.getFazenda()
                + "', `projeto` = '"+estoque.getProjeto()
                + "', `ano_rotacao` = '"+estoque.getAno_rotacao()
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
                + "', `madeira_talhao` = '"+estoque.getMadeira_talhao()
                + "', `madeira_praca` = '"+estoque.getMadeira_praca()
                + "', `madeira_forno` = '"+estoque.getMadeira_forno()
                + "', `mad_ton_tot` = '"+estoque.getMad_ton_tot()
                + "', `carv_ton_tot` = '"+estoque.getCarv_ton_tot()
                + "' WHERE id_estoque_p = "+estoque.getId_estoque_p();
        
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }

}
