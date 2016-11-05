package bean;

import model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped //Application, pois os usuários cadastrados deverão permanecer mesmo se fizer logout.
public class RelatorioMB {

    //CRUD
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSelecionado;

    public RelatorioMB() {
        
    }
    
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    

    public String novoUsuario(){
        usuarioSelecionado=new Usuario();        
        return("/admin/cadastroUsuarios?faces-redirect=true");
    }

    public String adicionarUsuario(){        
        listaUsuarios.add(usuarioSelecionado);
        this.novoUsuario();
        return("/admin/confirmaCadastroUsuario?faces-redirect=true");
    }
    
    public String mostrarRelatorios(){        
        return("/admin/relatorios/relatorios?faces-redirect=true");
    }
    
    public String mostrarRelatoriosUsuario(){        
        return("/usuario/relatorios/relatorios?faces-redirect=true");
    }

    public String editarUsuario(Usuario u){
        usuarioSelecionado = u;
        return("/admin/edicaoUsuarios?faces-redirect=true");
    }
    public String atualizarUsuario(){
        return("/admin/listaUsuarios?faces-redirect=true");
    }

    public void removerUsuario(Usuario usuario){
        listaUsuarios.remove(usuario);
    }

}
