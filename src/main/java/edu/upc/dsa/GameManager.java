package edu.upc.dsa;

import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Botiga;
import edu.upc.dsa.models.Usuari;


import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.List;

public interface GameManager {

    public Usuari obtenirUsuariPerNomusuari(String nomUsuari);
    public void registreUsuari(String nom, String cognom, String nomusuari, String password) throws UserAlreadyExistsException;
    public List<Usuari> llistaUsuaris();
    public boolean usuariExisteix(String nomUsuari);
    void login(String nomusuari, String password) throws UserNotFoundException, IncorrectPasswordException;
    public List<Botiga> llistarItemsPerPreuAscendent();

    //mètodes extres
    public void deleteUsuari(String id);
    public Botiga addItem(Botiga i);
    public Botiga addItem(String color, int preu, String descripcio, BufferedImage imatge);
    public Botiga getItem(String color, int preu, String descripcio, BufferedImage imatge);
    public List<Botiga> findAll();
    public int size();

}

