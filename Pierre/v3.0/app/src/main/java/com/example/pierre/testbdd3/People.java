package com.example.pierre.testbdd3;


public class People {
    private int id;
    private String name;
    private String first_name;
    private String sexe;
    private String date_of_birth;

    public People() {}

    public People(String name, String first_name, String sexe, String date_of_birth){
        this.name = name;
        this.first_name = first_name;
        this.sexe = sexe;
        this.date_of_birth = date_of_birth;
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

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
}
