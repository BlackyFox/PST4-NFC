package objects;


import java.util.Calendar;
import java.util.TimeZone;

public class Client {
    private int id;
    private int id_peop;
    private int id_comp;
    private int num_client;
    private int nb_loyalties;
    private int last_used;
    private String up_date;

    public Client() {}

    public Client(int id_peop, int id_comp, int num_client, int nb_loyalties, int last_used) {
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

    public int getNum_client() {
        return num_client;
    }

    public void setNum_client(int num_client) {
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
