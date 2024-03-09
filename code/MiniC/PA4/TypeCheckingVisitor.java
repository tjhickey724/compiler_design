import syntaxtree.*;
import java.util.HashMap;

/*
 * TypeCheckingVisitor
 *    This currently only checks MiniC programs for type errors.
 * This is the visitor that will be used to check for type errors.
 * It has the method to traverse the syntax tree for each node type,
 * The constructor will take the symbol table as a parameter.
 * The data parameter will be the current prefix, initially the empty string ""
 * It will use the symbol table to check the types of the expressions and statements.
 * Errors will be reported to the standard output and the program will continue
 * as if there was no error, looking for more errors
 * 
 * The visit methods will return the type of the node as a string
 * and will use "*void"  for statements and nodes that don't have a type
 */
public class TypeCheckingVisitor implements Visitor {

    public SymbolTable st;

    public int num_errors=0;

    public static PP_Visitor miniC = new PP_Visitor();

    public TypeCheckingVisitor(SymbolTable st) {
        this.st = st;
    }

    public static String getTypeName(Object node){
        // we only recognize 3 types in miniC  int, boolean, and *void
        if (node.getClass().equals(syntaxtree.BooleanType.class)){
            return "boolean";
        }else if (node.getClass().equals(syntaxtree.IntegerType.class)){
            return "int";
        } else {return "*void";}
    }

    public Object visit(And node, Object data){ 
        // not in miniC
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        
        return "";
    } 

    public Object visit(ArrayAssign node, Object data){ 
        // not in miniC
        Identifier i = node.i;
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.i.accept(this,data);
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        return "*void";
    } 

    public Object visit(ArrayLength node, Object data){ 
        // not in miniC
        Exp e=node.e;
        node.e.accept(this, data);
        return data; 
    } 

    public Object visit(ArrayLookup node, Object data){ 
        // not in miniC
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    } 

    public Object visit(Assign node, Object data){ 
        Identifier i=node.i;
        Exp e=node.e;
        String t1 = (String) node.i.accept(this, data);
        String t2 = (String) node.e.accept(this, data);
        if (!t1.equals(t2)) {
            System.out.println("Assign Type error: " + t1 + " != " + t2+" in node"+node);
            System.out.println("in "+node.accept(miniC,0));
            num_errors++;
        }
        return "*void"; 
    } 

    public Object visit(Block node, Object data){ 
        StatementList slist=node.slist;
        if (node.slist != null){    
            node.slist.accept(this, data);
        }
        return "*void"; 
    } 

    public Object visit(BooleanType node, Object data){ 
        return "*void";
    } 

    public Object visit(Call node, Object data){ 
        // have to check that the method exists and that
        // the types of the formal parameters are the same as
        // the types of the corresponding arguments.
        //Exp e1 = node.e1; // in miniC there is no e1 for a call
        Identifier i = node.i;
        ExpList e2=node.e2;

        
        // first we get the method m that this call is calling
        MethodDecl m = st.methods.get("$"+i.s);
        
        // paramTypes is the type of the parameters of the method
        // e.g. "int int boolean int"
        // we get the parameter types by "typing" the formals
        // this is somewhat inefficient, we should do this
        // when we construct the symbol table....
        String paramTypes = (String) m.f.accept(this,m.i.s); 
        String argTypes = "";
        if (node.e2 != null){
            argTypes = (String) node.e2.accept(this, data);
        }
        if (!paramTypes.equals(argTypes)) {
            System.out.println("Call Type error: " + paramTypes + " != " + argTypes+" in method "+i.s);
            System.out.println("in \n"+node.accept(miniC,0));
            num_errors++;
        }

        return getTypeName(m.t);
    } 

    public Object visit(ClassDecl node, Object data){ 
        // not in miniC
        Identifier i = node.i;
        VarDeclList v=node.v;
        MethodDeclList m=node.m;
        node.i.accept(this, data);
        if (node.v != null){
            node.v.accept(this, data);
        }
        if (node.m != null){
            node.m.accept(this, data);
        }
        return data;
    } 

    public Object visit(ClassDeclList node, Object data){ 
        // not in miniC
        ClassDecl c=node.c;
        ClassDeclList clist=node.clist;
        node.c.accept(this, data);
        if (node.clist != null){
            node.clist.accept(this, data);
        }

        return data;
    } 

    public Object visit(ExpGroup node, Object data){ 
        Exp e=node.e;
        String result = (String) node.e.accept(this, data);

        return result; 
    } 

    public Object visit(ExpList node, Object data){ 
        // this return a list of the types of the expressions!
        Exp e=node.e;
        ExpList elist=node.elist;
        String t1 = (String) node.e.accept(this, data);
        String t2 = "";
        if (node.elist != null){
            t2 = (String) node.elist.accept(this, data);
        }
        return t1+" "+t2; 
    }

    public Object visit(False node, Object data){ 
        return "boolean";
    } 

    public Object visit(Formal node, Object data){ 
        Identifier i=node.i;
        Type t=node.t;
        //node.i.accept(this, data);
        //node.t.accept(this, data);  
        if (node.t instanceof BooleanType) {
            return "boolean";
        } else if (node.t instanceof IntegerType) {
            return "int";
        } else {
            System.out.println("Formal Type error: " + node.t + " is not a valid type");
            num_errors++;
            return "*void";
        }
    }

