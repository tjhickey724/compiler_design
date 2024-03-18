.globl _main
_main:
pushq %rbp
movq %rsp, %rbp
movq $0, -8(%rbp)
pushq -8(%rbp)
movq $0, -16(%rbp)
pushq -16(%rbp)
pushq $100
popq %rax
movq %rax, 16(%rbp)
pushq 16(%rbp)
popq %rdi
callq _print
pushq $3
pushq 16(%rbp)
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
popq %rax
movq %rax, -16(%rbp)
pushq -16(%rbp)
popq %rdi
callq _print
pushq -16(%rbp)
pushq -16(%rbp)
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
popq %rax
movq %rax, -8(%rbp)
pushq -8(%rbp)
popq %rdi
callq _print
pushq -8(%rbp)
pushq -8(%rbp)
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
pushq -8(%rbp)
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
pushq -8(%rbp)
pushq -8(%rbp)
popq %rdx
popq %rax
imulq %rdx, %rax
pushq %rax
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
pushq -8(%rbp)
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
pushq $1
popq %rdx
popq %rax
addq %rdx, %rax
pushq %rax
popq %rax
movq %rax, -8(%rbp)
pushq -8(%rbp)
popq %rdi
callq _print
pushq 16(%rbp)
popq %rax
movq %rbp, %rsp
popq %rbp
retq
