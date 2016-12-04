
package webService;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.Livro;
import rn.LivroRN;

/**
 * REST Web Service
 *
 * @author Francke
 */
@Path("relatoriosLivrosDisp")
public class RelatorioWSLivrosDisp {
    
    @EJB
    private LivroRN livroRN;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public RelatorioWSLivrosDisp() {
    }

    /**
     * Retrieves representation of an instance of webService.RelatorioWSClientesRetiram
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Livro> getLivrosDisp() {
        return livroRN.disponiveis();
    }
}