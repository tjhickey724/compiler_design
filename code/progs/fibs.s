	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 10, 15, 4	sdk_version 10, 15, 4
	.globl	_print                  ## -- Begin function print
	.p2align	4, 0x90
_print:                                 ## @print
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	%edi, -4(%rbp)
	movl	-4(%rbp), %esi
	leaq	L_.str(%rip), %rdi
	movb	$0, %al
	callq	_printf
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_fib                    ## -- Begin function fib
	.p2align	4, 0x90
_fib:                                   ## @fib
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	%edi, -4(%rbp)
	cmpl	$2, -4(%rbp)
	jge	LBB1_2
## %bb.1:
	movl	$1, -8(%rbp)
	jmp	LBB1_3
LBB1_2:
	movl	-4(%rbp), %eax
	subl	$1, %eax
	movl	%eax, %edi
	callq	_fib
	movl	-4(%rbp), %ecx
	subl	$2, %ecx
	movl	%ecx, %edi
	movl	%eax, -12(%rbp)         ## 4-byte Spill
	callq	_fib
	movl	-12(%rbp), %ecx         ## 4-byte Reload
	addl	%eax, %ecx
	movl	%ecx, -8(%rbp)
LBB1_3:
	movl	-8(%rbp), %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_fibs                   ## -- Begin function fibs
	.p2align	4, 0x90
_fibs:                                  ## @fibs
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$32, %rsp
	xorl	%eax, %eax
	movl	%edi, -4(%rbp)
	movb	$0, -17(%rbp)
	cmpl	-4(%rbp), %eax
	jge	LBB2_2
## %bb.1:
	movl	-4(%rbp), %eax
	subl	$1, %eax
	movl	%eax, %edi
	callq	_fibs
	movl	%eax, -12(%rbp)
	movl	-4(%rbp), %edi
	callq	_fib
	movl	%eax, -8(%rbp)
	imull	$1000, -8(%rbp), %eax   ## imm = 0x3E8
	addl	-4(%rbp), %eax
	movl	%eax, %edi
	callq	_print
	movb	$1, -17(%rbp)
	jmp	LBB2_3
LBB2_2:
	movb	$0, -17(%rbp)
LBB2_3:
	testb	$1, -17(%rbp)
	je	LBB2_5
## %bb.4:
	movl	-8(%rbp), %eax
	movl	%eax, -16(%rbp)
	jmp	LBB2_6
LBB2_5:
	movl	$0, -16(%rbp)
LBB2_6:
	movl	-16(%rbp), %eax
	addq	$32, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_main                   ## -- Begin function main
	.p2align	4, 0x90
_main:                                  ## @main
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	subq	$16, %rsp
	movl	$0, -4(%rbp)
	movl	%edi, -8(%rbp)
	movl	$40, %edi
	callq	_fibs
	movl	%eax, %edi
	callq	_print
	movl	$1, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.section	__TEXT,__cstring,cstring_literals
L_.str:                                 ## @.str
	.asciz	"%10d\n"


.subsections_via_symbols
