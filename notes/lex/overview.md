# Lexical Analysis

Lexical Analysis is the phase of compilatio when the program is transformed from a sequence of characters to a sequence of tokens. The tokens correspond to the core vocabulary of the formal language. Typical tokens are
* integer constants (123  -77 +1234 0777 0xfff 0b110011)
* decimal constants (0.0 2.718281828 -3.141592  6.022e23)
* keywords (while for if then else elif class function def and or not void public private ...)
* punctuation and operators (+ - * / // % ' " ! # )
* variable names (x interval Interval int2string main

Tokens are usually expressed using Regular Expressions
