/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.LivroAntigo;

/**
 *
 * @author mari
 */
@Stateless
public class LivroFacade extends AbstractRN<LivroAntigo> {
    @PersistenceContext(unitName = "BibliotecaJsfJpfPU")
    private EntityManager manager;

    @Override
    protected EntityManager getEntityManager() {
        return manager;
    }

    public LivroFacade() {
        super(LivroAntigo.class);
    }
     public void salvar(LivroAntigo l){
        //validar parâmetros
        if(l.getId()==null){
            super.adicionar(l);
        }
        else
            super.atualizar(l);
    }
}
