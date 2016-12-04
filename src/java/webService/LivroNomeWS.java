package webService;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Livro;
import rn.LivroRN;

/**
 * REST Web Service
 *
 * @author Francke
 */

@Path("livrosNome")
public class LivroNomeWS {

    @EJB
    private LivroRN livroRN;

    @Context
    private UriInfo context;

    public LivroNomeWS() {
    }

    @GET
    @Path("/{nome}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Livro> getLivroNome(@PathParam("nome") String nome) {
        return livroRN.buscarPorTitulo(nome);
    }
}