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
public class InserirMadeiraCtrl {
    public InserirMadeiraCtrl(ControleMadeira madeira)
    {      
      String query = "INSERT INTO controle_madeira (`id_controle_madeira`, `id_estoque_p`, `id_operario`, `talhao`, `saida_volume_talhao`, `data_talhao`, `altura1_t`, `altura2_t`, `altura3_t`, `comprimento_t`, `largura_t`, `peso_t`, `entrada_volume_praca`, `data_praca`, `altura1_p`, `altura2_p`, `altura3_p`, `comprimento_p`, `largura_p`, `peso_p`, `fator`) "
              + "VALUES (" + null
              + ", '" + madeira.getId_estoque()
              + "', '" + madeira.getId_operario()
              + "', '" + madeira.getTalhao()
              + "', '" + madeira.getSaida_volume_talhao()
              + "', '" + madeira.getData_talhao()
              + "', '" + madeira.getAltura1_t()
              + "', '" + madeira.getAltura2_t()
              + "', '" + madeira.getAltura3_t()
              + "', '" + madeira.getComprimento_t()
              + "', '" + madeira.getLargura_t()
              + "', '" + madeira.getPeso_t()
              +"', '0.0"
              +"', '00-00-0000 00:00:00"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              +"', '0.0"
              + "')";             
              //+ "', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)";
      ExecutarSql execut = new ExecutarSql();
      execut.executar(query);
      //JOptionPane.showMessageDialog(null, "Erro!"+query);
    }
}
