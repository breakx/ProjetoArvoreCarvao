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
public class InserirCarvaoCtrl {
    public InserirCarvaoCtrl(ControleCarvao carvao) {
        String query = "INSERT INTO controle_carvao (`id_controle_carvao`, `id_estoque_p`, `id_operario_mad`, `talhao`, `forno`, `volume_madeira`, `data_entrada_madeira_forno`, `id_operario_carv`, `volume_carvao`, `data_saida_carvao_forno`) "
              + "VALUES (" + null
              + ", '" + carvao.getId_estoque()
              + "', '" + carvao.getId_operario()
              + "', '" + carvao.getTalhao()
              + "', '" + carvao.getForno()
              + "', '" + carvao.getVolume_madeira()
              + "', '" + carvao.getData_entrada_madeira_forno()
              +"', '---"
              +"', '0.0"
              +"', '00-00-0000 00:00:00"
              +"')";
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }
    
}
