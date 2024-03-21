import syntaxtree.*;
import java.util.HashMap;

import org.omg.CORBA.SystemException;

/*
 * CodeGen_Visitor class
 * This class implements a naive code generation for the MiniJava language.
 * The parameters are all passed on the stack and all local variables are stored on the stack.
 * All expressions are evaluated using the stack and two registers rax and rdx
 * Conditionals tests are viewed as expressions returning 0 for false and 1 for true
 * This code can not be used to generate libaries that other C programs can call because
 * it doesn't follow the X86-64 calling convention.
 * The code is printed to standard output as we visit the nodes of the syntax tree.
 * 
 * We maintain a few hashmaps 
 * 1. labelMap: to keep track of the labels for class methods
 *     this will map method names to their labels
 * 2. varMap: to keep track of the local variables in a method
 *     this will map variable names to their negative offsets from the base pointer
 * 3. paramMap: to keep track of the parameters of a method
 *     this will map parameter names to their positive offsets from the base pointer
 * 
 * We also need some auxiliary variables to keep track of 
 * 1. currClass - the current class
 * 2. currMethod - the current method
 * 3. labelNum - the current label number, for use in loops and conditionals
 * 4. stackOffset - the current stack offset, to keep track of temporaries
 * 5. formalOffset - the current formal offset, to keep track of parameters
 * 
 * Once we get this naive method working we can look into building an optimizing compiler.
 */

import java.util.HashMap;

public class CodeGen_Visitor implements Visitor {

    private HashMap<String, String> labelMap = new HashMap<String, String>();
    private HashMap<String, String> varMap = new HashMap<String, String>();
    private HashMap<String, String> paramMap = new HashMap<String, String>();
    private String currClass = "";
    private String currMethod = "";
    private int labelNum = 0;
    private int stackOffset = 0;
    private int formalOffset = 0;


    public Object visit(And node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    } 

    public Object visit(ArrayAssign node, Object data){ 
        Identifier i = node.i;
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.i.accept(this,data);
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    } 

    public Object visit(ArrayLength node, Object data){ 
        Exp e=node.e;
        node.e.accept(this, data);
        return data; 
    } 

    public Object visit(ArrayLookup node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return data; 
    } 

    public Object visit(Assign node, Object data){ 
        Identifier i=node.i;
        Exp e=node.e;
        node.i.accept(this, data);
        node.e.accept(this, data);

        System.out.println("popq %rax");
        String varName = currClass+"_"+currMethod+"_"+i.s;
        String location = varMap.get(varName);
        System.out.println("movq %rax, " + location);
        
        return data; 
    } 

    public Object visit(Block node, Object data){ 
        StatementList slist=node.slist;
        if (node.slist != null){    
            node.slist.accept(this, data);
        }
        return data; 
    } 

    public Object visit(BooleanType node, Object data){ 
        return data;
    } 

    public Object visit(Call node, Object data){ 
        Exp e1 = node.e1;
        Identifier i = node.i;
        ExpList e2=node.e2;
        if (node.e1 != null){
            node.e1.accept(this, data);
        }
        node.i.accept(this, data);
        if (node.e2 != null){
            node.e2.accept(this, data);
        }

        return data;
    } 

    public Object visit(ClassDecl node, Object data){ 
        Identifier i = node.i;
        VarDeclList v=node.v;
        MethodDeclList m=node.m;

        String theLabel = i.s + "_"+(labelNum++);
        labelMap.put(i.s, theLabel);
        System.out.println(theLabel+":");
        currClass = i.s;

        
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
        node.e.accept(this, data);

        return data; 
    } 

    public Object visit(ExpList node, Object data){ 
        Exp e=node.e;
        ExpList elist=node.elist;
        node.e.accept(this, data);
        if (node.elist != null){
            node.elist.accept(this, data);
        }
        return data; 
    }

    public Object visit(False node, Object data){ 
        return data;
    } 

    public Object visit(Formal node, Object data){ 
        Identifier i=node.i;
        Type t=node.t;

        formalOffset += 8;
        String varName = currClass+"_"+currMethod+"_"+i.s;
        String location = formalOffset + "(%rbp)";
        varMap.put(varName, location);

        node.i.accept(this, data);
        node.t.accept(this, data);

        return data; 
    }

    public Object visit(FormalList node, Object data){ 
        Formal f=node.f;
        FormalList flist=node.flist;
        
        node.f.accept(this, data);
        if (node.flist != null) {
            node.flist.accept(this, data);
        }
        
        

        return data; 
    }

