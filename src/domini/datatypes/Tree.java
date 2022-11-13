package datatypes;
import java.util.*;

public class Tree {

    public TreeNode root;
    public Tree(List<String> exp) {
        List<String> llista;
        llista = infixToPost(exp);
        root = expressionTree(llista);
    }

    private boolean isOperator(String s) {
        return s.length() == 1 && (s.equals("&") || s.equals("|") || s.equals("!") || s.equals("(") || s.equals(")"));
    }

    private TreeNode expressionTree(List<String> postfix){
        Stack<TreeNode> st = new Stack<>();
        TreeNode t1 = null;
        TreeNode t2, operator;

        for (String s : postfix) {
            if (!isOperator(s)){
                operator = new TreeNode(s);
                st.push(operator);
            }
            else {
                operator = new TreeNode(s);
                if (!s.equals("!")) t1 = st.pop();
                t2 = st.pop();
                //System.out.println("pare: " + s + " left: " + t2.data + " right: " + t1.data);

                operator.leftNode = t2;
                if (!s.equals("!")) operator.rightNode = t1;

                st.push(operator);
            }
        }
        operator = st.pop();
        return operator;
    }

    private int priority(String s) {
        if (s.equals("|")) {
            return 1;              //Precedence of | 1
        }
        else if (s.equals("&")) {
            return 2;            //Precedence of & is 2
        }
        else if (s.equals("!")) {
            return 3;            //Precedence of ! is 3
        }
        else if (s.equals("(")) {
            return 0;
        }
        return -1;
    }

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
    }
}
