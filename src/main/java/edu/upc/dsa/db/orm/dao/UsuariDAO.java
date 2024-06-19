
package edu.upc.dsa.db.orm.dao;

import edu.upc.dsa.exception.*;
import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.models.UsuariLogin;

import java.util.List;

public interface UsuariDAO {

    public void addUsuari(String nom, String cognom, String nomusuari, String password, String password2, int coins) throws MissingDataException, IncorrectPasswordException, UserAlreadyExistsException;
    public UsuariLogin loginUsuari(String nomusuari, String password) throws MissingDataException, UserNotFoundException, IncorrectPasswordException;

    public Usuari getUsuari(String nomusuari) throws UserNotFoundException;
    public List<Usuari> llistaUsuarisDAO();
    public Item getItem(String color) throws ItemNotFoundException;
    public boolean dinersSuficient(String nomUsuari, String itemColor);
    public Usuari comprarItem(String nomUsuari, String itemColor) throws MissingDataException, UserNotFoundException, ItemNotFoundException;
}
    /*
    public void updateUsuari(int employeeID, String name, String surname, double salary);
    public void deleteUsuari(int employeeID);

    public List<Usuari> getUsuaris();
    public List <Usuari> getUsuariByDept(int deptId);
*/