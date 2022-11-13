package datatypes;

/**
 * @author Pol Fradera
 */
public class TreeNode {
    public String data;
    public TreeNode leftNode;
    public TreeNode rightNode;

    public TreeNode(String s) {
        this.leftNode = null;
        this.rightNode = null;
        this.data = s;
    }
}
