package datatypes;

public class Pair {
    private String x; //autor, nomEB
    private String y; //titol, expEB

    public Pair(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {}

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }
}