package edu.upc.dsa.models;

public class UsuariLogin {
    String nomusuari;
    String contrasenya;

    public UsuariLogin(){}

    public UsuariLogin (String nomusuari, String contrasenya){
        this.nomusuari = nomusuari;
        this.contrasenya = contrasenya;
    }

    public String getNomusuari(){
        return nomusuari;
    }

    public void setNomusuari(String nomusuari){
        this.nomusuari = nomusuari;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
}
