package edu.upc.dsa;

import edu.upc.dsa.exception.*;
import edu.upc.dsa.models.Formulari;
import edu.upc.dsa.models.Issue;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.models.Faq;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface GameManager {

    public Usuari obtenirUsuariPerNomusuari(String nomUsuari);
    public void registreUsuari(String nom, String cognom, String nomusuari, String password, String password2) throws UserAlreadyExistsException, IncorrectPasswordException, MissingDataException, SQLException;
    public List<Usuari> llistaUsuaris();
    public boolean usuariExisteix(String nomUsuari);
    void login(String nomusuari, String password) throws UserNotFoundException, IncorrectPasswordException, MissingDataException;
    public Item obtenirItemPerColor(String color);
    public void addItem(String color, int preu, String descripcio, String imatge) throws MissingDataException, ItemAlreadyExistsException;
    public void delItem(String color) throws MissingDataException, ItemNotFoundException;
    public List<Item> findAll();
    public int size();
    public void formulari(String data, String title, String message, String sender) throws MissingDataException;
    public List<Formulari> llistaFormularis();
    public void addIssue(String date, String informer, String message) throws MissingDataException;
    public List<Issue> llistaIssues();
    List<Faq> llistaFaqs();
    public void addFaq(Faq faq);
    public void removeFaq(String faqId);
    //mètodes extres
    public void baixaUsuari(String nomusuari) throws UserNotFoundException, SQLException;
    public Item getItem(String color, int preu, String descripcio, String imatge);

    public void addRanking(String nomUsuari, int puntuacio, Date data);

    public List<Usuari> ranking();

}

