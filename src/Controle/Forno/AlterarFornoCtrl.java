/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.Forno;

import Controle.ControleForno;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class AlterarFornoCtrl {

    public AlterarFornoCtrl(ControleForno forno) {
        String query = "UPDATE forno SET "
                + "`nome_forno` = '"+forno.getNome_forno()
                + "', `volume_maximo_forno` = '"+forno.getVolume_maximo_forno()
                + "', `situacao_forno` = '"+forno.getSituacao_forno()
                + "', `upc_forno` = '"+forno.getUpc_forno()
                + "', `data_alteracao` = '"+forno.getData_alteracao()
                + "', `usuario_alt` = '"+forno.getUsuario_alt()
                + "' WHERE id_forno = "+forno.getId_forno();
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }    
}
