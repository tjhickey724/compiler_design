# X86-64 Architecture

The X86 architecture consists of 16 64-bit registers 
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
There are variations of these names for the embedded 32, 16, and 8 bit subwords.
