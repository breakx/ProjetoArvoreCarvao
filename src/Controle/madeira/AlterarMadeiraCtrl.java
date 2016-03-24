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
                //+"', saida_volume_talhao = '"+madeira.getSaida_volume_talhao()
                //+"', data_talhao = '"+madeira.getData_talhao()
                //+"', altura1_t = '"+madeira.getAltura1_t()
                //+"', altura2_t = '"+madeira.getAltura2_t()
                //+"', altura3_t = '"+madeira.getAltura3_t()
                //+"', comprimento_t = '"+madeira.getComprimento_t()
                //+"', largura_t = '"+madeira.getLargura_t()
                //+"', peso_t = '"+madeira.getPeso_t()
                +"entrada_volume_praca = '"+madeira.getEntrada_volume_praca()
                +"', data_praca = '"+madeira.getData_praca()
                +"', altura1_p = '"+madeira.getAltura1_p()
                +"', altura2_p = '"+madeira.getAltura2_p()
                +"', altura3_p = '"+madeira.getAltura3_p()
                +"', comprimento_p = '"+madeira.getComprimento_p()
                +"', largura_p = '"+madeira.getLargura_p()
                +"', peso_p = '"+madeira.getPeso_p()
                +"', fator = '"+madeira.getFator()
                +"' WHERE id_controle_madeira = "+madeira.getId_controle_madeira();
        //String query = "Update controle_madeira set id_operario = '" + usuario.getLogin_usuario()+ "', senha_usuario='" + usuario.getSenha_usuario()+ "', tipo_usuario='" + usuario.getTipo_usuario()+ "', nome_usuario='" + usuario.getNome_usuario()+"'where id_usuario='"+ usuario.getId_usuario()+"'";
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }   
}
