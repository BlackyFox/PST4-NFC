package com.example.pierre.testbdd4.Objects;


public class People {
    private int id;
    private String username;
    private String password;
    private String name;
    private String first_name;
    private String sexe;
    private String date_of_birth;
    private String role;

    public People() {}

    public People(String username, String password, String name, String first_name, String sexe, String date_of_birth, String role){
        this.username = username;
        this.password = password;
        this.name = name;
        this.first_name = first_name;
        this.sexe = sexe;
        this.date_of_birth = date_of_birth;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
