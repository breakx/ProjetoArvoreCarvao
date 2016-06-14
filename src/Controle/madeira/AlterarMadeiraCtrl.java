/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.madeira;

import Controle.ControleMadeira;
import Modelo.ExecutarSql;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristiano GD
 */
public class AlterarMadeiraCtrl {
    public AlterarMadeiraCtrl(ControleMadeira madeira)
    {
        String query = "UPDATE controle_madeira SET "
                //+ "id_operario = '"+madeira.getId_operario()
                //+"', id_estoque_p = '"+madeira.getId_estoque_p()
                //+"', talhao = '"+madeira.getTalhao()
                +"mad_volume_m_stereo = '"+madeira.getMad_volume_m_stereo()
                +"', mad_volume_m3 = '"+madeira.getMad_volume_m3()
                +"', altura1_t = '"+madeira.getAltura1_t()
                +"', altura2_t = '"+madeira.getAltura2_t()
                +"', altura3_t = '"+madeira.getAltura3_t()
                +"', comprimento_t = '"+madeira.getComprimento_t()
                +"', largura_t = '"+madeira.getLargura_t()
                +"', peso_t = '"+madeira.getPeso_t()
                //+"entrada_volume_praca = '"+madeira.getEntrada_volume_praca()
                //+"', data_praca = '"+madeira.getData_praca()
                +"', altura1_bt = '"+madeira.getAltura1_bt()
                +"', altura2_bt = '"+madeira.getAltura2_bt()
                +"', altura3_bt = '"+madeira.getAltura3_bt()
                +"', comprimento_bt = '"+madeira.getComprimento_bt()
                +"', largura_bt = '"+madeira.getLargura_bt()
                +"', peso_bt = '"+madeira.getPeso_bt()
                +"' WHERE id_controle_madeira = "+madeira.getId_controle_madeira();
        //System.out.println("query: " + query);
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }   
}
