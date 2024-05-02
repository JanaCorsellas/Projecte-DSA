package edu.upc.dsa;

import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
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
    public void registreUsuari(String nom, String cognom, String nomusuari, String contrasenya) throws UserAlreadyExistsException {
        /*Usuari usuari = new Usuari(nom, cognom, nomusuari, contrasenya);
        if (usuari.getNomusuari().equals(nomusuari)) {
            logger.error("Aquest nom d'usuari ja existeix.");
        }
        else {
            this.usuaris.add(usuari);
        }*/
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomusuari)) {
                throw new UserAlreadyExistsException("Aquest nom d'usuari ja existeix: " + nomusuari);
            }
        }

        // Si el nombre de usuario no está en uso, registra el usuario
        Usuari usuari = new Usuari(nom, cognom, nomusuari, contrasenya);
        this.usuaris.add(usuari);
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
    public boolean contrasenyaCorrecte(String nomusuari, String contrasenya) {
        Usuari usuari = obtenirUsuariPerNomusuari(nomusuari);

        if (usuari == null) {
            return false;
        }

        // Comparar la contraseña proporcionada con la contraseña almacenada para el usuario
        return usuari.getContrasenya().equals(contrasenya);
    }
    public void login(String nomusuari, String contrasenya) throws UserNotFoundException, IncorrectPasswordException {
        // Lógica para buscar el usuario en tu sistema
        // Si el usuario no se encuentra, lanza la excepción
        if (!usuariExisteix(nomusuari)) {
            throw new UserNotFoundException("El usuario no existe" + nomusuari);
        }
        if (!contrasenyaCorrecte(nomusuari, contrasenya)){
            throw new IncorrectPasswordException("Contrasenya incorrecte");
        }
        else{
            logger.info("Usuari existeix");
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

    public Item addItem(Item i) {
        logger.info("new item " + i);

        this.items.add (i);
        logger.info("new Track added");
        return i;
    }

    public Item addItem(String color, int preu) {
        return this.addItem(new Item(color, preu));
    }

    @Override
    public Item getItem(String color) {
        logger.info("getTrack("+color+")");

        for (Item i: this.items) {
            if (i.getColor().equals(color)) {
                logger.info("getTrack("+color+"): "+i);

                return i;
            }
        }
        logger.warn("not found " + color);
        return null;
    }
}