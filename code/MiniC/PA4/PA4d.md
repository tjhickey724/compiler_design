# PA4  - Type Check a MiniJava program

I have given you the TypeChecking_Visitor.java file, which type checks MiniC programs.  As usual, you need to 
extend it to type check MiniJava programs. It expects to have access to the SymbolTable generated in PA4c and it
will use that to look for Type errors.

## Type Checking
Type checking is a process that assigns a type to every subtree of the Abstract Syntax Tree and then tests to make sure
that certain typing rules hold..  Here are a few of the type rules:
* for Times(a,b) both a and b must have type "int" and the Times node will have type int
* for And(a,b) both a and b must have type "boolean" and the And node will have type boolean
* for an ArrayLookup(e1,e2)  e1 must have type int[] and e2 must have type int and the ArrayLookup node will have type int
* for a call  Call(F,(a1,...,an)) we need to look at the signature of the method F, i.e. find its return value T and the types of its formals T1,...,Tn. The expression list (a1,...,an) must have type (T1 .... Tn) and the type of the Call node will be T.


## Practice
Let's practice type checking with the following program
```
class Demo3 {
    public static void main(String[] args){
        System.out.println((new Test()).start(5));
    }
}
class Test{
    boolean debugging;
    int[] log;
    int max;

    boolean init(boolean d,int m){
        debugging = d;
        max = m;
        log = new int[m];
        return d;
    }

    int start(int x){
        boolean b;
        Test t;
        int i;
        t = this;
        max=10;
        b=(0<max) && (max<10);
        b=t.init(b,max);
        i=0;
        while (i<max){
            log[i]=2*i-1;
            i=i+1;
        }
        return log[max-1];
    }

}

```
  
## Example for a multiplication node
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
In this case, we get the two subnodes e1, e2 of the times node and invoke the visitor (this) on those two nodes
to get their types, which we expect to be "int"

If either one (or both) is not "int" then we generate a type error, but we return the expected type "int"
so that the type checking can continue and find all of the type errors.

## Pretty Printing errors...
The Type Checking Visitor defines a PP_Visitor variable called miniC (which you might want to change to be prettyPrint).
It can be invoked to generate a pretty printed version of the offending code, e.g. we can modify the above method as follows
to print out the expression that generated the error:
```
public Object visit(Times node, Object data){ 
        Exp e1=node.e1;
        Exp e2=node.e2;
        String t1 = (String) node.e1.accept(this, data);
        String t2 = (String) node.e2.accept(this, data);
        if (!t1.equals("int") || !t2.equals("int")) {
            System.out.println("Type error: " + t1 + " != " + t2+" in node"+node);
            System.out.println("in "+node.accept(miniC,0));
            num_errors++;
        }

        return "int"; 
    }
```

## Example for a Call node
Let's also look at the case of the Call node representing a function call
``` java
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
```


### Modifications of Call typing for MiniJava
You will need to modify this code to uncomment the line
``` java
//Exp e1 = node.e1; // in miniC there is no e1 for a call
```
and invoke the visitor on that node to get its expected type, which should be a class name
If it isn't then you print a type error and return some error type of your choice.

This needs to find the types of the arguments to the call with this code:
``` java
        String argTypes = "";
        if (node.e2 != null){
            argTypes = (String) node.e2.accept(this, data);
        }
```
where node.e2 is the ExprList for the call, e.g. for addvals(1,3)  it would be 
``` java
new ExprList(
     new IntegerLiteral(1),
     new ExprList(
        new IntegerLiteral(3),
        null))
```
We invoke the TypeChecker on this and it will return a list of the types (as a string "int int" in our case)

Next we need to figure out which types the method is expecting to find. We do that by looking at the formal arguments
in its list of parameters. we find this by looking up the AST for the method with our symbolTable.
``` java
MethodDecl m = st.methods.get("$"+i.s);
```
and we need to append the "$" to the method name "i.s"
Note, you will need to also append the name of the class, which you'll get by type checking "e1"

We can then get the formals from the MethodDecl objects with "m.f" and find the parameter types by invoking the visitor
on this list of formals:
``` java
 String paramTypes = (String) m.f.accept(this,m.i.s); 
```

Finally, we test to see if the argtypes and paramtypes are the same (e.g. both are "int int")
and if not we print a Type Error message.

In any case, we look up the type of value the method is supposed to return from its AST "m"
and we return that value.

## Instance Variables
One tricky part of this assignment is dealing with instance variables.
Consider, for example, the following simple program.
```
class Test {
  public static void main(String[] args){
     return (new Demo()).go(1)
}
class Demo {
  int a;
  Demo d;
  int go(int n){
        a=1;
        a=a+n;
        return a;
  }  
}
```
When the variable "a" will be encoded in the Symbol Table as $Demo$a  but it is being called in the method go where
"n" is encoded as $Demo$go$n.  When you are looking up the type of "a" you would initially look for any variables "a"
defined in the current method, so the prefix would be "$Demo$go$a$  but this won't exist in the symbol table, so you'll need
to pop off the last environment "$go$, and look up "$Demo.a$ which it will find and which has type "int".

## Type Checking Rules
We can write the type checking rules in the following shorthand, where we use *void to mean the node doesn't have a type.
For each class in the package syntax tree we need to determine the type rules it must follow. Here are a few ...
You'll need to work them all out based on your understanding of Java typing.
* Times(int,int)-> int
* Plus(int,int) -> int
* Minus(int, int) -> int
* And(boolean, boolean) -> boolean
* Not(boolean) -> boolean
* If(boolean, *void, *void) -> *void
* While(boolean, *void) -> *void
* Class(*class,*void,*void) -> *void
* ExpList(e,elist) -> e+" "+elist  (so Explist("int","bool bool") -> "int bool bool"
* Call(method(T,(T1,..,Tn)), (T1,...,Tn)) -> T
* Assign(a,b) a,b must be same type
* IdentifierExp(i) -> symbolTable.get(i) unless it is null, in which case look up symboltable.get(parent(i))
* etc. for each class in the syntaxtree package



## What you need to do
In addition to getting the type checker to work for "Call" nodes as described above, you also need too handle the following cases
* newObject(Identifier i)
* newArray(Exp e)
* ArrayLookup(Exp e1, Exp e2)
* ArrayLength(Exp e)
* and possibly others...

and for each you will need to see if there is a type error (e.g. finding the length of a non-array)
and return the expected type (e.g. an "int" for an ArrayLookup)

## Updating the PA4.jj main file
You will also need to uncomment the relevant parts of PA4.jj so that it
* generates the AST ```Program n = t.Start();```
* creates the SymbolTableVisitor ```SymbolTableVisitor v3 = new SymbolTableVisitor();```  
* creates the SymbolTable ```SymbolTable st = v3.symbolTable; n.accept(v3,"");```
* creates the TypeCheckingVisitor ```TypeCheckingVisitor v4 = new TypeCheckingVisitor(st);```
* invokes the TypeChecker on the syntax tree ```n.accept(v4,"");```
* prints out the number of errors ```System.out.println(v4.num_errors+" type errors found");```


