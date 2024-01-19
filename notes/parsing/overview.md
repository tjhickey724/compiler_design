# Overview of Parsing

Programming Languages are defined, syntactically by a formal grammar, called a Context Free Grammar.

## Context Free Grammars
Such a grammar $G$ defines a set of strings $L(G)$ in $\Sigma^*$ where $\Sigma$ is the set of terminals for the Language.
The language is defined by a start symbol $S$ and a set of rewrite rules, called productions, of the form:

$N -> \alpha$

where $N$ is a symbol from the set ${\cal N}$ of non-terminals, and $\alpha$ is a string of nonterminals and terminals, possibly empty.

For example, here is a grammar defining simple arithmetic expressions:

```
E -> T+T
E -> T-T
T -> F*F
T -> F/F
F -> id
F -> num
F -> (E)
```
where id,num,(, and ). are the terminals and E,T,F are the nonterminals, and E is the start symbol.

## Derivations
Given a grammar G, we can define a derivation to be a sequence of strings  of terminal and nonterminals (ie. in $({\cal N}\cup\Sigma)^*$)
where each string is obtained from the previous one by replacing one of the nonterminals $N$ by the right hand side of a production $N -> \alpha$
in the grammar.

For example, here is a derivation of id + id * id
```
E -> T + T
  -> F + T
  -> id + T
  -> id + F * F
  -> it + id * F
  -> id + id * id
```


