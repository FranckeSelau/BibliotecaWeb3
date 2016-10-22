/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import model.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lhries
 */
@Stateless
public class ClienteRN extends AbstractRN<Cliente>{
    @PersistenceContext(unitName="BibliotecaPU")
    private EntityManager manager;
    
    public ClienteRN(){
        super(Cliente.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return manager;
    }
    
    public void salvar(Cliente u){
        //validar parâmetros
        if(u.getMatricula()==null){
            super.adicionar(u);
        }
        else
            super.atualizar(u);
    }
}
