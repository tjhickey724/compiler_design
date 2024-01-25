# Overview of Parsing

Programming Languages are defined, syntactically by a formal grammar, called a [Context Free Grammar](./CFG.md).

The Grammar is what allows us to generate a parse tree from a program. First we convert the program into a sequence of tokens,
then the grammar specifies a parse tree built on top of those tokens. After parsing, the parse tree is used to analyze the program
and to generate the code.

A special case of CFGs are the [Operator Grammars](./OperatorGrammars.md) which are easy to understand and give a good introduction to both
LL and LR parsing.

If the grammar satisfies certain syntactic properties, we can easily construct an efficient parser for that grammar.
The main kinds of grammars considered in compiler design are as follows (where $k\ge 0$ is a integer specifying the "lookahead" needed by the parser).
* [LL(k) - recursive descent parsers](./recursive_descent.md)  
  which generates a left-most derivation using a lookahead of k characters to decide which rule to use
* [LR(k) - shift-reduce parsers](./shift-reduce.md) 
  which generate rightmost derivations with lookahead of k characters to decide which rule to use 
* SLR - simple LR parsers,
  efficient LR parsers for simple languages
* LALR(l) - lookahead shift-reduce parsers
  most commonly used LR parser (efficient in time and space)

the [javacc compiler generator](./javacc.md) generates a lexical analyzer and an LL(1) recursive descent parser from annotated specifications.

There are parsers for general context free grammars, e.g. [Earley's parser](https://en.wikipedia.org/wiki/Earley_parser).
These can be useful for parsing Natural Language, but are too inefficient (time complexity of $O(n^3)$) to be useful for programming languages.

The [javacc](https://javacc.github.io/javacc/) parser generator uses an LL(1) parser
while the [sablecc](https://sablecc.org/) parser generator uses an LALR(1) parser.

  



