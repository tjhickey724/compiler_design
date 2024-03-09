# PA4

The learning goals of this assignment are for you to 
* learn how to use recursive descent to
  * generate an Abstract Syntax Tree
* learn how to write and use Visitors to traverse a syntax tree and perform useful operations such as
  * pretty printing
  * building a symbol table
  * type checking a program

We could do all of this using a javacc file just as we did for PA3, but you will see that using the Visitor pattern
is much simpler with code that is relatively easy to read, write, and modify.

This assignment has four parts corresponding to the four parts listed above.

## STEPS
1. copy the MiniC_v4.jj file to your folder, along with the syntaxtree folder, and the javacc.jar file as well
2. Rename MiniC_v4.jj to be PA4.jj and replace all occurrences of MiniC_v4 in the code with PA4
3. Follow the instructions below for PA4a to extend PA4.jj to generate ASTs for all of MiniJava,
4. Follow the instructions for PA4b to extend the PP_Visitor.java file to pretty print all of MiniJava, not just MiniC
5. follow the instructions for PA4c to extend the SymbolTable_Vistor.java file to build a symbol table for all of MiniJava
6. follow the instructions for PA4d to extend the TypeChecking_Visitor.java file to syntax check full MiniJava programs
7. upload your answers to PA4a,b,c,d and the associated videos as requested in the MasteryLearningApp probems

## PA4a Generate an Abstract Syntax Tree
An Abstract Syntax tree is a tree where each node corresponds to a grammar rule. We usually don't use a much
simpler grammar for ASTs than we used for parsing because they don't need to be left-recursive or can even be
ambiguous. 

The syntax tree folder has a [README file][../syntaxtree/README.md) describing all of the constructors for the tree
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
When you modify 

## PA4b - Pretty Printing with a Visitor
I have given you the PP_Visitor.java file which pretty prints a miniC program from the abstract syntax tree.
You are to modify it so that it pretty prints the output of your PA4.jj program. 

This is pretty straight forward. Consider the code below from the AST_Visitor for processing an if statement
```
 public Object visit(If node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.e.accept(this,data);
        node.s1.accept(this,data);
        node.s2.accept(this,data);
        --indent;
        return data;
   }
```
If it is given an If node, then it will
1. print the class name "If" with an appropriate indentation
2. invoke the visitor pattern (indented one step more) on the three children (node.e, node.s1, node.s2)
3. the recursive invocation will print those three AST trees

The PP_Visitor takes a different approach. Rather than print the code immediately, it returns a string which is then
printed in the main method of P4.java.  Here is the way we needed to modify the code for the If statement
```
   public Object visit(If node, Object data){
        // 
        int indent = (int) data; 
        String expr = (String)node.e.accept(this,indent+1);
        String s1 = (String) node.s1.accept(this,indent+1);
        String s2 = (String) node.s2.accept(this,indent+1);
        return indentString(indent)+
        "if ("+expr+")\n"+
        s1+
        indentString(indent)+"else\n"+
        s2;
   }
```
Observe that 
* the indentation level is being passed in the data parameter (which must be casted to int)
* we store the Strings representing pretty printed versions of the expression and the two statements in variables (expr, s1,s2)
* we use expr, s1, s2 to create the return string where we indent the statements and put them on their own line

You must use a similar approach for the 10-12 additional rules in MiniJava that are not in MiniC (or are different from MiniC)
* Program
* MainClass
* ClassDecl (and ClassDeclList)
* IntArrayType
* IdentifierType
* ArrayAssign
* While
* NewArray
* NewObject
* Not
* And
* This




