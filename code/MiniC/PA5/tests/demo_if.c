#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}


int main(int x){
    int a;
    int b;
    a=10;
    b=700;
    if (a<b){
        if (b<10*a){
            x=100;
        }else {
            x=200;
        }
    } else {
        if (a<10*b) {
            x=300;
        } else {
            x=400;
        }
    }
    print(x);
    return x;
}