# MiniC vs MiniJava

MiniC is a (slightly modified) subset of MiniJava, the language defined in Andrew Appel's textbook on Modern Compilers in Java.

We describe the two langauges (and especially their diff) in this document.

## Grammar Rules:

### MiniC has the following grammar rules
```
Start -> <PREFACE> MethodDeclList <SEMICOLON>
Method DeclList -> MethodDecl *
MethodDecl -> Type <ID> <LPAREN> FormalList <RPAREM>
                    <LCURLY>
                        VarDecls
                        Statements
                        <RETURN> Exp <SEMICOLON>
                    <RCURLY>
VarDecls -> VarDecl *
VarDecl -> Type <ID> <SEMICOLON>
Type -> <BOOLEAN>
Type -> <INT>
FormalList -> Formal (<COMMA> FormalList) *
Formal -> Type <ID>
StatementList -> Statement *
Statement -> <LCURLY> StatementList <RCURLY>
Statement -> <IF> <LPAREN> Exp <RPAREN> Statement <ELSE> Statement
Statement -> <PRINTLN> <LPAREN> Exp <RPAREN> <SEMICOLON>
Statement -> <ID> <EQUALS> Exp <SEMICOLON>
Exp -> Exp9 (<LT> Exp9)*
Exp9 -> Exp11 (<PLUS> Exp11 | <MINUS> Exp11) *
Exp11 -> Exp12 (<TIMES> Exp12)*
Exp12 -> Exp14
Exp14 -> Exp16
Exp16 -> <NUMBER>
Exp16 -> <TRUE>
Exp16 -> <FALSE>
Exp16 -> <LPAREN> Exp <RPAREN>
Exp16 -> <ID> <LPAREN> ExpList <RPAREN>
Exp16 -> <ID>
ExpList -> Exp (<COMMA> Exp)*
```
MiniJava adds the following rules for defining classes (including the main class)
and adding integer array types (int[]) and class types (e.g. ArrayList) 
```
Start -> MainClass ClassDeclList   (replaces the Start rule of MiniC)
MainClass -> <CLASS> <ID> <LCURLY>
               <PUBLIC> <STATIC> <VOID> <MAIN>
                 <LPAREN> <STRING> <LBRACKET><RBRACKET> <ID> <RPAREN>
                  <LCURLY> Statement <RCURLY>
              <RCURLY>
ClassDeclList -> ClassDecl *
ClassDecl -> <CLASS> <ID> <LCURLY>
                VarDeclList
                MethodDeclList
              <RCURLY>
Type -> <INT> <LBRACKET> <RBRACKET>     (MiniC doesn't int array types)
Type -> <ID>                            (MiniC doesn't have class types)
Statement -> <WHILE> <LPAREN> Exp <RPAREN> Statement
Statement -> <ID><LBRACKET>Exp<RBRACKET> <EQUALS> Exp <SEMICOLON>

```
and modifies following expression rules
```
Exp -> Exp4 (<AND> Expr)*       // adding logical conjunction
Exp4 -> Exp9 (<LT> Exp9)*       // moving < down the hierarchy
Exp12 -> Exp14
      (<DOT> <LENGTH>           // adding the array length operator x.length
       |
       <DOT> <ID> <LPAREN> Explist <RPAREN>  // adding method calls f.a(1,2)
       |
        <LBRACKET> Exp <RBRACKET>   //adding array access
                                  // e.g. f(5)[2] if f returns an int[]
      )
Exp14 -> (<BANG> Exp16 | Exp16)   // adding logical negation
Exp16 -> <THIS>                   // adding the "this" variable
Exp16 -> <NEW> <INT> <LBRACKET> Exp <RBRACKET>  // adding new array constructor
Exp16 -> <NEW> <ID> <LPAREN> <RPAREN>  // adding new object constructor
```
and remove the following MiniC rule for function calls..
```
Exp16 -> <ID> <LPAREN> ExpList <RPAREN>   // old rule for function calls
                                          // replace by Exp12 rule for method calls
```


## Tokens
MiniC and MiniJava have almost the same tokens, except that
* MiniJava has a <PREFACE> token with value
```    #include <stdio.h>\n#include <stdbool.h>\nvoid print(int n){printf(\"%10d\\n\",n);}```
and the Print tokens differ
*  ```System.out.println``` for MiniJava
*  ```print``` for MiniC

So to extend a program from one that works on MiniC to one that works on MiniJava you need to handle 8 more grammar rules
and change two tokens.

