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
import edu.upc.dsa.models.*;
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
import java.util.ArrayList;
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
            manager.registreUsuari(usuari.getNom(), usuari.getCognom(), usuari.getNomusuari(), usuari.getPassword(), usuari.getPassword2(), usuari.getCoins());
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
    @ApiOperation(value = "Obtenir una llista de tots els usuaris", notes = "Usuaris de la presó")
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

    @DELETE
    @ApiOperation(value = "Donar de baixa un usuari", notes = "Eliminar el registre d'un usuari")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuari donat de baixa correctament"),
            @ApiResponse(code = 404, message = "Usuari no trobat"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/baixaUsuari/{nomusuari}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response baixaUsuari(@PathParam("nomusuari") String nomusuari) {
        try {
            GameManager manager = GameManagerImpl.getInstance();
            manager.baixaUsuari(nomusuari);
            return Response.status(200).build();
        } catch (UserNotFoundException e) {
            return Response.status(404).entity(nomusuari).build();
        } catch (SQLException e) {
            return Response.status(500).entity(nomusuari).build();
        } catch (Exception e) {
            return Response.status(500).entity(nomusuari).build();
        }
    }

    @GET
    @ApiOperation(value = "Obtenir perfil a partir del nomUsuari", notes = "Perfil del jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuari.class),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/getPerfil/{nomusuari}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerfil(@PathParam("nomusuari") String nomusuari) {
        Usuari usuari = this.um.obtenirUsuariPerNomusuari(nomusuari);
        if (usuari==null) return Response.status(404).build();
        else return Response.status(201).entity(usuari).build();
    }

    //MÍNIM 2 - JANA CORSELLAS
    @POST
    @ApiOperation(value = "Enviar un nou formulari", notes = "Formulari per solicitar i enviar informació sobre un tema")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Formulari enviat correctament", response= Formulari.class),
            @ApiResponse(code = 404, message = "Falta completar algun camp"),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/formulariSolicitud")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response formulari(Formulari formulari){
        try {
            GameManager manager = GameManagerImpl.getInstance();
            manager.formulari(formulari.getData(), formulari.getTitle(), formulari.getMessage(), formulari.getSender());
            return Response.status(201).entity(formulari).build();
        } catch (MissingDataException e) {
            return Response.status(404).entity(formulari).build();
        } catch (Exception e) {
            return Response.status(500).entity(formulari).build();
        }
    }

    @GET
    @ApiOperation(value = "Obtenir una llista de tots els formularis", notes = "Formularis dels usuaris")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Issue.class, responseContainer="List"),
    })
    @Path("/llistaFormularis")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFormularis() {

        List<Formulari> formularis = this.um.llistaFormularis();
        GenericEntity<List<Formulari>> entity = new GenericEntity<List<Formulari>>(formularis) {};
        return Response.status(201).entity(entity).build();
    }


    //MÍNIM 2 - ANDREA ZAPATA
    @GET
    @ApiOperation(value = "Obtenir una llista de tots els FAQs", notes = "FAQs dels usuaris")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Faq.class, responseContainer="List"),
    })
    @Path("/llistaFaqs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Faq> getFaqs() {
        // Aquí simulamos la obtención de las FAQs desde algún origen de datos
        List<Faq> faqs = this.um.llistaFaqs();
        //faqs.add(new Faq("f1", "q1"));
        //faqs.add(new Faq("f2", "q2"));
        // Agrega más FAQs según sea necesario
        GenericEntity<List<Faq>> entity = new GenericEntity<List<Faq>>(faqs) {};
        return (List<Faq>) Response.status(201).entity(entity).build();
    }

    @POST
    @ApiOperation(value = "Afegir FAQ", notes = "Faq")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós"),
            @ApiResponse(code = 500, message = "Error de validació")

    })
    @Path("/addFAQs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFAQ(Faq faq) throws MissingDataException{
        // Aquí simulamos la obtención de las FAQs desde algún origen de datos
        GameManager manager = GameManagerImpl.getInstance();
        try{
            manager.addFaq(faq);
            return Response.status(201).entity(faq).build();
        } catch (Exception e){
            return Response.status(500).entity(faq).build();
        }
    }






    @POST
    @ApiOperation(value = "Comprar Item", notes = "comprar")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós", response = Usuari.class),
            @ApiResponse(code = 500, message = "Error de validació"),
            @ApiResponse(code = 404, message = "Diners Insuficients")

    })
    @Path("/ComprarItem/{nomusuari} {item}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response comprarItem(@PathParam("nomusuari") String nomusuari, @PathParam("item") String item) throws MissingDataException{

        //GameManager manager = GameManagerImpl.getInstance();
        try{
            Usuari usuari = this.um.comprarItem(nomusuari, item);

            if (usuari.getSkin() == item)
                return Response.status(201).entity(usuari).build();
            else
                return Response.status(404).build();

        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

}