/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import model.Devolucao;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Francke
 */
@Stateless
public class DevolucaoRN extends AbstractRN<Devolucao>{
    @PersistenceContext(unitName="BibliotecaWeb2PU")
    private EntityManager manager;
    
    public DevolucaoRN(){
        super(Devolucao.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return manager;
    }
    
    public void salvar(Devolucao l){
        //validar parâmetros
        if(l.getId()==null){
            super.adicionar(l);
        }
        else
            super.atualizar(l);
    }
}
