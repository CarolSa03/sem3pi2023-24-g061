#include <stdio.h>
#include "asm.h"

int main() {
    int buffer[] = {1, 2, 3, 4};
    int lenght = 4;
    int read = 3;
    int write = 2;
    int num_elements = 3;
    int vec[3];

    move_num_vec(buffer, lenght, &read, &write, num_elements, vec);

    printf("Elementos removidos: ");

    for (int i = 0; i < num_elements; i++) {
        printf("%d ", vec[i]);
    }
    printf("\n");
    return 0;
}
