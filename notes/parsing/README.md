# Overview of Parsing

Programming Languages are defined, syntactically by a formal grammar, called a [Context Free Grammar](./CFG.md).

The Grammar is what allows us to generate a parse tree from a program. First we convert the program into a sequence of tokens,
then the grammar specifies a parse tree built on top of those tokens. After parsing, the parse tree is used to analyze the program
and to generate the code.

A special case of CFGs are the [Operator Grammars](./OperatorGrammars.md) which are easy to understand and give a good introduction to both
LL and LR parsing.

If the grammar satisfies certain syntactic properties, we can easily construct an efficient parser for that grammar.
The main kinds of grammars considered in compiler design are LL(k) and LR(k) grammars (where $k\ge 0$ is a integer specifying the "lookahead" needed by the parser).

## LL(k) parsers
These are [recursive descent parsers](./recursive_descent.md) which generates a left-most derivation using a lookahead of k characters to decide which rule to use. They rely on a parsing table to lookup for the leftmost non-terminal and the currently unmatched terminal, which rule to use to expand the nonterminal. Generating the parsing table requires finding the nullable non-terminals and the first and follow sets of terminals for each non-terminal. The LL parsers generate a parse tree from the top down and left to right.

The [javacc compiler generator](./javacc.md) generates a lexical analyzer and an LL(1) recursive descent parser from annotated specifications.

Most programming languages do not have natural LL(1) grammars, so we need to remove left recursion and left factoring. 
[Here](./expressionDemo.md) is an example of how to that with 
a grammar for arithmetic java expression.

##  LR(k) parsers
These are [shift-reduce parsers](./shift-reduce.md) that generate a parse tree from the bottom up, and left to right. They use the grammar rules as to reduce a sequence of terminals (and later, terminala and non-terminals) into a nonterminal. That is, given a rules $A\rightarrow \alpha$ if $\alpha$ is on the top of the parsing stack, then it gets repaced by (i.e. reduced to) $A$.

### LR(0) grammars
The challenge with shift-reduce parsing is determining which rule to apply next. The LR parsers solve this by generating a Deterministic Finite Automata, that scans the parsing stack and determines whether to shift or to reduce. This DFA is generated from LR(0) items using the GoTo and Predict operations. Sometimes the grammar has the property that DFA generated this way always terminates in states with either a shift operation or a single reduce operation.
These are the LR(0) grammars.  
Here is an example showing how to generate an LR(0) parser [shift-reduce](./shift-reduce.md).

### SLR - simple LR parsers,
If a grammar is not LR(0), there might be two or more rules that could be used to reduce the top of the stack, say $A_i\rightarrow\alpha_i$ for $i=1,\ldots,r$.  If we used the ith rule to reduce the top of the stack, then the next terminal in the string would follow $A_i$ in the derivation, and so it would be in the follow set for $A_i$.  If the follow sets for all such rules are disjoint, then the grammar is said to be SLR, and we can slightly modify the LR(0) parser to look ahead to the next character to decide which rule to use. 

### LR(1) parsers
If a grammar is not SLR, then we can modify the LR(0) items to include a lookahead for each rule!
This method generate a more specific notion of "follow" sets which determines which terminal can follow a non-terminal at a particular node of the parser DFA. The challenge with these parsers is that the DFA can be **much** larger than the SLR DFA.

