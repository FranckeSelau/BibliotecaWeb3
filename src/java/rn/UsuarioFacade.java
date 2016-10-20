/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Usuario;

/**
 *
 * @author mari
 */
@Stateless
public class UsuarioFacade extends AbstractRN<Usuario> {
    @PersistenceContext(unitName = "BibliotecaJsfJpfPU")
    private EntityManager manager;

    @Override
    protected EntityManager getEntityManager() {
        return manager;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
      public void salvar(Usuario u){
        //validar parâmetros
        if(u.getId()==null){
            super.adicionar(u);
        }
        else
            super.atualizar(u);
    }
}


