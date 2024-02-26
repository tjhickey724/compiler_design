# ExprTree

In this note, we show how to use javacc to generate Abstract Syntax Trees for miniJava Expressions
and how to use the Visitor pattern to convert those trees to a procedural language (e.g. python, C, javascript, etc.)

We will use javacc to generate abstract syntax trees, with one AST class for each grammar rule. We only create a node though if it has
more than one child so as to simplify the resulting tree.

Let's explore this looking a the expression grammar for miniJava.
Here is a javacc program for parsing miniJava expressions and generating an AST
* [exptree.jj](../../code/ExpTree/ExpTree.jj)

