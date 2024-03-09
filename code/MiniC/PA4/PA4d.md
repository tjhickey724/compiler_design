# PA4  - Type Check a MiniJava program

I have given you the TypeChecking_Visitor.java file, which type checks MiniC programs.  As usual, you need to 
extend it to type check MiniJava programs. It expects to have access to the SymbolTable generated in PA4c and it
will use that to look for Type errors.

Let's see how it works on the Times(Exp,Exp) node
```
public Object visit(Times node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "int"; 
    }
```
