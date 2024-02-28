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


    
    
