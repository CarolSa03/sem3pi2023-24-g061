.section .text
    .global extract_token

extract_token:
    movq $0, %r9
    movq $0, %r10
    movq $0, %rcx

    movq %rsi, %r8

match_first_char:
    movq %r8, %rsi       # reset token

    movb (%rdi), %cl
    cmpb $0, %cl
    jz end

    cmpb %cl, (%rsi)
    je next_char

    incq %rdi
    jmp match_first_char

next_char:
    incq %rsi

    movb (%rsi), %r9b
    cmpb $0, %r9b
    je copy_token_value          # If null terminator, copy the value of the token

    incq %rdi

    cmpb %r9b, (%rdi)
    je next_char                # If equal, go to the next character
    jmp match_first_char          # If not, reset token

copy_token_value:
    incq %rdi

    movb (%rdi), %cl
    cmpb $'.', %cl
    je copy_token_value           # If is a decimal point, continue copying
    jl copy_value_end            # If less than '0', end

    cmpb $'9', %cl
    jg copy_value_end              # If greater than '9', end

    imull $10, %r10d
    subb $'0', %cl                # Convert character to integer
    addl %ecx, %r10d

    jmp copy_token_value

copy_value_end:
    movl %r10d, (%rdx)
    ret

end:
    ret