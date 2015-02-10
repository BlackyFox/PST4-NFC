package objects;


public class Offer {
    private int id;
    private int id_comp;
    private int id_redu;

    public Offer() {}

    public Offer(int id_comp, int id_redu) {
        this.id_comp = id_comp;
        this.id_redu = id_redu;
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
}
