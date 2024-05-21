package edu.upc.dsa;

import edu.upc.dsa.exception.*;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;

import java.util.*;

import org.apache.log4j.Logger;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    protected List<Usuari> usuaris;
    protected List<Item> items;
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    private GameManagerImpl() {
        this.usuaris = new LinkedList<>();
        this.items = new LinkedList<>();
    }

    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
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

    @Override
    public void registreUsuari(String nom, String cognom, String nomusuari, String password, String password2) throws UserAlreadyExistsException, IncorrectPasswordException, MissingDataException {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomusuari)) {
                throw new UserAlreadyExistsException("Aquest nom d'usuari ja existeix: " + nomusuari);
            }
        }
        if(nom == "" || cognom == "" || nomusuari == "" || password == "" || password2 == ""){
            throw new MissingDataException("Falten camps per completar");
        }
        else if (!Objects.equals(password, password2)){
            throw new IncorrectPasswordException("La contrasenya no coincideix");
        }
        else{
            // Si el nombre de usuario no está en uso, registra el usuario
            Usuari usuari = new Usuari(nom, cognom, nomusuari, password, password2);
            this.usuaris.add(usuari);
        }
    }
    @Override
    public List<Usuari> llistaUsuaris() {
        return new ArrayList<>(usuaris);
    }
    @Override
    public boolean usuariExisteix(String nomUsuari) {
        //implementa la lògica per verificar si existeix un usuari
        //¡busca en una llista d'usuaris
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomUsuari)) {
                return true;
            }
        }
        return false;
    }
    public boolean contrasenyaCorrecte(String nomusuari, String password) {
        Usuari usuari = obtenirUsuariPerNomusuari(nomusuari);

        if (usuari == null) {
            return false;
        }

        // Comparar la contraseña proporcionada con la contraseña almacenada para el usuario
        return usuari.getPassword().equals(password);
    }

    @Override
    public void login(String nomusuari, String password) throws UserNotFoundException, IncorrectPasswordException, MissingDataException {
        // Lógica para buscar el usuario en tu sistema
        // Si el usuario no se encuentra, lanza la excepción
        if (!usuariExisteix(nomusuari)) {
            throw new UserNotFoundException("L'usuari no existeix" + nomusuari);
        }
        if (!contrasenyaCorrecte(nomusuari, password)){
            throw new IncorrectPasswordException("Contrasenya incorrecte");
        }
        if (nomusuari == "" || password == ""){
            throw new MissingDataException("Completa tots els camps");
        }
        else{
            logger.info("Has iniciat sessió");
        }
    }

    @Override
    public List<Item> llistarItemsPerPreuAscendent() {
        logger.info("Llistem els ítems de la botiga per ordre de preu ascendent");
        List<Item> itemsOrdenats = new ArrayList<>(items);
        Comparator<Item> comparadorAscendent = Comparator.comparingInt(Item::getPreu);
        Collections.sort(itemsOrdenats, comparadorAscendent);
        logger.info("Items ordenats correctament");
        return itemsOrdenats;
    }

    @Override
    public Item obtenirItemPerColor(String color) {
        for (Item producte : items) {
            if (producte.getColor().equals(producte)) {
                return producte;
            }
        }
        return null;
    }

    //mètodes extres
    @Override
    public void deleteUsuari(String nomusuari) {
        Usuari t = this.obtenirUsuariPerNomusuari(nomusuari);
        if (t==null){
            logger.warn("no s'ha trobat l'usuari " + t);
        }
        else logger.info(t+" eliminat ");
        this.usuaris.remove(t);
    }

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

    @Override
    public void delItem(String color, int preu, String descripcio, String imatge) throws MissingDataException, ItemNotFoundException{
        if(color == null){
            logger.info("Digues el color de la skin");
            throw new MissingDataException("Cal completar els camps");
        }
        boolean itemFound = false;
        Iterator<Item> iterator = this.items.iterator();

        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getColor().equals(color)) {
                iterator.remove();
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            throw new ItemNotFoundException("No s'ha trobat cap item amb aquest color: " + color);
        }
    }

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
}