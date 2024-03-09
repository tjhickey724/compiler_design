## PA4a Generate an Abstract Syntax Tree
An Abstract Syntax tree is a tree where each node corresponds to a grammar rule. We usually don't use a much
simpler grammar for ASTs than we used for parsing because they don't need to be left-recursive or can even be
ambiguous. 

The syntax tree folder has a [README file](./syntaxtree/README.md) describing all of the constructors for the tree
and giving a simple example. 

For example, the Abstract Syntax Tree for the following simple program:
```
class Hello {
  public static void main(String[] args){
    {
     System.out.println(5*5);
    }
  }
}
```
could be constructed as follows.
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
The javacc program MiniC_v4a constructs this abstract syntax tree for MiniC programs.
Your job is to extend it to MiniJava programs by adding code for the additional 8-12 rules.

This code can be constructed by taking the MiniC_v3 code for pretty printing, and modifying each rule
so that it returns an Abstract Syntax Tree instead of pretty print the program segment. For example,
the code for pretty printing a multiplication expression in MiniC_v3 is
```
void Exp11(int n):
{}
{ 
  Exp12(n) 
  ( 
    LOOKAHEAD(2) 
    "*" {print("*");} Exp12(n) 
   )* 
}
```
We can convert this to code to generate an Abstract Syntax Tree by follows:
```
Exp Exp11():
{Exp a,b;}
{ 
  a=Exp12() 
  ( 
    LOOKAHEAD(2) 
    "*" b=Exp12() 
    {a = new Times(a,b);}
   )* 
  {return(a);}
}
```
Notice what we have done:
1. the method now returns an Exp  (in this case a Times(Exp, Exp) object) instead of void
2. we get the parse trees from the two expressions in the rule and store them in variables "a" and "b"
3. for each "+ Exp" term that appears we create a new Times node in the tree
4. at the end we return either the original "a" expressions, if there were no multiplications at this level, or a Times node

For example, if we applied this rule to (2*3*4) we would get
```
  new Times(
    new Times( new IntegerLiteral(2), new IntegerLiteral(3))
    new IntegerLiteral(4)
  )
```
Now you need to do this for the 10-15 new rules of MiniJava that aren't in MiniC!
For example, the rule for Program from PA3 is
```
void Start(int n):
{}
{
  MainClass(n)
  ClassDeclList(n)
}
```
and this would change to
```
void Start(int n):
{
 MainClass m;
 ClassDeclList clist;
}
{
  m =MainClass(n)
  clist = ClassDeclList(n)
  {return new Program(m,clist);}
}
```
The MiniC_v4.jj file pretty prints its Abstract Syntax Tree using the AST_Visitor.java file.

You will also need to comment out those parts of the main method MiniC_v4.jj that correspond to PA4b,4c,4d as follows:
``` java
import syntaxtree.*;

public class MiniC {

  /** Main entry point. */
  public static void main(String args[]) {
    MiniC t = new MiniC(System.in);
    try {
      MethodDeclList n = t.Start();

      System.out.println("\n\nPretty Printing the Abstract Syntax Tree");
      Visitor v1 = new AST_Visitor();  // pretty prints the Abstract Syntax Tree
      n.accept(v1, 0);

/*  commenting this code out until PA4b
      System.out.println("\n\nPretty Print the Program");
      Visitor v2 = new PP_Visitor();  // pretty prints the MiniC program
      String s = (String) n.accept(v2, 0);
      System.out.println("#include <stdio.h>\n#include <stdbool.h>\nvoid print(int n){printf(\"%10d\\n\",n);}");
      System.out.println(s);
/*

/* commenting this code out until PA4c
      System.out.println("\n\nGenerating Symbol Table");
      SymbolTableVisitor v3 = new SymbolTableVisitor(); // generates a SymbolTable
      SymbolTable st = v3.symbolTable;
      n.accept(v3,"");
      System.out.println(st);
*/

/* commenting this code out until PA4d
      System.out.println("\n\nType Checking");
      TypeCheckingVisitor v4 = new TypeCheckingVisitor(st);
      n.accept(v4,"");
      System.out.println(v4.num_errors+" type errors found");
*/
      System.out.println("\n\nDone!");

    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }


}


```
