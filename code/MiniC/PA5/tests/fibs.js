  
fibresult.value = "running!\n";

function fib( n) {

    if (n < 2) {
        result = 1;
    }else {
        result = fib(n-1)+fib(n-2);
    }
    return result;
    }
    
    function fibs( n) {
    
    if (0<n){
        s = fibs(n-1);
        r = fib(n);
        fibresult.value += "\n"+r;
    
    }else{
    
    }
    b =(1==1);
    if (b){
        result2=r;
    }else{
        result2=0;
    }
    return result2;
    }
    
    function main(x){
    fibs(4);
    }
    
    main(1)
    