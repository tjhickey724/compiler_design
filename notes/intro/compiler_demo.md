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
