# Overview

We will follow the text book fairly closely with the goal of completing the first 11 chapters

1. [Intro](#intro)
2. [Lexing](#lexical-analysis)
3. [Parsing](#parsing)
4. [Abstract Syntax](#abstract-syntax)
5. [Semantic Analysis](#semantic-analysis)
6. [Activation Records](#activation-records)
7. [Translation to IR trees](Translation-to-IR-trees)
8. Basic Blocks and Traces
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

## Basic Blocks and Traces

## Instruction Selection

## Liveness Analysis

## Register Allocation

## Putting it all together

