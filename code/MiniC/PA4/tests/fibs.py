#Compile to Python
def fib(n):
    if (n < 2 ):
            result =  1 ;
    else:
            result = fib(n  -  1 ) + fib(n  -  2 );
    return result ;

def fibs(n):
    b = ( 0 < 0 );
    if ( 0 <n ):
            s = fibs(n  -  1 );
            r = fib(n );
            print(r  *  1000  + n );
            b = ( 0 < 1 );
    else:
            b = ( 0 < 0 );
    if (b ):
            result2 = r ;
    else:
            result2 =  0 ;
    return result2 ;

def main(x):
    print(fibs( 40 ));
    return ( 1 );
