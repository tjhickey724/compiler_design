# Examples of compiled code

Consider the following C program
``` C
#include <stdio.h> 
#include <math.h> 
int main() { 
    int n; printf("Enter an integer n: "); 
    scanf("%d", &n); 
    printf("\nNumber\tSquare\n"); 
    printf("----------------------------------\n"); 
    for (int i = 1; i <= n; i++) { 
        int square = i * i; 
        printf("%d\t%d\n", i, square); } 
    return 0; } 
```
which prompts the user for an integer n and then prints a table of the squares of all integers fromm 1 to n

If we compile this in a Mac terminal with
``` bash
 gcc test.c
```
it generates an executable file called "a.out", in machine language, that we cna run using the command
``` bash
./a.out
Enter an integer n: 5

Number  Square          Square Root
----------------------------------
1       1               1.00
2       4               1.41
3       9               1.73
4       16              2.00
5       25              2.24
```
In this class, you will learn how to compile programs in a subset of Java (called minijava)
to assembly language, which can then be converted to an a.out file in machine language.

## Assembly on an Intel Macbook Pro
We can see the assembly language produced by the C compiler on the Mac using
``` bash
 gcc -S -masm=intel -o test_intel.s test.c
```
which produces the file test_intel.s containing the following code:
``` assembly
	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 10, 15, 4	sdk_version 10, 15, 4
	.intel_syntax noprefix
	.globl	_main                   ## -- Begin function main
	.p2align	4, 0x90
_main:                                  ## @main
	.cfi_startproc
## %bb.0:
	push	rbp
	.cfi_def_cfa_offset 16
	.cfi_offset rbp, -16
	mov	rbp, rsp
	.cfi_def_cfa_register rbp
	sub	rsp, 48
	mov	dword ptr [rbp - 4], 0
	lea	rdi, [rip + L_.str]
	mov	al, 0
	call	_printf
	lea	rdi, [rip + L_.str.1]
	lea	rsi, [rbp - 8]
	mov	dword ptr [rbp - 28], eax ## 4-byte Spill
	mov	al, 0
	call	_scanf
	lea	rdi, [rip + L_.str.2]
	mov	dword ptr [rbp - 32], eax ## 4-byte Spill
	mov	al, 0
	call	_printf
	lea	rdi, [rip + L_.str.3]
	mov	dword ptr [rbp - 36], eax ## 4-byte Spill
	mov	al, 0
	call	_printf
	mov	dword ptr [rbp - 12], 1
LBB0_1:                                 ## =>This Inner Loop Header: Depth=1
	mov	eax, dword ptr [rbp - 12]
	cmp	eax, dword ptr [rbp - 8]
	jg	LBB0_4
## %bb.2:                               ##   in Loop: Header=BB0_1 Depth=1
	mov	eax, dword ptr [rbp - 12]
	imul	eax, dword ptr [rbp - 12]
	mov	dword ptr [rbp - 16], eax
	cvtsi2sd	xmm0, dword ptr [rbp - 12]
	sqrtsd	xmm0, xmm0
	movsd	qword ptr [rbp - 24], xmm0
	mov	esi, dword ptr [rbp - 12]
	mov	edx, dword ptr [rbp - 16]
	movsd	xmm0, qword ptr [rbp - 24] ## xmm0 = mem[0],zero
	lea	rdi, [rip + L_.str.4]
	mov	al, 1
	call	_printf
## %bb.3:                               ##   in Loop: Header=BB0_1 Depth=1
	mov	eax, dword ptr [rbp - 12]
	add	eax, 1
	mov	dword ptr [rbp - 12], eax
	jmp	LBB0_1
LBB0_4:
	xor	eax, eax
	add	rsp, 48
	pop	rbp
	ret
	.cfi_endproc
                                        ## -- End function
	.section	__TEXT,__cstring,cstring_literals
L_.str:                                 ## @.str
	.asciz	"Enter an integer n: "

L_.str.1:                               ## @.str.1
	.asciz	"%d"

L_.str.2:                               ## @.str.2
	.asciz	"\nNumber\tSquare\t\tSquare Root\n"

L_.str.3:                               ## @.str.3
	.asciz	"----------------------------------\n"

L_.str.4:                               ## @.str.4
	.asciz	"%d\t%d\t\t%.2lf\n"


.subsections_via_symbols

```

## Compiling on a linux machine
If instead we run that command on one of the departments Linux machines we get the following code
which is in the x86-64 Assembly Language:
``` assembly
	.file	"test.c"
	.section	.rodata
.LC0:
	.string	"Enter an integer n: "
.LC1:
	.string	"%d"
.LC2:
	.string	"\nNumber\tSquare"
	.align 8
.LC3:
	.string	"----------------------------------"
.LC4:
	.string	"%d\t%d\n"
	.text
	.globl	main
	.type	main, @function
main:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movl	$.LC0, %edi
	movl	$0, %eax
	call	printf
	leaq	-12(%rbp), %rax
	movq	%rax, %rsi
	movl	$.LC1, %edi
	movl	$0, %eax
	call	__isoc99_scanf
	movl	$.LC2, %edi
	call	puts
	movl	$.LC3, %edi
	call	puts
	movl	$1, -4(%rbp)
	jmp	.L2
.L3:
	movl	-4(%rbp), %eax
	imull	-4(%rbp), %eax
	movl	%eax, -8(%rbp)
	movl	-8(%rbp), %edx
	movl	-4(%rbp), %eax
	movl	%eax, %esi
	movl	$.LC4, %edi
	movl	$0, %eax
	call	printf
	addl	$1, -4(%rbp)
.L2:
	movl	-12(%rbp), %eax
	cmpl	%eax, -4(%rbp)
	jle	.L3
	movl	$0, %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.ident	"GCC: (GNU) 4.8.5 20150623 (Red Hat 4.8.5-39)"
	.section	.note.GNU-stack,"",@progbits
```
