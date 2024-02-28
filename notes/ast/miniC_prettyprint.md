# Pretty Printing the miniC program
To pretty print the program we create a visitor which returns the pretty printed version of each node.
* [PP_Visitor.java](../../code/MiniC/PP_Visitor.java)

This is pretty straightforward.  Let's look again at the visit method for handing the MethodDecl nodes:
```
public Object visit(MethodDecl node, Object data){
        // 
        int indent = (int) data; 
        String type = (String) node.t.accept(this,indent);
        String id = (String) node.i.accept(this,indent);
        String formalList = "";
        if (node.f!=null){
            formalList = (String) node.f.accept(this,indent);
        }
        String varList = "";
        if (node.v!=null){
            varList = (String) node.v.accept(this,indent+1);
        }
        String statementList = "";
        if (node.s!=null){
            statementList = (String) node.s.accept(this,indent+1);
        }
        String expr = (String) node.e.accept(this,indent);
        return 
        indentString(indent)+
        type+" "+id+"("+formalList+"){\n"+
        varList +
        statementList+
        indentString(indent+1)+"return "+expr +";\n"+
        indentString(indent)+"}\n";
   }
```
We pass in the indent level in the data parameter as with AST_Visitor, but we create String variables for
each child node of the Method Declaration and the in the end create a big string representing the pretty printed
version of that method.

In class we will have you extend the miniC language by adding some features, and then pretty printing them,
e.g. adding integer arrays!
