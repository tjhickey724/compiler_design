# PA4c - Generate a Symbol Table for MiniJava programs

For this assignment I have given you the code SymbolTable_Visitor.java which defines a visitor to construct a SymbolTable for MiniC.
Your goal is to extend this to MiniJava.  The SymbolTable for MiniC is defined by the [SymbolTable.java](./SymbolTable.java) class
It basically consists of four hashmaps and a toString method to print it out nicely.
The first three hashmaps map identifiers to their Abstract Syntax Tree.
The last hashmap (typeName) maps identifiers to their type as a string, either "int", "boolean", or "*void" for MiniC

You will need to expand this by adding a HashMap for classes. and to extend the .toString method to print out class names too.

Also, the MiniC identifiers are prefixed by their method, so "x" appearing in method "test2" is stored as
``` $test2$x```
when you add classes you will need to prepend the class name as well, this is to disambiguate between the same identifier
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
## Practice
Let's get some practice... Try to calculate, by hand the symbol table for the following programs.
In three steps:
1. Find all declarations of identifiers in this program and list them with their full prefixes, e.g. $Demo3, $Demo3$main, $Test$log, $Test$init$d
2. Record the types of each declared identifier ($Demo= *class, $Test$log = *method $Test$init$d=boolean ...)
### Demo2a
```
class Demo2a {
    public static void main(String[] args){
       System.out.println((new Test()).test(57));
    }
  }
  class Test {
    int a;
    int[] log;
    boolean test(int b){
      Test d;
      boolean e;
      int c;
      a=1; c=2; e=true; d=this;
      System.out.println(a+c);
      return a<c;
    }  
  }
```

### Demo3
Here is a more complex example ..
```
class Demo3 {
    public static void main(String[] args){
        System.out.println((new Test()).start(5));
    }
}
class Test{
    boolean debugging;
    int[] log;
    int max;

    boolean init(boolean d,int m){
        debugging = d;
        max = m;
        log = new int[m];
        return d;
    }

    int start(int x){
        boolean b;
        Test t;
        int i;
        t = this;
        max=10;
        b=(0<max) && (max<10);
        t.init(b,max);
        i=0;
        while (i<max){
            log[i]=2*i-1;
            i=i+1;
        }
        return log[max-1];
    }

}

```
## Testing your Symbol Table Generator
The file [Demo.java](./Demo.java) has been constructed to use all of the grammatical structures in MiniJava,
(let me know if I missed anything).

If you run your Symbol Table Visitor on this code it should print out the following Symbol Table:
```
Main Class:
$Demo:syntaxtree.MainClass@3d4eac69

Classes:
$Debug: syntaxtree.ClassDecl@75b84c92, 
$PrintList: syntaxtree.ClassDecl@42a57993, 


Methods: 
$Debug$getDebugging: syntaxtree.MethodDecl@28d93b30, 
$Debug$getMe: syntaxtree.MethodDecl@6bc7c054, 
$Debug$init: syntaxtree.MethodDecl@232204a1, 
$PrintList$getData: syntaxtree.MethodDecl@7d4991ad, 
$PrintList$go: syntaxtree.MethodDecl@4aa298b7, 
$PrintList$printall: syntaxtree.MethodDecl@1b6d3586, 

Variables: 
$Debug$debugging: syntaxtree.VarDecl@45ee12a7, 
$Debug$init$oldVal: syntaxtree.VarDecl@7f31245a, 
$PrintList$data: syntaxtree.VarDecl@14ae5a5, 
$PrintList$debugging: syntaxtree.VarDecl@7ea987ac, 
$PrintList$getData$z: syntaxtree.VarDecl@677327b6, 
$PrintList$go$i: syntaxtree.VarDecl@6d6f6e28, 
$PrintList$go$oldVal: syntaxtree.VarDecl@2503dbd3, 
$PrintList$go$r: syntaxtree.VarDecl@330bedb4, 
$PrintList$go$size: syntaxtree.VarDecl@135fbaa4, 
$PrintList$go$sum: syntaxtree.VarDecl@74a14482, 
$PrintList$printall$r: syntaxtree.VarDecl@4554617c, 
$PrintList$printall$sum: syntaxtree.VarDecl@1540e19d, 
$PrintList$printall$test: syntaxtree.VarDecl@12a3a380, 
$PrintList$size: syntaxtree.VarDecl@4b67cf4d, 

TypeNames: 
 $Debug$debugging: boolean,
 $Debug$getDebugging$b: boolean,
 $Debug$getMe$b: boolean,
 $Debug$init$oldVal: boolean,
 $Debug$init$v: boolean,
 $Debug: *class,
 $Demo: *class,
 $PrintList$data: int[],
 $PrintList$debugging: Debug,
 $PrintList$getData$b: boolean,
 $PrintList$getData$z: int[],
 $PrintList$go$i: int,
 $PrintList$go$n: int,
 $PrintList$go$oldVal: boolean,
 $PrintList$go$r: int,
 $PrintList$go$size: int,
 $PrintList$go$sum: int,
 $PrintList$printall$n: int,
 $PrintList$printall$r: int,
 $PrintList$printall$sum: int,
 $PrintList$printall$test: boolean,
 $PrintList$size: int,
 $PrintList: *class,
```


