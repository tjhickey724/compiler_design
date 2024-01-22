## Context Free Grammars
A **context free grammar** $G$ defines a set of strings $L(G)$ in $\Sigma^*$ where $\Sigma$ is the set of terminals for the Language.
The language is defined by a start symbol $S$ and a set of rewrite rules, called productions, of the form:

$N -> \alpha$

where $N$ is a symbol from the set ${\cal N}$ of non-terminals, and $\alpha$ is a string of nonterminals and terminals, possibly empty.

For example, here is a grammar defining simple arithmetic expressions:

```
E -> E+T
E -> E-T
T -> T*F
T -> T/F
F -> id
F -> num
F -> (E)
```
where id,num,(, and ). are the terminals and E,T,F are the nonterminals, and E is the start symbol.
The grammar rules are designed so that the arithmetic operators are right associative (i.e. a+b+c is parsed as (a+b)+c)
and that mult and div have higher precedences that add and sub (i.e. a*b+c/d is parsed as (a*b)+(c/d) )

## Derivations
Given a grammar G, we can define a **derivation** to be a sequence of strings  of terminal and nonterminals (ie. in $({\cal N}\cup\Sigma)^*$)
where each string is obtained from the previous one by replacing one of the nonterminals $N$ by the right hand side of a production $N -> \alpha$
in the grammar. The strings that are generated during a derivation are called **sentential forms**.

For example, here is a derivation of id + id * id
```
E -> E + T
  -> T + T
  -> F + T
  -> id + T
  -> id + T * F
  -> id + F * F
  -> id + id * F
  -> id + id * id
```
This is called a **leftmost derivation** because at each step we replace the leftmost non-terminal with the right hand side of a production.
We can likewise define rightmost derivations, and some derivations are neither leftmost nor rightmost.

Given a derivation of a string $\sigma$ from the grammar, we can construct a **parse tree** whose root is the start symbol $S$
and the non-leaf nodes are all non-terminals, and the children of any node $N$ are given by the production used to rewrite that node
in the derivation.

If a string has two different parse trees for a particular grammar, we say the grammar is **ambiguous**. For compiling programming languages,
we do not want to have ambiguous grammars because we will use the parse tree to generate the assembly language, and we would like the 
result of the compilation to depend on the program itself and not the particular parse tree we use.

Here is an ambiguous grammar
```
E -> E+E
E -> id
E -> num
```
Show that the string ```id + id + id``` has two different parse trees, which is what it means for the grammar to be ambiguous.

