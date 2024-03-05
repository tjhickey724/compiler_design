# Frames and the X86-64 Calling Protocol

The X86 architecture consists of 16 64-bit registers and six of them are used for passing values in to a function call
as shown below.  Two are used for marking the beginning (rbp) and ending (rsp) of the current frame, one is for storing
the value to be returned by the function (rax), two are for storing temporary data (r10 and r11), and the remaining 5
can be used but the values they contain have to be stored in the stack and the restored before the function returns.

```
rax -- return value  (also eax for right 32 bit, ax for right 16 bits, al for right 8 bits)
rbx -- callee saved register
rcx -- 4th arg
rdx -- 3rd arg
rsi -- 2nd arg
rdi -- 1st arg
rbp -- callee saved (and pointer to beginning of the frame)
rsp -- pointer to the top of the stack
r8 -- 5th arg
r9 -- 6th arg
r10 - temporary
r11 - temporary
r12 r13 r14 r15 -- callee saved registers
```

Let's look at an example...
Consider the following program
```
#include <stdio.h>  // program to be compiled to X86-64
long test();

int main(void)
{ long a=11; long b=22; long c= 33; long d= 44; long e= 55; long f= 66; 
  long g= 77; long h= 88; long i= 99; long j= 110;  long z = -1;
  z=test(a,b,c,d,e,f,g,h,i,j);
  printf("z=%ld\n",z);return 1;
}

long test(long a, long b, long c, long d, long e, long f, long g, long h, long i, long j){
    long x =  a+b+c+d+e+f+g+h+i+j;
    long y =  a+b+c+d+e;
    long z =  x-y;
    return z;
}
```
Compiling this with no optimizations ``` gcc -Wall  -m64 -fverbose-asm test2.c -S -o test2.S```
will give us the following code
```
	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 10, 15, 4	sdk_version 10, 15, 4
	.globl	_main                   ## -- Begin function main
	.p2align	4, 0x90
_main:                                  ## @main
	.cfi_startproc
## %bb.0:                   ## PROLOGUE - storing info of previous frame
	pushq	%rbp                    ## push base of previous frame into stack
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp              ## store rsp as base of current frame
	.cfi_def_cfa_register %rbp
	pushq	%r14                    ## store callee saved registers
	pushq	%rbx                    ## that are used below
	subq	$144, %rsp				## CREATE STACK FRAME (18 longs)
	.cfi_offset %rbx, -32
	.cfi_offset %r14, -24
	movl	$0, -20(%rbp)
	movq	$11, -32(%rbp)
	movq	$22, -40(%rbp)
	movq	$33, -48(%rbp)
	movq	$44, -56(%rbp)
	movq	$55, -64(%rbp)
	movq	$66, -72(%rbp)
	movq	$77, -80(%rbp)
	movq	$88, -88(%rbp)
	movq	$99, -96(%rbp)
	movq	$110, -104(%rbp)
	movq	$-1, -112(%rbp)
	movq	-32(%rbp), %rdi
	movq	-40(%rbp), %rsi
	movq	-48(%rbp), %rdx
	movq	-56(%rbp), %rcx
	movq	-64(%rbp), %r8
	movq	-72(%rbp), %r9
	movq	-80(%rbp), %rax
	movq	-88(%rbp), %r10
	movq	-96(%rbp), %r11
	movq	-104(%rbp), %rbx
	movq	%rax, (%rsp)
	movq	%r10, 8(%rsp)
	movq	%r11, 16(%rsp)
	movq	%rbx, 24(%rsp)
	callq	_test
	movq	%rax, -112(%rbp)
	movq	-112(%rbp), %rsi
	leaq	L_.str(%rip), %rdi
	movb	$0, %al
	callq	_printf
	movl	$1, %r14d
	movl	%eax, -116(%rbp)        ## 4-byte Spill
	movl	%r14d, %eax
	addq	$144, %rsp			## REMOVE STACK FRAME
	popq	%rbx				## restore callee save registers
	popq	%r14
	popq	%rbp				## restore the base of previous frame
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_test                   ## -- Begin function test
	.p2align	4, 0x90
_test:                                  ## @test
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	pushq	%rbx
	.cfi_offset %rbx, -24
	movq	40(%rbp), %rax
	movq	32(%rbp), %r10
	movq	24(%rbp), %r11
	movq	16(%rbp), %rbx
	movq	%rdi, -16(%rbp)
	movq	%rsi, -24(%rbp)
	movq	%rdx, -32(%rbp)
	movq	%rcx, -40(%rbp)
	movq	%r8, -48(%rbp)
	movq	%r9, -56(%rbp)
	movq	-16(%rbp), %rcx
	addq	-24(%rbp), %rcx
	addq	-32(%rbp), %rcx
	addq	-40(%rbp), %rcx
	addq	-48(%rbp), %rcx
	addq	-56(%rbp), %rcx
	addq	16(%rbp), %rcx
	addq	24(%rbp), %rcx
	addq	32(%rbp), %rcx
	addq	40(%rbp), %rcx
	movq	%rcx, -64(%rbp)
	movq	-16(%rbp), %rcx
	addq	-24(%rbp), %rcx
	addq	-32(%rbp), %rcx
	addq	-40(%rbp), %rcx
	addq	-48(%rbp), %rcx
	movq	%rcx, -72(%rbp)
	movq	-64(%rbp), %rcx
	subq	-72(%rbp), %rcx
	movq	%rcx, -80(%rbp)
	movq	-80(%rbp), %rcx
	movq	%rax, -88(%rbp)         ## 8-byte Spill
	movq	%rcx, %rax
	popq	%rbx
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.section	__TEXT,__cstring,cstring_literals
L_.str:                                 ## @.str
	.asciz	"z=%ld\n"


.subsections_via_symbols

```

Let's walk through the execution of this assembly language program step-by-step
using these slides on 

[Stack Frame Tracing](./StackFrameTracing.pdf)
