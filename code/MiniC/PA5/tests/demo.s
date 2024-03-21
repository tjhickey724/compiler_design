.globl _main
_main:
# prologue
pushq %rbp
movq %rsp, %rbp
# formals
# # _main_x->8(%rbp)

#locals
# _main_c->-32(%rbp)
# _main_b->-40(%rbp)
# _main_a->-48(%rbp)
#make space for locals on stack
subq $48, %rsp

# 10 
pushq $10
#a =  10 ;

popq %rax
movq %rax, -48(%rbp)

# 700 
pushq $700
#b =  700 ;

popq %rax
movq %rax, -40(%rbp)

# conditional statement
#  a < b 
# a 
pushq -48(%rbp) #   a 
# b 
pushq -40(%rbp) #   b 
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
popq %rax
cmpq	$1, %rax
jne L10

# conditional statement
#  b < 10  *  a 
# b 
pushq -40(%rbp) #   b 
# 10 
pushq $10
# a 
pushq -48(%rbp) #   a 
# times: 10  *  a 
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
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
jne L4

# 100 
pushq $100
#c =  100 ;

popq %rax
movq %rax, -32(%rbp)
jmp L5
L4:

# 200 
pushq $200
#c =  200 ;

popq %rax
movq %rax, -32(%rbp)
L5:
jmp L11
L10:

# conditional statement
#  a < 10  *  b 
# a 
pushq -48(%rbp) #   a 
# 10 
pushq $10
# b 
pushq -40(%rbp) #   b 
# times: 10  *  b 
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
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
popq %rax
cmpq	$1, %rax
jne L8

# 300 
pushq $300
#c =  300 ;

popq %rax
movq %rax, -32(%rbp)
jmp L9
L8:

# 400 
pushq $400
#c =  400 ;

popq %rax
movq %rax, -32(%rbp)
L9:
L11:

# c 
pushq -32(%rbp) #   c 
# print( c );
popq %rdi
movb	$0, %al
callq _print
# calculate return value
# c 
pushq -32(%rbp) #   c 
# epilogue
popq %rax
addq $48, %rsp
movq %rbp, %rsp
popq %rbp
retq

