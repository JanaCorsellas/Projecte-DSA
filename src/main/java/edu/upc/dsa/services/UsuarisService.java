package edu.upc.dsa.services;


import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.db.DBUtils;
import edu.upc.dsa.db.orm.Sessio;
import edu.upc.dsa.db.orm.SessioImpl;
import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.MissingDataException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
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
import java.sql.Connection;
import java.sql.SQLException;
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
            @ApiResponse(code = 401, message = "La contrasenya no coincideix"),
            @ApiResponse(code = 404, message = "Falta completar algun camp"),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/registreUsuari")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registreUsuari(Usuari usuari){
        try {
            GameManager manager = GameManagerImpl.getInstance();
            manager.registreUsuari(usuari.getNom(), usuari.getCognom(), usuari.getNomusuari(), usuari.getPassword(), usuari.getPassword2());
            return Response.status(201).entity(usuari).build();
        } catch (UserAlreadyExistsException e) {
            return Response.status(409).entity(usuari).build();
        } catch (IncorrectPasswordException e) {
            return Response.status(401).entity(usuari).build();
        } catch (MissingDataException e) {
            return Response.status(404).entity(usuari).build();
        } catch (Exception e) {
            return Response.status(500).entity(usuari).build();
        }
    }

    @POST
    @ApiOperation(value = "Iniciar sessió", notes = "Iniciar sessió amb usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitós"),
            @ApiResponse(code = 401, message = "Contrasenya incorrecte"),
            @ApiResponse(code = 404, message = "Falta completar algun camp"),
            @ApiResponse(code = 409, message = "Usuari no registrat"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UsuariLogin usuari) {
        GameManager manager = GameManagerImpl.getInstance();
        try {
            manager.login(usuari.getNomusuari(), usuari.getPassword());
            return Response.status(200).entity(usuari).build();
        } catch (UserNotFoundException e) {
            return Response.status(409).entity(usuari).build();
        } catch (MissingDataException e) {
            return Response.status(404).entity(usuari).build();
        } catch (IncorrectPasswordException e) {
            return Response.status(401).entity(usuari).build();
        } catch (Exception e) {
            return Response.status(500).entity(usuari).build();
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
    @GET
    @ApiOperation(value = "Obtenir un ranking dels usuaris", notes = "usuaris ordenats")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuari.class, responseContainer="List"),
    })
    @Path("/stats/ranking")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRanking() {

        List<Usuari> usuaris = this.um.ranking();
        GenericEntity<List<Usuari>> entity = new GenericEntity<List<Usuari>>(usuaris) {};
        return Response.status(201).entity(entity).build()  ;
    }
}