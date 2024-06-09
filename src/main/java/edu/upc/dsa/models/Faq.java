package edu.upc.dsa.models;

public class Faq {
    private String f;
    private String q;

    private String id;

    // Constructor que acepta dos argumentos: f para la pregunta y q para la respuesta
    public Faq(String f, String q) {
        this.f = f;
        this.q = q;
    }

    // Getters y setters
    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }


    // Constructor, getters y setters para otros atributos

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

