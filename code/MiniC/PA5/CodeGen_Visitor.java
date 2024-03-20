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
    private Visitor ppVisitor = new PP_Visitor();


    public Object visit(And node, Object data){ 
        // not in MiniC
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return "# AND not implemented\n"; 
    } 

    public Object visit(ArrayAssign node, Object data){ 
        // not in MiniC
        Identifier i = node.i;
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.i.accept(this,data);
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return "# ArrayAssign not implemented\n";
    } 

    public Object visit(ArrayLength node, Object data){ 
        // not in MiniC
        Exp e=node.e;
        node.e.accept(this, data);
        return "#Array Length not implemented\n"; 
    } 

    public Object visit(ArrayLookup node, Object data){ 
        // not in MiniC
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);
        return "#ArrayLookup not implemented\n"; 
    } 

    public Object visit(Assign node, Object data){ 
        Identifier i=node.i;
        Exp e=node.e;

        //node.i.accept(this, data);
        String expCode = (String) node.e.accept(this, data);

        String varName = currClass+"_"+currMethod+"_"+i.s;
        String location = varMap.get(varName);

        String result = 
            
            expCode
            + "#"+node.accept(ppVisitor,0)+"\n"
            + "popq %rax\n"
            + "movq %rax, "+location+"\n";
        
        return result; 
    } 

    public Object visit(Block node, Object data){ 
        StatementList slist=node.slist;
        String result="";
        if (node.slist != null){    
            result = (String) node.slist.accept(this, data);
        }
        return result; 
    } 

    public Object visit(BooleanType node, Object data){ 
        // no code generated
        return "# no code for BooleanType\n";
    } 

    public Object visit(Call node, Object data){ 
        // not implemented yet ...

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

        return "# Call not implemented\n";
    } 

    public Object visit(ClassDecl node, Object data){ 
        // not in MiniC
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

        return "# ClassDecl not implemented\n";
    } 

    public Object visit(ClassDeclList node, Object data){ 
        // not in MiniC
        ClassDecl c=node.c;
        ClassDeclList clist=node.clist;
        node.c.accept(this, data);
        if (node.clist != null){
            node.clist.accept(this, data);
        }

        return "#ClassDeclList not implemented\n";
    } 

    public Object visit(ExpGroup node, Object data){ 
        Exp e=node.e;
        String code = (String) node.e.accept(this, data);

        return code; 
    } 

    public Object visit(ExpList node, Object data){ 
        // not implemented yet, only need for Call
        Exp e=node.e;
        ExpList elist=node.elist;
        node.e.accept(this, data);
        if (node.elist != null){
            node.elist.accept(this, data);
        }
        return "#ExpList not implemented \n"; 
    }

    public Object visit(False node, Object data){ 
        // not implemented yet
        return "# False not implemented ";
    } 

    public Object visit(Formal node, Object data){ 
        // find location of formal in stack
        Identifier i=node.i;
        Type t=node.t;

        formalOffset += 8;
        String varName = currClass+"_"+currMethod+"_"+i.s;
        String location = formalOffset + "(%rbp)";
        varMap.put(varName, location);

        //node.i.accept(this, data);
        //node.t.accept(this, data);

        return "# " + varName+"->"+location+"\n"; 
    }

    public Object visit(FormalList node, Object data){ 
        // find locations in stack for all formals, return no code
        Formal f=node.f;
        FormalList flist=node.flist;
        String result="";
        result = (String) node.f.accept(this, data);
        if (node.flist != null) {
            result+="\n"+ (String) node.flist.accept(this, data);
        }
        
        

        return "# "+result+"\n"; 
    }

    public Object visit(Identifier node, Object data){ 
        // no code generated for identifiers
        String s=node.s;  

        return "#no code for identifiers\n"; 
    }

    public Object visit(IdentifierExp node, Object data){ 
        // lookup location of identifier in stack
        // push that value onto the stack
        String s=node.s;
        // lookup the location of the identifier
        String varName = currClass+"_"+currMethod+"_"+s;
        String location = varMap.get(varName);
        // push it into the stack
        String result = 
            "#"+node.accept(ppVisitor,0)+"\n"
            + "pushq "+location+" #"
            + "  "+node.accept(ppVisitor, 0)
            +"\n";

        return result ; 
    }

    public Object visit(IdentifierType node, Object data){ 
        // not in MiniC
        String s=node.s;

        return data; 
    }

    public Object visit(If node, Object data){ 
        // not implemented yet
        Exp e=node.e;
        Statement s1=node.s1;
        Statement s2=node.s2;
        node.e.accept(this, data);
        node.s1.accept(this, data);
        node.s2.accept(this, data);

        return "# If not implemented yet\n"; 
    }

    public Object visit(IntArrayType node, Object data){ 
        // not in MiniC
        return "# not in MiniC\n"; 
    }

    public Object visit(IntegerLiteral node, Object data){ 
        // push the constant into the stack
        int i=node.i;

        String result = 
        "#"+node.accept(ppVisitor,0)+"\n"
        + "pushq $" + i+"\n";

        return result; 
    }

    public Object visit(IntegerType node, Object data){ 
        // no code generated
        return "#no code generated for Type decls\n"; 
    }

    public Object visit(LessThan node, Object data){ 
        // not implemented yet
        Exp e1=node.e1;
        Exp e2=node.e2;
        node.e1.accept(this, data);
        node.e2.accept(this, data);

        return "# not implemented yet\n";
    }

    public Object visit(MainClass node, Object data){ 
        // not in MiniC
        Identifier i=node.i;
        Statement s=node.s;
        labelMap.put(i.s, "_main");
        System.out.println("_main:");

        node.i.accept(this, data);
        node.s.accept(this, data);

        return "# not implemented\n"; 
    }

    public Object visit(MethodDecl node, Object data){ 
        // generate code for a function declaration
        Type t=node.t;
        Identifier i=node.i;
        FormalList f=node.f;
        VarDeclList v=node.v;
        StatementList s=node.s;
        Exp e=node.e;

        String prologue="";
        String statementCode = "";
        String expressionCode = "";
        String epilogue = "";

        String formals="# formals\n";
        String locals="#locals\n";



        formalOffset = 8;
        String theLabel = i.s ; //labelMap.get(currClass) + i.s;
        labelMap.put(currClass + i.s, theLabel);

        currMethod = i.s;  // set "global variable"
        stackOffset = 0;

        

        //node.t.accept(this, data);
        //node.i.accept(this, data);

        // assign locations for formals
        if (node.f != null){
            formals += (String) node.f.accept(this, data);
        }

        

        // assign locations for local variables
        if (node.v != null){
            locals += (String) node.v.accept(this, data);
        }

        if (node.s != null){
            statementCode = (String) node.s.accept(this, data);
        }

        expressionCode = (String) node.e.accept(this, data);

        int stackChange = - stackOffset;

        prologue = 
        
        ".globl "+"_"+theLabel+"\n"
        +"_"+theLabel+":\n"
        +formals
        + "# prologue\n"
        + "pushq %rbp\n"
        + "movq %rsp, %rbp\n"
        + locals
        + "#make space for locals on stack\n"
        + "subq $"+stackChange+", %rsp\n";

        epilogue =
        "# epilogue\n"
        +   "popq %rax\n"
        +   "addq $"+stackChange+", %rsp\n"
        +   "movq %rbp, %rsp\n"
        +   "popq %rbp\n"
        // maybe insert code her to print out the return value
        +   "retq\n";

        return 
          prologue 
          + statementCode 
          +"# calculate return value\n"
          + expressionCode
          +epilogue; 
    }


    public Object visit(MethodDeclList node, Object data){ 
        // not implemented
        MethodDecl m=node.m;
        MethodDeclList mlist=node.mlist;
        String result = "";

        result = (String) node.m.accept(this, data);
        if (node.mlist != null) {
            result += (String) node.mlist.accept(this, data);
        }
        

        return result; 
    }   


    public Object visit(Minus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String minusCode = 
        
          e1Code
        + e2Code
        + "# minus:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "subq %rdx, %rax\n"
        +   "pushq %rax\n";

        return minusCode; 
    }

    public Object visit(NewArray node, Object data){ 
        // not in MiniC
        Exp e=node.e;
        node.e.accept(this, data);

        return "# NewArray not implemented\n";
    }


    public Object visit(NewObject node, Object data){ 
        // not in MiniC
        Identifier i=node.i;
        node.i.accept(this, data);

        return "# NewObject not implemented\n"; 
    }


    public Object visit(Not node, Object data){ 
        // not in MiniC
        Exp e=node.e;
        node.e.accept(this, data);

        return "#Not not implemented\n"; 
    }


    public Object visit(Plus node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String addCode = 
        
          e1Code
        + e2Code
        + "# plus:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "addq %rdx, %rax\n"
        +   "pushq %rax\n";

        return addCode; 
    }


    public Object visit(Print node, Object data){ 
        Exp e=node.e;
        String expCode = (String) node.e.accept(this, data);

        String result = 
        
            expCode
        + "# "+node.accept(ppVisitor, 0)
        +   "popq %rdi\n"
        +   "callq _print\n";

        return result;
    }


    public Object visit(Program node, Object data){ 
        // not in MiniC
        MainClass m=node.m;
        ClassDeclList c=node.c;
        node.m.accept(this, data);
        if (node.c != null){
            node.c.accept(this, data);
        }
        

        return "# Program not implemented\n"; 
    }


    public Object visit(StatementList node, Object data){ 
        Statement s=node.s;
        StatementList slist=node.slist;
        String result="";
        
        
        
        if (node.slist != null){
            result += (String) node.slist.accept(this, data);
        }
         result += "\n"+(String) node.s.accept(this, data);
        
        

        return result; 
    }


    public Object visit(This node, Object data){ 
        // not in MiniC
        return "# This not implemented\n";
    }



    public Object visit(Times node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);

        String timesCode = 
        
        e1Code
        + e2Code
        + "# times:"+node.accept(ppVisitor,0)+"\n"
        +   "popq %rdx\n"
        +   "popq %rax\n"
        +   "imulq %rdx, %rax\n"
        +   "pushq %rax\n";

        return timesCode; 
    }


    public Object visit(True node, Object data){ 
        // not in MiniC
        return "# True not implemented\n"; 
    }


    public Object visit(VarDecl node, Object data){ 
        // find and store location of local variable
        // don't generate any code.
        Type t=node.t;
        Identifier i=node.i;

        stackOffset -= 8;
        String location = (stackOffset) + "(%rbp)";
        String varName = currClass+"_"+currMethod+"_"+i.s;
        varMap.put(varName, location);
 
        //node.t.accept(this, data);
        //node.i.accept(this, data);

        return "# "+varName+"->"+location+"\n";
    }


    public Object visit(VarDeclList node, Object data){ 
        VarDecl v=node.v;
        VarDeclList vlist=node.vlist;
        String vars="";
        vars += (String) node.v.accept(this, data);
        if (node.vlist != null) {
            vars += node.vlist.accept(this, data);
        }
        return vars; 
    }

    public Object visit(While node, Object data){ 
        // not in MiniC
        Exp e=node.e;
        Statement s=node.s;
        node.e.accept(this, data);
        node.s.accept(this, data);

        return "# while not implemented\n"; 
    }

}

