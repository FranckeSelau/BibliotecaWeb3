package bean;

import model.Livro;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import rn.LivroRN;

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
        return("/admin/cadastroLivros?faces-redirect=true");
    }
    
    public String adicionarLivro(){
        livroRN.salvar(livroSelecionado);
        this.novoLivro();
        return("/admin/confirmaCadastroLivro?faces-redirect=true");
    }
    
    public String mostrarLivros(){        
        return("/admin/listaLivros?faces-redirect=true");
    }
    
    public String mostrarLivrosUsuario(){        
        return("/usuario/listaLivros?faces-redirect=true");
    }
    
    public String editarLivro(Livro c){
        livroSelecionado = c;
        return("/admin/edicaoLivros?faces-redirect=true");        
    }
    
    public String atualizarLivro(){
        livroRN.salvar(livroSelecionado);
        return("/admin/listaLivros?faces-redirect=true");
    }
    
    public void removerLivro(Livro livro){
        livroRN.remover(livro);
    } 
    
    public String adicionarPesquisa() {
        pesquisaTitulo = buscarLivroTitulo(this.tituloBusca); 
        return ("/admin/buscaLivros?faces-redirect=true");
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
