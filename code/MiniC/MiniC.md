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
