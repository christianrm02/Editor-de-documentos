package datatypes;
import java.util.*;

public class Tree<String> {
    private Node<String> arrel;

    public Tree(String arrelValor) {
        arrel = new Node<String>();
        arrel.valor = arrelValor;
        arrel.fills = new ArrayList<Node<String>>();
    }

    public static class Node<String> {
        private String valor;
        private Node<String> pare;
        private List<Node<String>> fills;
    }
}