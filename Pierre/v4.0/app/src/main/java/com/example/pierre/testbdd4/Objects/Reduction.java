package com.example.pierre.testbdd4.Objects;


public class Reduction {
    private int id;
    private String type;

    public Reduction() {}

    public Reduction(String type){
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
