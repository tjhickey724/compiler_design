#include <stdio.h>
#include <stdbool.h>
void print(int n){printf("%10d\n",n);}

int main(int x){
  bool b;
  b = test(x,x);
  print(b);
  b = x*2*b;
  x = x-b;
  x = b;
  return b;
}

int test(int y, bool c){
  int z;
  z = 0;
  if (c)
    z = y;
  else
    z = c;
  return z;
}