    public Object visit(FormalList node, Object data){ 
        Formal f=node.f;
        FormalList flist=node.flist;
        String t1 = (String) node.f.accept(this, data);
        String t2 = "";
        if (node.flist != null) {
            t2 = (String) node.flist.accept(this, data);
        }
        
        return t1+" "+t2; 
    }

    public Object visit(Identifier node, Object data){ 
        String s=node.s;  
        String result = st.typeName.get(data+"$"+s);

        return result; 
    }

    public Object visit(IdentifierExp node, Object data){ 
        String s=node.s;
        String result = st.typeName.get(data+"$"+s);

        return result; 
    }

    public Object visit(IdentifierType node, Object data){
        // not in miniC
        String s=node.s;

        return data; 
    }

    public Object visit(If node, Object data){ 
        Exp e=node.e;
        Statement s1=node.s1;
        Statement s2=node.s2;
        node.e.accept(this, data);
        node.s1.accept(this, data);
        node.s2.accept(this, data);

        return "*void"; 
    }

    public Object visit(IntArrayType node, Object data){
        return "int[]"; 
    }

    public Object visit(IntegerLiteral node, Object data){ 
        int i=node.i;

        return "int"; 
    }

    public Object visit(IntegerType node, Object data){ 
        return "int"; 
    }

    public Object visit(LessThan node, Object data){ 
        // not in miniC
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Comparison Type error: " + t1 + " != " + t2+" in node"+node);
            num_errors++;
        }

        return "boolean";
    }

    public Object visit(MainClass node, Object data){ 
        // not in miniC
        Identifier i=node.i;
        Statement s=node.s;
        node.i.accept(this, data);
        node.s.accept(this, data);

        return data; 
    }

    public Object visit(MethodDecl node, Object data){ 
        Type t=node.t;
        Identifier i=node.i;
        FormalList f=node.f;
        VarDeclList v=node.v;
        StatementList s=node.s;
        Exp e=node.e;
        //node.t.accept(this, data);
        //node.i.accept(this, data);
        if (node.f != null){
            //node.f.accept(this, data);
        }
        if (node.v != null){
            //node.v.accept(this, data);
        }
        if (node.s != null){
            node.s.accept(this, data+"$"+i.s);
        }
        
        String returnType = (String) node.e.accept(this, data+"$"+i.s);

        if (!returnType.equals(getTypeName(node.t))) {
            System.out.println("Method Return Type error: " + returnType + " != " + getTypeName(node.t)+" in method"+i.s);
            num_errors++;
        }

        return "*void"; 
    }


    public Object visit(MethodDeclList node, Object data){ 
        MethodDecl m=node.m;
        MethodDeclList mlist=node.mlist;
        node.m.accept(this, data);
        if (node.mlist != null) {
            node.mlist.accept(this, data);
        }
        

        return "*void"; 
    }   


    public Object visit(Minus node, Object data){ 
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

    public Object visit(NewArray node, Object data){ 
        // not in miniC
        Exp e=node.e;
        node.e.accept(this, data);

        return data; 
    }


    public Object visit(NewObject node, Object data){ 
        // not in miniC
        Identifier i=node.i;
        node.i.accept(this, data);

        return data; 
    }


    public Object visit(Not node, Object data){ 
        // not in miniC
        Exp e=node.e;
        node.e.accept(this, data);

        return data; 
    }


    public Object visit(Plus node, Object data){ 
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


    public Object visit(Print node, Object data){ 
        Exp e=node.e;
        String t1 = (String) node.e.accept(this, data);
        if (!t1.equals("int")) {
            System.out.println("Print Type error: " + t1 + " is not a valid type for print");
            num_errors++;
        }

        return "*void"; 
    }


    public Object visit(Program node, Object data){ 
        // not in miniC
        MainClass m=node.m;
        ClassDeclList c=node.c;
        node.m.accept(this, data);
        if (node.c != null){
            node.c.accept(this, data);
        }
        

        return data; 
    }


    public Object visit(StatementList node, Object data){ 
        Statement s=node.s;
        StatementList slist=node.slist;
        node.s.accept(this, data);
        if (node.slist != null){
            node.slist.accept(this, data);
        }
        

        return "*void"; 
    }


    public Object visit(This node, Object data){ 
        // not in miniC
        return data; 
    }



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


    public Object visit(True node, Object data){ 
        return "boolean"; 
    }


    public Object visit(VarDecl node, Object data){ 
        Type t=node.t;
        Identifier i=node.i;
        //node.t.accept(this, data);
        //node.i.accept(this, data);
        if (node.t instanceof BooleanType) {
            return "boolean";
        } else if (node.t instanceof IntegerType) {
            return "int";
        } else {
            System.out.println("Unknown Type, Type error: " + node.t + " is not a valid type");
            num_errors++;
            return "*void";
        }

    }


    public Object visit(VarDeclList node, Object data){ 
        VarDecl v=node.v;
        VarDeclList vlist=node.vlist;
        node.v.accept(this, data);
        if (node.vlist != null) {
            node.vlist.accept(this, data);
        }
        return "*void"; 
    }

    public Object visit(While node, Object data){ 
        // not in miniC
        Exp e=node.e;
        Statement s=node.s;
        node.e.accept(this, data);
        node.s.accept(this, data);

        return data; 
    }

}

