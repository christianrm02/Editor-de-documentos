package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.datatypes.*;
import test.indexs.*;
import test.transversal.*;


@RunWith(value = Suite.class)
@SuiteClasses(value = { 
    ExpressioBooleanaTest.class, TrieTest.class, UtilityTest.class, IndexExpBooleanaTest.class, IndexParaulaTFIDFTest.class, PairTest.class
})
public class MainTest {}