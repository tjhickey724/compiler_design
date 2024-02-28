#include <stdio.h>
#include <stdbool.h>
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
    bool b;
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


