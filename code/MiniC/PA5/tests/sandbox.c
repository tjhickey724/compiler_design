#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}


long sandbox(){
    long a;
    a = 7;
    return a;
}

int main(int x){
    sandbox();
    return 1;
}
