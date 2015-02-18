package objects;


import java.util.Calendar;
import java.util.TimeZone;

public class Offer {
    private int id;
    private int id_comp;
    private int id_redu;
    private String up_date;

    public Offer() {}

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

    public void setId_comp(int id_comp) {
        this.id_comp = id_comp;
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
