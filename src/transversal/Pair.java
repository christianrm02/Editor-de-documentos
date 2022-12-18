package transversal;

import java.io.Serializable;
import java.util.Objects;

/**
 * Pair, classe que unifica 2 objectes.
 * @author Christian Rivero
 */
public class Pair<T1, T2> implements Comparable, Serializable{
    public T1 x;
    public T2 y;

    /**
     * Creadora que inicialitza els paràmetres.
     */
    public Pair(T1 x, T2 y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creadora bàsica.
     */
    public Pair() {}

    /**
     * Mètode override de la funció equals per defecte. Retorna true si dos objectes pairs són iguals,
     * i fals si no ho són.
     */
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

    /**
     * Mètode override de la funció compareTo per defecte. Retorna 1 si el paràmetre implícit
     * és més petit que el paràmetre explícit, 0 si són iguals i -1 si és més gran. És més petit si
     * té un ‘x’ més petit, i si són iguals, si té un ‘y’ més petit.
     */
    @Override
    public int compareTo(Object o) {
        if(hashCode() < o.hashCode()) return -1;
        if(hashCode() == o.hashCode()) return 0;
        else return 1;
    }

    /**
     * Mètode override de la funció hashCode per defecte. Retorna el hashCode dels objectes ‘x’ i ‘y’.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}