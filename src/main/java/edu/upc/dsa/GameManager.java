package edu.upc.dsa;

import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.MissingDataException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;


import java.util.List;

public interface GameManager {

    public Usuari obtenirUsuariPerNomusuari(String nomUsuari);
    public void registreUsuari(String nom, String cognom, String nomusuari, String password, String password2) throws UserAlreadyExistsException, IncorrectPasswordException, MissingDataException;
    public List<Usuari> llistaUsuaris();
    public boolean usuariExisteix(String nomUsuari);
    void login(String nomusuari, String password) throws UserNotFoundException, IncorrectPasswordException, MissingDataException;
    public List<Item> llistarItemsPerPreuAscendent();

    public Item obtenirItemPerColor(String color);

    //mètodes extres
    public void deleteUsuari(String id);
    public Item addItem(Item i);
    public Item addItem(String color, int preu, String descripcio, String imatge);
    public Item getItem(String color, int preu, String descripcio, String imatge);
    public List<Item> findAll();
    public int size();

}

