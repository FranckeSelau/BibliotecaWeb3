
package webService;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.Retiradas;
import rn.RetiradasRN;

/**
 * REST Web Service
 *
 * @author Francke
 */
@Path("retiradas")
public class RetiradasWS {
    
    @EJB
    private RetiradasRN retiradasRN;

    @Context
    private UriInfo context;

    public RetiradasWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Retiradas> getRetiradas() {
        return retiradasRN.listar();
    }
}
