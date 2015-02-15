package objects;


import java.util.Calendar;
import java.util.TimeZone;

public class Company {
    private int id;
    private String name;
    private String logo;
    private String card;
    private String up_date;

    public Company() {}

    public Company(String name, String logo, String card) {
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
