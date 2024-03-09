#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}


int main(int x){
  int a;
  bool b;
  b = test2(a);
  if (b) 
    b=a;
  else
    b=a*a;
  return b;
}

int test2(bool x){
  bool a;
  int b;
  if (a) {
    b = main(a<0);
  } else {
    b = main(a);
  }
  return b;
}