/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Cliente;

/**
 *
 * @author mari
 */
@Stateless
public class ClienteRN extends AbstractRN<Cliente> {
    @PersistenceContext(unitName = "BibliotecaJsfJpfPU")
    private EntityManager manager;

    @Override
    protected EntityManager getEntityManager() {
        return manager;
    }

    public ClienteRN() {
        super(Cliente.class);
    }
     public void salvar(Cliente c){
        //validar parâmetros
        if(c.getId()==null){
            super.adicionar(c);
        }
        else
            super.atualizar(c);
    }
}
