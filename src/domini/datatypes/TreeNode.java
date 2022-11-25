package datatypes;

/**
 * @author Pol Fradera
 */
public class TreeNode {

    public String data;
    public TreeNode leftNode;
    public TreeNode rightNode;

    //Constructora
    public TreeNode(String s) {
        this.data = s;
        this.leftNode = null;
        this.rightNode = null;
    }
}
