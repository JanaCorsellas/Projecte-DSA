package edu.upc.dsa.db.orm.dao;

import edu.upc.dsa.db.orm.FactorySession;
import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.MissingDataException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.db.orm.Sessio;
import edu.upc.dsa.models.UsuariLogin;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;

public class UsuariDAOImpl implements UsuariDAO {


    private static UsuariDAO instance;

    public static UsuariDAO getInstance() {
        if (instance == null) instance = new UsuariDAOImpl();
        return instance;
    }


    public void addUsuari(String nom, String cognom, String nomusuari, String password, String password2, int coins) throws MissingDataException, IncorrectPasswordException, UserAlreadyExistsException {
        if (nom.isEmpty() || cognom.isEmpty() || nomusuari.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            throw new MissingDataException("Falten camps per completar");
        } else if (!password.equals(password2)) {
            throw new IncorrectPasswordException("La contrasenya no coincideix");
        }else{
            Sessio session = null;
            try {
                session = FactorySession.openSession();
                Usuari usuari = new Usuari(nom, cognom, nomusuari, password, password2, coins);
                usuari.setCoins(20);
                session.save(usuari);
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new UserAlreadyExistsException("Aquest nom d'usuari ja existeix.");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
    }

    @Override
    public UsuariLogin loginUsuari(String nomusuari, String password) throws MissingDataException, UserNotFoundException, IncorrectPasswordException {

        if (nomusuari.isEmpty() || password.isEmpty()){
            throw new MissingDataException("Falten camps per completar");
        }
        Sessio session = null;
        Usuari usuari;
        try{
            session = FactorySession.openSession();
            usuari = (Usuari) session.get(Usuari.class, "nomusuari", nomusuari);
            if(usuari == null){
                throw new UserNotFoundException("L'usuari no s'ha trobat");
            }else if(!usuari.getPassword().equals(password)) {
                throw new IncorrectPasswordException("Usuari o contrasenya equivocada");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        UsuariLogin credencials = new UsuariLogin();
        credencials.setNomusuari(nomusuari);
        credencials.setPassword(password);
        return credencials;
    }

    public List<Usuari> llistaUsuarisDAO(){
        Sessio session = null;
        List<Usuari> usuaris=null;
        try {
            session = FactorySession.openSession();
            usuaris = session.findAll(Usuari.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        return usuaris;
    }

    @Override
    public Usuari getUsuari(String nomusuari) throws UserNotFoundException {
        Sessio session = null;
        Usuari usuari = null;
        try {
            session = FactorySession.openSession();
            usuari = (Usuari) session.get(Usuari.class, "nomusuari", nomusuari);
            if (usuari == null) {
                throw new UserNotFoundException("Usuari no trobat.");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            assert session != null;
            session.close();
        }
        return usuari;

    }

    /*public Item getItem(String color) throws ItemNotFoundException{
        Sessio session = null;
        Item item = null;
        try {
            session = FactorySession.openSession();
            item = (Item) session.get(Item.class, "color", color);
            if (item == null) {
                throw new ItemNotFoundException("Item no trobat.");
            }
        }
    }*/

/*
    public void updateUsuari(int usuariID, String name, String surname, int salary) {
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


    public List<Usuari> getUsuaris() {
        Sessio session = null;
        List<Usuari> usuariList=null;
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


    public void customQuery(xxxx) {
        Session session = null;
        List<Usuari> usuariList=null;
        try {
            session = RoomSession.openSession();
            Connection c = session.getConnection();
            c.createStatement("SELECT * ")


        */
}





