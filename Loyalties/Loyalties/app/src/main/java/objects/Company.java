package objects;


public class Company {
    private int id;
    private String name;
    private String logo;
    private String card;

    public Company() {}

    public Company(String name, String logo, String card) {
        this.name = name;
        this.logo = logo;
        this.card = card;
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
}
