package com.example.pierre.testbdd4.Objects;


public class Offer {
    private int id;
    private int idComp;
    private int idRedu;

    public Offer() {}

    public Offer(int idComp, int idRedu) {
        this.idComp = idComp;
        this.idRedu = idRedu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdComp() {
        return idComp;
    }

    public void setIdComp(int idComp) {
        this.idComp = idComp;
    }

    public int getIdRedu() {
        return idRedu;
    }

    public void setIdRedu(int idRedu) {
        this.idRedu = idRedu;
    }
}
