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
    private List<Faq> faqs;

    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        this.usuaris = new LinkedList<>();
        this.items = new LinkedList<>();
        this.formularis = new LinkedList<>();
        this.issues = new LinkedList<>();
        this.faqs = new LinkedList<>();
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
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomUsuari)) {
                return usuari;
            }
        }
        return null;
    }

    public List<Item> findAll() {
        return this.items;
    }

    //Mètode per registrar un usuari
    @Override
    public void registreUsuari(String nom, String cognom, String nomusuari, String password, String password2) throws UserAlreadyExistsException, IncorrectPasswordException, MissingDataException, SQLException {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomusuari)) {
                throw new UserAlreadyExistsException("Aquest nom d'usuari ja existeix: " + nomusuari);
            }
        }
        if (nom == "" || cognom == "" || nomusuari == "" || password == "" || password2 == "") {
            throw new MissingDataException("Falten camps per completar");
        } else if (!Objects.equals(password, password2)) {
            throw new IncorrectPasswordException("La contrasenya no coincideix");
        } else {
            //Si el nombre de usuario no está en uso, registra el usuario
            Usuari usuari = new Usuari(nom, cognom, nomusuari, password, password2);
            this.usuaris.add(usuari);
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
        if (nomusuari == "" || password == "") {
            throw new MissingDataException("Completa tots els camps");
        } else {
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

    //Mètode per eliminar un usuari
    @Override
    public void deleteUsuari(String nomusuari) {
        Usuari t = this.obtenirUsuariPerNomusuari(nomusuari);
        if (t==null){
            logger.warn("no s'ha trobat l'usuari " + t);
        }
        else logger.info(t+" eliminat ");
        this.usuaris.remove(t);
    }

    //Mètode per afegir un ítem a la botiga
    @Override
    public void addItem(String color, int preu, String descripcio, String imatge) throws MissingDataException, ItemAlreadyExistsException {
        for (Item item : items) {
            if (item.getColor().equals(color)) {
                throw new ItemAlreadyExistsException("Aquesta skin ja existeix: " + color);
            }
        }
        if(color == "" || preu < 0 || descripcio == "" || imatge == ""){
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
        if (data == "" || title == "" || message == "" || sender == ""){
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

        if(date == "" || informer == "" || message == ""){
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
    public List<Faq> getAllFaqs() {
        return faqs;
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
}


