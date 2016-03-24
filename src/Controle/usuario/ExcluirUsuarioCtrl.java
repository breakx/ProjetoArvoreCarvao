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
public class ExcluirUsuarioCtrl {
    public ExcluirUsuarioCtrl(ControleUsuario usuario)
    {
        String query = "DELETE FROM usuario WHERE id_usuario = '"+ usuario.getId_usuario()+"'";
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }
}
