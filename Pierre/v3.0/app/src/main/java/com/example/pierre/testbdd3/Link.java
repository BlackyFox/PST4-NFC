package com.example.pierre.testbdd3;


public class Link {
    private int id;
    private int idComp;
    private int idPeop;
    private int idRedu;

    public Link() {}

    public Link(int idComp, int idPeop, int idRedu){
        this.idComp = idComp;
        this.idPeop = idPeop;
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

    public int getIdPeop() {
        return idPeop;
    }

    public void setIdPeop(int idPeop) {
        this.idPeop = idPeop;
    }

    public int getIdRedu() {
        return idRedu;
    }

    public void setIdRedu(int idRedu) {
        this.idRedu = idRedu;
    }
}
