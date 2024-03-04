# Architecture ovewview

For this section, we expect you to read Chapter 2 of the textbook on X86-64 Assembly Language Programming
and we make a few comments about the text in regards to compilation.

## Section 2.1
The main components of the computer, from a compiler view, are
* the CPU which constains 16 64 bit registers named as shown below
* the Random Access Memory which contains up to 2^64 bytes, each of which has a 64 bit address
   * the actual memory might not actually be that large (16 billion terabytes!)
   * data are stored using 1,2,4, or 8 bytes (byte, word, double word, quad word)


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

## Section 2.5
Memory is laid out with the code and static data at the low end, followed by the heap which grows up, and the stack which grows down
```
Stack growing down
...
"top" of stack
...
unused space
...
"top" of heap
...
"start of heap"
BSS 0 uninitialized data
initialized data
text (code)
lowest bytes reserved for the operating system
```



