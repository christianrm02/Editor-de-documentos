package datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * ContingutStub: Per fer el testing de les operacions getContingut i setContingut de la classe Document
 * @author Christian Rivero
 */
class ContingutStub extends Contingut {

    @Override
    public List<String> getFrases(){
        List<String> contingut = new ArrayList<>();
        contingut.add("En un lugar de la Mancha, de cuyo nombre no quiero acordarme.");
        return contingut;
    }
}