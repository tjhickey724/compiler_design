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


