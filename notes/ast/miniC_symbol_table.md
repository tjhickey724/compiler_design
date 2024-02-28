# Computing a Symbol Table for a miniC program
A symbol table is a map from identifiers to their "types". Our approach will be to implement it using hashtables for each of the major types:
* methods
* formal parameters
* local variables

and to have another HashMap that returns the string representation of the type of a variable.

Naming is a little tricky because two different methods can have the same variables names with different types, e.g.
```
int test(int x){
  int a;
  boolean b;
  if (b) return a;
}

int test2(boolean x){
  boolean a;
  int b;
  if (a) return b;
}
```
Our approach will be to prepend the method name for all formals and local variables, so the keys of the symbol table for this program would be
```
$test$x  of type int
$test$a  of type int
$test$b  of type boolean
$test2$x of type boolean
$test2$a of type boolean
$test2$b of type int
$test  of type MethodDecl
$test2 of type MethodDecl
```
When we store and lookup types we will need to prepend the prefix...

The [SymbolTable.java]](../../code/MiniC/SymbolTable.java) code defines a simple class which creates those four HashMaps 
and includes a toString to print them out.

We can now define a Visitor which will populate the SymbolTable each time it finds a MethodDecl, VarDecl, or Formal
[SymbolTableVisitor.java](../../code/MiniC/SymbolTableVisitor.java)




