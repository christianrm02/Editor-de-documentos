package test.datatypes;

import excepcions.ExpBoolNoValidaException;
import org.junit.Test;

import domini.datatypes.Tree;
import domini.datatypes.TreeNode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * TreeTest: Per fer el testing de la classe Tree
 * @author Pol Fradera
 */

public class TreeTest {

    /**
     * Objectes de la prova: Es prova les constructora de la classe Tree.
     * Altres elements integrats de la prova: -
     * Fitxers de dades necessaris: -
     * Valors estudiats: Es fa servir l’estratègia de caixa blanca. Es comprova que funciona correctament la creadora.
     * Efectes estudiats: -
     * Operativa: Executar el jUnit test.
     */
    @Test
    public void testConstructora() throws ExpBoolNoValidaException {
        List<String> exp = new ArrayList<>();
        exp.add("(");
        exp.add("p1");
        exp.add("&");
        exp.add("p2");
        exp.add("&");
        exp.add("p3");
        exp.add(")");
        exp.add("&");
        exp.add("(");
        exp.add("hola adéu");
        exp.add("|");
        exp.add("pep");
        exp.add(")");
        exp.add("&");
        exp.add("!");
        exp.add("joan");

        Tree arbre = new Tree(exp);

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