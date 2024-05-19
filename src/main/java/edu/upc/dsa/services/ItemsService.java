package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.exception.*;
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

@Api(value = "/items", description = "Endpoint to Item Service")
@Path("/items")
public class ItemsService {
    private GameManager um;

    public ItemsService() throws ItemAlreadyExistsException, MissingDataException {
        this.um = GameManagerImpl.getInstance();
        if (um.size()==0) {
            this.um.addItem("Vermell", 1, "Guanyes una vida", "https://i.pinimg.com/originals/2e/52/ab/2e52ab40fa59208c7f2d9c87f4a0227a.png");
            this.um.addItem("Verd", 2, "Tens més temps", "https://i.pinimg.com/originals/31/65/df/3165dfaa0fac0ca61802f9cdc1aef4d0.png");
            this.um.addItem("Groc", 3, "Obtens una pista", "https://s.namemc.com/3d/skin/body.png?id=6e52bc59debf0ea9&model=classic&width=308&height=308");
            this.um.addItem("Blau", 4, "Recompensa", "https://s.namemc.com/3d/skin/body.png?id=9489e7e4724918e8&model=slim&width=308&height=308");
        }
    }

    @GET
    @ApiOperation(value = "Obtenir una llista de tots els items", notes = "items de la botiga")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
    })
    @Path("/llista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {

        List<Item> items = this.um.findAll();
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(items) {};
        return Response.status(201).entity(entity).build()  ;
    }

    @POST
    @ApiOperation(value = "Afegir un ítem a la botiga", notes = "Operació per afegir una skin a la botiga")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós", response= Item.class),
            @ApiResponse(code = 409, message = "Aquest color de skin ja existeix"),
            @ApiResponse(code = 404, message = "Falta completar algun camp"),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/afegirItems")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItem(Item item){
        try {
            GameManager manager = GameManagerImpl.getInstance();
                manager.addItem(item.getColor(), item.getPreu(), item.getDescripcio(), item.getImatge());

            return Response.status(201).entity(item).build();
        } catch (ItemAlreadyExistsException e) {
            return Response.status(409).entity(item).build();
        } catch (MissingDataException e) {
            return Response.status(404).entity(item).build();
        } catch (Exception e) {
            return Response.status(500).entity(item).build();
        }
    }

    @DELETE
    @ApiOperation(value = "Eliminar un ítem de la botiga", notes = "Operació per eliminar una skin de la botiga")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Exitós", response= Item.class),
            @ApiResponse(code = 409, message = "Aquest color de skin no existeix"),
            @ApiResponse(code = 404, message = "Falta completar algun camp"),
            @ApiResponse(code = 500, message = "Error de validació")

    })

    @Path("/eliminarItems")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delItem(Item item){
        try {
            GameManager manager = GameManagerImpl.getInstance();
            manager.delItem(item.getColor(),item.getPreu(), item.getDescripcio(), item.getImatge());
            return Response.status(201).entity(item).build();
        } catch (ItemNotFoundException e) {
            return Response.status(409).entity(item).build();
        } catch (MissingDataException e) {
            return Response.status(404).entity(item).build();
        } catch (Exception e) {
            return Response.status(500).entity(item).build();
        }
    }

    /*@GET
    @ApiOperation(value = "Llistar items per preu ascendent", notes = "llista dels ítems")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitós", response = Item.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/llistarItems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response llistarItemsPerPreuAscendent() {
        GameManager manager = GameManagerImpl.getInstance();

        try {
            //obtenir la llista d'ítems odrenats per preu
            List<Item> itemsOrdenats = manager.llistarItemsPerPreuAscendent();
            return Response.status(200).entity(itemsOrdenats).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error intern del servidor").build();
        }
    }*/
}
