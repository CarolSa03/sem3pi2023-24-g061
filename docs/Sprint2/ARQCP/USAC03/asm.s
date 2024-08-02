.section .text
    .global move_num_vec

move_num_vec:
    #prologue
    pushq %rbp
    movq %rsp, %rbp

    # Parameters
    # %rdi = array pointer
    # %esi = length of array
    # %rdx = reader pointer
    # %rcx = writer pointer
    # %r8d = num
    # %r9 = vec (copy array)

    cmpl %esi, %r8d              	# Compare length with num
    jg ret_zero       				# Jump if num is greater than array length

    movl $1, %eax          			# Set return value to 1

vec_loop:
    cmpl $0, %r8d
    je done                			# Jump to done if num is 0

    movl (%rdx), %r10d     			# Load value at read pointer
    cmpl %r10d, (%rcx)     			# Compare with value at write pointer
    je ret_zero            			# Jump to ret_zero if values are equal

    movl (%rdi, %r10, 4), %r11d  	# Load value from array to r11d
    movl %r11d, (%r9)     			# Store the value into copy array
	addq $4, %r9          			# Move copy array pointer forward

    cmpl %esi, %r11d       			# Compare read value with array length
    je read_to_zero        			# Jump to read_to_zero if read value equals array length

    incl (%rdx)            			# Increment read pointer
    decl %r8d              			# Decrement num
    jmp vec_loop           			# Continue loop

read_to_zero:
    movl $0, (%rdx)        			# Set value at read pointer to 0

next_iteration:
    decl %r8d              			# Decrement num
    jmp vec_loop           			# Continue loop

ret_zero:
    movl $0, %eax          			# Set return value to 0

done:
    #epilogue
    movq %rbp, %rsp
    popq %rbp
    ret
