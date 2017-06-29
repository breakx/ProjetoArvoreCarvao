/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle.carvao;

import Controle.ControleCarvao;
import Controle.ControlePrincipal;
import Modelo.ExecutarSql;

/**
 *
 * @author Cristiano GD
 */
public class AlterarCarvaoCtrl {

    public AlterarCarvaoCtrl(ControleCarvao carvao) {
        String query = null;
        //System.out.println("condicao_forno: "+ControlePrincipal.condicao_forno);
        if(ControlePrincipal.condicao_forno.equals("Carbonizando")){
            query = "UPDATE controle_carvao SET "
                + "`data_ignicao` = '"+carvao.getData_ignicao()
                + "' WHERE id_controle_carvao = "+carvao.getId_controle_carvao();
        }else if(ControlePrincipal.condicao_forno.equals("Resfriando")){
            query = "UPDATE controle_carvao SET "
                + "`data_fim_carbonizacao` = '"+carvao.getData_fim_carbonizacao()
                + "' WHERE id_controle_carvao = "+carvao.getId_controle_carvao();
        }else{            
            query = "UPDATE controle_carvao SET "
                + "`volume_carvao` = '"+carvao.getVolume_carvao()
                + "', `data_saida_carvao_forno` = '"+carvao.getData_saida_carvao()
                + "', `rend_grav_forno` = '"+carvao.getRend_grav_forno()
                + "' WHERE id_controle_carvao = "+carvao.getId_controle_carvao();
        }
        //System.out.println("query: "+query);
        ExecutarSql execut = new ExecutarSql();
        execut.executar(query);
    }    
}
