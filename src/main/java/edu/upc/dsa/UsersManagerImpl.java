package edu.upc.dsa;

import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;

import java.util.*;

import org.apache.log4j.Logger;

public class UsersManagerImpl implements UsersManager {
    private static UsersManager instance;
    protected List<Usuari> usuaris;
    protected List<Item> items;
    final static Logger logger = Logger.getLogger(UsersManagerImpl.class);

    private UsersManagerImpl() {
        this.usuaris = new LinkedList<>();
        this.items = new LinkedList<>();
    }

    public static UsersManager getInstance() {
        if (instance==null) instance = new UsersManagerImpl();
        return instance;
    }

    @Override
    public Usuari obtenirUsuariPerId(String idUsuari) {
        for (Usuari usuari : usuaris) {
            if (usuari.getId().equals(idUsuari)) {
                return usuari;
            }
        }
        return null;
    }
    @Override
    public void registreUsuari(String id, String nom, String cognom, String nomusuari) {
        Usuari usuari = new Usuari(id, nom, cognom, nomusuari);
        if (usuari.getId().equals(id)) {
            logger.error("L'usuari amb aquesta id ja existeix.");
        }
        else {
            this.usuaris.add(usuari);
        }
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

    /*@Override
    public void login(String nomusuari) {
        boolean found = false;
        for (Usuari usuari : usuaris) {
            if (usuari.getNomusuari().equals(nomusuari)) {
                found = true;
                break;
            }
        }
        if (found) {
            logger.info("Usuari " + nomusuari + " trobat. Accés concedit.");
        } else {
            logger.error("L'usuari " + nomusuari + " no existeix.");
        }
    }*/
    public void login(String nomusuari) throws UserNotFoundException {
        // Lógica para buscar el usuario en tu sistema
        // Si el usuario no se encuentra, lanza la excepción
        if (!usuariExisteix(nomusuari)) {
            throw new UserNotFoundException("El usuario no existe" + nomusuari);
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
    public void deleteUsuari(String id) {
        Usuari t = this.obtenirUsuariPerId(id);
        if (t==null){
            logger.warn("no s'ha trobat l'usuari " + t);
        }
        else logger.info(t+" eliminat ");
        this.usuaris.remove(t);
    }

    @Override
    public void addItem(String color, int preu) {
        for (Item item : items){
            if (item.getColor().equals(color)){
                logger.error("Ja existeix una skin amb aquest color.");
                return;
            }
        }
    }
}