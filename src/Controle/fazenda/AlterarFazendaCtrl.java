/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.fazenda;

import Controle.ControleFazenda;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class AlterarFazendaCtrl {

    public AlterarFazendaCtrl(ControleFazenda fazenda) {
        String query = "UPDATE fazenda SET "
                + "`cod_estado` = '"+fazenda.getCod_estado()
                + "', `estado` = '"+fazenda.getEstado()
                + "', `cod_bloco` = '"+fazenda.getCod_bloco()
                + "', `bloco` = '"+fazenda.getBloco()
                + "', `municipio` = '"+fazenda.getMunicipio()
                + "', `fazenda` = '"+fazenda.getFazenda()
                + "', `projeto` = '"+fazenda.getProjeto()
                + "' WHERE id_fazenda = "+fazenda.getId_fazenda();
        
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }
    
}
