# PA4c - Generate a Symbol Table for MiniJava programs

For this assignment I have given you the code SymbolTable_Visitor.java which defines a visitor to construct a SymbolTable for MiniC.
Your goal is to extend this to MiniJava.  The SymbolTable for MiniC is defined by the [SymbolTable.java](./SymbolTable.java) class
It basically consists of four hashmaps and a toString method to print it out nicely.
The first three hashmaps map identifiers to their Abstract Syntax Tree.
The last hashmap (typeName) maps identifiers to their type as a string, either "int", "boolean", or "*void" for MiniC

You will need to expand this by adding a HashMap for classes. and to extend the .toString method to print out class names too.

Also, the MiniC identifiers are prefixed by their method, so "x" appearing in method "test2" is stored as
``` $test2$x```
when you add classers you will need to prepend the class name as well, this is to disambiguate between the same identifier
appearing in different methods and classes, e.g.
```
class test { int x; int incr(int y){x = x+y;}}
class test2 {int y; int incr(int x){x = x+y;}}
```
the identifiers stored in the typeName hashmap for this part of the program would be
```
$test$x        -> int
$test$incr$y   -> int
$test2$y       -> int
$test2$incr$x  -> int
```

The SymbolTable_Visitor for MiniC keeps track of the prefix it needs to add and updates the symbol table for three kinds of nodes
* Formal   which defines types of parameters of functions
* MethodDecl   which stores the AST for the method in the methods hash table
* VarDecl  which defines the local variables of a function

You will need to extend this to ClassDecl nodes.

You will also need to uncomment the code in the PA4.jj file that 
creates the SymbolTable_Visitor, runs it on the Abstract Syntax Tree, and prints the result, as shown below.
``` java
import syntaxtree.*;
public class MiniC {

  /** Main entry point. */
  public static void main(String args[]) {
    MiniC t = new MiniC(System.in);
    try {
      MethodDeclList n = t.Start();

/* uncomment this only for PA4a
...
*/

/* uncomment this only for PA4b
...
*/

/* uncomment this only for PA4c and PA4d */
      System.out.println("\n\nGenerating Symbol Table");
      SymbolTableVisitor v3 = new SymbolTableVisitor(); // generates a SymbolTable
      SymbolTable st = v3.symbolTable;
      n.accept(v3,"");
      System.out.println(st);


/* uncomment this only for PA4d
...
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

```


