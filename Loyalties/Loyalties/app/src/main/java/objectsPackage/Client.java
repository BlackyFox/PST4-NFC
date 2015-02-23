package objectsPackage;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Objet "client", lie un utilisateur à une entreprise.                                           */
/**************************************************************************************************/


import java.util.Calendar;
import java.util.TimeZone;

public class Client {
    private int id; // ID unique en ligne et en interne, clé
    private int id_peop; // ID de l'utilisateur
    private int id_comp; // ID de l'entreprise
    private String num_client; // Numéro du client, unique par entreprise
    private int nb_loyalties; // Nombre de points de fidélité
    private int last_used; // Ancienneté (dernière, avant-dernière ou avant-avant-dernière carte utilisée)
    private String up_date; // Date de la dernière synchronisation

    public Client() {}

    public Client(int id, int id_peop, int id_comp, String num_client, int nb_loyalties, int last_used) {
        this.id = id;
        this.id_peop = id_peop;
        this.id_comp = id_comp;
        this.num_client = num_client;
        this.nb_loyalties = nb_loyalties;
        this.last_used = last_used;
        this.up_date = Calendar.getInstance(TimeZone.getDefault()).toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_peop() {
        return id_peop;
    }

    public void setId_peop(int id_peop) {
        this.id_peop = id_peop;
    }

    public int getId_comp() {
        return id_comp;
    }

    public void setId_comp(int id_comp) {
        this.id_comp = id_comp;
    }

    public String getNum_client() {
        return num_client;
    }

    public void setNum_client(String num_client) {
        this.num_client = num_client;
    }

    public int getNb_loyalties() {
        return nb_loyalties;
    }

    public void setNb_loyalties(int nb_loyalties) {
        this.nb_loyalties = nb_loyalties;
    }

    public int getLast_used() {
        return last_used;
    }

    public void setLast_used(int last_used) {
        this.last_used = last_used;
    }

    public String getUp_date() {
        return up_date;
    }

    public void setUp_date(String update) {
        this.up_date = update;
    }
}
