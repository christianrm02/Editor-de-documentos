package datatypes;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressioBooleanaTest {


    /**
     * Objecte de la prova: Es prova l'operació getNom() de la classe ExpressioBooleana.
     * Altres elements integrats a la prova: -
     * Fitxers de dades necessaris: No calen fitxers de dades.
     * Valors estudiats: Es fa servir l'estratègia de caixa grisa. Com és una funcionalitat bàsica, el test també ho és.
     * Efectes estudiats: -
     * Operativa: Una vegada comprovat que la creadora funciona bé, el testeig d'aquest mètode és immediat.
     */
    @Test
    public void getNom() {
        ExpressioBooleana expTest = new ExpressioBooleana("Expressió 1", "{p1 p2 p3} & (\"hola adéu\" | pep) & !joan");
        assertEquals("Expressió 1", expTest.getNom());
    }

    @Test
    public void getExp() {
        ExpressioBooleana expTest = new ExpressioBooleana("Expressió 1", "{p1 p2 p3} & (\"hola adéu\" | pep) & !joan");
        assertEquals("{p1 p2 p3} & (\"hola adéu\" | pep) & !joan", expTest.getExp());
    }

    @Test
    public void getExpA() {
    }
}