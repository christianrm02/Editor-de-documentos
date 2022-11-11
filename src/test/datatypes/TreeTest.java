package datatypes;

import org.junit.Test;

import java.util.*;

import static datatypes.Tree.*;
import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void expressionTree() {
        List<String> list = new ArrayList<>();
        list.add("(");
        list.add("!");
        list.add("a");
        list.add("|");
        list.add("b");
        list.add(")");
        list.add("&");
        list.add("(");
        list.add("c");
        list.add("&");
        list.add("(");
        list.add("d");
        list.add("|");
        list.add("e");
        list.add(")");
        list.add(")");

        Tree tree = new Tree(list);
        System.out.println();
        InOrder(tree.root);
        System.out.println();
        PostOrder(tree.root);
    }

    @Test
    public void inToPost() {
        List<String> list = new ArrayList<>();
        list.add("(");
        list.add("!");
        list.add("a");
        list.add("|");
        list.add("b");
        list.add(")");
        list.add("&");
        list.add("(");
        list.add("c");
        list.add("&");
        list.add("(");
        list.add("d");
        list.add("|");
        list.add("e");
        list.add(")");
        list.add(")");
        list = inToPost(list);
        System.out.println();
        for (String s : list) System.out.print(s +", ");
    }

    private static List<String> inToPost(List<String> infix) {
        Stack<String> stk = new Stack<>();
        stk.push("#"); //add some extra character to avoid underflow

        List<String> postfix = new ArrayList<>();

        for (String s : infix) {
            if (!isOperator(s)) {
                postfix.add(s);
                System.out.print("1r");
            }
            else if (s.equals("(")) {
                System.out.print("2n");
                stk.push(s);
            }
            else if (s.equals(")")) {
                System.out.print("3r");
                while (!stk.peek().equals("#") && !stk.peek().equals("(")) {
                    postfix.add(stk.peek());
                    stk.pop();
                }
                stk.pop();
            }
            else {
                System.out.print("4t");
                if (preced(s) > preced(stk.peek())) stk.push(s);
                else {
                    while (!stk.peek().equals("#") && preced(s) <= preced(stk.peek())) {
                        postfix.add(stk.peek());
                        stk.pop();
                    }
                    stk.push(s);
                }
            }
        }

        while(!stk.peek().equals("#")) {
            postfix.add(stk.peek());        //store and pop until stack is not empty.
            stk.pop();
        }
        return postfix;
    }
}