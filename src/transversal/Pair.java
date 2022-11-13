package transversal;

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
}