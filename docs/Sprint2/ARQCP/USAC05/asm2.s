.section .text
    .global sort_array		#function to sort an array in descending order

sort_array:
	pushq 	%rbp 			#save the original value of rbp
	movq 	%rsp, %rbp 		#copy the current stack pointer to rbp
	
    jmp		main_loop		#start

main_loop:
	cmpl	$0, %esi		#check if it is the end of the original array
	je		end 			#if it is jump to end

	movq	%rdi, %rdx		#copy original array to %rdx
	movl	%esi, %ecx 		#copy number of elements of original array to %ecx

secundary_loop:
	decl	%ecx 			#copyNum--
	cmpl	$0, %ecx 		#check if it is the end of the copied array
	je		complete		#if it is jump to complete

	addq	$4, %rdx 		#move to next element of copied array
	
	movl	(%rdi), %r8d	#copy current value of original array to %r8d
	movl	(%rdx), %r9d	#copy current value of copied array to %r9d

	cmpl	%r8d, %r9d 		#compare value of copied array to value of original array
	jle		secundary_loop	#if the value of copied array is greater than the value of original array then next secundary iteration
	jg 		switch			#if the value of copied array is lesser than the value of original array then switch numbers

complete:
	addq	$4, %rdi 		#move to next element of original array
	decl	%esi 			#num--

    jmp 	main_loop 		#next main iteration
    
switch:
	movl	%r9d, (%rdi) 	#change current value of original array to the copy array value 
	movl	%r8d, (%rdx) 	#change current value of copy array to the original array value 
	
	jmp secundary_loop		#next secundary iteration

end:
	movq 	%rbp, %rsp 		#retrieve the original rsp value
	popq 	%rbp 			#restore the original rbp value
    ret						#return sorted array
