.globl _main
_main:
# formals
# # _main_x->16(%rbp)

# prologue
pushq %rbp
movq %rsp, %rbp
#locals
# _main_b->-8(%rbp)
# _main_a->-16(%rbp)
#make space for locals on stack
subq $16, %rsp

# x 
pushq 16(%rbp) #   x 
# print( x );
popq %rdi
callq _print

# 10 
pushq $10
#a =  10 ;

popq %rax
movq %rax, -16(%rbp)

# 7 
pushq $7
#b =  7 ;

popq %rax
movq %rax, -8(%rbp)

# a 
pushq -16(%rbp) #   a 
# b 
pushq -8(%rbp) #   b 
# plus: a  +  b 
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
# a 
pushq -16(%rbp) #   a 
# b 
pushq -8(%rbp) #   b 
# minus: a  -  b 
popq %rdx
popq %rax
subq %rdx, %rax
pushq %rax
# times:( a  +  b ) * ( a  -  b )
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
#x = ( a  +  b ) * ( a  -  b );

popq %rax
movq %rax, 16(%rbp)

# x 
pushq 16(%rbp) #   x 
# print( x );
popq %rdi
callq _print
# calculate return value
# x 
pushq 16(%rbp) #   x 
# epilogue
popq %rax
addq $16, %rsp
movq %rbp, %rsp
popq %rbp
retq

