/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.expedircarvao;

import Controle.ControleExpedirCarvao;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class InserirExpedicaoCarvaoCtrl {
    public InserirExpedicaoCarvaoCtrl(ControleExpedirCarvao expedircarvao) {
        String query = "INSERT INTO expedir_carvao ("
                + "`id_expedir_carvao`, "
                + "`id_estoque_p`, "
                + "`id_operario`, "
                + "`upc`, "
                + "`talhao`, "
                + "`peso_transportado`, "
                + "`volume_transportado`, "
                + "`destino_carvao`, "
                + "`placa_veiculo`, "
                + "`data_envio`, "
                + "`material_gen`"
                + ")"
              + "VALUES (" + null
              + ", '" + expedircarvao.getId_estoque_p()
              + "', '" + expedircarvao.getId_operario()
              + "', '" + expedircarvao.getUpc()
              + "', '" + expedircarvao.getTalhao()
              + "', '" + expedircarvao.getPeso_transportado()
              + "', '" + expedircarvao.getVolume_transportado()
              + "', '" + expedircarvao.getDestino_carvao()
              + "', '" + expedircarvao.getPlaca_veiculo()
              + "', '" + expedircarvao.getData_envio()
              + "', '" + expedircarvao.getMaterial_gen()
              +"')";
        //ALTER TABLE `controle_carvao` ADD `material_gen` VARCHAR(20) NOT NULL ;
        //System.out.println("query: "+query);
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }
}
