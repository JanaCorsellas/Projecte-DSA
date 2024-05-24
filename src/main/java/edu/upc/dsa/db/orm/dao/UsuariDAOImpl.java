package edu.upc.dsa.db.orm.dao;

import edu.upc.dsa.db.orm.FactorySession;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.db.orm.Sessio;

import java.util.HashMap;
import java.util.List;

public class UsuariDAOImpl implements UsuariDAO {

    public int addUsuari(String nom, String cognom, String nomusuari, String password, String password2) {
        Sessio session = null;
        int usuariID = 0;
        try {
            session = FactorySession.openSession();
            Usuari usuari = new Usuari(nom, cognom, nomusuari, password, password2);
            session.save(usuari);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }

        return usuariID;
    }


    public Usuari getUsuari(int usuariID) {
        Sessio session = null;
        Usuari usuari = null;
        try {
            session = FactorySession.openSession();
            usuari = (Usuari) session.get(Usuari.class, usuariID);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }

        return usuari;
    }


    public void updateUsuari(int usuariID, String name, String surname, double salary) {
        Usuari usuari  = this.getUsuari(usuariID);
        usuari.setNom(name);
        usuari.setCognom(surname);
        usuari.setCoins(salary);

        Sessio session = null;
        try {
            session = FactorySession.openSession();
            session.update(usuari);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }
    }


    public void deleteUsuari(int usuariID) {
        Usuari usuari = this.getUsuari(usuariID);
        Sessio session = null;
        try {
            session = FactorySession.openSession();
            session.delete(usuari);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }

    }


    public List<Object> getUsuaris() {
        Sessio session = null;
        List<Object> usuariList=null;
        try {
            session = FactorySession.openSession();
            usuariList = session.findAll(Usuari.class);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }
        return usuariList;
    }


    public List<Object> getUsuariByDept(int deptID) {
        // SELECT e.name, d.name FROM Usuaris e, DEpt d WHERE e.deptId = d.ID AND e.edat>87 AND ........
//        Connection c =
        Sessio session = null;
        List<Object> usuariList=null;
        try {
            session = FactorySession.openSession();


            HashMap<String, Integer> params = new HashMap<String, Integer>();
            params.put("deptID", deptID);

            usuariList = session.findAll(Usuari.class, params);
        }
        catch (Exception e) {
            // LOG
        }
        finally {
            session.close();
        }
        return usuariList;
    }

    /*

    public void customQuery(xxxx) {
        Session session = null;
        List<Usuari> usuariList=null;
        try {
            session = RoomSession.openSession();
            Connection c = session.getConnection();
            c.createStatement("SELECT * ")

        }
*/

}
