package com.example.pierre.testbdd4.Objects;


public class Link {
    private int id;
    private int idComp;
    private int idPeop;
    private int compt;
    private int idRedu;

    public Link() {}

    public Link(int idComp, int idPeop, int compt, int idRedu){
        this.idComp = idComp;
        this.idPeop = idPeop;
        this.compt = compt;
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

    public int getCompt() {
        return compt;
    }

    public void setCompt(int idCompt) {
        this.compt = compt;
    }

    public int getIdRedu() {
        return idRedu;
    }

    public void setIdRedu(int idRedu) {
        this.idRedu = idRedu;
    }
}
