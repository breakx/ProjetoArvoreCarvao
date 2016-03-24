/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo;
import Controle.ControlePrincipal;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cristiano GD
 */
public class ExecutarSql {
    public void executar(String comando)
    {
        int n = JOptionPane.showConfirmDialog(  
                    null,
                    "Continuar?!" ,
                    "",
                    JOptionPane.YES_NO_OPTION);

        if(n == JOptionPane.YES_OPTION)
        {
            try
            {
                //JOptionPane.showMessageDialog(null, "SQL= "+comando);
                ConexaoBD conexao = ConexaoBD.getConexao();
                Statement stmt = ConexaoBD.con.createStatement();
                stmt.executeUpdate(comando);
                stmt.close();
                conexao.fecharConexao();
                if(ControlePrincipal.id_estoque_principal!=null){
                    UpdateEstoque();
                }
            }
            catch( java.sql.SQLException e )
            {
                System.err.printf("\nExceção: %s\n"+comando, e);
                throw new java.lang.RuntimeException(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Executando...");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Cancelado");
        }       
    }
    
    private void UpdateEstoque(){
        ControlePrincipal.volume_madeira_total = ControlePrincipal.volume_madeira_talhao+ControlePrincipal.volume_madeira_praca+ControlePrincipal.volume_madeira_forno;
        String query = "";
        
        /*if(ControlePrincipal.volume_madeira_talhao > 0){
            query = "UPDATE estoque_principal SET "
                + "`madeira_talhao` = '"+ControlePrincipal.volume_madeira_talhao    
                + "', `mad_ton_tot` = '"+ControlePrincipal.volume_madeira_total           
                + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        }else if(ControlePrincipal.volume_madeira_praca > 0){
            query = "UPDATE estoque_principal SET "
                + "`madeira_praca` = '"+ControlePrincipal.volume_madeira_praca
                + "', `mad_ton_tot` = '"+ControlePrincipal.volume_madeira_total
                + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        }else if(ControlePrincipal.volume_madeira_forno > 0){
            query = "UPDATE estoque_principal SET "
                + "`madeira_praca` = '"+ControlePrincipal.volume_madeira_praca
                + "', `madeira_forno` = '"+ControlePrincipal.volume_madeira_forno
                + "', `mad_ton_tot` = '"+ControlePrincipal.volume_madeira_total
                + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        }else if(ControlePrincipal.volume_carvao_total > 0){
            query = "UPDATE estoque_principal SET "
                + "`madeira_forno` = '"+ControlePrincipal.volume_madeira_forno
                + "', `carv_ton_tot` = '"+ControlePrincipal.volume_carvao_total
                + "', `mad_ton_tot` = '"+ControlePrincipal.volume_madeira_total
                + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        }/*else if(ControlePrincipal.volume_madeira_total > 0){
            query = "UPDATE estoque_principal SET "
                + "`mad_ton_tot` = '"+ControlePrincipal.volume_madeira_total
                + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        }*/
        query = "UPDATE estoque_principal SET "
                + "`madeira_talhao` = '"+ControlePrincipal.volume_madeira_talhao
                + "', `madeira_praca` = '"+ControlePrincipal.volume_madeira_praca
                + "', `madeira_forno` = '"+ControlePrincipal.volume_madeira_forno
                + "', `mad_ton_tot` = '"+ControlePrincipal.volume_madeira_total
                + "', `carv_ton_tot` = '"+ControlePrincipal.volume_carvao_total
                + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        
        //JOptionPane.showMessageDialog(null, "Executar Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_total+" carv: "+ControlePrincipal.volume_carvao_total);
        
        try {
            ConexaoBD conexao = ConexaoBD.getConexao();
            Statement stmt = ConexaoBD.con.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conexao.fecharConexao();
        } catch (SQLException ex) {
            Logger.getLogger(ExecutarSql.class.getName()).log(Level.SEVERE, null, ex);
        }     
        ControlePrincipal.id_estoque_principal = null;
        ControlePrincipal.volume_carvao_total = 0;
        ControlePrincipal.volume_madeira_forno = 0;
        ControlePrincipal.volume_madeira_praca = 0;
        ControlePrincipal.volume_madeira_talhao = 0;
        ControlePrincipal.volume_madeira_total = 0;
    }
}
