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
MiniJava adds the following rules:
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
and modifies following expression rules:
```
Exp -> Exp4 (<AND> Expr)*
Exp4 -> Exp9 (<LT> Exp9)*
Exp12 -> Exp14
      (<DOT> <LENGTH>
       |
       <DOT> <ID> <LPAREN> Explist <RPAREN>
       |
        <LBRACKET> Exp <RBRACKET>
      )
Exp14 -> (<BANG> Exp16 | Exp16)
Exp16 -> <THIS>
Exp16 -> <NEW> <INT> <LBRACKET> Exp <RBRACKET>
Exp16 -> <NEW> <ID> <LPAREN> <RPAREN>
```
and remove the following MiniC rule for function calls:
```
Exp16 -> <ID> <LPAREN> ExpList <RPAREN>
```
as we have method calls in MiniJava with the second rule of Exp14

## Tokens
MiniC and MiniJava have almost the same tokens, except that
* MiniJava has a <PREFACE> token with value
```    #include <stdio.h>\n#include <stdbool.h>\nvoid print(int n){printf(\"%10d\\n\",n);}```
and the Print tokens differ
*  ```System.out.println``` for MiniJava
*  ```print``` for MiniC

So to extend a program from one that works on MiniC to one that works on MiniJava you need to handle 8 more grammar rules
and change two tokens.

