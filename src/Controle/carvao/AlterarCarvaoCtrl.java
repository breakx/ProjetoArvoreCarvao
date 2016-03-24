/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.carvao;

import Controle.ControleCarvao;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class AlterarCarvaoCtrl {

    public AlterarCarvaoCtrl(ControleCarvao carvao) {
        String query = "UPDATE controle_carvao SET "
                + "id_operario_carv = '"+ carvao.getId_operario()
                + "', `volume_carvao` = '"+carvao.getVolume_carvao()
                + "', `data_saida_carvao_forno` = '"+carvao.getData_saida_carvao()
                + "', `fator` = '"+carvao.getFator()
                + "' WHERE id_controle_carvao = "+carvao.getId_controle_carvao();
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }    
}
