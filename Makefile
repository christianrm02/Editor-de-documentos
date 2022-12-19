#MAKEFILE V2
CLASS_OUTPUT = ../EXE/output/

JAR_OUTPUT = ../EXE/

JUNIT_JARS = ./lib/hamcrest-core-1.3.jar:./lib/junit-4.13.2.jar

JGOODLE_JARS = #OMPLIR AMB LA VOSTRA LOCALITZACIO DE LES LLIBRERIES GRAFIQUES DE LA MATEIXA MANERA QUE JUNIT_JARS SEPARAT PER :

JAVA_SOURCES =	./src/domini/controladores/*.java \
				./src/domini/datatypes/*.java \
				./src/domini/indexs/*.java \
				./src/transversal/*.java \
				./src/persistencia/*.java \
				./src/presentacio/*.java \
				./src/excepcions/*.java

TEST_SOURCES = 	./src/test/datatypes/*.java \
				./src/test/indexs/*.java \
				./src/test/transversal/*.java \
				./src/test/*.java

#COMPILAR PROGRAMA
program:
	javac -cp $(JGOODLE_JARS) -d $(CLASS_OUTPUT) $(JAVA_SOURCES)
	jar cvmf ./src/presentacio/MANIFEST.MF $(JAR_OUTPUT)Documentator.jar -C $(CLASS_OUTPUT) .

#EXECUTAR PROGRAMA
exec_program: program
	java -jar $(JAR_OUTPUT)Documentator.jar

#CLEAN
clean:
	rm -rf ../EXE

