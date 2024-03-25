#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}

int fibs(int n) {
    int i;
    int a;
    int b;
    int temp;
    a=0;
    b=1;
    i=1;
    while (i<n) {
        temp = a+b;
        a = b;
        b = temp;
        print(b);
        i=i+1;
    }
    return b;
}

int equal(int a, int b){
    return !(a<b) && !(b<a);
}


int test(int a, int b, int c) {
    int d;
    if (equal(a,b)) {
        d=0;
    }
    else if ((a<b) && !(c<b)){
        d=111;
    } else if (!(a<b)){
        d=222;
    } else if (!(b<c)){
        d=333;
    } else {
        d=444;
    }
    print(d);
    return d;
}

int main(int x){
    int c;
    c=20;
    c=fibs(10);
    print(1000*c);
    c=test(100,100,200);
    c=test(100,150,200);
    c=test(100,10,200);
    c=test(100,300,200);
    return c;
}