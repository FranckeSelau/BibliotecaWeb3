package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import model.LivroAntigo;

@Named
@ApplicationScoped //Application, pois os usuários cadastrados deverão permanecer mesmo se fizer logout.
public class LivroMB {

    //CRUD
    private List<LivroAntigo> listaLivros;
    private LivroAntigo livroSelecionado;

    public LivroMB() {
        livroSelecionado = new LivroAntigo();
        listaLivros = new ArrayList<LivroAntigo>();
        listaLivros.add(new LivroAntigo("1", "Harry Potter I", "JK", "asd", "1995"));
        listaLivros.add(new LivroAntigo("2", "Harry Potter II", "JK", "asd", "1999"));
    }
    
    public LivroAntigo getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(LivroAntigo livroSelecionado) {
        this.livroSelecionado = livroSelecionado;
    }

    public List<LivroAntigo> getListaLivros() {
        return listaLivros;
    }

    public void setListaLivros(List<LivroAntigo> listaLivros) {
        this.listaLivros = listaLivros;
    }
    

    public String novoLivro(){
        livroSelecionado=new LivroAntigo();
        return("/admin/cadastroLivros?faces-redirect=true");
    }

    public String adicionarLivro(){
        listaLivros.add(livroSelecionado);
        this.novoLivro();
        return("/admin/confirmaCadastroLivro?faces-redirect=true");
    }
    
    public String mostrarLivros(){        
        return("/admin/listaLivros?faces-redirect=true");
    }
    
    public String mostrarLivrosUsuario(){        
        return("/usuario/listaLivros?faces-redirect=true");
    }

    public String editarLivro(LivroAntigo l){
        livroSelecionado = l;
        return("/admin/edicaoLivros?faces-redirect=true");
    }
    public String atualizarLivro()
    {
        return("/admin/listaLivros?faces-redirect=true");
    }

    public void removerLivro(LivroAntigo livro){
        listaLivros.remove(livro);
    }

}
