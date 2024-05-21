
package edu.upc.dsa.db.orm.dao;

import edu.upc.dsa.models.Usuari;

import java.util.List;

public interface UsuariDAO {

    public int addUsuari(String nom, String cognom, String nomusuari, String password, String password2);
    public Usuari getUsuari(int usuariID);
    public void updateUsuari(int usuariID, String name, String surname, double salary);
    public void deleteUsuari(int usuariID);
    public List<Usuari> getUsuaris();
    public List <Usuari> getUsuariByDept(int deptId);
}
