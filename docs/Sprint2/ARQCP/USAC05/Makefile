#Makefile

program: asm2.o asm.o main.o
	gcc asm2.o asm.o main.o -o program

asm.o: asm.s
	gcc -g -Wall -Wextra -fanalyzer -c asm.s -o asm.o

asm2.o: asm2.s
	gcc -g -Wall -Wextra -fanalyzer -c asm2.s -o asm2.o

main.o: main.c asm.h
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

clean:
	rm -f *.o program

run: program
	./program
