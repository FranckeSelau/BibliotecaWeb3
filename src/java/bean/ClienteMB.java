package bean;

import model.ClienteAntigo;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped //Application, pois os usuários cadastrados deverão permanecer mesmo se fizer logout.
public class ClienteMB {

    //CRUD
    private List<ClienteAntigo> listaClientes;
    private ClienteAntigo clienteSelecionado;
    private String matricula;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public ClienteMB() {
        clienteSelecionado = new ClienteAntigo();
        listaClientes = new ArrayList<ClienteAntigo>();
        listaClientes.add(new ClienteAntigo("01", "Fulano", "3333-4544"));
        listaClientes.add(new ClienteAntigo("02", "Beltrano", "3444-4545"));
    }
    
    public ClienteAntigo getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(ClienteAntigo clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public List<ClienteAntigo> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<ClienteAntigo> listaClientes) {
        this.listaClientes = listaClientes;
    }
    

    public String novoCliente(){
        clienteSelecionado=new ClienteAntigo();
        return("/admin/cadastroClientes?faces-redirect=true");
    }

    public String adicionarCliente(){        
        listaClientes.add(clienteSelecionado);
        this.novoCliente();
        return("/admin/confirmaCadastroCliente?faces-redirect=true");
    }
    
    public String mostrarClientes(){        
        return("/admin/listaClientes?faces-redirect=true");
    }
    
    public String mostrarClientesUsuario(){        
        return("/usuario/listaClientes?faces-redirect=true");
    }

    public String editarCliente(ClienteAntigo c){
        clienteSelecionado = c;
        return("/admin/edicaoClientes?faces-redirect=true");
    }
    public String atualizarCliente()
    {
        return("/admin/listaClientes?faces-redirect=true");
    }

    public void removerCliente(ClienteAntigo cliente){
        listaClientes.remove(cliente);
    }
    
    public ClienteAntigo buscarCliente(String matricula) {
        
        for (ClienteAntigo cliente : listaClientes) {
            if (cliente.getMatricula().equals(matricula)) {
                return cliente;
            }
        }        
        return null;
    }

}
