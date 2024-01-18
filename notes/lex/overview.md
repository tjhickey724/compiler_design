# Lexical Analysis

Lexical Analysis is the phase of compilatio when the program is transformed from a sequence of characters to a sequence of tokens. The tokens correspond to the core vocabulary of the formal language. 

On this page we'll define Lexical Analysis, Regular expressions, Regular Languages, DFAs, and NFAs and show how they are all related
and how to convert one into another...

## Tokens
Typical tokens for a programming language are
* integer constants (123  -77 +1234 0777 0xfff 0b110011)
* decimal constants (0.0 2.718281828 -3.141592  6.022e23)
* keywords (while for if then else elif class function def and or not void public private ...)
* punctuation and operators (+ - * / // % ' " ! # )
* variable names (x interval Interval int2string main

* [Regular Expressions](./regex.md) are used to describe the set of strings acceptable for each token type
* [Finite Automata](./nfadfa.md) are directed graphs with edge labels which can also be used to generate efficient programs for recognizing tokens defined by a regular expression. There are two types NFAs = Non-deterministic finite automata and DFAs = Deterministic Finite Automata. Every DFA is an NFA, and every NFA can be converted into a DFA (though possibly with exponentially more states).
* We can [convert Regular Expressions to NFAs](./regex2nfa.md) and [NFAs to Regular Expressions](./nfa2regex.md). So they are equivalent ways of specifying tokens.
* By combining all of these algorithms we can [create a lexical analyzer](./building_lexers.md) which are linear if the string has no lexical errors.

# Jupyter Notebook
[Here](NFA%2BDFA.ipynb) is a link to a jupyter notebook containing the code from this lesson
