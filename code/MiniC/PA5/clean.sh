#! /usr/bin/bash
mkdir save
mv *Visitor*.java save
mv SymbolTable* save
rm *.class
rm *.java
rm syntaxtree/*.class
mv save/* .
rmdir save

