package webService;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Livro;
import rn.LivroRN;

/**
 * REST Web Service
 *
 * @author Francke
 */

@Path("livros")
public class LivroWS {

    @EJB
    private LivroRN livroRN;

    @Context
    private UriInfo context;

    public LivroWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Livro> getLivros() {
        return livroRN.listar();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Livro getLivro(@PathParam("id") long id) {
        return livroRN.buscar(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void adicionarLivro(Livro l, @Context final HttpServletResponse response) {
        livroRN.salvar(l);
        //Alterar o codigo para 201 (Created)
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            //Erro 500
            throw new InternalServerErrorException();
        }

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void alterarLivro(@PathParam("id") long id, Livro livro) {
        Livro l =livroRN.buscar(id);
        l.setNome(livro.getNome());
        l.setAutor(livro.getAutor());
        l.setEditora(livro.getEditora());
        l.setAno(livro.getAno());
        livroRN.atualizar(l);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Livro removerLivro(@PathParam("id") long id) {
        Livro l =livroRN.buscar(id);
        livroRN.remover(l);
        return l;
    }
}