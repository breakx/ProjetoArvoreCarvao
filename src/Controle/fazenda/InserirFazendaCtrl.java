/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.fazenda;

import Controle.ControleFazenda;
import Controle.ControlePrincipal;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class InserirFazendaCtrl {

    public InserirFazendaCtrl(ControleFazenda fazenda) {
        String query = "INSERT INTO fazenda ("
                + "`id_fazenda`, "
                + "`estado`, "
                + "`bloco`, "
                + "`municipio`, "
                + "`fazenda`, "
                + "`projeto"
                + "`) "
              + "VALUES (" + null
              + ", '" + fazenda.getEstado()
              + "', '" + fazenda.getBloco()
              + "', '" + fazenda.getMunicipio()
              + "', '" + fazenda.getFazenda()
              + "', '" + fazenda.getProjeto()
              //+ "', '1"
              +"')";
        ExecutarSql execut = new ExecutarSql();
        if(!ControlePrincipal.excel_cmd){
            execut.executar(query);
        }else{
            execut.executar2(query);            
        }
    }
    
}
