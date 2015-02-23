package objectsPackage;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Objet "company", caractériqtiques d'une entreprise.                                            */
/**************************************************************************************************/


import java.util.Calendar;
import java.util.TimeZone;

public class Company {
    private int id; // ID unique en ligne et en interne, clé
    private String name; // Nom unique
    private String logo; // Chemin de l'image du logo
    private String card; // Chemin de l'image de la carte
    private String up_date; // Date de la dernière synchronisation

    public Company() {}

    public Company(int id, String name, String logo, String card) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.card = card;
        this.up_date = Calendar.getInstance(TimeZone.getDefault()).toString();
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getUp_date() {
        return up_date;
    }

    public void setUp_date(String up_date) {
        this.up_date = up_date;
    }
}
