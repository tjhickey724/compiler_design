# Overview of Parsing

Programming Languages are defined, syntactically by a formal grammar, called a [Context Free Grammar](./CFG.md).

The Grammar is what allows us to generate a parse tree from a program. First we convert the program into a sequence of tokens,
then the grammar specifies a parse tree built on top of those tokens. After parsing, the parse tree is used to analyze the program
and to generate the code.

If the grammar satisfies certain syntactic properties, we can easily construct an efficient parser for that grammar.
The main kinds of grammars consider in compiler design are:
* LL(1) - recursive descent parsers
* LR(1) - shift-reduce parsers with 
* LALR(1) - lookahead shift-reduce parsers
* SLR - simple LR parsers

  



