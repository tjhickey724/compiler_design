# MiniC vs MiniJava

MiniC is a subset of C, containing function calls and integer variables, assignments, and if statements.

MiniJava is a subset of Java, containing classes, instance variables and methods, and integer, boolean, and integer array variables
with assignments, if and while statements. 
It is the language defined in Andrew Appel's textbook on Modern Compilers in Java.

These two languages are very close, as you wll see below. This shows just how similar Java and C are.

We describe the two langauges (and especially their diff) in this document.

## Grammar Rules:

### MiniC has the following grammar rules
It is essentially what we get if we remove classes and arrays from MiniJava.
We also have to replace the method call rule with a function call rule,  and change the \\<PRINT\\> token.
```
Start -> <PREFACE> MethodDeclList <SEMICOLON>
Method DeclList -> MethodDecl *
MethodDecl -> Type Identifier <LPAREN> FormalList <RPAREM>
                    <LCURLY>
                        VarDecls
                        Statements
                        <RETURN> Exp <SEMICOLON>
                    <RCURLY>
VarDecls -> VarDecl *
VarDecl -> Type Identifier <SEMICOLON>
Type -> <BOOLEAN>
Type -> <INT>
FormalList -> Formal (<COMMA> FormalList) *
Formal -> Type Identifier
StatementList -> Statement *
Statement -> <LCURLY> StatementList <RCURLY>
Statement -> <IF> <LPAREN> Exp <RPAREN> Statement <ELSE> Statement
Statement -> <PRINTLN> <LPAREN> Exp <RPAREN> <SEMICOLON>
Statement -> Identifier <EQUALS> Exp <SEMICOLON>
Exp -> Exp9 (<LT> Exp9)*
Exp9 -> Exp11 (<PLUS> Exp11 | <MINUS> Exp11) *
Exp11 -> Exp12 (<TIMES> Exp12)*
Exp12 -> Exp14
Exp14 -> Exp16
Exp16 -> <NUMBER>
Exp16 -> <TRUE>
Exp16 -> <FALSE>
Exp16 -> <LPAREN> Exp <RPAREN>
Exp16 -> Identifier <LPAREN> ExpList <RPAREN>
Exp16 -> Identifier
ExpList -> Exp (<COMMA> Exp)*
Identifier -> <ID>
```
MiniJava adds the following rules for 
* defining classes (including the main class)
* integer array types (int[])
* class types (e.g. ArrayList)
* while statements
* array index assignment
* logical conjunction and negation
* method calls and object creation
* array access and creation


## the MiniJava grammar
* the rules at have been added are prefixed with ***
* the rules that have been modified are prefixed with MMM
* the rules that have been removed are prefixed with XXX
```
MMM Start -> MainClass ClassDeclList   (replaces the Start rule of MiniC)
*** MainClass -> <CLASS> Identifier <LCURLY>
               <PUBLIC> <STATIC> <VOID> <MAIN>
                 <LPAREN> <STRING> <LBRACKET><RBRACKET> Identifier <RPAREN>
                  <LCURLY> Statement <RCURLY>
              <RCURLY>
*** ClassDeclList -> ClassDecl *
*** ClassDecl -> <CLASS> Identifier <LCURLY>
                VarDeclList
                MethodDeclList
              <RCURLY>
    Method DeclList -> MethodDecl *
    MethodDecl -> Type Identifier <LPAREN> FormalList <RPAREM>
                    <LCURLY>
                        VarDecls
                        Statements
                        <RETURN> Exp <SEMICOLON>
                    <RCURLY>
    VarDecls -> VarDecl *
    VarDecl -> Type Identifier <SEMICOLON>
    Type -> <BOOLEAN>
    Type -> <INT>
*** Type -> <INT> <LBRACKET> <RBRACKET>     (MiniC doesn't int array types)
*** Type -> Identifier                            (MiniC doesn't have class types)
    FormalList -> Formal (<COMMA> FormalList) *
    Formal -> Type Identifier
    StatementList -> Statement *
    Statement -> <LCURLY> StatementList <RCURLY>
    Statement -> <IF> <LPAREN> Exp <RPAREN> Statement <ELSE> Statement
    Statement -> <PRINTLN> <LPAREN> Exp <RPAREN> <SEMICOLON>
    Statement -> Identifier <EQUALS> Exp <SEMICOLON>
*** Statement -> <WHILE> <LPAREN> Exp <RPAREN> Statement
*** Statement -> Identifier<LBRACKET>Exp<RBRACKET> <EQUALS> Exp <SEMICOLON>
MMM Exp -> Exp4 (<AND> Expr)*       // adding logical conjunction
*** Exp4 -> Exp9 (<LT> Exp9)*       // moving < down the hierarchy
    Exp9 -> Exp11 (<PLUS> Exp11 | <MINUS> Exp11) *
    Exp11 -> Exp12 (<TIMES> Exp12)*
*** Exp12 -> Exp14
      (<DOT> <LENGTH>           // adding the array length operator x.length
       |
       <DOT> Identifier <LPAREN> Explist <RPAREN>  // adding method calls f.a(1,2)
       |
        <LBRACKET> Exp <RBRACKET>   //adding array access
                                  // e.g. f(5)[2] if f returns an int[]
      )
MMM Exp14 -> (<BANG> Exp16 | Exp16)   // adding logical negation
    Exp16 -> <NUMBER>
    Exp16 -> <TRUE>
    Exp16 -> <FALSE>
    Exp16 -> <LPAREN> Exp <RPAREN>
XXX Exp16 -> Identifier <LPAREN> ExpList <RPAREN>
    Exp16 -> Identifier
*** Exp16 -> <THIS>                   // adding the "this" variable
*** Exp16 -> <NEW> <INT> <LBRACKET> Exp <RBRACKET>  // adding new array constructor
*** Exp16 -> <NEW> Identifier <LPAREN> <RPAREN>  // adding new object constructor
    ExpList -> Exp (<COMMA> Exp)*
    Identifier -> <ID>

```


## Tokens
MiniC and MiniJava have almost the same tokens, except that
* MiniJava has a <PREFACE> token with value
```    #include <stdio.h>\n#include <stdbool.h>\nvoid print(int n){printf(\"%10d\\n\",n);}```
and the Print tokens differ
*  ```System.out.println``` for MiniJava
*  ```print``` for MiniC

So to extend a program from one that works on MiniC to one that works on MiniJava you need to
make the modifications to the grammar and token rules as shown above.


