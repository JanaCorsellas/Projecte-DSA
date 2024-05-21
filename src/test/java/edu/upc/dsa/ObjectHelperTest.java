package edu.upc.dsa;

import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.util.ObjectHelper;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ObjectHelperTest {


    @Test
    public void setterTest() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Usuari e = new Usuari("nomtest", "cognomtest", "nomusuartest","pwdtest","pwd2test");

        e.setNom("Peopito");
        ObjectHelper.setter(e, "name", "Pepito");
        ObjectHelper.setter(e, "surname", "Grillo");

        Assert.assertEquals("Pepito", e.getNom());
        Assert.assertEquals("Grillo", e.getCognom());

    }


    @Test
    public void getterTest() throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Usuari e = new Usuari("nomtest", "cognomtest", "nomusuartest","pwdtest","pwd2test");


        String userNom = (String) ObjectHelper.getter(e, "name");
        String userCognom = (String) ObjectHelper.getter(e, "surname");


        Assert.assertEquals("Pepito", userNom);
        Assert.assertEquals("Grillo", userCognom);

    }

}
