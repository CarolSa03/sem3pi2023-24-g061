.section .text
    .global sort_array		#function to sort an array in ascending order

sort_array:
	cmpl	$1, %esi		#check if array has 1 or less elements
	jle		end				#if it does jump to end
    jmp		main_loop		#start

main_loop:
	cmpl	$0, %esi		#check if it is the end of the original array
	jle		end 			#if it is jump to end

	movq	%rdi, %rdx		#copy original array to %rdx
	movl	%esi, %ecx 		#copy number of elements of original array to %ecx

secundary_loop:
	decl	%ecx 			#copyNum--
	cmpl	$0, %ecx 		#check if it is the end of the copied array
	je		complete		#if it is jump to complete

	addq	$4, %rdx 		#move to next element of copied array

	movq	$0, %r8 		#reset r8
	movq	$0, %r9 		#reset r9
	
	movl	(%rdi), %r8d	#copy current value of original array to %r8d
	movl	(%rdx), %r9d	#copy current value of copied array to %r9d

	cmpl	%r8d, %r9d 		#compare value of copied array to value of original array
	jge		secundary_loop	#if the value of copied array is greater than the value of original array then next secundary iteration
	jl 		switch			#if the value of copied array is lesser than the value of original array then switch numbers

complete:
	addq	$4, %rdi 		#move to next element of original array
	decl	%esi 			#num--

    jmp 	main_loop 		#next main iteration
    
switch:
	movl	%r9d, (%rdi) 	#change current value of original array to the copy array value 
	movl	%r8d, (%rdx) 	#change current value of copy array to the original array value 
	
	jmp secundary_loop		#next secundary iteration

end:
    ret						#return sorted array
