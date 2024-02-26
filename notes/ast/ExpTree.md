# ExprTree

In this note, we show how to use javacc to generate Abstract Syntax Trees for miniJava Expressions
and how to use the Visitor pattern to convert those trees to a procedural language (e.g. python, C, javascript, etc.)

We will use javacc to generate abstract syntax trees, with one AST class for each grammar rule. We only create a node though if it has
more than one child so as to simplify the resulting tree.

Let's explore this looking a the expression grammar for miniJava.
Here is a javacc program for parsing miniJava expressions and generating an AST
* [exptree.jj](../../code/ExpTree/ExpTree.jj)

The Abstract Syntax Tree is created using the classes in the [syntaxtree package](../../code/ExpTree/syntaxtree/). This package
contains classes as defined in the textbook, but the only ones we care about for now are:
* Times(a,b)
* Plus(a,b)
* Minus(a,b)
* IntegerLiteral(n)

The parser generates a tree from the expression, for example from
* 2*(1+4-3)

it would generate the same tree as this code...
```
new Times(new IntegerLiteral(2),
          new Minus(
              new Plus(new IntegerLiteral(1),new IntegerLiteral(4)),
              new IntegerLiteral(3)))
```

When we call the visitor on this tree it will produce
```
time(2,minus(plu(1,4),3)))
```
