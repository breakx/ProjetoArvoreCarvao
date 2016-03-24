/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle.usuario;
import Controle.ControleUsuario;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class AlterarUsuarioCtrl {
    public AlterarUsuarioCtrl(ControleUsuario usuario)
    {
       String query = "Update usuario set login_usuario = '" + usuario.getLogin_usuario()+ "', senha_usuario='" + usuario.getSenha_usuario()+ "', tipo_usuario='" + usuario.getTipo_usuario()+ "', nome_usuario='" + usuario.getNome_usuario()+"'where id_usuario='"+ usuario.getId_usuario()+"'";
       ExecutarSql execut = new ExecutarSql();
       execut.executar(query);
    }
}
