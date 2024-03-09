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

