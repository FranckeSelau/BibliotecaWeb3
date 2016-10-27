package bean;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Cliente;
import model.Livro;
import model.Retiradas;
import rn.RetiradasRN;

@Named
@ApplicationScoped
public class RetiradasMB implements Serializable {

    @Inject
    RetiradasRN retiradaRN;
    @Inject
    ClienteMB clienteMB;
    @Inject
    LivroMB livroMB;
    private long id;
    private Cliente clienteSelecionado;
    private Livro livroSelecionado;

    //CRUD
    private List<Retiradas> listaRetiradas;
    private Retiradas retiradaSelecionada;

    public RetiradasMB() {
        retiradaSelecionada = new Retiradas();
        clienteSelecionado = new Cliente();
        livroSelecionado = new Livro();        
    }

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(Livro livroSelecionado) {
        this.livroSelecionado = livroSelecionado;
    }    
    
    public Retiradas getRetiradaSelecionada() {
        return retiradaSelecionada;
    }

    public void setRetiradaSelecionada(Retiradas retiradaSelecionada) {
        this.retiradaSelecionada = retiradaSelecionada;
    }

    public List<Retiradas> getListaRetiradas() {
        return retiradaRN.listar();
    }

    public String novaRetirada() {
        retiradaSelecionada = new Retiradas();
        return ("/admin/retirada?faces-redirect=true");
    }

    public String novaRetiradaUsuario() {
        retiradaSelecionada = new Retiradas();
        return ("/usuario/retirada?faces-redirect=true");
    }

    public String adicionarRetirada() {
        retiradaSelecionada.setCliente(clienteSelecionado);
        retiradaSelecionada.setLivro(livroSelecionado);
        retiradaRN.salvar(retiradaSelecionada);
        return (this.novaRetirada());
    }

    public String editarRetirada(Retiradas u) {
        retiradaSelecionada = u;
        return ("/admin/edicaoUsuarios?faces-redirect=true"); //fazer pagina
    }

    public String atualizarRetirada() {
        retiradaRN.salvar(retiradaSelecionada);
        return ("/admin/index?faces-redirect=true"); //fazer pagina
    }

    public void removerRetirada(Retiradas retirada) {
        retiradaRN.remover(retirada);
    }
/*
    public String verificaCliente() {
        //Obtém o usuarioMB criado pelo servidor (nível de aplicação)
        //UsuarioMB usuarioMB = (UsuarioMB) contexto.getExternalContext().getApplicationMap().get("usuarioMB");
        //A partir do usuarioMB do servidor, pegamos a lista de usuários cadastrados no sistema
        List<Cliente> listaClientes = clienteMB.getListaClientes();

        for (Livro cliente : listaClientes) {
            if (cliente.verificaCliente(matricula)) {
                clienteSelecionado = cliente;
            }
        }

        return ("admin/retirada?faces-redirect=true");
    }*/

}
