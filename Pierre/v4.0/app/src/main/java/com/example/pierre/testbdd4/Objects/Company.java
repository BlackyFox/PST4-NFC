package com.example.pierre.testbdd4.Objects;


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
