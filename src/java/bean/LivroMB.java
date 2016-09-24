package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import model.Livro;

@Named
@ApplicationScoped //Application, pois os usuários cadastrados deverão permanecer mesmo se fizer logout.
public class LivroMB {

    //CRUD
    private List<Livro> listaLivros;
    private Livro livroSelecionado;

    public LivroMB() {
        livroSelecionado = new Livro();
        listaLivros = new ArrayList<Livro>();
        listaLivros.add(new Livro("1", "Harry Potter I", "JK", "asd", "1995"));
        listaLivros.add(new Livro("2", "Harry Potter II", "JK", "asd", "1999"));
    }
    
    public Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(Livro livroSelecionado) {
        this.livroSelecionado = livroSelecionado;
    }

    public List<Livro> getListaLivros() {
        return listaLivros;
    }

    public void setListaLivros(List<Livro> listaLivros) {
        this.listaLivros = listaLivros;
    }
    

    public String novoLivro(){
        livroSelecionado=new Livro();
        return("/admin/cadastroLivros?faces-redirect=true");
    }

    public String adicionarLivro(){
        listaLivros.add(livroSelecionado);
        this.novoLivro();
        return("/admin/confirmaCadastroLivro?faces-redirect=true");
    }

    public String editarLivro(Livro l){
        livroSelecionado = l;
        return("/admin/edicaoLivros?faces-redirect=true");
    }
    public String atualizarLivro()
    {
        return("/admin/index?faces-redirect=true");
    }

    public void removerLivro(Livro livro){
        listaLivros.remove(livro);
    }

}
