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
    private Date dataAtual = new Date(System.currentTimeMillis());

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
        adicionarDevolucao(retirada);
        retiradaRN.remover(retirada);        
    }

    public String novaDevolucao() {
        devolucaoSelecionada = new Devolucao();
        return ("/admin/devolucao?faces-redirect=true");
    }

    public String novaDevolucaoUsuario() {
        devolucaoSelecionada = new Devolucao();
        return ("/usuario/devolucao?faces-redirect=true");
    }

    public void limparPesquisa(Devolucao r) {
        pesquisa.remove(r);
    }

    public String adicionarDevolucao(Retiradas retirada) {
            devolucaoSelecionada.setCliente(retirada.getCliente());
            devolucaoSelecionada.setLivro(retirada.getLivro());
            devolucaoSelecionada.setDataRetirada(retirada.getDataRetirada());
            devolucaoSelecionada.setDataDevolucao(retirada.getDataDevolucao());
            devolucaoSelecionada.setDataDevolvido(dataAtual);
            devolucaoRN.salvar(devolucaoSelecionada);            
            return (this.novaDevolucao());
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
    
    public String mostrarDevolucao(){        
        return("/admin/listaDevolucao?faces-redirect=true");
    }
    
    public String mostrarDevolucaoUsuario(){        
        return("/usuario/listaDevolucao?faces-redirect=true");
    }

    public void removerDevolucao(Devolucao devolucao) {
        devolucaoRN.remover(devolucao);
    }
    
    public String getAtrasadoString(Devolucao d){
        if(d.getDataDevolucao().before(d.getDataDevolvido())) return "Sim";
        else return "Não";
    }
    
    public String getLabel(Devolucao d){
        if(d.getDataDevolucao().before(d.getDataDevolvido())) return "label-danger";
        else return "label-success";
    }

    public Livro buscarLivroPorNome(String nome) {
        for (Livro l : getLivrosDisponiveis()) {
            if (l.getNome().equals(nome)) {
                return l;
            }
        }
        return null;
    }
}