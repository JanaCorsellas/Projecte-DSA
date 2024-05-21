package edu.upc.dsa;

import edu.upc.dsa.db.orm.FactorySession;
import edu.upc.dsa.db.orm.Sessio;
import edu.upc.dsa.models.*;

import org.junit.Test;

public class ORMTest {


    @Test
    public void registerTest() {
    /*    String url = "127.0.0.1";
        String user = "root";
        String password = "1234";
*/
        Sessio session = FactorySession.openSession(); //url, user, password);
        Usuari usuari = new Usuari("nomtest", "cognomtest", "nomusuartest","pwdtest","pwd2test");
        session.save(usuari); // INSERT INTO usuari (idXXX, pepito, ...)

        ///
        // POST ==> hi ha un nou usuari a la taula POU
        //
    }

    @Test
    public void loginTest() {
        String url = "127.0.0.1";
        String user = "root";
        String password = "azapata7532";

        Sessio session = FactorySession.openSession(url, user, password);
        Usuari e = (Usuari)session.get(Usuari.class, 0);

    }
}
