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
    public void executar(String comando){
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
                //System.out.println("SQL= "+comando);
                ConexaoBD conexao = ConexaoBD.getConexao(0);
                Statement stmt = ConexaoBD.con.createStatement();
                stmt.executeUpdate(comando);
                stmt.close();
                conexao.fecharConexao();
                ControlePrincipal.desbrota=null;
                if(ControlePrincipal.atualizarDados!=null){
                    UpdateEstoque();
                }
                if(ControlePrincipal.id_forno_usado!=null){
                    UpdateForno();
                }
            }
            catch( java.sql.SQLException e )
            {
                //System.err.printf("\nExceção: %s\n"+comando, e);
                throw new java.lang.RuntimeException(e.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Executando...");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Cancelado");
        }       
    }
    
    public void executar2(String comando){
        try
        {
            ConexaoBD conexao = ConexaoBD.getConexao(0);
            Statement stmt = ConexaoBD.con.createStatement();
            stmt.executeUpdate(comando);
            stmt.close();
            conexao.fecharConexao();
        }
        catch( java.sql.SQLException e )
        {
            //System.err.printf("\nExceção: %s\n"+comando, e);
            throw new java.lang.RuntimeException(e.getMessage());
        }       
    }
    
    private void UpdateForno(){
        String query = "UPDATE forno SET "
                + "`situacao_forno` = '"+ControlePrincipal.condicao_forno
                + "' WHERE id_forno = "+ControlePrincipal.id_forno_usado;
        
        //System.out.printf("UpdateForno: "+query);
        try {
            ConexaoBD conexao = ConexaoBD.getConexao(0);
            Statement stmt = ConexaoBD.con.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conexao.fecharConexao();
        } catch (SQLException ex) {
            Logger.getLogger(ExecutarSql.class.getName()).log(Level.SEVERE, null, ex);
        }     
        ControlePrincipal.condicao_forno = null;
        ControlePrincipal.id_forno_usado = null;
    }
    
    private void UpdateEstoque(){
        String query = "";
        switch (ControlePrincipal.atualizarDados) {
            case "madeira":
                query = "UPDATE estoque_principal SET "
                        + "`vol_mad_transp` = '"+ControlePrincipal.vol_mad_transp
                        + "', `vol_mad_balanco` = '"+ControlePrincipal.vol_mad_balanco
                        + "', `mad_ton_transp` = '"+ControlePrincipal.mad_ton_transp
                        + "', `mad_ton_balanco` = '"+ControlePrincipal.mad_ton_balanco
                        + "', `madeira_praca` = '"+ControlePrincipal.madeira_praca
                        + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
                break;
            case "carvao":
                query = "UPDATE estoque_principal SET "
                        + "`mdc_prod` = '"+ControlePrincipal.mdc_prod
                        + "', `mdc_balanco` = '"+ControlePrincipal.mdc_balanco
                        + "', `carv_ton_prod` = '"+ControlePrincipal.carv_ton_prod
                        + "', `carv_ton_balanco` = '"+ControlePrincipal.carv_ton_balanco
                        + "', `madeira_praca` = '"+ControlePrincipal.madeira_praca
                        + "', `carvao_praca` = '"+ControlePrincipal.carvao_praca
                        + "', `mdc_transp` = '"+ControlePrincipal.mdc_transp
                        + "', `carv_ton_transp` = '"+ControlePrincipal.carv_ton_transp
                        + "', `madeira_forno` = '"+ControlePrincipal.madeira_forno
                        + "', `rend_grav_real` = '"+ControlePrincipal.rend_grav_real
                        + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
                break;
        }
        
        //System.out.println("SQL= "+query);
        //JOptionPane.showMessageDialog(null, "Executar Talhao: "+query);
        
        try {
            ConexaoBD conexao = ConexaoBD.getConexao(0);
            Statement stmt = ConexaoBD.con.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conexao.fecharConexao();
        } catch (SQLException ex) {
            Logger.getLogger(ExecutarSql.class.getName()).log(Level.SEVERE, null, ex);
        }     
        LimparDados();        
    }
    
    public void LimparDados(){
        ControlePrincipal.id_estoque_principal = null;
        ControlePrincipal.densidade_madeira = 0;
        ControlePrincipal.densidade_carvao = 0;
    
        ControlePrincipal.vol_mad_estimado = 0;
        ControlePrincipal.vol_mad_transp = 0;
        ControlePrincipal.vol_mad_balanco = 0;
        ControlePrincipal.mdc_estimado = 0;
        ControlePrincipal.mdc_prod = 0;
        ControlePrincipal.mdc_balanco = 0;
        ControlePrincipal.mad_ton_estimado = 0;
        ControlePrincipal.mad_ton_transp = 0;
        ControlePrincipal.mad_ton_balanco = 0;
        ControlePrincipal.carv_ton_estimado = 0;
        ControlePrincipal.carv_ton_transp = 0;
        ControlePrincipal.carv_ton_balanco = 0;
        ControlePrincipal.madeira_praca = 0;
        ControlePrincipal.madeira_forno = 0;
        ControlePrincipal.mad_ton_tot = 0;
        ControlePrincipal.carv_ton_tot = 0;
        ControlePrincipal.rend_grav_real = 0;
        ControlePrincipal.atualizarDados = null; 
        //ControlePrincipal.condicao_forno = null;
    }
}
