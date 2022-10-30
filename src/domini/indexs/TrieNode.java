package indexs;
import java.util.HashMap;

public class TrieNode {

    public HashMap<Character, TrieNode> children;
    public char data;
    public boolean isEndWord;

    public TrieNode(char data) {
        this.data = data;
    }

}
