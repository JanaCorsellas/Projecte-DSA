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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        initializeItems();
        this.formularis = new LinkedList<>();
        this.issues = new LinkedList<>();
        this.faqs = new LinkedList<>();
        this.ranking = new LinkedList<>();
    }

    private void initializeItems() {
        items.add(new Item("Vermell", 1, "Guanyes una vida", "https://i.pinimg.com/originals/2e/52/ab/2e52ab40fa59208c7f2d9c87f4a0227a.png"));
        items.add(new Item("Verd", 2, "Tens més temps", "https://i.pinimg.com/originals/31/65/df/3165dfaa0fac0ca61802f9cdc1aef4d0.png"));
        items.add(new Item("Groc", 3, "Obtens una pista", "https://s.namemc.com/3d/skin/body.png?id=6e52bc59debf0ea9&model=classic&width=308&height=308"));
        items.add(new Item("Blau", 4, "Recompensa", "https://s.namemc.com/3d/skin/body.png?id=9489e7e4724918e8&model=slim&width=308&height=308"));
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


    public List<Item> llistaItems() {
        return this.items;
    }


    //Mètode per registrar un usuari
    @Override
    public void registreUsuari(String nom, String cognom, String nomusuari, String password, String password2, int coins) throws UserAlreadyExistsException, IncorrectPasswordException, MissingDataException, SQLException {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomusuari)) {
                throw new UserAlreadyExistsException("Aquest nom d'usuari ja existeix: " + nomusuari);
            }
        }
        if (nom.equals("") || cognom.equals("") || nomusuari.equals("") || password.equals("") || password2.equals("")) {
            throw new MissingDataException("Falten camps per completar");
        } else if (!Objects.equals(password, password2)) {
            throw new IncorrectPasswordException("La contrasenya no coincideix");
        } else {
            //Si el nombre de usuario no está en uso, registra el usuario
            Usuari usuari = new Usuari(nom, cognom, nomusuari, password, password2, coins);
            usuari.setCoins(20);
            this.usuaris.add(usuari);

            /*try {
                Connection conn = DBUtils.getConnection();
                Sessio session = new SessioImpl(conn);
                session.save(usuari); // INSERT INTO usuari (idXXX, pepito, ...)
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (nom.equals("") || cognom.equals("") || nomusuari.equals("") || password.equals("") || password2.equals("")) {
                throw new MissingDataException("Falten camps per completar");
            }*/

//        if (password.equals(password2)) {
//            // Las contraseñas coinciden, procedemos con la inserción en la base de datos
//            try {
//                Connection conn = DBUtils.getConnection();
//                Sessio session = new SessioImpl(conn);
//                session.save(usuari); // INSERT INTO usuari (idXXX, pepito, ...)
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // Las contraseñas no coinciden, manejar el error
//            throw new IncorrectPasswordException("La contrasenya no coincideix");
//        }
        }

    }

    //Mètode per iniciar sessió buscant l'usuari a la bbdd, en cas que no estigui registrat avisa
    @Override
    public void login(String nomusuari, String password) throws UserNotFoundException, IncorrectPasswordException, MissingDataException {
        // Lógica para buscar el usuario en tu sistema
        // Si el usuario no se encuentra, lanza la excepción
        if (!usuariExisteix(nomusuari)) {
            throw new UserNotFoundException("L'usuari no existeix" + nomusuari);
        }
        if (!contrasenyaCorrecte(nomusuari, password)) {
            throw new IncorrectPasswordException("Contrasenya incorrecte");
        }
        if (nomusuari.equals("") || password.equals("")) {
            throw new MissingDataException("Completa tots els camps");
        } else {
            //
            /*Connection conn = null;
                try {
                    conn = DBUtils.getConnection();
                    Sessio session = new SessioImpl(conn);
                    Usuari e = (Usuari) session.get(Usuari.class, getUserId(nomusuari, password));
                    System.out.println("id:" + getUserId(nomusuari, password));
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }*/
            logger.info("Has iniciat sessió");
        }
    }

    @Override
    public List<Usuari> llistaUsuaris() {
        return new ArrayList<>(usuaris);
    }

    //Mètode per verificar si un usuari existeix buscant a la llista d'usuaris
    @Override
    public boolean usuariExisteix(String nomUsuari) {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomUsuari)) {
                return true;
            }
        }
        return false;
    }
    //Mètode per comprovar que la contrasenya proporcionada per l'usuari és la correcte, comparant-la amb la que tenim a la bbdd
    public boolean contrasenyaCorrecte(String nomusuari, String password) {
        Usuari usuari = obtenirUsuariPerNomusuari(nomusuari);

        if (usuari == null) {
            return false;
        }
        return usuari.getPassword().equals(password);
    }

    public Integer getUserId(String username, String password) {
        String query = "SELECT id FROM usuari WHERE nomusuari = ? AND password = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, username);
            pstm.setString(2, password);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new IllegalArgumentException("Usuario o contraseña incorrectos");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // O maneja la excepción de otra manera apropiada
        }
    }




    //Mètode per donar de baixa/eliminar un usuari
    @Override
    public void baixaUsuari(String nomusuari) throws UserNotFoundException, SQLException {
        Usuari usuari = obtenirUsuariPerNomusuari(nomusuari);
        if (usuari == null) {
            logger.warn("No s'ha trobat l'usuari " + nomusuari);
            throw new UserNotFoundException("L'usuari no existeix: " + nomusuari);
        } else {
            // Lógica adicional para la baja, como registrar en un log de auditoría
            logger.info("Donant de baixa l'usuari " + nomusuari);

            // Eliminar de la base de datos, si es necesario
            try (Connection conn = DBUtils.getConnection()) {
                String query = "DELETE FROM usuari WHERE nomusuari = ?";
                try (PreparedStatement pstm = conn.prepareStatement(query)) {
                    pstm.setString(1, nomusuari);
                    int affectedRows = pstm.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("No s'ha pogut eliminar l'usuari de la base de dades.");
                    }
                }
            } catch (SQLException e) {
                logger.error("Error en eliminar l'usuari de la base de dades", e);
                throw e;
            }

            // Eliminar de la llista local
            this.usuaris.remove(usuari);
            logger.info("Usuari " + nomusuari + " donat de baixa amb èxit.");
        }
    }

    //Mètode per afegir un ítem a la botiga
    @Override
    public void addItem(String color, int preu, String descripcio, String imatge) throws MissingDataException, ItemAlreadyExistsException {
        for (Item item : items) {
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




    @Override
    public Usuari obtenirUsuariPerNomusuari(String nomUsuari) {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomUsuari)) {
                return usuari;
            }
        }
        return null;
    }
    //Mètode per obtenir un ítem per color
    @Override
    public Item obtenirItemPerColor(String color) {
        for (Item item : items) {
            if (item.getColor().equals(color)) {
                return item;
            }
        }
        return null;
    }

    public boolean dinersSuficient(String nomUsuari, String itemColor){
        Usuari usuari = obtenirUsuariPerNomusuari(nomUsuari);
        Item item = obtenirItemPerColor(itemColor);
        if (usuari.getCoins() >= item.getPreu()){
            return true;
        }
        else
            return false;
    }

    public Usuari comprarItem(String nomUsuari, String item) throws MissingDataException {
        Usuari usuari = obtenirUsuariPerNomusuari(nomUsuari);
        if(dinersSuficient(nomUsuari, item) == true){
            usuari.setSkin(item);

            //RESTAR ELS DINERS DE L'ITEM COMPRAT A L'USUARI
            Item i = obtenirItemPerColor(item);
            int diners = usuari.getCoins();
            diners = diners - i.getPreu();
            usuari.setCoins(diners);

            return usuari;
        }
        else
            throw new MissingDataException("Falten diners per poder comprar l'Item");
    }
}