    public Object visit(Identifier node, Object data){ 
        String s=node.s;  

        return data; 
    }

    public Object visit(IdentifierExp node, Object data){ 
        String s=node.s;
        // lookup the location of the identifier
        String varName = currClass+"_"+currMethod+"_"+s;
        String location = varMap.get(varName);
        // push it into the stack
        System.out.println("pushq "+location);

        return data; 
    }

    public Object visit(IdentifierType node, Object data){ 
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

        return data; 
    }

    public Object visit(IntArrayType node, Object data){ 
        return data; 
    }

    public Object visit(IntegerLiteral node, Object data){ 
        int i=node.i;
        System.out.println("pushq $" + i);

        return data; 
    }

    public Object visit(IntegerType node, Object data){ 
        return data; 
    }

    public Object visit(LessThan node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        return data;
    }

    public Object visit(MainClass node, Object data){ 
        Identifier i=node.i;
        Statement s=node.s;
        labelMap.put(i.s, "_main");
        System.out.println("_main:");

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



        formalOffset = 8;
        String theLabel = i.s ; //labelMap.get(currClass) + i.s;
        labelMap.put(currClass + i.s, theLabel);
        System.out.println(".globl "+"_"+theLabel);
        System.out.println("_"+theLabel+":");
        currMethod = i.s;

        System.out.println("pushq %rbp");
        System.out.println("movq %rsp, %rbp");

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

        System.out.println("popq %rax");  
        System.out.println("movq %rbp, %rsp");
        System.out.println("popq %rbp");
        // maybe insert code her to print out the return value
        System.out.println("retq");

        return data; 
    }


    public Object visit(MethodDeclList node, Object data){ 
        MethodDecl m=node.m;
        MethodDeclList mlist=node.mlist;
        node.m.accept(this, data);
        if (node.mlist != null) {
            node.mlist.accept(this, data);
        }
        

        return data; 
    }   


    public Object visit(Minus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        System.out.println("popq %rdx");
        System.out.println("popq %rax");
        System.out.println("subq %rdx, %rax");
        System.out.println("pushq %rax");

        return data; 
    }

    public Object visit(NewArray node, Object data){ 
        Exp e=node.e;
        node.e.accept(this, data);

        return data; 
    }


    public Object visit(NewObject node, Object data){ 
        Identifier i=node.i;
        node.i.accept(this, data);

        return data; 
    }


    public Object visit(Not node, Object data){ 
        Exp e=node.e;
        node.e.accept(this, data);

        return data; 
    }


    public Object visit(Plus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        System.out.println("popq %rdx");
        System.out.println("popq %rax");
        System.out.println("addq %rdx, %rax");
        System.out.println("pushq %rax");

        return data; 
    }


    public Object visit(Print node, Object data){ 
        Exp e=node.e;
        node.e.accept(this, data);
        System.out.println("popq %rdi");
        System.out.println("callq _print");

        return data; 
    }


    public Object visit(Program node, Object data){ 
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
        
        
        
        if (node.slist != null){
            node.slist.accept(this, data);
        }
        node.s.accept(this, data);
        
        

        return data; 
    }


    public Object visit(This node, Object data){ 
        return data; 
    }



    public Object visit(Times node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        System.out.println("popq %rdx");
        System.out.println("popq %rax");
        System.out.println("imulq %rdx, %rax");
        System.out.println("pushq %rax");

        return data; 
    }


    public Object visit(True node, Object data){ 
        return data; 
    }


    public Object visit(VarDecl node, Object data){ 
        Type t=node.t;
        Identifier i=node.i;

        stackOffset -= 8;
        String location = (stackOffset) + "(%rbp)";
        String varName = currClass+"_"+currMethod+"_"+i.s;
        varMap.put(varName, location);
        
        System.out.println("movq $0, "+location);
        System.out.println("pushq" + " "+location);

 
        node.t.accept(this, data);
        node.i.accept(this, data);

        return data;
    }


    public Object visit(VarDeclList node, Object data){ 
        VarDecl v=node.v;
        VarDeclList vlist=node.vlist;
        node.v.accept(this, data);
        if (node.vlist != null) {
            node.vlist.accept(this, data);
        }
        return data; 
    }

    public Object visit(While node, Object data){ 
        Exp e=node.e;
        Statement s=node.s;
        node.e.accept(this, data);
        node.s.accept(this, data);

        return data; 
    }

}

