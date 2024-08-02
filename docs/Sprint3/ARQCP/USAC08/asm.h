#include <stdio.h>
#include "/media/sf_partilha/arqcp23242dfg04/sprint3/usac06/asm.h"
#include "/media/sf_partilha/arqcp23242dfg04/sprint3/structs/structs.h" 

int extract_token(char* input, char* token, int* output);
SensorInfo** receive_data(int numLeituras, FILE *file);
