package test.persistencia;

import org.junit.Test;
import persistencia.GestorExpBooleanes;
import transversal.Pair;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GestorExpBooleanesTest {

    @Test
    public void carregarExpB() throws IOException {
        List<Pair<String, String>> exps;
        exps = GestorExpBooleanes.CarregarExpB();
        for (Pair<String,String> p : exps) {
            System.out.println(p.x + "  " + p.y);
        }
    }

    @Test
    public void guardarExpB() throws IOException {
        List<Pair<String, String>> exps = new ArrayList<>();
        Pair<String, String> p = new Pair<>();
        p.x = "primera";
        p.y = "\"'Kimetsu no Yaiba'\" & !(!(!(hola | !({una levanta} | \"cuenta oficial\"))))";
        exps.add(p);

        Pair<String, String> p2 = new Pair<>();
        p2.x = "segona";
        p2.y = "hola & !adeu | \"bon dia\"";
        exps.add(p2);

        GestorExpBooleanes.GuardarExpB(exps);
    }
}