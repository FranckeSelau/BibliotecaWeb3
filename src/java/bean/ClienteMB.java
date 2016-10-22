/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import model.Cliente;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import rn.ClienteRN;

/**
 *
 * @author Francke
 */
@Named(value = "clienteMB")
@SessionScoped
public class ClienteMB implements Serializable {
    
    private Cliente clienteSelecionado;
    @Inject
    private ClienteRN clienteRN;

    public ClienteMB() {
        clienteSelecionado = new Cliente();
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente usuarioSelecionado) {
        this.clienteSelecionado = usuarioSelecionado;
    }
    
    public List<Cliente> getListaClientes(){
        return clienteRN.listar();
    }
    
    public String novoCliente(){
        clienteSelecionado = new Cliente();
        return("/formularioCadastroCliente");
    }
    
    public String adicionarCliente(){
        clienteRN.salvar(clienteSelecionado);
        return(this.novoCliente());
    }
    
    public String editarCliente(Cliente c){
        clienteSelecionado = c;
        return("/formularioEdicaoCliente");        
    }
    
    public String atualizarCliente(){
        clienteRN.salvar(clienteSelecionado);
        return("/index");
    }
    
    public void removerCliente(Cliente cliente){
        clienteRN.remover(cliente);
    }
    
}
