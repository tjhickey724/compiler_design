# Generating the miniC Abstract Syntax Tree

The classes we will use to create the miniC AST (and the miniJava AST) are in the package syntaxtree
* [syntaxtree package](../../code/MiniC/syntaxtree)

We have one class for each grammar rule in the miniJava language. Since miniC is (essentially) a subset
of miniJava, we can use the same classes for miniC, but we won't use all of them. For example, a miniC
AST will not have any ClassDecl nodes or any ArrayAssign nodes.

We will use javacc to create the abstract syntax tree and then to apply several visitors to the tree
to print it, generate a symbol table, and run a type check.

## javacc
Here is a link to the javacc program to create the miniC AST
* [miniC.jj](../../code/MiniC/MiniC.jj)

If we remove the code for the visitors, then the main method has the following form
```
 public static void main(String args[]) {
    MiniC t = new MiniC(System.in);
    try {
      MethodDeclList n = t.Start();
      System.out.println("\n\nDone!");
    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
````
This reads the program from the stdin input (i.e. keyboard or a piped program)
and uses that to construct a MiniC parsing object.
* ```MiniC t = new MiniC(System.in);```

Then we invoke the Start method on the parser to generate the top level node of the AST
which will be a MethodDeclList object. Our version of miniC does not allow global
variable declarations (though we could easily add the with a rule 
* Program -> VarDeclList() MethodDeclList()

* ```MethodDeclList n = t.Start();```

Each Grammar Rule in the miniC language is annotated with some additional code
to generate the Abstract Syntax Tree for that node.  The toplevel code is for the
Start nonterminal and its javacc code is
```
MethodDeclList Start():
{
  MethodDeclList ms;
}
{
  <PREFACE>
   ms = MethodDecls()
  {return ms;}
  <EOF>

}
```
Oberve that it returns a MethodDeclList node as the rool of the AST for the miniC program.
It expects to see the <PREFACE> token which defines the print function in C,
then it looks for the MethodDecls and returns it.


Let's look at the MethodDecl ruleas a good example of a more complex rule:
```
MethodDecl MethodDecl()  :
{
  Type ty;
  Token t;
  FormalList flist;
  VarDeclList vlist;
  StatementList slist;
  Exp e;
}
{
  ty=Type() t=<ID> <LPAREN> flist=FormalList() <RPAREN>

      <LCURLY>
           vlist=VarDecls() 
           slist = Statements()
           <RETURN> e=Exp() <SEMICOLON>
      <RCURLY>
  {return new MethodDecl(ty,new Identifier(t.image),flist,vlist,slist,e);}

}
```
The MethodDecl class will have 6 instance variables for each of the components of
the method declaration ... We declare variables ty,t,flist,vlist,slist,e in the
javacc preable to hold these subtrees.  
```
{
  Type ty;
  Token t;
  FormalList flist;
  VarDeclList vlist;
  StatementList slist;
  Exp e;
}
```
Then in the grammar rule, we capture the
subtrees from the nonterminals, which will all return classes in the syntaxtree package.
```
ty=Type() t=<ID> <LPAREN> flist=FormalList() <RPAREN>

      <LCURLY>
           vlist=VarDecls() 
           slist = Statements()
           <RETURN> e=Exp() <SEMICOLON>
      <RCURLY>
```
finally we use these subtrees to create the MethodDecl node, which we return to the caller
(probably the MethoDeclList())
```
  {return new MethodDecl(ty,new Identifier(t.image),flist,vlist,slist,e);}
```
Note that we create a new Identifier from the <ID> token, We could also have done this using
an Identifier() non-terminal instead of an <ID> token.

The code for the MethodDecl class is as shown below (from the [syntaxtree package](../../code/MiniC/syntaxtree).
It simply has fields for each of the components of a method declaration.

We use the same approach as shown here for all of the grammar rules. 
```
package syntaxtree;

public class MethodDecl {
    public Type t;
    public Identifier i;
    public FormalList f;
    public VarDeclList v;
    public StatementList s;
    public Exp e;

    public MethodDecl(Type t, Identifier i, FormalList f,
                       VarDeclList v,StatementList s, Exp e){
        this.t=t;this.i=i;this.f=f;this.v=v;this.s=s;this.e=e;
    }

    public Object accept(Visitor visitor, Object data) {
        return
            visitor.visit(this, data);
      }
    
}
```

For PA4, you will be asked to extend (and modify) the MiniC.jj file to get a MiniJava.jj file which will create the AST for a MiniJava program.
    
