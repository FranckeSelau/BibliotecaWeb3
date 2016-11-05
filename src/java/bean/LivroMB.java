package bean;

import model.Livro;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import model.Devolucao;
import rn.LivroRN;
import rn.DevolucaoRN;

/**
 *
 * @author Francke
 */
@Named(value = "livroMB")
@SessionScoped
public class LivroMB implements Serializable {
    
    private Livro livroSelecionado;
    private String id;
    private List<Livro> pesquisaTitulo;
    private String tituloBusca;
    @Inject
    private LivroRN livroRN;
    @Inject
    private DevolucaoRN devolucaoRN;

    public LivroMB() {
        livroSelecionado = new Livro();
    }

    public Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(Livro usuarioSelecionado) {
        this.livroSelecionado = usuarioSelecionado;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public List<Livro> getListaLivros(){
        return livroRN.listar();
    }

    public List<Livro> getPesquisaTitulo() {
        return pesquisaTitulo;
    }

    public void setPesquisaTitulo(List<Livro> pesquisaTitulo) {
        this.pesquisaTitulo = pesquisaTitulo;
    }    

    public String getTituloBusca() {
        return tituloBusca;
    }

    public void setTituloBusca(String tituloBusca) {
        this.tituloBusca = tituloBusca;
    }
    
    public String novoLivro(){
        livroSelecionado = new Livro();
        return("/admin/livros/cadastroLivros?faces-redirect=true");
    }
    
    public String adicionarLivro(){
        livroRN.salvar(livroSelecionado);
        this.novoLivro();
        return("/admin/livros/confirmaCadastroLivro?faces-redirect=true");
    }
    
    public String mostrarLivros(){        
        return("/admin/livros/listaLivros?faces-redirect=true");
    }
    
    public String mostrarLivrosUsuario(){        
        return("/usuario/livros/listaLivros?faces-redirect=true");
    }
    
    public String editarLivro(Livro c){
        livroSelecionado = c;
        return("/admin/livros/edicaoLivros?faces-redirect=true");        
    }
    
    public String atualizarLivro(){
        livroRN.salvar(livroSelecionado);
        return("/admin/livros/listaLivros?faces-redirect=true");
    }
    
    public void removerLivro(Livro livro){// verifica se este livro est� relacionado a tabela devolu��o
        List<Devolucao> pesquisaCascade = devolucaoRN.buscarLivroExclusao(livro.getId());
        for (Devolucao devolucao : pesquisaCascade) {
            devolucaoRN.remover(devolucao);      
        }
        livroRN.remover(livro);       
    } 
    
    public String adicionarPesquisa() {
        pesquisaTitulo = buscarLivroTitulo(this.tituloBusca); 
        return ("/admin/livros/buscaLivros?faces-redirect=true");
    }
    
    public String adicionarPesquisaUsuario() {
        pesquisaTitulo = buscarLivroTitulo(this.tituloBusca); 
        return ("/usuario/livros/buscaLivros?faces-redirect=true");
    }
    
    public List<Livro> buscarLivroTitulo(String titulo){
        return livroRN.buscarPorTitulo(titulo);
    }
    
    //metodo para box option selec one
    public Livro buscarLivroPorNome(String nome){
        for(Livro l: getListaLivros())
            if(l.getNome().equals(nome))
                return l;
        return null;
    }
    
    
}
