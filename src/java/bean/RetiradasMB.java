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
import rn.ClienteRN;
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
    ClienteRN clienteRN;
    @Inject
    ClienteMB clienteMB;
    @Inject
    LivroMB livroMB;
    private long id;
    private long matriculaCliente;
    private long idLivro;
    private String dtFormatada;
    private Livro livroSelecionado;

    //CRUD
    private List<Retiradas> listaRetiradas;
    private List<Retiradas> pesquisa;
    private Retiradas retiradaSelecionada;
    private Retiradas pesquisaSelecionada;

    public RetiradasMB() {
        pesquisa = new ArrayList<>();
        pesquisaSelecionada = new Retiradas();
        retiradaSelecionada = new Retiradas();
        livroSelecionado = new Livro();

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

    public List<Retiradas> getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(List<Retiradas> pesquisa) {
        this.pesquisa = pesquisa;
    }    
    
    public Retiradas getPesquisaSelecionada() {
        return pesquisaSelecionada;
    }

    public void setPesquisaSelecionada(Retiradas pesquisaSelecionada) {
        this.pesquisaSelecionada = pesquisaSelecionada;
    }

    public String novaRetirada() {
        retiradaSelecionada = new Retiradas();
        return ("/admin/retirada?faces-redirect=true");
    }

    public String novaRetiradaUsuario() {
        pesquisaSelecionada = new Retiradas();
        retiradaSelecionada = new Retiradas();
        return ("/usuario/retirada?faces-redirect=true");
    }
    
    public String adicionarPesquisa() {
        Livro l = this.livroSelecionado;
        Cliente c = buscaClienteMat(this.getMatriculaCliente());
        pesquisaSelecionada.setCliente(c);
        pesquisaSelecionada.setLivro(l);
        pesquisaSelecionada.setDataRetirada(new Date(System.currentTimeMillis()));
        pesquisaSelecionada.setDataDevolucao(new Date(System.currentTimeMillis() + (7 * (1000 * 60 * 60 * 24))));
        pesquisa.add(pesquisaSelecionada);
        return (this.novaRetirada());
    } 
    
    public void limparPesquisa(Retiradas r) {
        pesquisa.remove(r);      
    }
    
    public String adicionarRetirada() {
        Livro l = this.livroSelecionado;
        Cliente c = buscaClienteMat(this.getMatriculaCliente());
        l.setDisponivel(false);
        l.setRetiradas(l.getRetiradas()+1);
        retiradaSelecionada.setCliente(c);
        retiradaSelecionada.setLivro(l);
        retiradaSelecionada.setDataRetirada(new Date(System.currentTimeMillis()));
        retiradaSelecionada.setDataDevolucao(new Date(System.currentTimeMillis() + (7 * (1000 * 60 * 60 * 24))));
        livroRN.salvar(l);
        retiradaRN.salvar(retiradaSelecionada);
        limparPesquisa(pesquisaSelecionada);
        return (this.novaRetirada());
    }    
    
    public Cliente buscaClienteMat(Long mat){
        return clienteRN.buscar(mat);
    }
    
    public Livro buscaLivroID(Long id){
        return livroRN.buscar(id);
    }
    
    public List<Livro> getLivrosDisponiveis(){
        List<Livro> disponiveis = new ArrayList<>();
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
    
    public Livro buscarLivroPorNome(String nome){
        for(Livro l: getLivrosDisponiveis())
            if(l.getNome().equals(nome))
                return l;
        return null;
    }
/*
    public String verificaCliente() {
        //Obt�m o usuarioMB criado pelo servidor (n�vel de aplica��o)
        //UsuarioMB usuarioMB = (UsuarioMB) contexto.getExternalContext().getApplicationMap().get("usuarioMB");
        //A partir do usuarioMB do servidor, pegamos a lista de usu�rios cadastrados no sistema
        List<Cliente> listaClientes = clienteMB.getListaClientes();

        for (Livro cliente : listaClientes) {
            if (cliente.verificaCliente(matricula)) {
                clienteSelecionado = cliente;
            }
        }

        return ("admin/retirada?faces-redirect=true");
    }*/

}
