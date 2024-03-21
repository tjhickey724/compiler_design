#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}


int main(int x){
    int a;
    int b;
    int c;
    a=10;
    b=700;
    if (a<b){
        c=a;
    }else {
        c=b;
    }
    print(c);
    return x;
}