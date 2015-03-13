package com.example.pierre.applicompanies;

public class OfferRowItem {

    private String reduction;
    private int pos;

    public OfferRowItem(String reduction, int pos) {
        this.reduction = reduction;
        this.pos = pos;
    }

    public String getReduction() {
        return reduction;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
