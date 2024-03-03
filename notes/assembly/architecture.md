# Architecture ovewview

For this section, we expect you to read Chapter 2 of the textbook on X86-64 Assembly Language Programming
and we make a few comments about the text in regards to compilation.

There are 16 64-bit registers that by convention have certain roles as listed below.
```
rax - return value
rbx - callee save value (preserved after calling)
rcx - arg 4
rdx - arg 3
rsi - arg 2
rdi - arg 1
rbp - callee save (frame pointer)
rsp - stack pointer
r8 -  5th arg
r9 -  6th arg
r10 - temporary
r11 - temporary
r12 - callee save
r13 - callee save
r14 - callee save 
r15 - callee save
```


