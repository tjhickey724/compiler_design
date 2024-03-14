PROGRAM Test;

FUNCTION fib(n:INTEGER ) :INTEGER ;
VAR result:INTEGER;
BEGIN
    if ( n < 2 ) THEN
        BEGIN
            result :=  1 ;
        END
    ELSE
        BEGIN
            result := fib( n  -  1 ) + fib( n  -  2 );
        END;
    fib :=  result ;

END;

FUNCTION fibs(n:integer ) :integer ;
    var r:integer ;
    var s:integer ;
    var result2:integer ;
    var b:boolean ;
BEGIN

    b := ( 0 < 0 );
    if ( 0 < n ) then
        BEGIN
            s := fibs( n  -  1 );
            r := s;
            r := fib( n );
            writeln( r  *  1000  +  n );
            b := ( 0 < 1 );
        END
    else
        BEGIN
            b := ( 0 < 0 );
        END;
    if ( b ) then
        BEGIN
            result2 :=  r ;
        END
    else
        BEGIN
            result2 :=  0 ;
        END;
    fibs :=  result2 

END;

PROCEDURE main(x:integer ) ;
BEGIN
    writeln(fibs( 40 ));
END;

BEGIN
  main(1);
END.

