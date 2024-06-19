package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.MissingDataException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Issue;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.models.UsuariLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//MÍNIM 2 - CARLA OTERO
@Api(value = "/issues", description = "Les denuncies")
@Path("/issues")
public class IssueService {
    private GameManager um;

    public IssueService() {
        this.um = GameManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "Enviar Issue", notes = "Envia una denuncia")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós"),
            @ApiResponse(code = 500, message = "Error de validació")
    })
    @Path("/addIssue")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addIssue(Issue issue) throws MissingDataException {
        GameManager manager = GameManagerImpl.getInstance();
        try {
            manager.addIssue(issue.getDate(), issue.getInformer(), issue.getMessage());
            return Response.status(201).entity(issue).build();
        } catch (Exception e) {
            return Response.status(500).entity(issue).build();
        }
    }

    @GET
    @ApiOperation(value = "Obtenir una llista de tots els issues", notes = "issues dels usuaris")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Issue.class, responseContainer="List"),
    })
    @Path("/llistaIssues")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssues() {

        List<Issue> issues = this.um.llistaIssues();
        GenericEntity<List<Issue>> entity = new GenericEntity<List<Issue>>(issues) {};
        return Response.status(201).entity(entity).build()  ;
    }

}
