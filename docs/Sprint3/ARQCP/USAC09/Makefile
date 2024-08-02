#Makefile

program: insert_data.o
	gcc insert_data.o -o program

insert_data.o: insert_data.c asm.h
	gcc -g -Wall -Wextra -fanalyzer -c insert_data.c -o insert_data.o

clean:
	rm -f *.o program

run: program
	./program
