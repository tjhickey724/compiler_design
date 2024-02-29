# Type Checking MiniC

Type errors can occur in operator or method or assignment statement. For example, the following miniC program has several type errors. Can you spot them?
```
int main(int x){
  bool b = test(x,x);
  print(b);
  b = x*2;
  x = x-b;
  x = b;
  return b;

int test(int y, bool c){
  int z = 0
  if (c)
    z = y;
  else
    z = c;
  return z
```

To implement the SymbolTableVisitor from the Default Visitor we must modify all of the methods
so that they return their type.  Grammatical constructs without a type,  for example, an if statement, will be given the type "*void"

Some methods will require accessing the symbol table to determine the return type of the node
and to check if the children of the node have the correct types, e.g.
* Call(exp,identifier,explist) - checks that the types of the explist agree with the types
  in the Method Declaration for '$identifier', and sets its type to the return type of the method
  and checks that the return type is "int" or "bool"
* Identifier - looks up its type in the symbol table (it appears on the left side of an assignment)
* IdentifierExp - looks up its type in the symbol table (it appears in expressions)
* LessThan - checks that its arguments are ints and return bool
* Plus, Minus, Times - check that their arguments are ints and returns int
* Formal - check that the "type" is int or bool
* VarDecl - check that the "type" is int or bool
* Print - check that the argument type is "int"

Let's look at some of these.  First the Times method. 
Observe that we get the types of the arguments by the recursive visitor calls
to node.e1 and node.e2. If there is an mismatch we report it, but in any case
we return the expected type "int".

```public Object visit(Times node, Object data){ 
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

Now let's look at the Call node, which must check that the arguments and the formals have the same type.
Consider for example the program above and lets look at the call
```
test(x,x)
```
To type check the call we have to find the types of the parameters in the original method declaration
so we need to look that up in the symbol table with
```
MethodDecl m = st.methods.get("$"+i.s);
```
The list of formals is then ```m.f``` and we get the types of the formals ```m.f``` of the method ```m``` 
by calling the visitor on ```m.f``` as so
``` String paramTypes = (String) m.f.accept(this,m.i.s);```
and the result in our case will be 
* "int bool"
  
Next we get the types of the actual arguments by calling
```
argTypes = (String) node.e2.accept(this, data);
```
as e2 is the ExpList of arguments for the call.

Here is the visitor method for the Call node:
```
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

        // argTypes is the concatenation of type of the expression
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
