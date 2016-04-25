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
                JOptionPane.showMessageDialog(null, "SQL= "+comando);
                ConexaoBD conexao = ConexaoBD.getConexao();
                Statement stmt = ConexaoBD.con.createStatement();
                stmt.executeUpdate(comando);
                stmt.close();
                conexao.fecharConexao();
                if(ControlePrincipal.atualizarDados!=null){
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
        String query = "";
        if(ControlePrincipal.tipo_u.equals("op_m")){
            query = "UPDATE estoque_principal SET "
                + "`vol_mad_transp` = '"+ControlePrincipal.vol_mad_transp
                + "', `vol_mad_balanco` = '"+ControlePrincipal.vol_mad_balanco
                + "', `mad_ton_transp` = '"+ControlePrincipal.mad_ton_transp
                + "', `mad_ton_balanco` = '"+ControlePrincipal.mad_ton_balanco
                + "', `madeira_praca` = '"+ControlePrincipal.madeira_praca 
                + "', `mad_ton_tot` = '"+ControlePrincipal.mad_ton_tot
                + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        }else if(ControlePrincipal.tipo_u.equals("op_c")){
            query = "UPDATE estoque_principal SET "
                + "`mdc_transp` = '"+ControlePrincipal.mdc_transp
                + "', `mdc_balanco` = '"+ControlePrincipal.mdc_balanco
                + "', `carv_ton_transp` = '"+ControlePrincipal.carv_ton_transp
                + "', `carv_ton_balanco` = '"+ControlePrincipal.carv_ton_balanco
                + "', `madeira_praca` = '"+ControlePrincipal.madeira_praca
                + "', `madeira_forno` = '"+ControlePrincipal.madeira_forno   
                + "', `carv_ton_tot` = '"+ControlePrincipal.carv_ton_tot
                + "', `rend_grav_real` = '"+ControlePrincipal.rend_grav_real
                + "' WHERE id_estoque_p = "+ControlePrincipal.id_estoque_principal;
        }
        
        //JOptionPane.showMessageDialog(null, "Executar Talhao: "+ControlePrincipal.volume_madeira_talhao+" praca: "+ControlePrincipal.volume_madeira_praca+" forno: "+ControlePrincipal.volume_madeira_forno+" mad: "+ControlePrincipal.volume_madeira_transp+" carv: "+ControlePrincipal.volume_carvao_transp);
        
        try {
            ConexaoBD conexao = ConexaoBD.getConexao();
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
        ControlePrincipal.mdc_transp = 0;
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
    }
}
