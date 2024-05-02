package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.Item;
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

    public ItemsService() {
        this.um = GameManagerImpl.getInstance();
        if (um.size()==0) {
            this.um.addItem("Vermell", 1);
            this.um.addItem("Verd", 2);
            this.um.addItem("Groc", 3);
            this.um.addItem("Blau", 4);
        }
    }

    @GET
    @ApiOperation(value = "Obtenir una llista de tots els items", notes = "items de la botiga")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {

        List<Item> items = this.um.findAll();

        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(items) {};
        return Response.status(201).entity(entity).build()  ;
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
        GameManager manager = GameManagerImpl.getInstance();

        try {
            //obtenir la llista d'ítems odrenats per preu
            List<Item> itemsOrdenats = manager.llistarItemsPerPreuAscendent();
            return Response.status(200).entity(itemsOrdenats).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error intern del servidor").build();
        }
    }
}
