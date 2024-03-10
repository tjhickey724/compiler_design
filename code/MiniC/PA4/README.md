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

This assignment has four parts corresponding to the four parts listed above.

## STEPS
1. copy the [MiniC_v4.jj](./MiniC_v4.jj) file to your folder, along with the syntaxtree folder, and the javacc.jar file as well
2. Rename MiniC_v4.jj to be PA4.jj and replace all occurrences of MiniC_v4 in the code with PA4
3. Follow the instructions for [PA4a](./PA4a.md) to extend PA4.jj to generate ASTs for all of MiniJava,
4. Follow the instructions for [PA4b](./PA4b.md) to extend the PP_Visitor.java file to pretty print all of MiniJava, not just MiniC
5. follow the instructions for [PA4c](./PA4c.md) to extend the SymbolTable_Vistor.java file to build a symbol table for all of MiniJava
6. follow the instructions for [PA4d](./PA4d.md) to extend the TypeChecking_Visitor.java file to syntax check full MiniJava programs
7. upload your answers to PA4a,b,c,d and the associated videos as requested in the MasteryLearningApp probems





