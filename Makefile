OUTPUT_DIR = ./bin

JUNIT_JARS = 	./lib/hamcrest-core-1.3.jar:./lib/junit-4.13.2.jar

JAVA_SOURCES =	./src/domini/controladores/*.java \
				./src/domini/datatypes/*.java \
				./src/domini/indexs/*.java \
				./src/transversal/*.java

TEST_SOURCES = 	./src/test/datatypes/*.java \
				./src/test/indexs/*.java \
				./src/test/transversal/*.java


all:
	javac -d $(OUTPUT_DIR) $(JAVA_SOURCES)
	cp ./src/domini/indexs/*.txt ./bin/indexs

driver:
	javac -d $(OUTPUT_DIR) $(JAVA_SOURCES) ./src/drivers/DriverCtrlDomini.java
	jar cmvf ./src/drivers/DriverCtrlDomini.mf ./DriverCtrlDomini.jar -C $(OUTPUT_DIR) .

tests:
	javac -d $(OUTPUT_DIR) $(JAVA_SOURCES)
	javac -cp $(JUNIT_JARS) -d $(OUTPUT_DIR) $(JAVA_SOURCES) $(TEST_SOURCES)
	java -cp $(JUNIT_JARS):$(OUTPUT_DIR) org.junit.runner.JUnitCore test.MasterTestSuite

run:
	java -cp "./bin;./bin/controladores;./bin/datatypes;./bin/transversal;./bin/indexs" drivers.DriverCtrlDomini

clean:
	rm -rf ./bin

