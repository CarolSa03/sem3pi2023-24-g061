program: main.o asm.o asm2.o
	gcc -g -Wall -Wextra -fanalyzer main.o asm.o asm2.o -o program

main.o: main.c asm.h
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o

asm.o: asm.c
	gcc -g -Wall -Wextra -fanalyzer -c asm.c -o asm.o

asm2.o: asm2.c
	gcc -g -Wall -Wextra -fanalyzer -c asm2.c -o asm2.o

run: program
	./program intermedio output 60000

clean:
	rm *.o program
