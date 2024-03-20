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
	.globl	_sandbox                ## -- Begin function sandbox
	.p2align	4, 0x90
_sandbox:                               ## @sandbox
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	pushq	%rbx  ## push the caller-owned registers onto stack
	pushq	%r12
	pushq	%r13
	pushq	%r14
	pushq	%r15

	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	movq	$7, -8(%rbp)
	movq	-8(%rbp), %rax
	popq	%r15	## pop the caller owned registers of the stack
	popq	%r14
	popq	%r13
	popq	%r12
	popq	%rbx
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
	callq	_sandbox
	movl	$1, %ecx
	movq	%rax, -16(%rbp)         ## 8-byte Spill
	movl	%ecx, %eax
	addq	$16, %rsp
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.section	__TEXT,__cstring,cstring_literals
L_.str:                                 ## @.str
	.asciz	"%10d\n"


.subsections_via_symbols
