/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristiano GD
 */
public class ConexaoBD {
    private static ConexaoBD instancia;
    public static Connection con = null;    

    private ConexaoBD()
    {
        String database = "appmadeira";
        String url = "jdbc:mysql://127.0.0.1:3306/"+database;
        String usuario_bd = "root";
        String senha_bd = "";
        try
        {
            Class.forName("org.gjt.mm.mysql.Driver");
            con = DriverManager.getConnection(url,usuario_bd,senha_bd);
        }catch( Exception ex )
        {
            System.err.printf("\nExceção: %s\n", ex);
            //JOptionPane.showMessageDialog(null, "Banco de dados não encontrado!");
            throw new java.lang.RuntimeException("Erro ao conectar 2");
        }
    }

    public static ConexaoBD getConexao()
    {
        try
        {
            if(con == null){
               instancia = new ConexaoBD();
            }
            return instancia;
        }catch( Exception ex ){
            System.err.printf("\nExceção: %s\n", ex);
            JOptionPane.showMessageDialog(null, "Servidor/Banco de Dados não encontrado!");
            throw new java.lang.RuntimeException("Erro ao conectar 1");
        }
    }


    public void fecharConexao()
    {
        try{

            if(con != null){
                con.close();
                con = null;

            }
        } catch (Exception ex)
        {
            System.err.printf("\nExceção: %s\n", ex);
            throw new java.lang.RuntimeException("Erro ao conectar 3");
        }

    }

    public ResultSet consultaSql(String comando)
    {
        try
        {
            Statement stmt = con.createStatement();
            return stmt.executeQuery(comando);
        }
        catch( java.sql.SQLException ex )
        {
            System.err.printf("\nExceção: %s\n", ex);
            throw new java.lang.RuntimeException(ex.getMessage());
        }
    }
}
