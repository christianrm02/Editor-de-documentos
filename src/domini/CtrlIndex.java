import java.util.List;
import indexs.*;


class CtrlIndex {

    IndexAutorPrefix indexAutorPrefix;

    public CtrlIndex() {
        indexAutorPrefix = new IndexAutorPrefix();
    }

    public List<String> GetAutorsPrefix(String prefix) {
        return indexAutorPrefix.getAutorsPrefix(prefix);
    }

}