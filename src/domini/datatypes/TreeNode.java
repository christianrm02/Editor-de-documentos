package datatypes;
import java.util.*;

public class TreeNode {
    public String data;
    public TreeNode leftNode;
    public TreeNode rightNode;

    public TreeNode() {
        this.leftNode = null;
        this.rightNode = null;
    }

    public TreeNode(String s) {
        this.leftNode = null;
        this.rightNode = null;
        this.data = s;
    }

    private boolean isOperator(String s) {
        return s.length() == 1 && (s.equals("&") || s.equals("|") || s.equals("!"));
    }

    public  TreeNode expressionTree(List<String> postfix){
        Stack<TreeNode> st = new Stack<TreeNode>();
        TreeNode t1,t2,temp;

        for (String s : postfix) {
            if(!isOperator(s)){
                temp = new TreeNode(s);
                st.push(temp);
            }
            else{
                temp = new TreeNode(s);

                t1 = st.pop();
                t2 = st.pop();

                temp.leftNode = t2;
                temp.rightNode = t1;

                st.push(temp);
            }

        }
        temp = st.pop();
        return temp;
    }

    private int preced(String s) {
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

    public List<String> inToPost(List<String> infix) {
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



    private void InOrder(TreeNode node) {
        if (node == null) return;
        else {
            InOrder(node.leftNode);
            System.out.println(node.data);
            InOrder(node.rightNode);
        }
    }

}