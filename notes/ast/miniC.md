# miniC and Type Checking
miniC is a (slightly modified) subset of miniJava obtained by 

removing
* Class declarations  (as C doesn't have classes)
* integer arrays (to simplify the language)
* while loops (as you iterate using method calls)
* && operators
* True and False

and replacing
* System.out.println with print
* method calls with function calls (so f(a,...z) instead of x.f(a,...,z) )

We will show how to use javacc and the Visitor pattern to build a symbol table for miniC
and the type check miniC, as well as pretty printing miniC programs, and drawing the abstract
syntax tree of a miniC program. Later we will show how to compile the bodies of miniC functions
into an Intermediate Representation and further into assembly language.  We will deviate from the
text in the way we generate code. Our approach will be to first build a very inefficient compiler
and then to circle back and optimize it over several rounds.

## Example miniC program
Here is an example of [a miniC program to print a table of fibonacci numbers](../../code/progs/fibs.c). The first two lines
are the header and are always the same for every miniC program, it allows us to do some formatted
printing of integer values.
```
#include <stdio.h>
void print(int n){printf("%10d\n",n);}


int fib(int n) {
    int result;

    if (n < 2) {
        result = 1;
    }else {
        result = fib(n-1)+fib(n-2);
    }
    return result;
}

int fibs(int n) {
    int r;
    int s;
    int result2;
    boolean b;
    b=(0<0);

    if (0<n){
        s = fibs(n-1);
        r = fib(n);
        print(r*1000+n);
        b=(0<1);
    }else{
        b=(0<0);
    }

    if (b){
        result2=r;
    }else{
        result2=0;
    }
    return result2;
}

int main(int x){
    print(fibs(40));
    return(1);
}
```
