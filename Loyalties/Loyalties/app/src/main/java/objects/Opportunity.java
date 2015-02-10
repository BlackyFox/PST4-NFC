package objects;


public class Opportunity {
    private int id;
    private int id_client;
    private int id_redu;

    public Opportunity() {}

    public Opportunity(int id_client, int id_redu) {
        this.id_client = id_client;
        this.id_redu = id_redu;
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
}
