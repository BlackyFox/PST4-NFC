package com.example.pierre.applicompanies.objectsPackage;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Objet "Opportunity", lie un client à une réduction.                                            */
/**************************************************************************************************/


import java.util.Calendar;
import java.util.TimeZone;

public class Opportunity {
    private int id; // ID de l'opportunity, non lié à l'ID en ligne car calculé en local
    private int id_client; // ID du client
    private int id_redu; // ID de la réduction
    private String up_date; // Date de la dernière synchronisation

    public Opportunity(int id_client, int id_redu) {
        this.id_client = id_client;
        this.id_redu = id_redu;
        this.up_date = Calendar.getInstance(TimeZone.getDefault()).toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return this.id_client;
    }

    public int getId_redu() {
        return id_redu;
    }

    public String getUp_date() {
        return up_date;
    }

    public void setUp_date(String up_date) {
        this.up_date = up_date;
    }
}
