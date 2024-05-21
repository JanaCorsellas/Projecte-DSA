package edu.upc.dsa;

import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.util.QueryHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class QueryHelperTest {


    @Test
    public void testQueryINSERT() {
/*        Assert.assertEquals("INSERT INTO Usuari (ID, name, surname, address, birthDay, salary) VALUES (?, ?, ?, ?, ?, ?)",
                QueryHelper.createQueryINSERT(new Usuari("Juan", "lopez", 333333)));*/
        Assert.assertEquals("INSERT INTO Usuari (ID, name, surname, address, birthDay, genere, salary) VALUES (?, ?, ?, ?, ?, ?, ?)",
                QueryHelper.createQueryINSERT(new Usuari("nomtest", "cognomtest", "nomusuartest","pwdtest","pwd2test")));

    }


    @Test
    public void testQueryINSERT3() {

        Assert.assertEquals("INSERT INTO   (ID, name) VALUES (?, ?)",
                QueryHelper.createQueryINSERT(new Partida()));
    }


    @Test
    public void testQuerySELECT() {
        Assert.assertEquals("SELECT * FROM Usuari WHERE ID = ?",
                QueryHelper.createQuerySELECT(new Usuari("nomtest", "cognomtest", "nomusuartest","pwdtest","pwd2test")));
    }


    @Test
    public void testQueryFindAll() {

        HashMap<String, String> hs = new HashMap<String, String>();
        hs.put("username", "toni");

        Assert.assertEquals("SELECT * FROM Usuari WHERE 1=1 AND username=?",
                QueryHelper.createSelectFindAll(Usuari.class, hs));
    }

    @Test
    public void testQueryFindAll2() {

        HashMap<String, String> hs = new HashMap();
        hs.put("categoria", "defensa");
        hs.put("color", "rojo");

        String theQuery = QueryHelper.createSelectFindAll(Usuari.class, hs);
        System.out.println(theQuery);
        Assert.assertEquals("SELECT * FROM Usuari WHERE 1=1 AND color=? AND categoria=?", theQuery);
    }

}
