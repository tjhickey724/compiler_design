#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}

int test(int x){
    int y;
    y=x+1;
    return 2*y;
}

int main(int x){
    print(test(5));
    return 1;
}