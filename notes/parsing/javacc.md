# javacc overview

javacc is a compiler generator which accepts specially formatted rules for tokens and grammatical constructs
and then generates an LL(1) recursive descent parser. You can add functionality to the parser so that it will
act as an interpreter or will generate a parse tree (concrete or abstract). Since it is a recursive descent parser
the actions you add are identical to ones you would add for traversing a tree in a depth-first left-to-right fashion.

Here is a nice [tutorial for using javacc](https://www.engr.mun.ca/~theo/JavaCC-Tutorial/javacc-tutorial.pdf)

## Lexical Analysis
Here is a link to a Lexical Analyzer for a simple language for arithmetic expressions. This is from a compiler course at WPI

[arith expr lexical analyzer](https://web.cs.wpi.edu/~kal/courses/cs4533/JAVACC/JavaccScanner.htm)


## Running javacc
To run these examples, you need to visit [the javacc site](https://javacc.github.io/javacc/) and download the javacc.jar file.
Actually download the javacc.7.0.12.zip file, unzip it, and you'll find javacc.jar in the bootstrap folder. 

Once you have the jar file, give the following commands to create the lexical analyzer.

``` bash
 % java -classpath javacc javacc cal.jj 
Java Compiler Compiler Version 7.0.13 (Parser Generator)
(type "javacc" with no arguments for help)
Reading from file cal.jj . . .
File "TokenMgrError.java" is being rebuilt.
File "ParseException.java" is being rebuilt.
File "Token.java" is being rebuilt.
File "SimpleCharStream.java" is being rebuilt.
Parser generated successfully.

% javac cal.java

% cat test.txt
6*(11-7)/3+100

% java cal test.txt
NUMBER:    6
MULT_OP:   *
LPAREN:    (
NUMBER:    11
ADD_OP:    -
NUMBER:    7
RPAREN:    )
MULT_OP:   /
NUMBER:    3
ADD_OP:    +
NUMBER:    100
```

## Simple Parser
and here is a parser/interpreter for evaluating arithmetic expressions using javacc

[parse and evaluate](https://gist.github.com/jac18281828/2435b575b699684a4ee36201af472d04)

Run this as
```
% java -classpath javacc.jar javacc Calculator.jj
% javac Calculator.java
% java Calculator
4*5+5*6
50
1+2
3
```

```
