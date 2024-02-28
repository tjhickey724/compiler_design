#include <stdio.h>
void print(int n){printf("%10d\n",n);}
int  fib(int  n){
    int  result;
    if ( n < 2 )
        {
            result =  1 ;
        }
    else
        {
            result = fib( n  -  1 ) + fib( n  -  2 );
        }
    return  result ;
}

int  fibs(int  n){
    int  r;
    int  s;
    if ( 0 < n )
        {
            s = fibs( n  -  1 );
            r = fib( n );
            print( r  *  1000  +  n );
        }
    else
        {
            r =  1 ;
        }
    return ( r );
}

int  main(int  x){
    print(fibs( 20 ));
    return ( 1 );
}

