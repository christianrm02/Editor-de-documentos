#MAKEFILE V2
CLASS_OUTPUT = ../EXE/output/

JAR_OUTPUT = ../EXE/

JUNIT_JARS = ./lib/hamcrest-core-1.3.jar:./lib/junit-4.13.2.jar

#CANVIAR PER LES VOSTRES LOCALITZACIONS DEL JGOODIES
#JGOODIES_JARS = ./lib/\*
JGOODIES_JARS = ./lib/jgoodies-forms-1.9.0.jar:./lib/forms_rt.jar:./lib/jgoodies-binding-2.13.0.jar:./lib/jgoodies-common-1.8.1.jar:./lib/jgoodies-validation-2.5.1.jar
#./lib/forms-1.1-preview.jar:./lib/jgoodies-binding-2.13.0.jar:./lib/jgoodies-common-1.8.1.jar:./lib/jgoodies-forms-1.9.0.jar:./lib/jgoodies-validation-2.5.1.jar:./lib/javac2-7.0.3.jar:./lib/ant-1.7.0.jar:./lib/ant-launcher-1.7.0.jar:./lib/asm-3.0.jar:./lib/asm-commons-3.0.jar:./lib/asm-tree-3.0.jar:./lib/jdom-1.0.jar
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
program: clean
	javac -classpath $(JGOODIES_JARS) -d $(CLASS_OUTPUT) $(JAVA_SOURCES)
	jar vxf ./lib/forms_rt.jar
	jar vxf ./lib/jgoodies-forms-1.9.0.jar
	jar vxf ./lib/jgoodies-binding-2.13.0.jar
	jar vxf ./lib/jgoodies-common-1.8.1.jar
	jar vxf ./lib/jgoodies-validation-2.5.1.jar
	mv ./com $(CLASS_OUTPUT)
	cp -r ./src/presentacio/moreOptions.png ./src/presentacio/icons $(CLASS_OUTPUT)presentacio
	rm -rf META-INF __index__

#EXECUTAR PROGRAMA
exec:
	jar cvmf ./src/presentacio/MANIFEST.MF $(JAR_OUTPUT)Documenteitor.jar -C $(CLASS_OUTPUT) .
	java -jar $(JAR_OUTPUT)Documenteitor.jar

#COMPILAR I EXECUTAR PROGRAMA
comp_exec_program: program
	java -jar $(JAR_OUTPUT)Documenteitor.jar

#CLEAN
clean:
	rm -rf ../EXE

