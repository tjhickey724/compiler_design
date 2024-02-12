# Abstract Syntax Trees
In this note we show how to use jjTree to generate abstract syntax trees for an LL(1) grammar
and how to traverse this tree using the Visitor Pattern. 

We will be using [jjtree](https://www.cs.purdue.edu/homes/hosking/javacc/doc/JJTree.html) to
generate the trees. This is an application that takes an annotated javacc file 
* test.jjt

and produces
* a javacc file test.jj
* set set of Java classes, with names starting with AST, implementing the Visitor pattern
* a DefaultVisitor class for traversing the Abstract Syntax Tree.

