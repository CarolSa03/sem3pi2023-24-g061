#include <stdio.h>
#include "asm.h"

int main(){
	
	int vec[] = {43, -2, -123, 203, 482, 20};
	int num = sizeof(vec)/sizeof(int);
	int *ptrvec = vec;
	
	printf("\nUnsorted array: \n");
	for (int i = 0; i < num; i++)
	{
		printf("%d ", *(ptrvec + i));
	}
	
	sort_array(vec, num);
	printf("\n\n");
	
	printf("Sorted array: \n");
	for (int j = 0; j < num; j++)
	{
		printf("%d ", *(ptrvec + j));
	}
	
	printf("\n");
	
	return 0;
}
