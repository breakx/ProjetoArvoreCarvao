/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.madeira;

import Controle.ControleMadeira;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class ExcluirMadeiraCtrl {
    public ExcluirMadeiraCtrl(ControleMadeira madeira)
    {
        String query = "DELETE FROM controle_madeira WHERE id_controle_madeira = "+ madeira.getId_controle_madeira();
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }
}
