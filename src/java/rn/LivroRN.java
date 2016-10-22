/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import model.Livro;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lhries
 */
@Stateless
public class LivroRN extends AbstractRN<Livro>{
    @PersistenceContext(unitName="BibliotecaWeb2PU")
    private EntityManager manager;
    
    public LivroRN(){
        super(Livro.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return manager;
    }
    
    public void salvar(Livro l){
        //validar parâmetros
        if(l.getId()==null){
            super.adicionar(l);
        }
        else
            super.atualizar(l);
    }
}
