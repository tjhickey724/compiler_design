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

Here is the syntax rule for addition and subtraction ...
```
Exp Exp():
{Exp a,b;}
{ 
  a=Exp11() 
    (
    <PLUS> b=Exp11(){a = new Plus(a,b);}
    |
    <MINUS> b = Exp11() {a = new Minus(a,b);}
    )* 

  {return(a);}
}
```
It recursively builds the ASTs for the subexpressions and the either calls new Plus(a,b) or new Minus(a,b)
to generate the AST for each summand (or differand?). It the returns the generated left associative tree to its parent.

When we call the visitor on this tree it will produce
```
times(2,minus(plus(1,4),3))
```
Let's look at the code for this inside of the DumpVisitor.java program
```
public Object visit(Plus node, Object data){
    System.out.print("plus(");
    node.e1.accept(this,data);
    System.out.print(",");
    node.e2.accept(this,data);
    System.out.print(")");
    return(data);}
```
Given a Plus node and some data (which we aren't using currently).
It prints "plus(" and then calls 
```
node.e1.accept(this,data)
```
where the Plus node as two instance variables e1 and e2 corresponding to the two subtrees of the addition tree.

We don't know what type ```node.e1``` is, it could be a Plus or Minus or IntegerLiteral or Times.

This accept method generates a call of the visitor on node.e1 which will automatically get routed to the correct method based on the type!
We could probably have done this more directly as
```
this.visit(node.e1,data)
```

