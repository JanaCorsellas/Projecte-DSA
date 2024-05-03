package edu.upc.dsa.services;


import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Item;
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

@Api(value = "/usuaris", description = "Endpoint to Usuari Service")
@Path("/usuaris")
public class UsuarisService {

    private GameManager um;

    public UsuarisService() {
        this.um = GameManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "Registrar un nou usuari", notes = "Afegirem un usuari nou")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós", response= Usuari.class),
            @ApiResponse(code = 409, message = "El nom d'usuari ja existeix"),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/registreUsuari")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registreUsuari(Usuari usuari){
        try {
            GameManager manager = GameManagerImpl.getInstance();
            manager.registreUsuari(usuari.getNom(), usuari.getCognom(), usuari.getNomusuari(), usuari.getContrasenya());

            return Response.status(201).entity(usuari).build();
        } catch (UserAlreadyExistsException e) {
            return Response.status(409).entity("El nom d'usuari ja existeix").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error intern del servidor").build();
        }
    }

    @POST
    @ApiOperation(value = "Iniciar sessió", notes = "Iniciar sessió amb usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitós"),
            @ApiResponse(code = 401, message = "Nom d'usuari incorrecte"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UsuariLogin usuari) {
        GameManager manager = GameManagerImpl.getInstance();
        try {
            manager.login(usuari.getNomusuari(), usuari.getContrasenya());
            return Response.status(200).entity("Inici de sessió exitós").build();
        } catch (UserNotFoundException e) {
            return Response.status(401).entity("Nom d'usuari invàlid").build();
        } catch (IncorrectPasswordException e) {
            return Response.status(401).entity("Contrasenya incorrecte").build();
        } catch (Exception e) {
            return Response.status(500).entity("Error intern del servidor").build();
        }
    }

    @GET
    @ApiOperation(value = "Obtenir una llista de tots els usuaris", notes = "usuaris de la presó")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuari.class, responseContainer="List"),
    })
    @Path("/llistaUsuaris")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuaris() {

        List<Usuari> usuaris = this.um.llistaUsuaris();
        GenericEntity<List<Usuari>> entity = new GenericEntity<List<Usuari>>(usuaris) {};
        return Response.status(201).entity(entity).build()  ;
    }

}