package com.example.pierre.testbdd3;

/**
 * Created by Pierre on 15/01/2015.
 */
public class Company {
    private int id;
    private String name;

    public Company() {}

    public Company(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
