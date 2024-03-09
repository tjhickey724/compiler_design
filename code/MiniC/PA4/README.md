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
