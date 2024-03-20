#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}


int main(int x){
    int a;
    int b;
    print(x);
    a=10;
    b=7;
    x = (a+b)*(a-b);
    print(x);
    return x;
}