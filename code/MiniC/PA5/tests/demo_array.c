#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}
#include <stdlib.h>

long arrayDemo(int s,int t){
    long a;
    long* b;
    a = 0;
    b = (long *) malloc(s*sizeof(long));
    b[0]=100;
    b[t]=200;
    a = b[t];
    print(a);
    return a;
}

int main(int x){
    long a;
    a = arrayDemo(10,3);
    return 1;
}