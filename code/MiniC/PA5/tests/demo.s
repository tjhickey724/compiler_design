


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


# 5 
pushq $5
popq %rdi
# calling test
callq _test
pushq %rax
# print(test( 5 ));
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



.globl _test
_test:
# prologue
pushq %rbp
movq %rsp, %rbp
# make space for locals on stack, must be multiple of 16
subq $16, %rsp
# copy formals in registers to stack frame
movq %rdi, -8(%rbp)  # formal _test_x->-8(%rbp)
# initialize local variables to zero

movq $0, -16(%rbp)  # _test_y->-16(%rbp)

# x 
pushq -8(%rbp) #   x 
# 1 
pushq $1
# plus: x  +  1 
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
#y =  x  +  1 ;

popq %rax
movq %rax, -16(%rbp)
# calculate return value
# 2 
pushq $2
# y 
pushq -16(%rbp) #   y 
# times: 2  *  y 
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
# epilogue
popq %rax
addq $16, %rsp
popq %rbp
retq

