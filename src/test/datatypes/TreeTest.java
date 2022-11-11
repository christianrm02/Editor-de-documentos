package datatypes;

import org.junit.Test;

import java.util.*;

import static datatypes.Tree.InOrder;
import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void expressionTree() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("|");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("|");
        list.add("&");
        list.add("&");
        //list.add("h");
        //list.add("&");
        Tree tree = new Tree(list);
        InOrder(tree.root);
    }

    @Test
    public void inToPost() {
    }
}