#include <stdio.h>
#include "asm.h"
#include "asm2.h"

int main(){
	
	int evenVec[] = {-2, 4, 5, 54, 123, -22};
	int evenNum = sizeof(evenVec)/sizeof(int);
	int oddVec[] = {1, 50, -20, 120, 293, -200, 100};
	int oddNum = sizeof(oddVec) /sizeof(int);
	int resultEven;
	int resultOdd;
	
	int *ptrEvenVec = evenVec;
	int *ptrOddVec = oddVec;
	
	printf("\nUnsorted array with even size: \n");
	for (int i = 0; i < evenNum; i++)
	{
		printf("%d ", *(ptrEvenVec + i));
	}
	printf("\n\n");
	
	printf("Unsorted array with odd size: \n");
	for (int i = 0; i < oddNum; i++)
	{
		printf("%d ", *(ptrOddVec + i));
	}
	printf("\n\n\n");

	resultEven = mediana(evenVec, evenNum);
	resultOdd = mediana(oddVec, oddNum);
	
	printf("Sorted array with even size: \n");
	for (int j = 0; j < evenNum; j++)
	{
		printf("%d ", *(ptrEvenVec + j));
	}
	printf("\n\n");	
	
	printf("Sorted array with odd size: \n");	
	for (int j = 0; j < oddNum; j++)
	{
	printf("%d ", *(ptrOddVec + j));
	}
	
	printf("\n\n\n");
	printf("Median of array with even size: %d\n", resultEven);
	printf("Median of array with odd size: %d\n", resultOdd);
	return 0;
}
