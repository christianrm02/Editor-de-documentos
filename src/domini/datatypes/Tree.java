package datatypes;
import java.util.*;

public class Tree {

    private TreeNode root;

    public Tree() {
        this.root = null;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public TreeNode getLeftNode() {
        return root.leftNode;
    }

    public TreeNode getRightNode() {
        return root.rightNode;
    }

    public void insert(String data) {
        if (root == null) root = new TreeNode(data);
        else {
            //insertar al lloc corresponen
        }
    }

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
    }

    public List<String> getTreeInOrder() {
        List<String> seq = new ArrayList<String>();
    }

    private void InOrder(TreeNode node, List<String> seq) {
        if (node == null) return;
        else {
            InOrder(node.leftNode,seq);
            seq.add(node.data);
            InOrder(node.rightNode,seq);
        }
    }

}

public class TreeNode {
    public String data;
    public TreeNode leftNode;
    public TreeNode rightNode;

    public TreeNode(String data) {
        this.data = data;
        this.leftNode = null;
        this.rightNode = null;
    }

    /*public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public TreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(TreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public TreeNode getRightNode() {
        return rightNode;
    }
    public void setRightNode(TreeNode rightNode) {
        this.rightNode = rightNode;
    }*/
}