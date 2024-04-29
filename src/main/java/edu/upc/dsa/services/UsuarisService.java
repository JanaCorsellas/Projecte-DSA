package edu.upc.dsa.services;


import edu.upc.dsa.UsersManager;
import edu.upc.dsa.UsersManagerImpl;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;
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

    private UsersManager um;

    public UsuarisService() {
        this.um = UsersManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "Registrar un nou usuari", notes = "Afegirem un usuari nou")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós", response= Usuari.class),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/registreUsuari")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registreUsuari(Usuari usuari){
        //verifiquem si es proporciona un usuari vàlid
        if (usuari == null || usuari.getId() == null || usuari.getNom() == null || usuari.getCognom() == null || usuari.getNomusuari() == null) {
            return Response.status(500).entity("Error de validació: Informació d'usuari incompleta").build();
        }

        //agreguem l'usuari
        UsersManager manager = UsersManagerImpl.getInstance();
        manager.registreUsuari(usuari.getId(), usuari.getNom(), usuari.getCognom(), usuari.getNomusuari());

        return Response.status(201).entity(usuari).build();
    }

    @POST
    @ApiOperation(value = "Iniciar sessió", notes = "Iniciar sessió amb usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Inicio de sesión exitoso"),
            @ApiResponse(code = 401, message = "Nom d'usuari incorrecte"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Usuari usuari) {
        // Verificar si se proporciona un nombre de usuario válido
        if (usuari == null || usuari.getNomusuari() == null) {
            return Response.status(401).entity("Nom d'usuari invàlid").build();
        }

        // Intentar iniciar sesión con el nombre de usuario proporcionado
        UsersManager manager = UsersManagerImpl.getInstance();
        try {
            manager.login(usuari.getNomusuari());
            // Si no se lanzó una excepción, entonces el inicio de sesión fue exitoso
            return Response.status(200).entity("Inici de sessió exitós").build();
        } catch (UserNotFoundException e) {
            // Si se lanzó una excepción, entonces el nombre de usuario no existe en el sistema
            return Response.status(401).entity("Nombre de usuario invàlid").build();
        } catch (Exception e) {
            // Capturar cualquier otro error interno del servidor
            return Response.status(500).entity("Error interno del servidor").build();
        }
    }

    @GET
    @ApiOperation(value = "Llistar items per preu ascendent", notes = "llista dels ítems")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitós", response = Item.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/llistarItems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response llistarItemsPerPreuAscendent() {
        UsersManager manager = UsersManagerImpl.getInstance();

        try {
            //obtenir la llista d'ítems odrenats per preu
            List<Item> itemsOrdenats = manager.llistarItemsPerPreuAscendent();
            return Response.status(200).entity(itemsOrdenats).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error intern del servidor").build();
        }
    }

}