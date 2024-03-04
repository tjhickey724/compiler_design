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

## Chapter 3 Data representaiton
All of our data will be either
* 32 bit integer (but we could also make them all 64 bit to simplify the compiler)
* integer arrays (which are allocated in the heap and represented by the 64 bit address of the lowest byte)
* Java objects (which are also allocated in the heap)

### Signed integers
in 32 bit integers, the most significant bit is the sign bit
If the unsigned binary number is n and if the sign bit is 1, then it represents n-2^32

We won't worry about floating point, but it is in the textbook

Also, we don't worry about Strings, but they are typically encoded with unicode (2 bytes per character)




