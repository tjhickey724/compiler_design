#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}

int test(int a,int b,int c,int d,int e,int f,int g,int h,int i,int j){
    int z;
    z=a+b+c+d+e+f+g+h+i+j;
    print(z);
    return z;
}

int main(int x){
    int a;
    int b;
    print(1);
    print(2);
    a = test(10,20,30,40,50,60,70,80,90,100);
    print(a);
    b = test(20,30,40,50,60,70,80,90,100,110);
    print(b);
    return x;
}