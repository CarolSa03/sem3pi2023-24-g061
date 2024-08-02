.section .text
	.global enqueue_value
	
enqueue_value:
	#array is stored, by default, in rdi register
	#length is stored, by default, in esi register
	#read is stored, by default, in rdx register
	#write is stored, by default, in rcx register
	#value is stored, by default, in r8d register
	
	#prologue
	pushq %rbp					#save base pointer on stack
	movq %rsp, %rbp				#set base pointer
	
	#body
	
get_write_value:
	cmpl %esi, (%rcx)
	jl insert_in_write_position
	
	subl %esi, (%rcx)
	incl (%rdx)
	
	jmp get_write_value
	
insert_in_write_position:
	movl (%rcx), %eax
	movl %r8d, (%rdi, %rax, 4) 	

	decl %esi
	cmpl %esi, (%rcx)
	jl increment_write
	
	movl $0, (%rcx)
	
	jmp compare_read_and_write
	
increment_write:
	incl (%rcx)
	
compare_read_and_write:
	movl (%rcx), %eax
	cmpl %eax, (%rdx)
	jne end
	
	incl (%rdx)
	
end:
	incl %esi
	 
	#epilogue
	movq %rbp, %rsp				#restore stack pointer
	popq %rbp					#restore base pointer
	
	ret
