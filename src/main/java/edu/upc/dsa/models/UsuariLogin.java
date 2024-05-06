package edu.upc.dsa.models;

public class UsuariLogin {
    String nomusuari;
    String password;

    public UsuariLogin(){}

    public UsuariLogin (String nomusuari, String password){
        this.nomusuari = nomusuari;
        this.password = password;
    }

    public String getNomusuari(){
        return nomusuari;
    }

    public void setNomusuari(String nomusuari){
        this.nomusuari = nomusuari;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
