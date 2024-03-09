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

You will need to uncomment part of the code in the PA4.jj main method that creates the PP_visitor so it will call the Visitor on the tree:
This code does the following:
1. create a PP_Visitor ```Visitor v2 = new PP_Visitor();```
2. run it on the abstract syntax tree and store the resulting string ```String s = (String) n.accept(v2, 0);```
3. print the C Preface (you will need to delete this line)
4. print out the string ```System.out.println(s);```


```
PARSER_BEGIN(MiniC)
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

/* the following five lines have been uncommented */
      System.out.println("\n\nPretty Print the Program");
      Visitor v2 = new PP_Visitor();  // pretty prints the MiniC program
      String s = (String) n.accept(v2, 0);
      // comment out the following line when processing MiniJava not MiniC
      System.out.println("#include <stdio.h>\n#include <stdbool.h>\nvoid print(int n){printf(\"%10d\\n\",n);}");
      System.out.println(s);

/* commenting code out for PA4c ... */
/* commenting code out for PA4d ... */

      System.out.println("\n\nDone!");

    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }


}

PARSER_END(MiniC)

```
