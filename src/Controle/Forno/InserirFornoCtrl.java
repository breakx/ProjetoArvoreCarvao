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
public class InserirFornoCtrl {
    public InserirFornoCtrl(ControleForno forno) {
        String query = "INSERT INTO forno ("
                + "`id_forno`, "
                + "`nome_forno`, "
                + "`volume_maximo_forno`, "
                + "`situacao_forno`, "
                + "`upc_forno`, "
                + "`data_alteracao`, "
                + "`usuario_alt`"
                + ")"
              + "VALUES (" + null
              + ", '" + forno.getNome_forno()
              + "', '" + forno.getVolume_maximo_forno()
              + "', '" + forno.getSituacao_forno()
              + "', '" + forno.getUpc_forno()
              + "', '" + forno.getData_alteracao()
              + "', '" + forno.getUsuario_alt()
              +"')";
        //System.out.println("query: "+query);
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }
    
}
