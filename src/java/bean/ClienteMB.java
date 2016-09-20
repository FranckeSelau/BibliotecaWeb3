package bean;

import model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped //Application, pois os usu�rios cadastrados dever�o permanecer mesmo se fizer logout.
public class ClienteMB {

    //CRUD
    private List<Cliente> listaClientes;
    private Cliente clienteSelecionado;

    public ClienteMB() {
        clienteSelecionado = new Cliente();
        listaClientes = new ArrayList<Cliente>();
        listaClientes.add(new Cliente("01", "Fulano", "3333-4544"));
        listaClientes.add(new Cliente("02", "Beltrano", "3444-4545"));
    }
    
    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
    

    public String novoCliente(){
        clienteSelecionado=new Cliente();
        return("/admin/cadastroClientes?faces-redirect=true");
    }

    public String adicionarCliente()
    {
        listaClientes.add(clienteSelecionado);
        return(this.novoCliente());
    }

    public String editarCliente(Cliente c){
        clienteSelecionado = c;
        return("/admin/formularioEdicao?faces-redirect=true");
    }
    public String atualizarCliente()
    {
        return("/admin/index?faces-redirect=true");
    }

    public void removerCliente(Cliente cliente){
        listaClientes.remove(cliente);
    }

}