### LALR(l) parsers
There are lookahead shift-reduce parsers like ther LR(1) parsers but they generate smaller tables by merging LR(1) states that differ only in the lookaheads for each rule. By combining the lookaheads for the same rules, they might generate shift-reduce or reduce-reduce conflicts, but in practice most grammars for programming languages are LALR(1).
The [sablecc](https://sablecc.org/) parser generator generates an LALR(1) parser from an
annotated grammar file and automatically generates a concrete parse tree for the grammar.

### Earley parsers
There are parsers for general context free grammars, that generate sets of LR(0) items  e.g. [Earley's parser](https://en.wikipedia.org/wiki/Earley_parser).
These can be useful for parsing Natural Language, but are too inefficient (time complexity of $O(n^3)$) to be useful for programming languages.
Here is an example of using the Earley algorithm for an LR(0) grammar to show the ideas: [LR Parsing]./(LRparsing.md)

## Implementing interpreters and compilers with javacc
It is possible to use javacc to implement interpreters and compilers for simple languages,
but this approach doesn't scale as the code gets very complex very quickly.  Still here is an example
of a javacc program for interpreting arithmetic expressions:
* [Calculator.jj](https://github.com/tjhickey724/compiler_design/blob/main/code/Calculators/Calculator.jj)

This examples shows how to define the javacc functions corresponding to non-terminals so that they return a value
(in this case the value of the subexpression in the tree whose root is that non-terminal). Here is an example of the
rule for expressions (which are are a term followed by one or more sums or differences of terms:
```
double expr():
{
    double a;
    double b;
}
{
    a=term()
    (
        "+" b=term()    { a += b; }
    |   "-" b=term()    { a -= b; }
    )*
                        { return a; }
}
```
This uses the javacc feature where the right hand side of a rule can be a regular expression with "|" and "*"
in addition to tokens (e.g. "+", "-") and nonterminals (e.g. term() and expre()). This code gets the value of the
first term and stores it in the variable "a", then for each "+T" or "-T" that follows it add or subtracts the term
value with "a", finally returning "a".

We could use this same technique to generate a parse tree for the expression, but we will use the jjTree package
instead, which automatically generates a tree implementing the Visitor Pattern 
that we'll talk about in the "Abstract Syntax Trees" section.


## Pretty Printing
Once we have a parser for a programming language, it is fairly easy to create a pretty printer (and a cross compiler).
Here are some notes on how to pretty print mini-java. I won't show the whole code here, just snippets.

First is the main program for the parser.
The idea is that when we recognize a
```
/*
 * This is MiniJavaPP.jj file.
 * It will build a tree we can traverse using the Visitor pattern!
 */

options {
  IGNORE_CASE=false;
}

PARSER_BEGIN(MiniJavaPP)
public class MiniJavaPP {

  /** Main entry point. */
  public static void main(String args[]) {
    System.out.println("Reading from standard input...");
    MiniJavaPP t = new MiniJavaPP(System.in);
    try {
      t.Start(0);
      System.out.println("Thank you.");
    } catch (Exception e) {
      System.out.println("Oops.");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
PARSER_END(MiniJavaPP)

/* Program Syntax */

void Start(int indent):
{}
{
  MainClass(indent)
  ClassDecls(indent)
  <EOF>

}

void MainClass(int indent) :
{Token s,t;}
{
  "class" 
  t=<ID> 
  <LCURLY>  
      {System.out.println("class "+t.image+"{");}
  "public" "static" "void" <MAIN> 
      {System.out.print("    public static void main"); }
  <LPAREN> "String" <LBRACKET> <RBRACKET> 
  s=<ID> 
  <RPAREN>
      {System.out.println("(String[] "+s.image + "){");  }
  <LCURLY> 
  Statement(indent+1) 
  <RCURLY>
  <RCURLY>
      {System.out.println("\n  }\n}\n"); }
}

```


Here is part of the code for Statement:
```

void Statement(int indent) :
{Token t;}
{ 
  <LCURLY> 
      {System.out.format("%1$"+4*indent+"s", "");}
      {System.out.println("{");}
  Statements(indent+1) 
      {System.out.format("%1$"+4*indent+"s", "");}
      {System.out.println("}");}
  <RCURLY> 

  |
      {System.out.format("%1$"+4*indent+"s", "");}
  <IF> 
  <LPAREN> 
      {System.out.print("if (");}
  Exp()
  <RPAREN> 
      {System.out.println(")");}
  Statement(indent+1) 
  <ELSE> 
      {System.out.format("%1$"+4*indent+"s", "");}
      {System.out.println("else ");}
  Statement(indent+1)
      {System.out.println();}

  |
// et cetera
}
```
  



