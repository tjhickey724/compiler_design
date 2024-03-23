


.globl _main
_main:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $16, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal _main_x->-8(%rbp)
# initialize local variables to zero


# 40 
pushq $40
popq %rdi
# calling fibs
callq _fibs
pushq %rax
# print(fibs( 40 ));
popq %rdi
movb	$0, %al
callq _print
# calculate return value
# 1 
pushq $1
# epilogue
popq %rax
addq $16, %rsp
popq %rbp
retq



.globl _fibs
_fibs:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $48, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal _fibs_n->-8(%rbp)
# initialize local variables to zero

movq $0, -16(%rbp)  # _fibs_r->-16(%rbp)
movq $0, -24(%rbp)  # _fibs_s->-24(%rbp)
movq $0, -32(%rbp)  # _fibs_result2->-32(%rbp)
movq $0, -40(%rbp)  # _fibs_b->-40(%rbp)

#  0 < 0 
# 0 
pushq $0
# 0 
pushq $0
# compare rdx<rax and push 1 on stack if true, 0 else
popq %rdx
popq %rax
cmpq %rdx, %rax
jge L0
pushq $1
jmp L1
L0:
pushq $0
L1:
#b = ( 0 < 0 );

popq %rax
movq %rax, -40(%rbp)

# conditional statement
#  0 < n 
# 0 
pushq $0
# n 
pushq -8(%rbp) #   n 
# compare rdx<rax and push 1 on stack if true, 0 else
popq %rdx
popq %rax
cmpq %rdx, %rax
jge L2
pushq $1
jmp L3
L2:
pushq $0
L3:
popq %rax
cmpq	$1, %rax
jne L8

# n 
pushq -8(%rbp) #   n 
# 1 
pushq $1
# minus: n  -  1 
popq %rdx
popq %rax
subq %rdx, %rax
pushq %rax
popq %rdi
# calling fibs
callq _fibs
pushq %rax
#s = fibs( n  -  1 );

popq %rax
movq %rax, -24(%rbp)

# n 
pushq -8(%rbp) #   n 
popq %rdi
# calling fib
callq _fib
pushq %rax
#r = fib( n );

popq %rax
movq %rax, -16(%rbp)

# r 
pushq -16(%rbp) #   r 
# 1000 
pushq $1000
# times: r  *  1000 
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
# n 
pushq -8(%rbp) #   n 
# plus: r  *  1000  +  n 
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
# print( r  *  1000  +  n );
popq %rdi
movb	$0, %al
callq _print

#  0 < 1 
# 0 
pushq $0
# 1 
pushq $1
# compare rdx<rax and push 1 on stack if true, 0 else
popq %rdx
popq %rax
cmpq %rdx, %rax
jge L4
pushq $1
jmp L5
L4:
pushq $0
L5:
#b = ( 0 < 1 );

popq %rax
movq %rax, -40(%rbp)
jmp L9
L8:

#  0 < 0 
# 0 
pushq $0
# 0 
pushq $0
# compare rdx<rax and push 1 on stack if true, 0 else
popq %rdx
popq %rax
cmpq %rdx, %rax
jge L6
pushq $1
jmp L7
L6:
pushq $0
L7:
#b = ( 0 < 0 );

popq %rax
movq %rax, -40(%rbp)
L9:

# conditional statement
# b 
pushq -40(%rbp) #   b 
popq %rax
cmpq	$1, %rax
jne L10

# r 
pushq -16(%rbp) #   r 
#result2 =  r ;

popq %rax
movq %rax, -32(%rbp)
jmp L11
L10:

# 0 
pushq $0
#result2 =  0 ;

popq %rax
movq %rax, -32(%rbp)
L11:
# calculate return value
# result2 
pushq -32(%rbp) #   result2 
# epilogue
popq %rax
addq $48, %rsp
popq %rbp
retq



.globl _fib
_fib:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $16, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal _fib_n->-8(%rbp)
# initialize local variables to zero

movq $0, -16(%rbp)  # _fib_result->-16(%rbp)

# conditional statement
#  n < 2 
# n 
pushq -8(%rbp) #   n 
# 2 
pushq $2
# compare rdx<rax and push 1 on stack if true, 0 else
popq %rdx
popq %rax
cmpq %rdx, %rax
jge L12
pushq $1
jmp L13
L12:
pushq $0
L13:
popq %rax
cmpq	$1, %rax
jne L14

# 1 
pushq $1
#result =  1 ;

popq %rax
movq %rax, -16(%rbp)
jmp L15
L14:

# n 
pushq -8(%rbp) #   n 
# 1 
pushq $1
# minus: n  -  1 
popq %rdx
popq %rax
subq %rdx, %rax
pushq %rax
popq %rdi
# calling fib
callq _fib
pushq %rax
# n 
pushq -8(%rbp) #   n 
# 2 
pushq $2
# minus: n  -  2 
popq %rdx
popq %rax
subq %rdx, %rax
pushq %rax
popq %rdi
# calling fib
callq _fib
pushq %rax
# plus:fib( n  -  1 ) + fib( n  -  2 )
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#result = fib( n  -  1 ) + fib( n  -  2 );

popq %rax
movq %rax, -16(%rbp)
L15:
# calculate return value
# result 
pushq -16(%rbp) #   result 
# epilogue
popq %rax
addq $16, %rsp
popq %rbp
retq

