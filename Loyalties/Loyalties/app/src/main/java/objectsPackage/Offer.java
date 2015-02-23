package objectsPackage;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Objet "offer", lie une entreprise à une réduction.                                             */
/**************************************************************************************************/


import java.util.Calendar;
import java.util.TimeZone;

public class Offer {
    private int id; // ID unique en ligne et en interne, clé
    private int id_comp; // ID de l'entreprise
    private int id_redu; // ID de la réduction
    private String up_date; // Date de la dernière synchronisation

    public Offer(int id, int id_comp, int id_redu) {
        this.id = id;
        this.id_comp = id_comp;
        this.id_redu = id_redu;
        this.up_date = Calendar.getInstance(TimeZone.getDefault()).toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_comp() {
        return id_comp;
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
