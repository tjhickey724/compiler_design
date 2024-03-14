# Translating between languages
Once we have a parser that generates an Abstract Syntax Tree, we can use that tree to do interesting things such as
* pretty print
* minify (remove all unneeded white space and comments)
* list all variables and their types (i.e. make a Symbol Table)
* type check the program

We can also translate programs from the source language (in our case MiniC) to another language.

Modern Imperative languages all have the same core data types and constrol structures, and so it is
relatively easy to convert a program from MiniC into any of these other languages.  We can also try to
convert MiniJava to the other languages but there is more variation in the way programming languages handle
Objects...

In this note we show how to convert a MiniC program to a Pascal program.

## Pascal
Pascal was the preeminent programming language for teaching in the 1970's and 1980's but was replaced by C in the 1990's.

Wikipedia has a nice article on [Pascal](https://en.wikipedia.org/wiki/Pascal_(programming_language))

We will translate the [fibs.c](./tests/fibs.c) program to [fibs.p](./tests/fibs.p) a Pascal program.

Take a moment to look at the Pascal program and write down some of the ways it differs, syntactically, from the MiniC program.
