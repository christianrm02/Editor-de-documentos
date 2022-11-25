CLASS_OUTPUT = ../EXE/output/

JAR_OUTPUT = ../EXE/

JUNIT_JARS = 	./lib/hamcrest-core-1.3.jar:./lib/junit-4.13.2.jar

JAVA_SOURCES =	./src/domini/controladores/*.java \
				./src/domini/datatypes/*.java \
				./src/domini/indexs/*.java \
				./src/transversal/*.java

TEST_SOURCES = 	./src/test/datatypes/*.java \
				./src/test/indexs/*.java \
				./src/test/transversal/*.java \
				./src/test/*.java

#COMPILAR DRIVER
driver:
	javac -d $(CLASS_OUTPUT) $(JAVA_SOURCES) ./src/drivers/DriverCtrlDomini.java
	jar cvmf ./src/drivers/MANIFEST.MF $(JAR_OUTPUT)DriverCtrlDomini.jar -C $(CLASS_OUTPUT) .

#COMPILAR TESTS
tests:
	javac -d $(CLASS_OUTPUT) $(JAVA_SOURCES)
	javac -cp $(JUNIT_JARS) -d $(CLASS_OUTPUT) $(JAVA_SOURCES) $(TEST_SOURCES)

#EXECUTAR DRIVER
exec_driver: driver
	java -jar $(JAR_OUTPUT)DriverCtrlDomini.jar

exec_driver_jocdeproves: driver
	java -jar $(JAR_OUTPUT)DriverCtrlDomini.jar < Joc_de_proves_sense_comentaris_input_directe_pel_driver.txt

#EXECUTAR TESTS
exec_tests: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.MainTest

exec_pair: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.transversal.PairTest

exec_contingut: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.datatypes.ContingutTest

exec_document: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.datatypes.DocumentTest

exec_expbooleana: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.datatypes.ExpressioBooleanaTest

exec_tree: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.datatypes.TreeTest

exec_trie: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.datatypes.TrieTest

exec_utility: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.datatypes.UtilityTest

exec_indexexpbooleana: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.indexs.IndexExpBooleanaTest

exec_indexparaulatfidf: tests
	java -cp $(JUNIT_JARS):$(CLASS_OUTPUT) org.junit.runner.JUnitCore test.indexs.IndexParaulaTFIDFTest

#CLEAN
clean:
	rm -rf ../EXE

