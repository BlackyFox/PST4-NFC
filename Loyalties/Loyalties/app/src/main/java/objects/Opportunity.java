package objects;


import java.util.Calendar;
import java.util.TimeZone;

public class Opportunity {
    private int id;
    private int id_client;
    private int id_redu;
    private String up_date;

    public Opportunity() {}

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

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_redu() {
        return id_redu;
    }

    public void setId_redu(int id_redu) {
        this.id_redu = id_redu;
    }

    public String getUp_date() {
        return up_date;
    }

    public void setUp_date(String up_date) {
        this.up_date = up_date;
    }
}
