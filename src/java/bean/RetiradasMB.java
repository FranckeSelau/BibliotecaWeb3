package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Cliente;
import model.Livro;
import model.Retiradas;
import rn.LivroRN;
import rn.RetiradasRN;
import static util.DateUtil.dateToString;

@Named
@ApplicationScoped
public class RetiradasMB implements Serializable {

    @Inject
    RetiradasRN retiradaRN;
    @Inject
    LivroRN livroRN;
    @Inject
    ClienteMB clienteMB;
    @Inject
    LivroMB livroMB;
    private long id;
    private long matriculaCliente;
    private long idLivro;
    private String dtFormatada;

    //CRUD
    private List<Retiradas> listaRetiradas;
    private Retiradas retiradaSelecionada;

    public RetiradasMB() {
        retiradaSelecionada = new Retiradas();
          
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatriculaCliente() {
        return matriculaCliente;
    }

    public void setMatriculaCliente(long matriculaCliente) {
        this.matriculaCliente = matriculaCliente;
    }

    public long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(long idLivro) {
        this.idLivro = idLivro;
    }

    public String getDtFormatada() {
        return dtFormatada;
    }

    public void setDtFormatada(String dtFormatada) {
        this.dtFormatada = dtFormatada;
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
        Livro l = buscaLivroID(this.getIdLivro());
        Cliente c = buscaClienteMat(this.getMatriculaCliente());
        l.setDisponivel(false);
        l.setRetiradas(l.getRetiradas()+1);
        retiradaSelecionada.setCliente(c);
        retiradaSelecionada.setLivro(l);
        retiradaSelecionada.setDataRetirada(new Date(System.currentTimeMillis()));
        retiradaSelecionada.setDataDevolucao(new Date(System.currentTimeMillis() + (7 * (1000 * 60 * 60 * 24))));
        livroRN.salvar(l);
        retiradaRN.salvar(retiradaSelecionada);
                
        return (this.novaRetirada());
    }
    public Livro buscaLivroID(Long id){
        Livro auxLivro = new Livro();
        for(Livro l : this.livroMB.getListaLivros()){
            if(l.getId().equals(id)){
                auxLivro=l;
            }
        }
        return auxLivro;            
    }
    
    public Cliente buscaClienteMat(Long mat){
        Cliente auxCliente = new Cliente();
        for(Cliente c : this.clienteMB.getListaClientes()){
            if(c.getMatricula().equals(mat)){
                auxCliente = c;
            }
        }
        return auxCliente;
    }
    
      public List<Livro> LivrosDisponiveis(){
        List<Livro> disponiveis = new ArrayList<Livro>();
        for(Livro l : this.livroMB.getListaLivros()){
            if(l.isDisponivel()){
                disponiveis.add(l);
            }
        }
        return disponiveis;
    }
    
    public String formataData(Date data){
        this.dtFormatada = dateToString(data);
        return this.dtFormatada;
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
