package com.example.pierre.applicompanies.objectsPackage;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Objet "Reduction", caractéristiques de la réduction.                                           */
/**************************************************************************************************/


import java.util.Calendar;
import java.util.TimeZone;

public class Reduction {
    private int id; // ID unique en ligne et en interne, clé
    private String name; // Nom de la réduction
    private String description; // Texte de description de la réduction
    // Conditions d'éligibilité :
    private String sexe; // Sexe : "M" (Man), "W" (Woman), "A" (All)
    private String age_relation; // ">", "<", "=", "A"
    private int age_value; // 0 si age_relation = "A"
    private String nb_points_relation; // ">", "<", "=", "A"
    private int nb_points_value; // 0 si nb_points_relation = "A"
    private String city; // Ville
    private String up_date; // Date de la dernière synchronisation


    public Reduction() {}

    public Reduction(int id, String name, String description, String sexe, String age_relation, int age_value, String nb_points_relation, int nb_points_value, String city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sexe = sexe;
        this.age_relation = age_relation;
        this.age_value = age_value;
        this.nb_points_relation = nb_points_relation;
        this.nb_points_value = nb_points_value;
        this.city = city;
        this.up_date = Calendar.getInstance(TimeZone.getDefault()).getTime().toString();
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

    public String getUp_date() {
        return up_date;
    }

    public void setUp_date(String up_date) {
        this.up_date = up_date;
    }
}
