#include <stdio.h>
#include "asm.h"

int main() {
	
	int vec[] = {2, 0, 1, 3, 4, 5};
	
	int *array = vec;
	int length = 6;
	
	int rd = 0;
	int *read = &rd;
	
	int wr = 0;
	int *write = &wr;
	
	int value = 10;
	
	for (int i = 0; i < length; i++) {
		printf("Position %d: %d\n", i+1, vec[i]);
	}
	printf("\n\n");
	
	enqueue_value(array, length, read, write, value);
	
	for (int i = 0; i < length; i++) {
		printf("Position %d: %d\n", i+1, vec[i]);
	}
	
	return 0;
	
}
