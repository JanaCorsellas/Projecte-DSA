package edu.upc.dsa;

import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.MissingDataException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Productes;
import edu.upc.dsa.models.Usuari;

import java.util.*;

import org.apache.log4j.Logger;

public class GameManagerImpl implements GameManager {
    private static GameManager instance;
    protected List<Usuari> usuaris;
    protected List<Productes> items;
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

    public List<Productes> findAll() {
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
    public List<Productes> llistarItemsPerPreuAscendent() {
        logger.info("Llistem els ítems de la botiga per ordre de preu ascendent");
        List<Productes> itemsOrdenats = new ArrayList<>(items);
        Comparator<Productes> comparadorAscendent = Comparator.comparingInt(Productes::getPreu);
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

    public Productes addItem(Productes i) {
        logger.info("nou item " + i);

        this.items.add (i);
        logger.info("nou skin afegit");
        return i;
    }

    public Productes addItem(String color, int preu, String descripcio, String imatge) {
        return this.addItem(new Productes(color, preu, descripcio, imatge));
    }

    @Override
    public Productes getItem(String color, int preu, String descripcio, String imatge) {
        logger.info("getItem(" + color + ")");

        for (Productes i : this.items) {
            if (i.getColor().equalsIgnoreCase(color)) { // Usamos equalsIgnoreCase para ignorar diferencias de mayúsculas/minúsculas
                logger.info("getItem(" + color + "): " + i);
                return i;
            }
        }
        logger.warn("not found " + color);
        return null;
    }
}