#!/usr/bin/bash
java -classpath ../../javacc.jar javacc MiniC_v5.jj
javac MiniC_v5.java
javac CodeGen_Visitor.java
echo "compiled java files"
#java MiniC_v5 < tests/demo1.c
java MiniC_v5 < tests/demo.c > tests/demo.s
cd tests; 
gcc demo.s print.s -o demo; ./demo

