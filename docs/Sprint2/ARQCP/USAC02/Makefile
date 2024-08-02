prog_usac02: asm.o main.o
	gcc asm.o main.o -o prog_usac02
	
main.o: main.c asm.h
	gcc -g -Wall -Wextra -fanalyzer -c main.c -o main.o
	
asm.o : asm.s
	gcc -g -Wall -Wextra -fanalyzer -c asm.s -o asm.o
	
debug: prog_usac02
	gdb prog_usac02

run: prog_usac02
	./prog_usac02

clean:
	rm -f *.o prog_usac02
