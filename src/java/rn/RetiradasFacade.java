/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Retiradas;

/**
 *
 * @author mari
 */
@Stateless
public class RetiradasFacade extends AbstractRN<Retiradas> {
    @PersistenceContext(unitName = "BibliotecaJsfJpfPU")
    private EntityManager manager;

    @Override
    protected EntityManager getEntityManager() {
        return manager;
    }

    public RetiradasFacade() {
        super(Retiradas.class);
    }
    public void salvar(Retiradas r){
        //validar parâmetros
        if(r.getRetirada()==null){
            super.adicionar(r);
        }
        else
            super.atualizar(r);
    }
}
