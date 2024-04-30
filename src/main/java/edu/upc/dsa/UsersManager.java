package edu.upc.dsa;

import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;

import java.util.List;

public interface UsersManager {

    public Usuari obtenirUsuariPerId (String idUsuari);
    public void registreUsuari(String id, String nom, String cognom, String nomusuari);
    public boolean usuariExisteix(String nomUsuari);
    void login(String nomusuari) throws UserNotFoundException;
    public List<Item> llistarItemsPerPreuAscendent();

    //mètodes extres
    public void deleteUsuari(String id);
    public Item addItem(Item i);
    public Item addItem(String color, int preu);
    public Item getItem(String color);
    public List<Item> findAll();
    public int size();

}
