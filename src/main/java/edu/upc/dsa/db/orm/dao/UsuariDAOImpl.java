package edu.upc.dsa.db.orm.dao;

import edu.upc.dsa.db.orm.FactorySession;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.db.orm.Sessio;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;

public class UsuariDAOImpl implements UsuariDAO {

    @Override
    public int addUsuari(String nom, String cognom, String nomusuari, String password, String password2) {
        if (!password.equals(password2)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        Sessio session = null;
        int usuariID = 0;
        try {
            session = FactorySession.openSession();
            Usuari usuari = new Usuari(nom, cognom, nomusuari, password, password2);
            session.save(usuari);
            // Assuming Usuari class has a getId() method
            usuariID = Integer.parseInt(usuari.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            // LOG - specific handling for constraint violations
            e.printStackTrace();
        } catch (Exception e) {
            // LOG - generic exception handling
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return usuariID;
    }

    @Override
    public Usuari getUsuari(int usuariID) {
        Sessio session = null;
        Usuari usuari = null;
        try {
            session = FactorySession.openSession();
            usuari = (Usuari) session.get(Usuari.class, usuariID);
        } catch (Exception e) {
            // LOG
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return usuari;
    }

    @Override
    public void updateUsuari(int usuariID, String name, String surname, int salary) {
        Sessio session = null;
        try {
            session = FactorySession.openSession();
            Usuari usuari = (Usuari) session.get(Usuari.class, usuariID);
            if (usuari != null) {
                usuari.setNom(name);
                usuari.setCognom(surname);
                usuari.setCoins(salary);
                session.update(usuari);
            }
        } catch (Exception e) {
            // LOG
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteUsuari(int usuariID) {
        Sessio session = null;
        try {
            session = FactorySession.openSession();
            Usuari usuari = (Usuari) session.get(Usuari.class, usuariID);
            if (usuari != null) {
                session.delete(usuari);
            }
        } catch (Exception e) {
            // LOG
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Object> getUsuaris() {
        Sessio session = null;
        List<Object> usuariList = null;
        try {
            session = FactorySession.openSession();
            usuariList = session.findAll(Usuari.class);
        } catch (Exception e) {
            // LOG
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuariList;
    }

    @Override
    public List<Object> getUsuariByDept(int deptID) {
        Sessio session = null;
        List<Object> usuariList = null;
        try {
            session = FactorySession.openSession();
            HashMap<String, Integer> params = new HashMap<>();
            params.put("deptID", deptID);
            usuariList = session.findAll(Usuari.class, params);
        } catch (Exception e) {
            // LOG
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return usuariList;
    }
}
