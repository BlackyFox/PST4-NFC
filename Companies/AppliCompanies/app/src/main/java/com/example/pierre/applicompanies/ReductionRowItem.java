package com.example.pierre.applicompanies;

public class ReductionRowItem {

    private String name;
    private String description;
    private String sexe;
    private String age_relation;
    private int age_value;
    private String nb_points_relation;
    private int nb_points_value;
    private String city;

    public ReductionRowItem(String name, String description, String sexe, String age_relation, int age_value, String nb_points_relation, int nb_points_value, String city) {
        this.name = name;
        this.description = description;
        this.sexe = sexe;
        this.age_relation = age_relation;
        this.age_value = age_value;
        this.nb_points_relation = nb_points_relation;
        this.nb_points_value = nb_points_value;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAge_relation() {
        return age_relation;
    }

    public void setAge_relation(String age_relation) {
        this.age_relation = age_relation;
    }

    public int getAge_value() {
        return age_value;
    }

    public void setAge_value(int age_value) {
        this.age_value = age_value;
    }

    public String getNb_points_relation() {
        return nb_points_relation;
    }

    public void setNb_points_relation(String nb_points_relation) {
        this.nb_points_relation = nb_points_relation;
    }

    public int getNb_points_value() {
        return nb_points_value;
    }

    public void setNb_points_value(int nb_points_value) {
        this.nb_points_value = nb_points_value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
