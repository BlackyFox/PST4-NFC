package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

/**
 * Created by Pierre on 13/02/2015.
 */
public class RowItem {

    private String name;
    private int logo;

    public RowItem(String name, int logo) {
        this.name = name;
        this.logo = logo;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
