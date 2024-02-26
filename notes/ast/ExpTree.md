# ExprTree and the Visitor Pattern

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
So
```
node.e1.accept(this,data);  // where this refers to the visitor.
```
The java compiler has been told that node.e1 extends the Exp abstract class and hence it must have an accept method.
We will see in our own compiler, that the location of the code implementing the "accept" method, will need to be stored in the node.e1 object
(more precisely, that object will need a link to its class, which will have the locations for each of its methods).

The compiled code then invokes the accept method of the node.e1 object, passing in the visitor "this" as v and this results in a call
```
v.visit(this,data);  // where this refers to the node.e1 object
```
and in this way, control passes back and forth between the visitor and the abstract syntax tree classees.

We might like to do this more directly as
```
this.visit(node.e1,data)   // instead of node.e1.accept(this,data); 
```
but at compile time the java compiler doesn't know the type of node.e1 (it could be Plus, Minus, Times, or IntegerLiteral)
and so it doesn't know which visit method to call...

## Looking closer at the visitor pattern
We could use a single static method with a cascade of conditionals to achieve the same effect as using the Visitor pattern,
as shown below, but this requires manually checking the class of the node argument, and then casting it to the right type.
```
import syntaxtree.*;

public class DumpNoVisitor {
    
    public static void visit(Object node){
        if (node.getClass()==IntegerLiteral.class){
            IntegerLiteral x = (IntegerLiteral)node;
            System.out.print(x.i);

        }else if (node.getClass()==Times.class){
            Times x = (Times) node;
            System.out.print("times(");
            visit(x.e1);
            System.out.print(",");
            visit(x.e2);
            System.out.print(")");
        }else if (node.getClass()==Plus.class){
            Plus x = (Plus) node;
            System.out.print("plus(");
            visit(x.e1);
            System.out.print(",");
            visit(x.e2);
            System.out.print(")");
        }else if (node.getClass()==Minus.class){
            Minus x = (Minus) node;
            System.out.print("minus(");
            visit(x.e1);
            System.out.print(",");
            visit(x.e2);
            System.out.print(")");
        }else {
            System.out.println("unknown class: "+node.getClass());
        }
    }
    
}
```
and compare that to the implementation using the Visitor pattern, shown below.
This is more elegant as the code for each class is encapsulated in its own method
and you don't need to do any casting or explicit type checking in your code.
```
import syntaxtree.*;

public class DumpVisitor implements Visitor{
    

  public Object visit(Plus node, Object data){
    System.out.print("plus(");
    node.e1.accept(this,data);
    System.out.print(",");
    node.e2.accept(this,data);
    System.out.print(")");
    return(data);}

  public Object visit(Minus node, Object data){
    System.out.print("minus(");
    node.e1.accept(this,data);
    System.out.print(",");
    node.e2.accept(this,data);
    System.out.print(")");
    return(data);}

  public Object visit(Times node, Object data){
    System.out.print("times(");
    node.e1.accept(this,data);
    System.out.print(",");
    node.e2.accept(this,data);
    System.out.print(")");
    return(data);}

  public Object visit(IntegerLiteral node, Object data){
    System.out.print(node.i);
    return(data);}

// here are all of the methods for the classes we don't need for expressions
  public Object visit(ArrayLookup node, Object data){return(data);}
  public Object visit(ArrayLength node, Object data){return(data);}
  public Object visit(Call node, Object data){return(data);}
  public Object visit(Program node, Object data){return(data);}
  public Object visit(MainClass node, Object data){return(data);}
  public Object visit(ClassDecl node, Object data){return(data);}
  public Object visit(VarDecl node, Object data){return(data);}
  public Object visit(MethodDecl node, Object data){return(data);}
  public Object visit(Formal node, Object data){return(data);}
  public Object visit(Type node, Object data){return(data);}
  public Object visit(IntArrayType node, Object data){return(data);}
  public Object visit(IntegerType node, Object data){return(data);}
  public Object visit(BooleanType node, Object data){return(data);}
  public Object visit(IdentifierType node, Object data){return(data);}
  public Object visit(Block node, Object data){return(data);}
  public Object visit(If node, Object data){return(data);}
  public Object visit(While node, Object data){return(data);}
  public Object visit(Print node, Object data){return(data);}
  public Object visit(Assign node, Object data){return(data);}
  public Object visit(ArrayAssign node, Object data){return(data);}
  public Object visit(And node, Object data){return(data);}
  public Object visit(LessThan node, Object data){return(data);}
  public Object visit(True node, Object data){return(data);}
  public Object visit(False node, Object data){return(data);}
  public Object visit(IdentifierExp node, Object data){return(data);}
  public Object visit(This node, Object data){return(data);}
  public Object visit(NewArray node, Object data){return(data);}
  public Object visit(NewObject node, Object data){return(data);}
  public Object visit(Not node, Object data){return(data);}
  public Object visit(Identifier node, Object data){return(data);}
  public Object visit(ClassDeclList node, Object data){return(data);}
  public Object visit(ExpList node, Object data){return(data);}
  public Object visit(FormalList node, Object data){return(data);}
  public Object visit(MethodDeclList node, Object data){return(data);}
  public Object visit(StatementList node, Object data){return(data);}
  public Object visit(VarDeclList node, Object data){return(data);}
}

```






