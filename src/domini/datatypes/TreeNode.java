package datatypes;

public class TreeNode {
    public String data;

    public TreeNode leftNode;
    public TreeNode rightNode;

    public TreeNode() {
        this.leftNode = null;
        this.rightNode = null;
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