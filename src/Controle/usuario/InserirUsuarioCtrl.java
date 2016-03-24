/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle.usuario;
import Controle.ControleUsuario;
import Modelo.ExecutarSql;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristiano GD
 */
public class InserirUsuarioCtrl {
    public InserirUsuarioCtrl(ControleUsuario usuario)
    {
      String query = "INSERT INTO usuario values (" + null 
              + ", '" + usuario.getLogin_usuario()
              + "', '" + usuario.getSenha_usuario()
              + "', '" + usuario.getTipo_usuario()
              + "', '" + usuario.getNome_usuario()
              + "')";
      ExecutarSql execut = new ExecutarSql();
      execut.executar(query);
      //JOptionPane.showMessageDialog(null, "Erro!"+query);
    }
}
