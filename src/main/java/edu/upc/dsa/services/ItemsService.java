package edu.upc.dsa.services;

import edu.upc.dsa.GameManager;
import edu.upc.dsa.GameManagerImpl;
import edu.upc.dsa.models.Botiga;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Api(value = "/items", description = "Endpoint to Item Service")
@Path("/items")
public class ItemsService {
    private GameManager um;

    public ItemsService() {
        this.um = GameManagerImpl.getInstance();
        if (um.size()==0) {
            this.um.addItem("Vermell", 1, "Guanyes una vida", "https://i.pinimg.com/originals/2e/52/ab/2e52ab40fa59208c7f2d9c87f4a0227a.png");
            this.um.addItem("Verd", 2, "Tens més temps", "https://i.pinimg.com/originals/31/65/df/3165dfaa0fac0ca61802f9cdc1aef4d0.png");
            this.um.addItem("Groc", 3, "Obtens una pista", "https://s.namemc.com/3d/skin/body.png?id=6e52bc59debf0ea9&model=classic&width=308&height=308");
            this.um.addItem("Blau", 4, "Recompensa", "https://s.namemc.com/3d/skin/body.png?id=9489e7e4724918e8&model=slim&width=308&height=308");
        }
        /*if (um.size()==0) {
            try {
                // Cargar la imagen desde una URL
                URL imageUrl = new URL("https://i.pinimg.com/originals/2e/52/ab/2e52ab40fa59208c7f2d9c87f4a0227a.png");
                BufferedImage imatge = ImageIO.read(imageUrl);

                // Agregar el ítem con la imagen al GameManager
                this.um.addItem("Vermell", 1, "Guanyes una vida", imatge);
            } catch (IOException e) {
                // Manejar la excepción si ocurre algún error al cargar la imagen
                e.printStackTrace();
            }
            try {
                // Cargar la imagen desde una URL
                URL imageUrl = new URL("https://i.pinimg.com/originals/31/65/df/3165dfaa0fac0ca61802f9cdc1aef4d0.png");
                BufferedImage imatge = ImageIO.read(imageUrl);

                // Agregar el ítem con la imagen al GameManager
                this.um.addItem("Verd", 2, "Tens més temps", imatge);
            } catch (IOException e) {
                // Manejar la excepción si ocurre algún error al cargar la imagen
                e.printStackTrace();
            }
            try {
                // Cargar la imagen desde una URL
                URL imageUrl = new URL("https://s.namemc.com/3d/skin/body.png?id=6e52bc59debf0ea9&model=classic&width=308&height=308");
                BufferedImage imatge = ImageIO.read(imageUrl);

                // Agregar el ítem con la imagen al GameManager
                this.um.addItem("Groc", 3, "Pista", imatge);
            } catch (IOException e) {
                // Manejar la excepción si ocurre algún error al cargar la imagen
                e.printStackTrace();
            }
            try {
                // Cargar la imagen desde una URL
                URL imageUrl = new URL("https://s.namemc.com/3d/skin/body.png?id=9489e7e4724918e8&model=slim&width=308&height=308");
                BufferedImage imatge = ImageIO.read(imageUrl);

                // Agregar el ítem con la imagen al GameManager
                this.um.addItem("Blau", 4, "Recompensa", imatge);
            } catch (IOException e) {
                // Manejar la excepción si ocurre algún error al cargar la imagen
                e.printStackTrace();
            }
        }*/
    }

    @GET
    @ApiOperation(value = "Obtenir una llista de tots els items", notes = "items de la botiga")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Botiga.class, responseContainer="List"),
    })
    @Path("/llista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {

        List<Botiga> items = this.um.findAll();
        /*for (Botiga item : items) {
            BufferedImage imatge = cargarImagenDesdeURL(String.valueOf(item.getImatge()));
            item.setImatge(imatge);
        }*/
        GenericEntity<List<Botiga>> entity = new GenericEntity<List<Botiga>>(items) {};
        return Response.status(201).entity(entity).build()  ;
    }

    /*private BufferedImage cargarImagenDesdeURL(String url) {
        try {
            URL imageUrl = new URL(url);
            return ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    @GET
    @ApiOperation(value = "Llistar items per preu ascendent", notes = "llista dels ítems")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exitós", response = Botiga.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Error intern del servidor")
    })
    @Path("/llistarItems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response llistarItemsPerPreuAscendent() {
        GameManager manager = GameManagerImpl.getInstance();

        try {
            //obtenir la llista d'ítems odrenats per preu
            List<Botiga> itemsOrdenats = manager.llistarItemsPerPreuAscendent();
            return Response.status(200).entity(itemsOrdenats).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error intern del servidor").build();
        }
    }
}
