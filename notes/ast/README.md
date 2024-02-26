# Abstract Syntax Trees

The goal of the front end of a compiler is to produce an abstract syntax tree (AST) representing the structure of the program and
then to convert that AST into some sort of Intermediate Representation structure that can be converted to assembly language by the back end.

There are tools, such as [jjTree](./jjTree.md) for automatically gnerating abstract syntax trees, but we will show how to build them directly
which gives us more control over the structure of the trees.

We begin with showing how to use javacc to generate ASTs for miniJava expressions
* [ExpTree](./ExpTree.md)




