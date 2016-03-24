/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;
//import Visao.CadastroUsuarios;
import Visao.login.Login;

/**
 *
 * @author Cristiano GD
 */
public class Main {
    public static void main(String[] args) {
        //Logger.getLogger(CadastroUsuarios.class.getName()).log(Level.SEVERE, null, ex);        
        new Login().setVisible(true);
    }
}
