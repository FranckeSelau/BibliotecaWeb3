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
import model.Cliente;
import rn.ClienteRN;

/**
 * REST Web Service
 *
 * @author Francke
 */

@Path("clientesNome")
public class ClienteNomeWS {

    @EJB
    private ClienteRN clienteRN;

    @Context
    private UriInfo context;

    public ClienteNomeWS() {
    }

    @GET
    @Path("/{nome}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Cliente> getClienteNome(@PathParam("nome") String nome) {
        return clienteRN.buscarPorNome(nome);
    }
}