package domini.datatypes;
import java.util.*;
import excepcions.*;

/**
 * @author Pol Fradera
 */

public class Tree {

    public TreeNode root;

    //Constructora
    public Tree(List<String> exp) throws ExpBoolNoValidaException {
        List<String> llista;
        llista = infixToPost(exp);
        root = expressionTree(llista);
    }

    //Retorna true si s és un operador lògic
    private boolean isOperator(String s) {
        return s.length() == 1 && (s.equals("&") || s.equals("|") || s.equals("!") || s.equals("(") || s.equals(")"));
    }

    //Retorna l'arrel de l'arbre que es crea a partir de la llista en notació postfix
    private TreeNode expressionTree(List<String> postfix) throws ExpBoolNoValidaException {
        Stack<TreeNode> st = new Stack<>();
        TreeNode t1 = null;
        TreeNode t2, node;
        try {
            for (String s : postfix) {
                if (!isOperator(s)) {
                    node = new TreeNode(s);
                    st.push(node);
                } else {
                    node = new TreeNode(s);

                    if (!s.equals("!")) t1 = st.pop();
                    t2 = st.pop();

                    node.leftNode = t2;
                    if (!s.equals("!")) node.rightNode = t1;

                    st.push(node);
                }
            }
            node = st.pop();
            if (!st.empty()) throw new ExpBoolNoValidaException(); //falten operadors
            return node;
        }
        catch (EmptyStackException e) {
            throw new ExpBoolNoValidaException(); //falten operands
        }
    }

    //Retorna la prioritat de l'operador
    private int priority(String s) {
        if (s.equals("|")) {
            return 1;               //Precedence of | 1
        }
        else if (s.equals("&")) {
            return 2;               //Precedence of & is 2
        }
        else if (s.equals("!")) {
            return 3;               //Precedence of ! is 3
        }
        else if (s.equals("(")) {
            return 0;
        }
        return -1;
    }

    //Retorna una llista convertida de infix a postfix
    private List<String> infixToPost(List<String> infix) {
        Stack<String> st = new Stack<>();
        st.push("#"); //per mirar quan la pila és buida

        List<String> postfix = new ArrayList<>();

        for (String s : infix) {
            if (!isOperator(s)) {
                postfix.add(s);
            }
            else if (s.equals("(")) {
                st.push(s);
            }
            else if (s.equals(")")) {
                while (!st.peek().equals("#") && !st.peek().equals("(")) {
                    postfix.add(st.peek());
                    st.pop();
                }
                st.pop();
            }
            else {
                if (priority(s) > priority(st.peek())) st.push(s);
                else {
                    while (!st.peek().equals("#") && priority(s) <= priority(st.peek())) {
                        postfix.add(st.peek());
                        st.pop();
                    }
                    st.push(s);
                }
            }
        }

        while(!st.peek().equals("#")) {
            postfix.add(st.peek());        //afegir els nodes fins que la pila quedi buida
            st.pop();
        }
        return postfix;
    }
/*
    public static void InOrder(TreeNode arrel) {
        if (arrel == null) return;
        else {
            InOrder(arrel.leftNode);
            System.out.print(arrel.data+", ");
            InOrder(arrel.rightNode);
        }
    }

    public static void PostOrder(TreeNode arrel) {
        if (arrel == null) return;
        else {
            PostOrder(arrel.leftNode);
            PostOrder(arrel.rightNode);
            System.out.print(arrel.data+", ");
        }
    }*/
}
