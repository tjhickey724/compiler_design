#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}


int main(int x){
    int a;
    int b;
    x=100;
    print(x);
    a = 3*x;
    print(a);
    b = a*a;
    print(b);
    b = b*b*b+b*b+b+1;
    print(b);
    return x;
}