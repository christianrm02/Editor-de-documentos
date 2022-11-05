package datatypes;
import java.util.*;

public class Tree {

    private TreeNode root;

    public Tree() {
        this.root = null;
    }

    public void insert(String data) {
        if (root == null) root = new TreeNode(data);
        else {
            //insertar al lloc corresponen
        }
    }

    public List<String> getTreeInOrder() {
        List<String> seq = new ArrayList<String>();

    }

    private void InOrder(TreeNode node, List<String> seq) {
        if (node == null) return;
        else {
            InOrder(node.getLeftNode());
            seq.add(node.getData());
            InOrder(node.getRightNode());
        }
    }

}

class TreeNode {
    private String data;
    private TreeNode leftNode;
    private TreeNode rightNode;

    public TreeNode(String data) {
        this.data = data;
        this.leftNode = null;
        this.rightNode = null;
    }

    public String getData() {
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
    }
}