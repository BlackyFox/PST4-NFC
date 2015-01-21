package com.example.pierre.testbdd4.Objects;


public class Opportunity {
    private int id;
    private int idClient;
    private int idRedu;

    public Opportunity() {}

    public Opportunity(int idClient, int idRedu) {
        this.idClient = idClient;
        this.idRedu = idRedu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return this.idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdRedu() {
        return idRedu;
    }

    public void setIdRedu(int idRedu) {
        this.idRedu = idRedu;
    }
}
