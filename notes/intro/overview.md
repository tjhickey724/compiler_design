# Overview

We will follow the text book fairly closely with the goal of completing the first 11 chapters

1. [Intro](#intro)
2. [Lexing](#lexical-analysis)
3. Parsing
4. Abstract Syntax
5. Semantic Analysis
6. Activation Records
7. Translation to IR trees
8. Basic Blocks and Traces
9. Instruction Selection
10. Liveness Analysis
11. Register Allocation
12. Putting it all together




## Intro
There are two ways of running a program on a computer -- using an interpreter or using a compiler, both of which share the first initial step.
* reading the program as a text file and converting it into a in internal representation as a tree structure

Interpreters then run the program by creating a "virtual machine" and using the tree to modify the state of this virtual machine while performing side effects on the operating system (like reading/writing files). 
* converting the tree to a low level lana


## Lexical Analysis

