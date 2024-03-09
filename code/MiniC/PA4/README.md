# PA4

The learning goals of this assignment are for you to 
* learn how to use recursive descent to
  * generate an Abstract Syntax Tree
* learn how to write and use Visitors to traverse a syntax tree and perform useful operations such as
  * pretty printing
  * building a symbol table
  * type checking a program

We could do all of this using a javacc file just as we did for PA3, but you will see that using the Visitor pattern
is much simpler with code that is relatively easy to read, write, and modify.

This assignment has four parts corresponding to the four steps listed above.

## PA4a Generate an Abstract Syntax Tree
An Abstract Syntax tree is a tree where each node corresponds to a grammar rule. We usually don't use a much
simpler grammar for ASTs than we used for parsing because they don't need to be left-recursive or can even be
ambiguous. 

The syntax tree folder has a [README file][../syntaxtree/README.md) describing all of the constructors for the tree
and giving a simple example. 

For example, the Abstract Syntax Tree for the following simple program:
```
class Hello {
  public static void main(String[] args){
    {
     System.out.println(5*5);
    }
  }
}
```
could be constructed as follows.
```
new Program(
  new MainClass(
    new Identifier("Hello"),
    new Print(
      new Times(
        new IntegerLiteral(5),
        new IntegerLiteral(5)
      )
    )
 )
)
```
The javacc program MiniC_v4a constructs this abstract syntax tree for MiniC programs.
Your job is to extend it to MiniJava programs by adding code for the additional 8-12 rules.

This code can be constructed by taking the MiniC_v3 code for pretty printing, and modifying each rule
so that it returns an Abstract Syntax Tree instead of pretty print the program segment. For example,
the code for pretty printing a multiplication expression in MiniC_v3 is
```
void Exp11(int n):
{}
{ 
  Exp12(n) 
  ( 
    LOOKAHEAD(2) 
    "*" {print("*");} Exp12(n) 
   )* 
}
```
We can convert this to code to generate an Abstract Syntax Tree by follows:
```
Exp Exp11():
{Exp a,b;}
{ 
  a=Exp12() 
  ( 
    LOOKAHEAD(2) 
    "*" b=Exp12() 
    {a = new Times(a,b);}
   )* 
  {return(a);}
}
```
Notice what we have done:
1. the method now returns an Exp  (in this case a Times(Exp, Exp) object) instead of void
2. we get the parse trees from the two expressions in the rule and store them in variables "a" and "b"
3. for each "+ Exp" term that appears we create a new Times node in the tree
4. at the end we return either the original "a" expressions, if there were no multiplications at this level, or a Times node

For example, if we applied this rule to (2*3*4) we would get
```
  new Times(
    new Times( new IntegerLiteral(2), new IntegerLiteral(3))
    new IntegerLiteral(4)
  )
```


