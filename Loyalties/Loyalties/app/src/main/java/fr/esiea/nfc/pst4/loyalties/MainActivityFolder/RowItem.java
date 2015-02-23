package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Objet "RowItem", caractérise l'affichage des items de ListItems (liste des cartes.             */
/**************************************************************************************************/

public class RowItem {

    private String name; // Nom de l'entreprise
    private int logo; // ID du logo (celui dans R.java)

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
