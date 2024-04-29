package edu.upc.dsa;

import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UsuarisServiceTest {
    UsersManager um;

    @Before
    public void setUp() throws Exception {
        this.um = UsersManagerImpl.getInstance();
    }
    @Test
    public void testaddDron() throws Exception {
        um.registreUsuari("1", "jordi", "gual", "jgual01");
        assertNotNull(um.obtenirUsuariPerId("1"));
    }

    @Test
    public void testLlistarItemsPerPreuAscendent(){
        //List<Item> items = List.of{

        um.addItem("Vermell", 1);
        um.addItem("Blau", 2);
        um.addItem("Verd", 3);
        um.addItem("Groc", 4);

        //assertEquals(4, um.llistarItemsPerPreuAscendent().size());
        assertEquals(1, um.llistarItemsPerPreuAscendent().get(0).getPreu());
        assertEquals(2, um.llistarItemsPerPreuAscendent().get(1).getPreu());
        assertEquals(3, um.llistarItemsPerPreuAscendent().get(2).getPreu());
        assertEquals(4, um.llistarItemsPerPreuAscendent().get(3).getPreu());
    }
}
