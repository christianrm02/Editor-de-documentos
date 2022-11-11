package datatypes;
import java.util.*;

public class Tree {

    public TreeNode root;
    public Tree(List<String> exp) {
        List<String> llista;
        llista = inToPost(exp);
        root = expressionTree(llista);
    }

    public static boolean isOperator(String s) {
        return s.length() == 1 && (s.equals("&") || s.equals("|") || s.equals("!") || s.equals("(") || s.equals(")"));
    }

    public TreeNode expressionTree(List<String> postfix){
        Stack<TreeNode> st = new Stack<>();
        TreeNode t1 = null;
        TreeNode t2,temp;

        for (String s : postfix) {
            if (!isOperator(s)){
                temp = new TreeNode(s);
                st.push(temp);
            }
            else {
                temp = new TreeNode(s);
                if (!s.equals("!")) t1 = st.pop();
                t2 = st.pop();
                //System.out.println("pare: " + s + " left: " + t2.data + " right: " + t1.data);

                temp.leftNode = t2;
                if (!s.equals("!")) temp.rightNode = t1;

                st.push(temp);
            }
        }
        temp = st.pop();
        return temp;
    }

    public static int preced(String s) {
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

    public static List<String> inToPost(List<String> infix) {
        Stack<String> stk = new Stack<>();
        stk.push("#"); //add some extra character to avoid underflow

        List<String> postfix = new ArrayList<>();

        for (String s : infix) {
            if (!isOperator(s)) {
                postfix.add(s);
            }
            else if (s.equals("(")) {
                stk.push(s);
            }
            else if (s.equals(")")) {
                while (!stk.peek().equals("#") && !stk.peek().equals("(")) {
                    postfix.add(stk.peek());
                    stk.pop();
                }
                stk.pop();
            }
            else {
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
