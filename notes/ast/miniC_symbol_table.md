# Computing a Symbol Table for a miniC program

## Defining the symbol table
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
  b = test2(a);
  if (b) return a;
}

int test2(boolean x){
  boolean a;
  int b;
  if (a) {
    b = test(a<0);
    return b;
  }
}
```
What are the type errors in this program (if any...)?

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

The [SymbolTable.java](../../code/MiniC/SymbolTable.java) code defines a simple class which creates those four HashMaps 
and includes a toString to print them out.

## Generating a Symbol Table with a Visitor

We can now define a Visitor which will populate the SymbolTable each time it finds a MethodDecl, VarDecl, or Formal
[SymbolTableVisitor.java](../../code/MiniC/SymbolTableVisitor.java)

For miniC the only grammar rules we need to modify in the DefaultVisitor are:
* Formal  - for the names of formal parameters in methods
* MethodDecl - for the names of the methods and their return types and lists of formals
* VarDecl - for the names and types of the local variables

In each case we simply create the extended name for the identifier (by adding a prefix indicating
its location in a method), and then put it into the SymbolTable.. 

### Processing Method Declarations
Here is the modified code for the MethodDecl.  It shows where we extend the prefix for
declarations inside the method. Also, we add a link to the MethodDecl into our Symbol Table
so that when type checking a method call we can look up the types and position of the formal
parameters to make sure they agree with the types of the arguments, and we can check that the
return type matches its context... 

```
public Object visit(MethodDecl node, Object data){ 
        Type t=node.t;
        Identifier i=node.i;
        FormalList f=node.f;
        VarDeclList v=node.v;
        StatementList s=node.s;
        Exp e=node.e;
        String data2 = data + "$" + i.s;  // we change the prefix when processing formals and decls
        //node.t.accept(this, data2);  // we don't need to traverse the type or identifier 
        //node.i.accept(this, data2);
        node.f.accept(this, data2);  // processing the formals (which can't be empty...)
        if (node.v != null) {
            node.v.accept(this, data2);  // processing the variable declarations (which can be empty)
        }
        //node.s.accept(this, data2);
        //node.e.accept(this, data2);

        symbolTable.methods.put(data + "$" + i.s, node);
        return data; 
    }
```

### Processing Local Variable Declarations
Processing a VarDecl is even simpler - we create the extended name and 
put the VarDecl node in a symbol table as well as the type name.
```
    public Object visit(VarDecl node, Object data){ 
        Type t=node.t;
        Identifier i=node.i;
        node.t.accept(this, data);
        node.i.accept(this, data);
        symbolTable.variables.put(data + "$" + i.s, node);
        symbolTable.typeName.put(data + "$" + i.s, getTypeName(t));

        return data;
    }
```

When you extend this to MiniJava, you will need to add another layer of context for the Class
that the Methods, Formals, and Locals appear in. You'll also need to expand the types to include
int[] and user-defined classes.

## Invoking the Symbol Table Generator from javacc
Below is the modified code from the main method in the MiniC.jj file which creates the SymbolTableVisitor and invokes it with the empty context "" as its data parameter, and 
prints out the Symbol Table after processing:

```
public static void main(String args[]) {
    MiniC t = new MiniC(System.in);
    try {
      MethodDeclList n = t.Start();

      System.out.println("\n\nGenerating Symbol Table");
      SymbolTableVisitor v3 = new SymbolTableVisitor(); // generates a SymbolTableVisitor
      SymbolTable st = v3.symbolTable;
      n.accept(v3,"");  // create the symbol table starting with empty context prefix: ""
      System.out.println(st);  // print out the symbol table

      System.out.println("\n\nDone!");

    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
```

