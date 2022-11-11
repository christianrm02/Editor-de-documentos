package datatypes;

import java.util.Objects;

public class Pair<T1, T2> implements Comparable{
    public T1 x;
    public T2 y;

    public Pair(T1 x, T2 y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) obj;
        return x.equals(pair.x) && y.equals(pair.y);
    }

    @Override
    public int compareTo(Object o) {
        if(hashCode() < o.hashCode()) return -1;
        if(hashCode() == o.hashCode()) return 0;
        else return 1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

/*package datatypes;

public class Pair<T1 extends Comparable<T1>, T2 extends Comparable<T2>>
        implements Comparable<Pair<T1, T2>> {
    public T1 x;
    public T2 y;

    @Override
    public int hashCode() {
        //return super.hashCode();
        return 31 * x.hashCode() + y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) obj;
        // call `equals()` method of the underlying objects
        if (!x.equals(pair.x)) {
            return false;
        }
        return y.equals(pair.y);
    }

    public Pair(T1 x, T2 y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {}

    @Override
    public int compareTo(Pair<T1, T2> o) {
        int retVal = x.compareTo(o.x);
        if (retVal != 0) {
            return retVal;
        }
        return y.compareTo(o.y);
    }*/

    /*public String getX() {
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
    }*/
}