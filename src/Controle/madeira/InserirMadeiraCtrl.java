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
        String query = "INSERT INTO controle_madeira (`id_controle_madeira`, `id_estoque_p`, `id_operario`, `talhao`, `data_entrega`, `mad_volume_m_stereo`, `mad_volume_m3`, `altura1_t`, `altura2_t`, `altura3_t`, `comprimento_t`, `largura_t`, `peso_t`, `altura1_bt`, `altura2_bt`, `altura3_bt`, `comprimento_bt`, `largura_bt`, `peso_bt`) "
            + "VALUES (" + null
            + ", '" + madeira.getId_estoque()
            + "', '" + madeira.getId_operario()
            + "', '" + madeira.getTalhao()
            + "', '" + madeira.getData_entrega()
            + "', '" + madeira.getMad_volume_m_stereo()
            + "', '" + madeira.getMad_volume_m3()
            + "', '" + madeira.getAltura1_t()
            + "', '" + madeira.getAltura2_t()
            + "', '" + madeira.getAltura3_t()
            + "', '" + madeira.getComprimento_t()
            + "', '" + madeira.getLargura_t()
            + "', '" + madeira.getPeso_t()
            + "', '" + madeira.getAltura1_bt()
            + "', '" + madeira.getAltura2_bt()
            + "', '" + madeira.getAltura3_bt()
            + "', '" + madeira.getComprimento_bt()
            + "', '" + madeira.getLargura_bt()
            + "', '" + madeira.getPeso_bt()
            + "')";
      ExecutarSql execut = new ExecutarSql();
      execut.executar(query);
      //JOptionPane.showMessageDialog(null, "Erro!"+query);
    }
}
