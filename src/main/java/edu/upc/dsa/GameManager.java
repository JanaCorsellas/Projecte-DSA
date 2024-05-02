package edu.upc.dsa;

import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;


import java.util.List;

public interface GameManager {

    public Usuari obtenirUsuariPerNomusuari(String nomUsuari);
    public void registreUsuari(String nom, String cognom, String nomusuari, String contrasenya) throws UserAlreadyExistsException;
    public boolean usuariExisteix(String nomUsuari);
    void login(String nomusuari, String contrasenya) throws UserNotFoundException, IncorrectPasswordException;
    public List<Item> llistarItemsPerPreuAscendent();

    //mètodes extres
    public void deleteUsuari(String id);
    public Item addItem(Item i);
    public Item addItem(String color, int preu);
    public Item getItem(String color);
    public List<Item> findAll();
    public int size();

}

