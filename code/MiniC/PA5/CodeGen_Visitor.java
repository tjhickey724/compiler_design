import syntaxtree.*;
import java.util.HashMap;


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
 * 5. numFormals - the current number of formal parameters processed
 * 6. formalOffset - the current formal offset, to keep track of parameters
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
    private int stackOffset = -24;
    private int formalOffset = 0;
    private int numFormals = 0;
    private Visitor ppVisitor = new PP_Visitor();
    private String[] regFormals= //registers for first six formals...
        {"%rdi","%rsi","%rdx","%rcx","%r8","%r9"}; 


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

    public int getNumArgs(ExpList e){
        /*
         * for the ith argument to the call, store it in
         * regFormals[i] starting with i=0
         */
        if (e==null){
            return 0;
        } else {
            return 1 + getNumArgs(e.elist);
        }
    }

    public Object visit(Call node, Object data){ 
        /*
        * For this implementation, we will assume there are
        * at most 6 arguments to the call. In which case,
        * all arguments get passed into registers. 
        * So we will pop the arguments off of the stack
        * and push them into the appropriate registers.
        * ExpList will generate code that evaluates the arguments
        * and pushes them into the stack. If the args are a1,...,ak
        * Then we first pop off a1 and the last to pop off is ak
        * We store them in "rdi", "rsi", ... which are in the
        * regFormals array
        */

        Exp e1 = node.e1;
        Identifier i = node.i;
        ExpList e2=node.e2;
        int numArgs = getNumArgs(e2);

        String evalArgsCode = "";
        String storeArgsCode = "";
        String makeCall = "";

        if (node.e1 != null){
            node.e1.accept(this, data);
        }
        //node.i.accept(this, data);  // don't generate code for the identifier

        if (node.e2 != null){
            evalArgsCode = (String) node.e2.accept(this, data);
        }

        
        for (int j =0; j<numArgs; j++){
            storeArgsCode += 
              "popq "+regFormals[j]+"\n";
        }

        makeCall = 
              "# calling "+i.s+"\n"
            + "callq _"+i.s+"\n"  // using function name as label with "_" prefix
            + "pushq %rax\n"; // push the result of the function on the stack

        return 
            evalArgsCode +
            storeArgsCode +
            makeCall;

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
        /*
         * ExpList is a left-associative tree, so
         * if node represents a1,a2,...,ak
         * then node.e = ak and
         * node.elist = a1,....,a(k-1)
         * This code will push ak first and a1 last
         * so when we pop, a1 will be the first to be popped
         * and ak the last
         */
        Exp e=node.e;
        ExpList elist=node.elist;
        String result="";

        result = (String) node.e.accept(this, data);
        if (node.elist != null){
            result += (String) node.elist.accept(this, data);
        }
        return result; 
    }

    public Object visit(False node, Object data){ 
        // not implemented yet
        return "# False not implemented ";
    } 

    public Object visit(Formal node, Object data){ 
        // find location of formal in stack
        /*
         * The first six parameters are passed in registers
         * rdi,rsi, rdx,rcx, r7, r8
         * the rest are passed on the stack above rbp 
         * 16(%rbp), 24(%rbp), ..
         * This compiler will copy the parameters in register
         * into the stack frame, so they will be treated
         * as locals!
         */
        Identifier i=node.i;
        Type t=node.t;


        String varName = currClass+"_"+currMethod+"_"+i.s;
        String location;

        formalOffset += 8;
        location = formalOffset + "(%rbp)";

        // if (numFormals < 6){
        //     stackOffset -= 8;
        //     location = (stackOffset) + "(%rbp)";
        //     /*
        //      * up to the first six formals are passed in registers
        //      * and we will the copy them to the stack frame as locals
        //      */
        // } else {
        //     location = (2+numFormals-6)*8 + "(%rbp)";
        //     /*
        //      * formals from 7 on are store in offsets 16,24,32, ... above rbp
        //      */
        // }
        // numFormals += 1;
        // formalOffset += 8;
        
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
        // evaluate e, jmp to s1 if true, s2 if false, 
        // jump to end after either case
        Exp e=node.e;
        Statement s1=node.s1;
        Statement s2=node.s2;
        String eCode = (String) node.e.accept(this, data);
        String thenCode= (String) node.s1.accept(this, data);
        String elseCode = (String) node.s2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return 
         "# conditional statement\n" 
         + eCode 
         + "popq %rax\n"
         + "cmpq	$1, %rax\n"
         + "jne "+label1+"\n"
         + thenCode
         + "jmp "+label2+"\n"
         + label1+":\n"
         + elseCode
         + label2+":\n";
        
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
        /* We implement this in an inefficient but simple way.
         * where we evaluate the two expressions in A<B
         * and push them into the stack as values "a" and "b"
         * then we do a comparison of "a" and "b"
         * and if "a"<"b" we push 1 onto the stack, else we push 0
         * So the result of LessThan is either 1 (if true) or 0 (if false)
         * This is what C does, it treats 0 as False and nonzero as True
         */
        Exp e1=node.e1;
        Exp e2=node.e2;
        String e1Code = (String) node.e1.accept(this, data);
        String e2Code = (String) node.e2.accept(this, data);
        String label1 = "L"+labelNum;
        labelNum += 1;
        String label2 = "L"+labelNum;
        labelNum += 1;

        return 
         "# "+node.accept(ppVisitor, data)+"\n"
         + e1Code
         + e2Code
         + "# compare rdx<rax and push 1 on stack if true, 0 else\n"
         + "popq %rdx\n"      // pop 2nd expr value B into rdx
         + "popq %rax\n"      // pop 1st expr value A into rax
         + "cmpq %rdx, %rax\n"// compare the two values
         + "jge "+label1+"\n" // if  A >= B jump to label1
         + "pushq $1\n"       // otherwise A < B, so push 1 on stack
         + "jmp "+label2+"\n" // and jump to the end of this code
         + label1+":\n"       // in this case, A >= B, so
         + "pushq $0\n"       // push 0 on stack
         + label2+":\n" ;      // both branches end up here
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

    public int getFormalsLocations(FormalList fs){
        /*
         * getFormals returns the number of formals in the FormalList
         * and uses varMap to assign a stack frame location to each formal variable
         */
        if (fs==null){
            return 0;
        } else {
            int n = getFormalsLocations(fs.flist);
            String varName = currClass+"_"+currMethod+"_"+fs.f.i.s;
            int offset = 8*(n+1);
            String location = "-"+offset+"(%rbp)";
            varMap.put(varName,location);
            return n+1;
        }
    }

    public String copyFormals(FormalList fs,int n){
        /*
         * fs.f is the last Formal in the list and
         * fs.flist is the list of earlier formals
         * n is the number of formals in the FormalList
         * so regFormals[n-1] is the register it is stored in
         */
        if (fs==null){
            return "";
        } else {
            String code = copyFormals(fs.flist,n-1);
            String formalName = currClass+"_"+currMethod+"_"+fs.f.i.s;
            String location = varMap.get(formalName);
            String register = regFormals[n-1];
            code += 
                "movq "+register+", "+location
                     +"  # formal "+formalName+"->"+location+"\n";
            return code;
        }
    }

    public int getLocalsLocations(VarDeclList vs,int numFormals){
        /*
         * getLocals returns the number of local variables s in the VarList
         * and uses varMap to assign a stack frame location to each local variable
         */
        if (vs==null){
            return 0;
        } else {
            int n = getLocalsLocations(vs.vlist,numFormals);
            String varName = currClass+"_"+currMethod+"_"+vs.v.i.s;
            int offset = 8*(n+1+numFormals);
            String location = "-"+offset+"(%rbp)";
            varMap.put(varName,location);
            return n+1;
        }
    }

    public String initializeLocals(VarDeclList vs){
        /*
         * look up location of each local variable
         * and store zero in that location
         */
        if (vs==null){
            return "\n";
        }else {
            String varName = currClass+"_"+currMethod+"_"+vs.v.i.s;
            String location = varMap.get(varName);
            String initCode = initializeLocals(vs.vlist);
            initCode +=
               "movq $0, "+location+"  # "+varName+"->"+location+"\n";
            return initCode;
        }
    }

    public Object visit(MethodDecl node, Object data){ 
        /*
         * The method will have its arguments passed in registers
         * (and we assume for now at most 6 arguments)
         * Assume there are r arguments corresponding to the
         * 4 formal parameters of the method a1,..., a4
         * 
         * Our implementation will copy the r arguments to the
         * first 4 slots in the frame:
         * -8(%rbp), -16(%rbp), -24(%rbp) etc.
         * and we will store these locations
         * (whose names a1,...,ar are stored in node.f) 
         * using the HashMap varMap. So ai will be in -M(%rbp)
         * where M = 8*i
         * 
         * Next we will initialize the local variables b1,...,bs
         * defined in node.v and store 0 in all of them
         * They will be in the next s slots of the frame
         * The jth local variable will be in -N(%rbp)
         * where N = 8*(r+j)
         * 
         * Next we generate the code for the statements stored in node.s
         * and finally evaluate the expression node.e and return it.
         * 
         * As usual we need a prologue to store the old base pointer %rbp
         * and set it to the new frame, and then to allocate r+s slots
         * in the stack frame for parameters and locals...
         */
        Type t=node.t;
        Identifier i=node.i;
        FormalList f=node.f;
        VarDeclList v=node.v;
        StatementList s=node.s;
        Exp e=node.e;

        currMethod = i.s;  // set "global variable for the current method"

        String prologueCode="";
        String statementCode = "";
        String expressionCode = "";
        String epilogueCode = "";

        // get frame locations for formals and locals 
        // and store in the HashMap  varMap
        // and count the number of formals and locals
        int numFormals = getFormalsLocations(node.f);
        int numLocals = getLocalsLocations(node.v,numFormals);

        // create label for this method and store in labelMap
        String fullMethodName = currClass+"_"+i.s;
        String theLabel = fullMethodName;
        labelMap.put(fullMethodName, theLabel);

        stackOffset = -8*(numFormals+numLocals); // reserve space for formals/locals

        //node.t.accept(this, data);
        //node.i.accept(this, data);

        // if (node.f != null){
        //     node.f.accept(this, data);
        // }

        String copyFormalsCode = 
            copyFormals(node.f,numFormals);
        
        String initializeLocalsCode =
            initializeLocals(node.v);
        

        // if (node.v != null){
        //     node.v.accept(this, data);
        // }

        if (node.s != null){
            statementCode = (String) node.s.accept(this, data);
        }

        expressionCode = (String) node.e.accept(this, data);

        int stackChange = (- stackOffset);

        // round stackChange up to the nearest multiple of 16
        stackChange = ((int)Math.ceil(stackChange/16.0))*16;

        /* VERY SUBTLE POINT!!!
         * before a function call, 
         * the stack must be aligned to a 16 byte boundary
         * so we round up the stack change to the nearest multiple of 16
         * When the call is made the return address is pushed onto the stack
         * then the base pointer is pushed on to the stack
         * So the stack pointer is a multiple of 16 as the return address
         * and the base pointer are 8 byte values.
         * Next we allocate space in the stack frame for the local variables
         * and that must be a multiple of 16
         * When we prepare for a function call we push the arguments onto the
         * stack. It is usually better to just expand the stack frame
         * by a mutiple of 16 and then move the arguments after the 6th
         * to the correct position relative to the stack pointer, i.e.
         * (%rsp), 8(%rsp), 16(%rsp), 24(%rsp), ...
         * For now, we can assume that we will never have more than 6 arguments
         */

        prologueCode = 
         "\n\n\n"
        +".globl "+theLabel+"\n"
        + theLabel+":\n"   
        + "# prologue\n"
        + "pushq %rbp\n"
        + "movq %rsp, %rbp\n"
        + "# make space for locals on stack, must be multiple of 16\n"
        + "subq $"+(stackChange)+", %rsp\n"
        + "# copy formals in registers to stack frame\n" 
        + copyFormalsCode
        + "# initialize local variables to zero\n"
        + initializeLocalsCode;
        

        epilogueCode =
        "# epilogue\n"
        +   "popq %rax\n"
        +   "addq $"+(stackChange)+", %rsp\n"
        +   "popq %rbp\n"
        +   "retq\n";

        return 
          prologueCode 
          + statementCode 
          +"# calculate return value\n"
          + expressionCode
          +epilogueCode; 
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
        +   "movb	$0, %al\n"  
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

