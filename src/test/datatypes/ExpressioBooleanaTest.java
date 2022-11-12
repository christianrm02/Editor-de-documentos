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
    /*

                             &(0)
                      /                \
                    &(1)              !(2)
                /         \             /
               &(3)         |(4)     joan(5)
           /     \      /         \
         &(6)  p3(7) hola adéu(8) pep(9)
        /   \
   p1(10)  p2(11)
*/
    @Test
    public void getExpA() {
        ExpressioBooleana expTest = new ExpressioBooleana("Expressió 1", "{p1 p2 p3} & (\"hola adéu\" | pep) & !joan");
        Tree arbre = expTest.getExpA();

        TreeNode node0 = arbre.root;
        TreeNode node1 = node0.leftNode;
        TreeNode node2 = node0.rightNode;
        TreeNode node3 = node1.leftNode;
        TreeNode node4 = node1.rightNode;
        TreeNode node5 = node2.leftNode;
        TreeNode node6 = node3.leftNode;
        TreeNode node7 = node3.rightNode;
        TreeNode node8 = node4.leftNode;
        TreeNode node9 = node4.rightNode;
        TreeNode node10 = node6.leftNode;
        TreeNode node11 = node6.rightNode;

        assertEquals("&", node0.data);
        assertEquals("&", node1.data);
        assertEquals("!", node2.data);
        assertEquals("&", node3.data);
        assertEquals("|", node4.data);
        assertEquals("joan", node5.data);
        assertEquals("&", node6.data);
        assertEquals("p3", node7.data);
        assertEquals("hola adéu", node8.data);
        assertEquals("pep", node9.data);
        assertEquals("p1", node10.data);
        assertEquals("p2", node11.data);

    }
}