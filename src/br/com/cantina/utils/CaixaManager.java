/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cantina.utils;

import br.com.cantina.modeldb.Caixa;
import br.com.cantina.modeldb.controller.CaixaJpaController;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;

/**
 *
 * @author ntbra
 */
public class CaixaManager {
    private static final CaixaJpaController controller = ControllerProvider.getCaixaJpaController();
    public static Caixa getOpenOrOpenNew(){
        TypedQuery<Caixa> query = controller.getEntityManager()
                .createNamedQuery("Caixa.findNotClosed", Caixa.class);
        List<Caixa> caixas = query.getResultList();
        if(caixas.isEmpty()){
            Caixa caixa = new Caixa();
            caixa.setAberto(Boolean.TRUE);
            caixa.setTimeopen(new Date());
            controller.create(caixa);
            return getOpenOrOpenNew();
        }
        switch(caixas.size()){
            case 1:{
                return caixas.get(0);
            }
            default:{
                System.err.println("ERRO MORE THAN EXPECTED. RETURNED SIZE ["+caixas.size()+"] -> "+caixas);
                return null;
            }
        }
    }
    
    public static boolean closeCaixa(Caixa caixa){
        caixa.setAberto(false);
        caixa.setTimeclose(new Date());
        try {
            controller.edit(caixa);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(CaixaManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
