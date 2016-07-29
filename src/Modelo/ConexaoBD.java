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
    
    public static String hostname = "";
    private String host = "";
    private String database = "";
    private String url = "";
    private String usuario_bd = "";
    private String senha_bd = "";

    private ConexaoBD(int index)
    {
        //System.err.println("ConexaoBD: "+ index);
        switch(index){
            case 0:
                BDSecundario();
                //BDPrincipal();
                //BDLocal();
                break;
            case 1:                
                BDSecundario();
                //BDLocal();
                break;
            case 2:
                BDLocal();
                break;
        }
        try
        {
            //Class.forName("org.gjt.mm.mysql.Driver").newInstance();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url,usuario_bd,senha_bd);
        }catch( ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex )
        {
            System.err.printf("\nExceção: %s\n", ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar: "+ex);
            //System.exit(0);
            throw new java.lang.RuntimeException("Erro ao conectar 2");
        }
    }
    
    private void BDPrincipal(){
        hostname ="BDPrincipal";
        host ="db4free.net:3306/";
        database = "appmadeiracarvao";
        url = "jdbc:mysql://"+host+database;
        usuario_bd = "crgddev";
        senha_bd = "duarte1207";
        //JOptionPane.showMessageDialog(null, "Conectando ao bd db4free!");
    }
    
    private void BDLocal(){
        hostname ="localhost";
        host ="localhost/";
        database = "appmadeira";
        url = "jdbc:mysql://"+host+database;
        usuario_bd = "root";
        senha_bd = "";
        //JOptionPane.showMessageDialog(null, "Conectando ao bd localhost!");
    }
    
    private void BDSecundario(){
        hostname ="BDSecundario";
        host ="mysql873.umbler.com:41890/";
        database = "crserver";
        url = "jdbc:mysql://"+host+database;
        usuario_bd = "crgd";
        senha_bd = "crgd1234";
        //JOptionPane.showMessageDialog(null, "Conectando ao bd umber!");
    }

    public static ConexaoBD getConexao(int index)
    {
        try
        {
            if(con == null){
               instancia = new ConexaoBD(index);
            }
            return instancia;
        }catch( Exception ex ){
            System.err.printf("\nExceção: %s\n", ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar: "+ex);
            //System.exit(0);
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
            JOptionPane.showMessageDialog(null, "Conexão fechada! "+ex);
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
