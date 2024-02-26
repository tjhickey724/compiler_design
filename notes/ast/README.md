# Abstract Syntax Trees

The goal of the front end of a compiler is to produce an abstract syntax tree (AST) representing the structure of the program and
then to convert that AST into some sort of Intermediate Representation structure that can be converted to assembly language by the back end.

We will use javacc to generate abstract syntax trees, with one AST class for each grammar rule. We only create a node though if it has
more than one child so as to simplify the resulting tree.

Let's explore this looking a the expression grammar for miniJava.
Here is a javacc program for parsing miniJava expressions and generating an AST
* [exptree.jj](../../code/ExpTree/ExpTree.jj)


