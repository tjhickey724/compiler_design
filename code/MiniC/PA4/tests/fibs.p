PROGRAM Demo;

FUNCTION fib(n:INTEGER) :INTEGER;
    VAR result:INTEGER;
BEGIN
    IF ( n < 2 ) THEN
        BEGIN
            result :=  1 ;
        END
    ELSE
        BEGIN
            result := fib( n  -  1 ) + fib( n  -  2 );
        END
;    fib:= result ;

END;

FUNCTION fibs(n:INTEGER) :INTEGER;
    VAR r:INTEGER;
    VAR s:INTEGER;
    VAR result2:INTEGER;
    VAR b:BOOLEAN;
BEGIN
    b := ( 0 < 0 );
    IF ( 0 < n ) THEN
        BEGIN
            s := fibs( n  -  1 );
            r := fib( n );
            writeln( r  *  1000  +  n );
            b := ( 0 < 1 );
        END
    ELSE
        BEGIN
            b := ( 0 < 0 );
        END
;    IF ( b ) THEN
        BEGIN
            result2 :=  r ;
        END
    ELSE
        BEGIN
            result2 :=  0 ;
        END
;    fibs:= result2 ;

END;

FUNCTION main(x:INTEGER) :INTEGER;
BEGIN
    writeln(fibs( 40 ));
    main:=( 1 );

END;

BEGIN
 main(1);
END.
