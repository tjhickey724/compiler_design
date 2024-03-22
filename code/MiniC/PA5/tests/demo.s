.globl _main
_main:
# prologue
pushq %rbp
movq %rsp, %rbp
# formals
# # _main_x->8(%rbp)

#locals
# _main_c->-32(%rbp)
#make space for locals on stack
subq $32, %rsp

# 1 
pushq $1
# print( 1 );
popq %rdi
movb	$0, %al
callq _print
# calculate return value
# 1 
pushq $1
# epilogue
popq %rax
addq $32, %rsp
movq %rbp, %rsp
popq %rbp
retq

