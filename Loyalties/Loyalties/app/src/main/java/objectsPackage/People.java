package objectsPackage;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Objet "People", caractéristiques de l'utilisateur.                                             */
/**************************************************************************************************/


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class People {
    private int id; // ID unique en ligne et en interne, clé
    private String username; // Username de l'utilisateur
    private String password; // Mot de passe de l'utilisateur
    private String name; // Nom de l'utilisateur, stocké en lettres capitales
    private String first_name; // Prénom de l'utilisateur, première lettre majuscule
    private String sexe; // Sexe de l'utilisateur : M (Man), W (Woman)
    private String date_of_birth; // Date de naissance de l'utilsateur (YYYY-MM-DD)
    private String mail; // Mail de l'utilisateur
    private String city; // Ville de l'utilisateur
    private String up_date; // Date de la dernière synchronisation

    public People() {}

    public People(int id, String username, String password, String name, String first_name, String sexe, String date_of_birth, String mail, String city){
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.first_name = first_name;
        this.sexe = sexe;
        this.date_of_birth = date_of_birth;
        this.mail = mail;
        this.city = city;
        this.up_date = Calendar.getInstance(TimeZone.getDefault()).getTime().toString();
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int[] getSeparateDateOfBirth() {
        String[] parts = this.date_of_birth.split("-");
        int[] partsInt = new int[3];
        partsInt[0] = Integer.parseInt(parts[0]);
        partsInt[1] = Integer.parseInt(parts[1]);
        partsInt[2] = Integer.parseInt(parts[2]);

        return partsInt;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String composePeopleJSONfromSQLite() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();
        map.put("username", getUsername());
        map.put("password", getPassword());
        map.put("name", getName());
        map.put("first_name", getFirst_name());
        map.put("sexe", getSexe());
        map.put("date_of_birth", getDate_of_birth());
        map.put("mail", getMail());
        map.put("city", getCity());
        map.put("up_date", getUp_date());
        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }
}
