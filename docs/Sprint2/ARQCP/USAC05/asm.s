.section .text
    .global mediana					#function to find median of an array

mediana:
	cmpl	$1, %esi				#check if array has 1 or less elements
	jl 		zero					#check if array has 0 element
	je		one						#check if array has 1 element
	
	pushq 	%rsi					#save the original value of rsi
	pushq 	%rdx					#save the original value of rdx
	pushq 	%rdi					#save the original value of rdi
	call 	sort_array				#call function to sort array
	popq 	%rdi					#restore the original rdi value					
	popq 	%rdx					#restore the original rdx value	
	popq 	%rsi					#restore the original rsi value	
	
	movl 	%esi, %eax				#move array size value to eax
	cdq								#sign-extend double word (long) in %eax to quad word in %edx:%eax
	
	movl 	$2, %r8d				#move value 2 to r8d
	idivl 	%r8d					#divide eax value by r8d value (2), quotient is in eax, remainder is in edx
	
	cmpl 	$0, %edx				#check if remainder is 0
	je		even					#if it is, the number is even, so jump to even
	jne		odd						#if it isn't, the number is odd, so jump to odd
	
zero:
	movl 	$0, %eax				#move 0 to eax
	ret
	
one:
	movl 	(%rdi), %eax			#move unique value to eax
	ret
	
even:
	movl	%eax, %ecx				#move quotient of the divison (index in array) to ecx
	subl 	$1, %ecx				#subtract 1 to the index 
	
	movl 	(%rdi, %rcx, 4), %eax	#move the value in that index to eax
	ret
	
odd:
	movl 	%eax, %ecx				#move quotient of the divison (index in array) to ecx
	
	movl 	(%rdi, %rcx, 4), %eax	#move the value in that index to eax
	ret							
	
