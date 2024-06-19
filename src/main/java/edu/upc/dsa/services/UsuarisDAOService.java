package edu.upc.dsa.services;

import edu.upc.dsa.db.orm.dao.UsuariDAO;
import edu.upc.dsa.db.orm.dao.UsuariDAOImpl;
import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.MissingDataException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/usuarisDAO", description = "Endpoint to Usuari DAO Service")
@Path("/usuarisDAO")
public class UsuarisDAOService {

    private UsuariDAO ud;
    public UsuarisDAOService() {
        this.ud = UsuariDAOImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "Registrar un nou usuari a la BBDD", notes = "Afegirem un usuari nou a la BBDD")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós", response= Usuari.class),
            @ApiResponse(code = 409, message = "El nom d'usuari ja existeix"),
            @ApiResponse(code = 401, message = "La contrasenya no coincideix"),
            @ApiResponse(code = 404, message = "Falta completar algun camp"),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/registreUsuarisDAO")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registreUsuarisDAO(Usuari usuari){
        try {
            UsuariDAO manager = UsuariDAOImpl.getInstance();
            manager.addUsuari(usuari.getNom(), usuari.getCognom(), usuari.getNomusuari(), usuari.getPassword(), usuari.getPassword2(), usuari.getCoins());
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
    @ApiOperation(value = "Iniciar sessió", notes = "Iniciar sessió amb usuari de la BBDD")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitós"),
            @ApiResponse(code = 401, message = "Contrasenya incorrecte"),
            @ApiResponse(code = 404, message = "Falta completar algun camp"),
            @ApiResponse(code = 409, message = "Usuari no registrat"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/loginDAO")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginDAO(UsuariLogin usuari) {
        UsuariDAO manager = UsuariDAOImpl.getInstance();
        try {
            manager.loginUsuari(usuari.getNomusuari(), usuari.getPassword());
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
    @ApiOperation(value = "Obtenir perfil a partir del nomUsuari", notes = "Perfil del jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuari.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/getPerfilDAO/{nomusuari}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerfil(@PathParam("nomusuari") String nomusuari) {
        try{
            Usuari u = this.ud.getUsuari(nomusuari);
            return Response.status(201).entity(u).build();
        } catch (UserNotFoundException e){
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @GET
    @ApiOperation(value = "Obtenir una llista de tots els usuaris", notes = "Usuaris de la presó")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuari.class, responseContainer="List"),
    })
    @Path("/llistaUsuarisDAO")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuaris() {

        List<Usuari> usuaris = this.ud.llistaUsuarisDAO();
        GenericEntity<List<Usuari>> entity = new GenericEntity<List<Usuari>>(usuaris) {};
        return Response.status(201).entity(entity).build()  ;
    }
}
