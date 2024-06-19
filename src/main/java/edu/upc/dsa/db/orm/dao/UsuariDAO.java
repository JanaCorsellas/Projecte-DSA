
package edu.upc.dsa.db.orm.dao;

import edu.upc.dsa.exception.IncorrectPasswordException;
import edu.upc.dsa.exception.MissingDataException;
import edu.upc.dsa.exception.UserAlreadyExistsException;
import edu.upc.dsa.exception.UserNotFoundException;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.models.UsuariLogin;

import java.util.List;

public interface UsuariDAO {

    public void addUsuari(String nom, String cognom, String nomusuari, String password, String password2, int coins) throws MissingDataException, IncorrectPasswordException, UserAlreadyExistsException;
    public UsuariLogin loginUsuari(String nomusuari, String password) throws MissingDataException, UserNotFoundException, IncorrectPasswordException;

    public Usuari getUsuari(String nomusuari) throws UserNotFoundException;
    public List<Usuari> llistaUsuarisDAO();
    public Item getItem(String color) throws ItemNotFoundException;
}
    /*
    public void updateUsuari(int employeeID, String name, String surname, double salary);
    public void deleteUsuari(int employeeID);

    public List<Usuari> getUsuaris();
    public List <Usuari> getUsuariByDept(int deptId);
*/