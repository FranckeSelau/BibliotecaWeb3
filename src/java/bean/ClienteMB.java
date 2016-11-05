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
import model.Devolucao;
import rn.ClienteRN;
import rn.DevolucaoRN;

/**
 *
 * @author Francke
 */
@Named(value = "clienteMB")
@SessionScoped
public class ClienteMB implements Serializable {
    
    private Cliente clienteSelecionado;
    private String matricula;
    private String nomeBusca;
    private List<Cliente> pesquisaNome;
    @Inject
    private ClienteRN clienteRN;
    @Inject
    private DevolucaoRN devolucaoRN;
    

    public ClienteMB() {
        clienteSelecionado = new Cliente();
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente usuarioSelecionado) {
        this.clienteSelecionado = usuarioSelecionado;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }    
    
    public List<Cliente> getListaClientes(){
        return clienteRN.listar();
    }

    public List<Cliente> getPesquisaNome() {
        return pesquisaNome;
    }

    public void setPesquisaNome(List<Cliente> pesquisaNome) {
        this.pesquisaNome = pesquisaNome;
    }    

    public String getNomeBusca() {
        return nomeBusca;
    }

    public void setNomeBusca(String nomeBusca) {
        this.nomeBusca = nomeBusca;
    }
    
    public String novoCliente(){
        clienteSelecionado = new Cliente();
        return("/admin/clientes/cadastroClientes?faces-redirect=true");
    }
    
    public String adicionarCliente(){
        clienteRN.salvar(clienteSelecionado);
        this.novoCliente();
        return("/admin/clientes/confirmaCadastroCliente?faces-redirect=true");
    }
    
    public String mostrarClientes(){        
        return("/admin/clientes/listaClientes?faces-redirect=true");
    }
    
    public String mostrarClientesUsuario(){        
        return("/usuario/clientes/listaClientes?faces-redirect=true");
    }
    
    public String editarCliente(Cliente c){
        clienteSelecionado = c;
        return("/admin/clientes/edicaoClientes?faces-redirect=true");        
    }
    
    public String atualizarCliente(){
        clienteRN.salvar(clienteSelecionado);
        return("/admin/clientes/listaClientes?faces-redirect=true");
    }
    
    public String adicionarPesquisa() {
        pesquisaNome = buscarCliente(this.nomeBusca); 
        return ("/admin/clientes/buscaCliente?faces-redirect=true");
    }
    
    public String adicionarPesquisaUsuario() {
        pesquisaNome = buscarCliente(this.nomeBusca); 
        return ("/usuario/clientes/buscaCliente?faces-redirect=true");
    }
    
    public void removerCliente(Cliente cliente){// verifica se este cliente está relacionado a tabela devolução
        List<Devolucao> pesquisaCascade = devolucaoRN.buscarClienteExclusao(cliente.getMatricula());
        for (Devolucao devolucao : pesquisaCascade) {
            devolucaoRN.remover(devolucao);      
        }
        clienteRN.remover(cliente);
    } 
    
    public List<Cliente> buscarCliente(String nome){
        return clienteRN.buscarPorNome(nome);
    }
}
