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
public class ExcluirCarvaoCrtl {

    public ExcluirCarvaoCrtl(ControleCarvao carvao) {
        String query = "DELETE FROM controle_carvao WHERE id_controle_carvao = "+ carvao.getId_controle_carvao();
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }
    
}
