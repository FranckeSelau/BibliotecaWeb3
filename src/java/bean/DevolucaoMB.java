package bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import model.Usuario;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import model.ClienteAntigo;
import model.LivroAntigo;
import model.Retiradas;

@Named
@ApplicationScoped
public class DevolucaoMB {

    //CRUD
    private List<Retiradas> listaRetiradas;
    private Retiradas retiradaSelecionada;

    public DevolucaoMB() {
        retiradaSelecionada = new Retiradas();
        listaRetiradas = new ArrayList<Retiradas>();
        listaRetiradas.add(new Retiradas("Beltrano", "Harry Potter I"));
        
    }
    
    
    public Retiradas getRetiradaSelecionada() {
        return retiradaSelecionada;
    }

    public void setRetiradaSelecionada(Retiradas retiradaSelecionada) {
        this.retiradaSelecionada = retiradaSelecionada;
    }

    public List<Retiradas> getListaRetiradas() {
        return listaRetiradas;
    }

    public void setListaRetiradas(List<Retiradas> listaRetiradas) {
        this.listaRetiradas = listaRetiradas;
    }
    

    public String novaDevolucao(){
        retiradaSelecionada=new Retiradas();
        return("/admin/devolucao?faces-redirect=true");
    }
    
    public String novaDevolucaoUsuario(){
        retiradaSelecionada=new Retiradas();
        return("/usuario/devolucao?faces-redirect=true");
    }
/*
    public String adicionarRetirada()
    {
        listaRetiradas.add(retiradaSelecionada);
        return(this.novaRetirada());
    }

    public String editarRetirada(Retiradas u){
        retiradaSelecionada = u;
        return("/admin/edicaoUsuarios?faces-redirect=true");
    }
    public String atualizarRetirada()
    {
        return("/admin/index?faces-redirect=true");
    }

    public void removerRetirada(Retiradas retirada){
        listaRetiradas.remove(retirada);
    }
*/
}

    
