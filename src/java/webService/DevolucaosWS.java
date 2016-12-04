
package webService;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.Devolucao;
import rn.DevolucaoRN;

/**
 * REST Web Service
 *
 * @author Francke
 */
@Path("devolucao")
public class DevolucaosWS {
    
    @EJB
    private DevolucaoRN devolucaoRN;

    @Context
    private UriInfo context;

    public DevolucaosWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Devolucao> getDevolucao() {
        return devolucaoRN.listar();
    }
}
