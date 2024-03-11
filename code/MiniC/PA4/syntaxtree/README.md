# Abstract Syntax Trees for MiniJava

The abstract syntax trees for MiniJava programs
are created using the following constructors.
From such a tree we can create a symbol table,
do type checking, pretty print the program,
generate the assembly language.

```
Program(MainClass m, ClassDeclList c)  // should have been clist instead of c
MainClass(Identifier i, Statement s)  // we ignore the name of the String[] formal as we never use it
ClassDecl(Identifier i, VarDeclList v, MethodDeclList m)  // should have been vlist and mlist instead of v and m....
VarDecl(Type t, Identifier i)
MethodDecl(Type t, Identifier i, FormalList f, VarDeclList v, StatementList s, Exp e)
Formal(Type t, Identifier i)
Identifier(String s)  

where Type is an abstract class with the following concrete classes
  BooleanType()
  IntegerType()
  IntArrayType()
  IdentifierType(String s)

where Statement is an abstract class with the following concrete classes
  Assign(Identifier i, Exp e)
  ArrayAssign(Identifier i, Exp e1, Exp e2)
  Call(Exp e1,Identifier i,ExpList e2)  // should have been e, i, elist
  Block(StatementList slist)
  If(Exp e,Statement s1,Statement s2)
  Print(Exp e)
  While(Exp e,Statement s)

where Exp is an abstract class with the following concrete classes
  ExpGroup(Exp e)
  IntegerListeral(int i)
  IdentifierExp(String s) // should have been (Identifier i) not (String s)
  True()
  False()
  Plus(Exp e1,Exp e2)
  Minus(Exp e1,Exp e2)
  Times(Exp e1,Exp e2)
  NewArray(Exp e)
  NewObject(Identifier i)
  LessThan(Exp e1,Exp e2)
  Not(Exp e)
  And(Exp e1,Exp e2)
  This()
  ArrayLookup(Exp e1, Exp e2)
  ArrayLength(Exp e)

and we have several classes for lists of elements of the same class
ClassDeclList(ClassDecl c,ClassDeclList clist) or null
ExpList(Exp e, ExpList elist)  or null
FormalList(Formal f, FormalList flist)  or null
MethodDeclList(MethodDecl m, MethodDeclList mlist) or null
StatementList(Statement s, StatementList slist) or null
Vardeclist(VarDecl v,VarDeclList vlist) or null

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
The tree should be the following
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
 ),
  null  // for ClassDeclList
)
```
note we ignore the identifier "args" because our language does not have String[] types,
so we can never process the args array anyway.

This Syntax Tree is the parse tree using the following syntax rules:
```
MMM Start -> MainClass ClassDeclList
    ClassDeclList -> epsilon
*** MainClass -> <CLASS> Identifier <LCURLY>                  (MainClass)
               <PUBLIC> <STATIC> <VOID> <MAIN>
                 <LPAREN>
                  <STRING> <LBRACKET><RBRACKET> Identifier
                 <RPAREN>
                  <LCURLY> Statement <RCURLY>
              <RCURLY>
    Statement -> <PRINTLN> <LPAREN> Exp <RPAREN> <SEMICOLON>  (Print)
    Identifier -> <ID>                                        (Identifier)
MMM Exp -> Exp4
*** Exp4 -> Exp9 
    Exp9 -> Exp11 
    Exp11 -> Exp12 <TIMES> Exp12                           (Times)
*** Exp12 -> Exp14   
MMM Exp14 -> Exp16
    Exp16 -> <NUMBER>                                         (IntegerLiteral)

```

