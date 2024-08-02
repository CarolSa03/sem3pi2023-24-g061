#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "asm.h"
#include "/media/sf_partilha/arqcp23242dfg04/sprint3/structs/structs.h"

SensorInfo** receive_data(int n, FILE *file){
    SensorInfo** data = (SensorInfo**) malloc(n * sizeof(SensorInfo*));							// allocate memory for an array of SensorInfo pointers

    if(data == NULL){																			// verify if allocation worker properly
        printf("Erro na alocação de memória\n");
        return NULL;
    }
    
    for (int i = 0; i < n; i++){
        *(data + i) = (SensorInfo*) malloc(sizeof(SensorInfo));									// allocate memory for each SensorInfo
        if(*(data + i) == NULL){																// check if memory allocation was successful for each one
            printf("Erro na alocação de memória\n");
            return NULL;
        }
    }
    
    int i = 0;
    int sensor_id_extracted, value_extracted, time_extracted;																						// counter
    char line[256];																				// array to store a line from the file
    while(i < n){
        fgets(line, sizeof(line), file);														// read data from the file
        sensor_id_extracted = extract_token(line, "sensor_id", &((*(data + i)) ->sensor_id));	// save sensor_id in SensorInfo
        value_extracted = extract_token(line, "value", &((*(data + i)) ->value));				// save value in SensorInfo
        time_extracted = extract_token(line, "time", &((*(data + i)) ->timestamp));				// save time in SensorInfo
        
        if(sensor_id_extracted && value_extracted && time_extracted) {
            i++;																				// increment counter
        } else {
            usleep(1001 * 1000);																// wait for 1.001 seconds
        }    
    }

    return data;
}
