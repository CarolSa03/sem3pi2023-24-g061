#Makefile

program: extract_token. receive_data.o
	gcc extract_token.o receive_data.o -o program

receive_data.o: receive_data.c asm.h
	gcc -g -Wall -Wextra -fanalyzer -c receive_data.c -o receive_data.o
	
extract_token.o: /media/sf_partilha/arqcp23242dfg04/sprint3/usac06/asm.s asm.h
	gcc -g -Wall -Wextra -fanalyzer -c /media/sf_partilha/arqcp23242dfg04/sprint3/usac06/asm.s -o extract_token.o

clean:
	rm -f *.o program

run: program
	./program
