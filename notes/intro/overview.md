# Overview

We will follow the text book fairly closely with the goal of completing the first 11 chapters

1. [Intro](#intro)
2. [Lexing](#lexical-analysis)
3. [Parsing](#parsing)
4. [Abstract Syntax](#abstract-syntax)
5. [Semantic Analysis](#semantic-analysis)
6. [Activation Records](#activation-records)
7. [Translation to IR trees](#translation-to-ir-trees)
8. [Basic Blocks and Traces](#Basic-Blocks-and-Traces)
9. Instruction Selection
10. Liveness Analysis
11. Register Allocation
12. Putting it all together




## Intro
There are two ways of running a program on a computer -- using an interpreter or using a compiler, both of which share the first initial step.
* reading the program as a text file and converting it into a in internal representation as a tree structure

Interpreters then run the program by creating a "virtual machine" and using the tree to modify the state of this virtual machine while performing side effects on the operating system (like reading/writing files). 

Compilers on the other hand, use the tree structure to convert the program from a high level language to a low level language. 
* If the low level language is "machine language" then the operating system can run the program directly on the CPU.
* if the low level language is "assembly language", then one can invoke an "assembler" to convert the program to "machine language"
* if the low level language is byte code for the Java Virtual Machine, then the program can be interpreted using the "java" command
* if the "low level language" is another high level language (like C or C++ or C# or Rust or Java), then the compiler for that language can be used to compile and run or interpret the program.




## Lexical Analysis
This is the process of converting the text file into a sequence of tokens that are typically described by regular expressions. Typical tokens are constants (integers, floating point numbers), keywords (if, for, while, class), operator symbols ( "+-*/.(){}[]\n\t<=" etc.) We will specify the tokens using regular expressions and recognize them using Determinstic Finite Automata. This phase will also detect and report on lexical errors (e.g. sequences of characters that don't match any of the legal tokens in the language).

## Parsing
Program are written in formal languages with very specific grammar rules.  The parsing Phase tests to see
that the program does indeed follow the grammar rules and it generates a tree structure that represents the grammatical and lexical structure of the program.

## Abstract Syntax
The trees used to represent the grammatical structure of the program do not have to be the same ones defining the language, and usually we want a simpler grammar that still captures all important features of the language. 

## Semantic Analysis
Once the tree structure has been determine we need to analyze the tree to see that it makes sense, for example that the program isn't trying to add a string and an array, or multiply a date by a file! This is the semantic analysis phase and we typically create a symbol table which gives semantic meaning to each
of the variables that appear in the program, e.g. the possible types they can store and which parts of the program they can be accessed from (i.e. the scope).

## Activation Records
A key feature of all modern programming languages is that they allow the user to use procedural abstraction to solve problems.  Procedural abstraction is implemented by creating a stack frame for each procedure call and one key part of the compiling process is specifying the structure of these stack frames. These structured frames are called Activation Records, they are a representation of a function/procedure/method that can be used to easily generate the code for procedure calls.


## Translation to IR trees
At this point we can produce trees representing the program which can then be used to compile to many different back ends (e.g. different CPUs or byte codes). This is the end of the "front end" part of the compiler it results in an Intermediate Representation which can then be used my many different back ends.

## Basic Blocks and Traces
This is an optimization phase where the IR tree is simplified by breaking the code up into regions that don't contain any jumps or branches or procedure calls. Each basic block ends with a jump to another basic block or to the code to stop the program. By ordering the basic blocks cleverly we can eliminate many of the jump statements which can speed up execution, sequences of basic blocks are called traces.

## Instruction Selection
The next step is to convert the tree into an abstract machine code called 3 address code in which
the instructions perform arithetic operations on registers (e.g. add R1 and R2 and put result in R3)
or move values between registers or between registers and memory.

## Liveness Analysis
This is an optimization phase, in the previous step we assumed there were infinitely many registers available, but in practice a given CPU will have a limited number of registers. When compiling the body of a function, we know at compile time the names of all of the variables that can be accessed in that code. Moreover, the code can be modeled as a directed graph of instructions and for each instruction we can determine which of the variables have a value that will be needed in the future. This is called liveness analysis and allows us to assign variables to registers in an efficient way that saves time and space.


## Register Allocation
In this phase we determine which registers each live variable should be placed in and with this knowledge we can generate the final assembly code.!

## Putting it all together
Once we get to this point you can put it all together to create a compiler for a subset of Java, or any other Objected-Oriented language.

