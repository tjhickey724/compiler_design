#include <stdio.h>
#include <stdbool.h>
void print(long n){printf("%10ld\n",n);}


long sandbox(){
    long a;
    long b;
    long c;
    long d;
    a = 7;
    b = 11;
    c = 13;
    d = a+b*c;
    print(d);
    return d;
}

int main(int x){
    sandbox();
    return 1;
}
