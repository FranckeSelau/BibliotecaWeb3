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
import model.Devolucao;
import model.Retiradas;
import rn.ClienteRN;
import rn.LivroRN;
import rn.DevolucaoRN;
import rn.RetiradasRN;
import static util.DateUtil.dateToString;

@Named
@ApplicationScoped
public class DevolucaoMB implements Serializable {

    @Inject
    DevolucaoRN devolucaoRN;
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
    @Inject
    RetiradasMB RetiradasMB;
    private long id;
    private long matriculaCliente;
    private long idLivro;
    private String dtFormatada;
    private Livro livroSelecionado;
    private Retiradas retiradaSelecionada;
    long DAY_IN_MS = 1000 * 60 * 60 * 24; // formatar data entrega
    private List<Devolucao> listaDevolucao;
    private List<Devolucao> pesquisa;
    private Devolucao devolucaoSelecionada;
    private Devolucao pesquisaSelecionada;

    public DevolucaoMB() {
        pesquisa = new ArrayList<>();
        pesquisaSelecionada = new Devolucao();
        devolucaoSelecionada = new Devolucao();
        livroSelecionado = new Livro();
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

    public Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public void setLivroSelecionado(Livro livroSelecionado) {
        this.livroSelecionado = livroSelecionado;
    }

    public Devolucao getDevolucaoSelecionada() {
        return devolucaoSelecionada;
    }

    public void setDevolucaoSelecionada(Devolucao devolucaoSelecionada) {
        this.devolucaoSelecionada = devolucaoSelecionada;
    }

    public List<Devolucao> getListaDevolucao() {
        return devolucaoRN.listar();
    }

    public List<Devolucao> getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(List<Devolucao> pesquisa) {
        this.pesquisa = pesquisa;
    }

    public Devolucao getPesquisaSelecionada() {
        return pesquisaSelecionada;
    }

    public void setPesquisaSelecionada(Devolucao pesquisaSelecionada) {
        this.pesquisaSelecionada = pesquisaSelecionada;
    }

    public Retiradas getRetiradaSelecionada() {
        return retiradaSelecionada;
    }

    public void setRetiradaSelecionada(Retiradas retiradaSelecionada) {
        this.retiradaSelecionada = retiradaSelecionada;
    }
    
    public void devolverLivro(Retiradas retirada){
        Livro l = retirada.getLivro();
        l.setDisponivel(true);
        livroRN.salvar(l);
        //retiradaRN.remover(retirada);
    }

    public String novaRetirada() {
        devolucaoSelecionada = new Devolucao();
        return ("/admin/retirada?faces-redirect=true");
    }

    public String novaRetiradaUsuario() {
        pesquisaSelecionada = new Devolucao();
        devolucaoSelecionada = new Devolucao();
        return ("/usuario/retirada?faces-redirect=true");
    }

    public String adicionarPesquisa() {        
        Livro l = this.livroSelecionado;
        Cliente c = buscaClienteMat(this.getMatriculaCliente());
        pesquisaSelecionada.setCliente(c);
        pesquisaSelecionada.setLivro(l);
        pesquisaSelecionada.setDataRetirada(new Date(System.currentTimeMillis()));
        pesquisaSelecionada.setDataDevolucao(new Date(System.currentTimeMillis() + (7 * DAY_IN_MS)));
        pesquisa.add(pesquisaSelecionada);
        return (this.novaRetirada());
    }

    public void limparPesquisa(Devolucao r) {
        pesquisa.remove(r);
    }

    public String adicionarRetirada() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        DevolucaoMB retiradasMB = (DevolucaoMB) contexto.getExternalContext().getApplicationMap().get("DevolucaoMB");
        if (!pesquisa.isEmpty()) {
            Livro l = this.livroSelecionado;
            Cliente c = buscaClienteMat(this.getMatriculaCliente());
            l.setDisponivel(false);
            //l.setDevolucao(l.getDevolucao() + 1);
            devolucaoSelecionada.setCliente(c);
            devolucaoSelecionada.setLivro(l);
            devolucaoSelecionada.setDataRetirada(new Date(System.currentTimeMillis()));
            devolucaoSelecionada.setDataDevolucao(new Date(System.currentTimeMillis() + (7 * (1000 * 60 * 60 * 24))));
            livroRN.salvar(l);
            devolucaoRN.salvar(devolucaoSelecionada);
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
    
    public Retiradas buscarRetiradasID(Long id){         
        return retiradaRN.buscar(id);
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

    public String editarRetirada(Devolucao u) {
        devolucaoSelecionada = u;
        return ("/admin/edicaoUsuarios?faces-redirect=true"); 
    }

    public String atualizarDevolucao() {
        devolucaoRN.salvar(devolucaoSelecionada);
        return ("/admin/index?faces-redirect=true"); 
    }

    public void removerDevolucao(Devolucao devolucao) {
        devolucaoRN.remover(devolucao);
    }

    public Livro buscarLivroPorNome(String nome) {
        for (Livro l : getLivrosDisponiveis()) {
            if (l.getNome().equals(nome)) {
                return l;
            }
        }
        return null;
    }
    
    /*
    public String verificaRetirada() {
        List<Retiradas> listaRetiradas = RetiradasMB.getListaRetiradas();

        for (Retiradas retirada : listaRetiradas) {
            if (retirada.) {
                clienteSelecionado = cliente;
            }
        }

        return ("admin/retirada?faces-redirect=true");
    }*/

}
