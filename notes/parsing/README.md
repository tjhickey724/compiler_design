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
Thes are [recursive descent parsers](./recursive_descent.md)  
  which generates a left-most derivation using a lookahead of k characters to decide which rule to use. They rely on a parsing table to lookup for the leftmost non-terminal and the currently unmatched terminal, which rule to use to expand the nonterminal. Generating the parsing table requires finding the nullable non-terminals and the first and follow sets of terminals for each non-terminal. The LL parsers generate a parse tree from the top down and left to right.

##  [LR(k) shift-reduce parsers](./shift-reduce.md) 
These parsers generate a parse tree from the bottom up, and left to right. They use the grammar rules as to reduce a sequence of terminals (and later, terminala and non-terminals) into a nonterminal. That is, given a rules $A\rightarrow \alpha$ if $\alpha$ is on the top of the parsing stack, then it gets repaced by (i.e. reduced to) $A$.

### LR(0) grammars
The challenge with shift-reduce parsing is determining which rule to apply next. The LR parsers solve this by generating a Deterministic Finite Automata, that scans the parsing stack and determines whether to shift or to reduce. This DFA is generated from LR(0) items using the GoTo and Predict operations. Sometimes the grammar has the property that DFA generated this way always terminates in states with either a shift operation or a single reduce operation.
These are the LR(0) grammars. Here is an example showing how to generate an LR(0) parser [shift-reduce](./shift-reduce.md).

### SLR - simple LR parsers,
If a grammar is not LR(0), there might be two or more rules that could be used to reduce the top of the stack, say $A_i\rightarrow\alpha_i$ for $i=1,\ldots,r$.  If we used the ith rule to reduce the top of the stack, then the next terminal in the string would follow $A_i$ in the derivation, and so it would be in the follow set for $A_i$.  If the follow sets for all such rules are disjoint, then the grammar is said to be SLR, and we can slightly modify the LR(0) parser to look ahead to the next character to decide which rule to use. 

### LR(1) parsers
If a grammar is not SLR, then 
* LALR(l) - lookahead shift-reduce parsers
  most commonly used LR parser (efficient in time and space)

the [javacc compiler generator](./javacc.md) generates a lexical analyzer and an LL(1) recursive descent parser from annotated specifications.

There are parsers for general context free grammars, e.g. [Earley's parser](https://en.wikipedia.org/wiki/Earley_parser).
These can be useful for parsing Natural Language, but are too inefficient (time complexity of $O(n^3)$) to be useful for programming languages.

The [javacc](https://javacc.github.io/javacc/) parser generator uses an LL(1) parser
while the [sablecc](https://sablecc.org/) parser generator uses an LALR(1) parser.

  



