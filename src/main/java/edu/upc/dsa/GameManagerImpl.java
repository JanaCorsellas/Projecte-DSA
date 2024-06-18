package edu.upc.dsa;

import edu.upc.dsa.db.DBUtils;
import edu.upc.dsa.db.orm.Sessio;
import edu.upc.dsa.db.orm.SessioImpl;
import edu.upc.dsa.exception.*;
import edu.upc.dsa.models.Formulari;
import edu.upc.dsa.models.Issue;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.models.Faq;
import edu.upc.dsa.models.*;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    protected List<Usuari> usuaris;
    protected List<Item> items;
    protected List<Formulari> formularis;
    protected List<Issue> issues;
    protected List<Faq> faqs;
    protected List<Usuari> ranking;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        this.usuaris = new LinkedList<>();
        this.items = new LinkedList<>();
        this.formularis = new LinkedList<>();
        this.issues = new LinkedList<>();
        this.faqs = new LinkedList<>();
        this.ranking = new LinkedList<>();
    }

    public static GameManager getInstance() {
        if (instance == null) instance = new GameManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.items.size();
        logger.info("size " + ret);

        return ret;
    }

    @Override
    public Usuari obtenirUsuariPerNomusuari(String nomUsuari) {
        /*for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomUsuari)) {
                return usuari;
            }
        }
        return null;*/
        String query = "SELECT * FROM Usuari WHERE nomusuari = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, nomUsuari);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new Usuari(
                            rs.getString("id"),
                            rs.getString("nom"),
                            rs.getString("cognom"),
                            rs.getString("nomusuari"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM Item";
        try (Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                items.add(new Item(
                        rs.getString("color"),
                        rs.getInt("preu"),
                        rs.getString("descripcio"),
                        rs.getString("imatge")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    //Mètode per registrar un usuari
    @Override
    public void registreUsuari(String nom, String cognom, String nomusuari, String password, String password2) throws UserAlreadyExistsException, IncorrectPasswordException, MissingDataException, SQLException {
        if (nom.isEmpty() || cognom.isEmpty() || nomusuari.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            throw new MissingDataException("Falten camps per completar");
        } else if (!password.equals(password2)) {
            throw new IncorrectPasswordException("La contrasenya no coincideix");
        } else {
            String query = "INSERT INTO Usuari (nom, cognom, nomusuari, password, password2, coins, clau, skin, puntuacio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBUtils.getConnection();
                 PreparedStatement pstm = conn.prepareStatement(query)) {

                pstm.setString(1, nom);
                pstm.setString(2, cognom);
                pstm.setString(3, nomusuari);
                pstm.setString(4, password);
                pstm.setString(5, password2);
                pstm.setInt(6, 0); // Inicializa coins a 0
                pstm.setBoolean(7, false); // Inicializa clau a false
                pstm.setString(8, "Verd"); // Inicializa skin a "Verd"
                pstm.setInt(9, 0); // Inicializa puntuacio a 0

                pstm.executeUpdate();
                logger.info("Usuari registrat: " + nomusuari);
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new UserAlreadyExistsException("Aquest nom d'usuari ja existeix.");
            }
        }
    }

    //Mètode per iniciar sessió buscant l'usuari a la bbdd, en cas que no estigui registrat avisa
    @Override
    public void login(String nomusuari, String password) throws UserNotFoundException, IncorrectPasswordException, MissingDataException, SQLException {
        // Lógica para buscar el usuario en tu sistema
        // Si el usuario no se encuentra, lanza la excepción
        if (nomusuari.isEmpty() || password.isEmpty()) {
            throw new MissingDataException("Completa tots els camps");
        }

        String query = "SELECT * FROM Usuari WHERE nomusuari = ?";
        //Usuari usuari;
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, nomusuari);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    Usuari usuari = new Usuari(
                            rs.getString("id"),
                            rs.getString("nom"),
                            rs.getString("cognom"),
                            rs.getString("nomusuari"),
                            rs.getString("password")
                    );

                    if (!usuari.getPassword().equals(password)) {
                        throw new IncorrectPasswordException("Usuari o contrasenya equivocada");
                    } else {
                        logger.info("Has iniciat sessió");
                    }
                } else {
                    throw new UserNotFoundException("L'usuari no existeix");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Usuari> llistaUsuaris() {
        List<Usuari> usuaris = new ArrayList<>();
        String query = "SELECT * FROM Usuari";
        try (Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                usuaris.add(new Usuari(
                        rs.getString("id"),
                        rs.getString("nom"),
                        rs.getString("cognom"),
                        rs.getString("nomusuari"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuaris;
    }


    //Mètode per verificar si un usuari existeix buscant a la llista d'usuaris
    @Override
    public boolean usuariExisteix(String nomUsuari) {
        /*for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomUsuari)) {
                return true;
            }
        }
        return false;*/
        return obtenirUsuariPerNomusuari(nomUsuari) != null;
    }
    //Mètode per comprovar que la contrasenya proporcionada per l'usuari és la correcte, comparant-la amb la que tenim a la bbdd
    public boolean contrasenyaCorrecte(String nomusuari, String password) {
        Usuari usuari = obtenirUsuariPerNomusuari(nomusuari);

        /*if (usuari == null) {
            return false;
        }
        return usuari.getPassword().equals(password);*/
        return usuari != null && usuari.getPassword().equals(password);
    }

    public Integer getUserId(String username, String password) {
        String query = "SELECT id FROM Usuari WHERE nomusuari = ? AND password = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, username);
            pstm.setString(2, password);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new IllegalArgumentException("Usuari o contrasenya incorrectes");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // O maneja la excepción de otra manera apropiada
        }
    }


    //Mètode per obtenir un ítem per color
    @Override
    public Item obtenirItemPerColor(String color) {
        for (Item producte : items) {
            if (producte.getColor().equals(producte)) {
                return producte;
            }
        }
        return null;
    }

    //Mètode per donar de baixa/eliminar un usuari
    @Override
    public void baixaUsuari(String nomusuari) throws UserNotFoundException, SQLException {
        Usuari usuari = obtenirUsuariPerNomusuari(nomusuari);
        if (usuari == null) {
            logger.warn("No s'ha trobat l'usuari " + nomusuari);
            throw new UserNotFoundException("L'usuari no existeix: " + nomusuari);
        } else {
            String query = "DELETE FROM Usuari WHERE nomusuari = ?";
            try (Connection conn = DBUtils.getConnection();
                 PreparedStatement pstm = conn.prepareStatement(query)) {

                pstm.setString(1, nomusuari);
                int affectedRows = pstm.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("No s'ha pogut eliminar l'usuari de la base de dades.");
                } else {
                    logger.info("Usuari " + nomusuari + " donat de baixa amb èxit.");
                }
            } catch (SQLException e) {
                logger.error("Error en eliminar l'usuari de la base de dades", e);
                throw e;
            }
        }
    }

    //Mètode per afegir un ítem a la botiga
    @Override
    public void addItem(String color, int preu, String descripcio, String imatge) throws MissingDataException, ItemAlreadyExistsException {
        /*for (Item item : items) {
            if (item.getColor().equals(color)) {
                throw new ItemAlreadyExistsException("Aquesta skin ja existeix: " + color);
            }
        }
        if(color.equals("") || preu < 0 || descripcio.equals("") || imatge.equals("")){
            throw new MissingDataException("Falten camps per completar");
        }
        else{
            // Si el nombre de usuario no está en uso, registra el usuario
            Item item = new Item(color, preu, descripcio, imatge);
            this.items.add(item);
        }*/
        if (color.isEmpty() || preu < 0 || descripcio.isEmpty() || imatge.isEmpty()) {
            throw new MissingDataException("Falten camps per completar");
        } else {
            String query = "INSERT INTO Item (color, preu, descripcio, imatge) VALUES (?, ?, ?, ?)";
            try (Connection conn = DBUtils.getConnection();
                 PreparedStatement pstm = conn.prepareStatement(query)) {

                pstm.setString(1, color);
                pstm.setInt(2, preu);
                pstm.setString(3, descripcio);
                pstm.setString(4, imatge);

                pstm.executeUpdate();
                logger.info("Item afegit: " + color);
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new ItemAlreadyExistsException("Aquesta skin ja existeix: " + color);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Mètode per eliminar un ítem de la botiga
    @Override
    public void delItem(String color) throws ItemNotFoundException, MissingDataException {
        if (color == null || color.isEmpty()) {
            throw new MissingDataException("Cal completar els camps");
        }
        boolean found = false;
        for (Item item : items) {
            if (item.getColor().equals(color)) {
                items.remove(item);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new ItemNotFoundException("No s'ha trobat cap item amb aquest color: " + color);
        }
    }

    //Mètode per obtenir un ítem
    @Override
    public Item getItem(String color, int preu, String descripcio, String imatge) {
        logger.info("getItem(" + color + ")");

        for (Item i : this.items) {
            if (i.getColor().equalsIgnoreCase(color)) { // Usamos equalsIgnoreCase para ignorar diferencias de mayúsculas/minúsculas
                logger.info("getItem(" + color + "): " + i);
                return i;
            }
        }
        logger.warn("not found " + color);
        return null;
    }

    //Mètode per escriure i enviar un formulari
    @Override
    public void formulari(String data, String title, String message, String sender) throws MissingDataException {
        if (data.equals("") || title.equals("") || message.equals("") || sender.equals("")){
            throw new MissingDataException("Falten camps per completar");
        }
        else{
            Formulari formulari = new Formulari (data, title, message, sender);
            this.formularis.add(formulari);
        }
    }

    //Mètode per obtenir una llista de tots els formularis
    public List<Formulari> llistaFormularis() {
        return new ArrayList<>(formularis);
    }

    //Mètode per afegir una issue
    @Override
    public void addIssue(String date, String informer, String message) throws MissingDataException {

        if(date.equals("") || informer.equals("") || message.equals("")){
            throw new MissingDataException("Falten camps per completar");
        }
        else{
            Issue issue = new Issue(date, informer, message);
            this.issues.add(issue);
        }
    }

    //Mètode per obtenir una llista de totes les issues
    public List<Issue> llistaIssues() {
        return new ArrayList<>(issues);
    }

    //Mètode per afegir una pregunta
    @Override
    public void addFaq(Faq faq) {
        faqs.add(faq);
    }

    //Mètode per obtenir una llista de totes les preguntes
    @Override
    public List<Faq> llistaFaqs() {
        return new ArrayList<>(faqs);
    }

    //Mètode per eliminar una pregunta
    @Override
    public void removeFaq(String faqId) {
        Iterator<Faq> iterator = faqs.iterator();
        while (iterator.hasNext()) {
            Faq faq = iterator.next();
            if (faq.getId().equals(faqId)) {
                iterator.remove();
                break; // Salir del bucle una vez que se elimina la FAQ
            }
        }
    }
    /*@Override
    public void addRanking(String nomUsuari, int puntuacio, Date data) {
        Usuari usuari = new Usuari(nomUsuari, puntuacio);
        Partida partida = new Partida();
        ranking.add(usuari);
    }
    //Mètode per obtenir una llista de totes les preguntes
    @Override
    public List<Usuari> ranking() {
        return new ArrayList<>(ranking);
    }*/
}


