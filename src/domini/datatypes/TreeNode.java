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

/*
    public TreeNode construir_arbre(List<String> exp) {
        Map<String, Integer> prio = new HashMap<>();

        prio.put("(", 1);
        prio.put("|", 2);
        prio.put("&", 3);
        prio.put("!", 4);

        Deque<String> ops = new ArrayDeque<>();
        Deque<TreeNode> stack = new ArrayDeque<>();

        for (int i = 0; i < exp.size(); i++) {
            String s = exp.get(i);
            if (s == "(") ops.push(s);
            else if (s == ")") {
                while (ops.peek() != "(") {
                    combine(ops, stack);
                }
                // pop left "("
                ops.pop();
            }

            else if (s == "&" || s == "!" || s == "|"){
                while (!ops.isEmpty() && prio.get(ops.peek()) >= prio.get(s)) { //
                    combine(ops, stack);
                }

                ops.push(s);
            }
            else stack.push(new TreeNode(s));
        }

        while (stack.size() > 1) {
            combine(ops, stack);
        }

        return stack.peek();
    }

        private void combine(Deque<String> ops, Deque<TreeNode> stack) {
            TreeNode root = new TreeNode(ops.pop());
            // right first, then left
            root.rightNode = stack.pop();
            root.leftNode = stack.pop();

            stack.push(root);
        }
    }*/

    private void InOrder(TreeNode node) {
        if (node == null) return;
        else {
            InOrder(node.leftNode);
            System.out.println(node.data);
            InOrder(node.rightNode);
        }
    }

}