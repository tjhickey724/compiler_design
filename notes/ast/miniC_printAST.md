# Using a Visitor to print the miniC AST
We can traverse a miniJava or miniC AST using the Visitor pattern, as described earlier.
For you convenience we have defined a default visitor that visits every node and does nothing,
but can easily be extended.
* [DefaultVisitor.java](../../code/MiniC/DefaultVisitor.java)

Here is the code for handing the MethodDecl. Let's have a look
```
    public Object visit(MethodDecl node, Object data){ 
        Type t=node.t;
        Identifier i=node.i;
        FormalList f=node.f;
        VarDeclList v=node.v;
        StatementList s=node.s;
        Exp e=node.e;
        node.t.accept(this, data);
        node.i.accept(this, data);
        if (node.f != null){
            node.f.accept(this, data);
        }
        if (node.v != null){
            node.v.accept(this, data);
        }
        if (node.s != null){
            node.s.accept(this, data);
        }
        node.e.accept(this, data);

        return data; 
    }
```
The first thing it does is to define variables for each of the children of the node.
Then it calls itself on each of its children using the Visitor pattern. The one place
we need to be careful is if the list nodes are empty, in which case the child variable
would be null.

To print the Abstract Syntax Tree, we just need to add to each of these rules some code
to indent (if necessary) to the proper level and then to print the name of the class.
We will pass the indentation level down in the "data" parameter. Let's look at the MethodDecl
for the AST_Visitor.java
```
 public Object visit(MethodDecl node, Object data){
        System.out.println(indentString() + getClassName(node));
        ++indent;
        node.t.accept(this,data);
        node.i.accept(this,data);
        if (node.f!=null){
            node.f.accept(this,data);
        }
        if (node.v!=null){
            node.v.accept(this,data);
        }
        if (node.s!=null){
            node.s.accept(this,data);
        }
        node.e.accept(this,data);
        --indent;
        return data;
   }
```
where indentString and getClassName are methods defined at the top of the AST_Visitor class
(with help from github copilot in vscode!)
```
 public class AST_Visitor implements Visitor
 {
   private int indent = 0;
 
   private String indentString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4*indent; ++i) {
        sb.append(' ');
        }
        return sb.toString();
   }

     /*
  getClassName(c) returns name of the Java class c without the package prefix
  */
  public static String getClassName(Object c){
     String s = c.getClass().getName();
     return s.substring(s.lastIndexOf('.')+1);
   }

...
```
We then modify the MiniC.jj file to create a visitor and call it on the AST as follows:
```
  public static void main(String args[]) {
    MiniC t = new MiniC(System.in);
    try {
      MethodDeclList n = t.Start();

      System.out.println("\n\nPretty Printing the Abstract Syntax Tree");
      Visitor v1 = new AST_Visitor();  // pretty prints the Abstract Syntax Tree
      n.accept(v1, 0);  // pass in 0 for the initial value of the data parameter

      System.out.println("\n\nDone!");

    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
```
