package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    long DAY_IN_MS = 1000 * 60 * 60 * 24;
    Date periodoEmprestimo = new Date(System.currentTimeMillis() + 7 * DAY_IN_MS); // uma semana
    Date dataAtual = new Date(System.currentTimeMillis());

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
        pesquisaSelecionada.setDataRetirada(dataAtual);
        pesquisaSelecionada.setDataDevolucao(periodoEmprestimo);
        pesquisa.add(pesquisaSelecionada);
        return (this.novaRetirada());
    }

    public void limparPesquisa(Retiradas r) {
        pesquisa.remove(r);
    }

    public String adicionarRetirada() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        RetiradasMB retiradasMB = (RetiradasMB) contexto.getExternalContext().getApplicationMap().get("RetiradasMB");
        if (!pesquisa.isEmpty()) {
            Livro l = this.livroSelecionado;
            Cliente c = buscaClienteMat(this.getMatriculaCliente());
            l.setDisponivel(false);
            l.setRetiradas(l.getRetiradas() + 1);
            retiradaSelecionada.setCliente(c);
            retiradaSelecionada.setLivro(l);
            retiradaSelecionada.setDataRetirada(dataAtual);
            retiradaSelecionada.setDataDevolucao(periodoEmprestimo);
            livroRN.salvar(l);
            retiradaRN.salvar(retiradaSelecionada);
            limparPesquisa(pesquisaSelecionada);
            this.novaRetirada();
            return ("/admin/confirmaRetirada?faces-redirect=true");
        }
        FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Erro!", "É necessario pesquisar antes!");
        contexto.addMessage("idMensagem", mensagem);
        return ("/admin/retirada?faces-redirect=true");
    }

    public Cliente buscaClienteMat(Long mat) {
        return clienteRN.buscar(mat);
    }

    public Livro buscaLivroID(Long id) {
        return livroRN.buscar(id);
    }

    public List<Livro> getLivrosDisponiveis() {
        List<Livro> disponiveis = new ArrayList<>();
        for (Livro l : this.livroMB.getListaLivros()) {
            if (l.isDisponivel()) {
                disponiveis.add(l);
            }
        }
        return disponiveis;
    }

    public String formataData(Date data) {
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
    
    public String mostrarRetiradas(){        
        return("/admin/listaRetiradas?faces-redirect=true");
    }

    public Livro buscarLivroPorNome(String nome) {
        for (Livro l : getLivrosDisponiveis()) {
            if (l.getNome().equals(nome)) {
                return l;
            }
        }
        return null;
    }
    
    public String getAtrasadoString(Retiradas r){
        if(r.getDataDevolucao().before(dataAtual)) return "Sim";
        else return "Não";
    }
    
    public String getLabel(Retiradas r){
        if(r.getDataDevolucao().before(dataAtual)) return "label-danger";
        else return "label-success";
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
