#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}


int main(int x){
    int a;
    int b;
    int c;
    
    a=10;
    b=700;
    c=2100;
    if (a<b){
        if (b<10*a){
            c=100;
        }else {
            c=200;
        }
    } else {
        if (a<10*b) {
            c=300;
        } else {
            c=400;
        }
    }
    print(c);
    return 1;
}