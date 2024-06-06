package edu.upc.dsa.models;

public class Formulari {
    String id;
    String data;
    String title;
    String message;
    String sender;

    public Formulari (){}
    public Formulari(String data, String title, String message, String sender){
        this.data = data;
        this.title = title;
        this.message = message;
        this.sender = sender;
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id=id;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data=data;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title=title;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message=message;
    }
    public String getSender() {
        return this.sender;
    }
    public void setSender(String sender) {
        this.sender=sender;
    }
}
