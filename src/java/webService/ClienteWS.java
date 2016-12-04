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
import model.Cliente;
import rn.ClienteRN;

/**
 * REST Web Service
 *
 * @author Francke
 */

@Path("clientes")
public class ClienteWS {

    @EJB
    private ClienteRN clienteRN;

    @Context
    private UriInfo context;

    public ClienteWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Cliente> getClientes() {
        return clienteRN.listar();
    }

    @GET
    @Path("/{matricula}")
    @Produces(MediaType.APPLICATION_XML)
    public Cliente getCliente(@PathParam("matricula") long matricula) {
        return clienteRN.buscar(matricula);
    }
   
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void adicionarCliente(Cliente u, @Context final HttpServletResponse response) {
        clienteRN.salvar(u);
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
    @Path("/{matricula}")
    @Consumes(MediaType.APPLICATION_XML)
    public void alterarCliente(@PathParam("matricula") long matricula, Cliente cliente) {
        Cliente c =clienteRN.buscar(matricula);
        c.setNome(cliente.getNome());
        c.setTelefone(cliente.getTelefone());
        clienteRN.atualizar(c);
    }

    @DELETE
    @Path("/{matricula}")
    @Produces(MediaType.APPLICATION_XML)
    public Cliente removerCliente(@PathParam("matricula") long matricula) {
        Cliente u =clienteRN.buscar(matricula);
        clienteRN.remover(u);
        return u;
    }
    /*
    @GET
    @Path("/{matricula}")
    @Produces(MediaType.APPLICATION_XML)
    public List<Cliente> getClienteNome(@PathParam("matricula") long matricula, String nome) {
        return clienteRN.buscarPorNome(nome);
    }*/
}