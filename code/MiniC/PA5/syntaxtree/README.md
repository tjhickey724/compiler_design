# Abstract Syntax Trees for MiniJava

The abstract syntax trees for MiniJava programs
are created using the following constructors.
From such a tree we can create a symbol table,
do type checking, pretty print the program,
generate the assembly language.

```
new Program(MainClass, ClassDeclList)
new MainClass(Identifier, Statement)
new ClassDecl(Identifier, VarDeclList, MethodDeclList)
new VarDecl(Type, Identifier)
new MethodDecl(Type, Identifier, FormalList, VarDeclList, StatementList, Exp)
new Formal(Type, Identifier)
new Identifier(String)

where Type is an abstract class with the following concrete classes
  new BooleanType()
  new IntegerType()
  new IntArrayType()
  new IdentifierType()

where Statement is an abstract class with the following concrete classes
  new Assign(Identifier, Exp)
  new ArrayAssign(Identifier, Exp, Exp)
  new Call(Exp,Identifier,ExpList)
  new Block(StatementList)
  new If(Exp,Statement,Statement)
  new Print(Exp)
  new While(Exp,Statement)

where Exp is an abstract class with the following concrete classes
  new ExpGroup(Exp)
  new IntegerListeral(int)
  new IdentifierExp(Identifier)
  new True()
  new False()
  new Plus(Exp,Exp)
  new Minus(Exp,Exp)
  new Times(Exp,Exp)
  new NewArray(Exp)
  new NewObject()
  new LessThan(Exp,Exp)
  new Not(Exp)
  new And(Exp,Exp)
  new This()
  new ArrayLookup()
  new ArrayLength()

and we have several classes for lists of elements of the same class
new ExpList(Exp, ExpList)
new StatementList(Statement, StatementList) or null
new MethodDeclList(MethodDecl, MethodDeclList) or null
new ClassDeclList(ClassDecl,ClassDeclList)
new Vardeclist(VarDecl VarDeclList)
new FormalList(Formal, FormalList)  or null
```
For example, let's create the Abstract Syntax Tree for the following simple program:
```
class Hello {
  public static void main(String[] args){
    {
     System.out.println(5*5);
    }
  }
}
```
The tree should be
```
new Program(
  new MainClass(
    new Identifier("Hello"),
    new Print(
      new Times(
        new IntegerLiteral(5),
        new IntegerLiteral(5)
      )
    )
 )
)
```

