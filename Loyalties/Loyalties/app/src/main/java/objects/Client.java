package objects;


public class Client {
    private int id;
    private int idPeop;
    private int idComp;
    private int points;

    public Client() {}

    public Client(int idPeop, int idComp, int points) {
        this.idPeop = idPeop;
        this.idComp = idComp;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPeop() {
        return idPeop;
    }

    public void setIdPeop(int idPeop) {
        this.idPeop = idPeop;
    }

    public int getIdComp() {
        return idComp;
    }

    public void setIdComp(int idComp) {
        this.idComp = idComp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
