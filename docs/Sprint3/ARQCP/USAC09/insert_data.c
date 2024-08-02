#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "asm.h"
#include "/media/sf_partilha/arqcp23242dfg04/sprint3/structs/structs.h"

void insert_data(Sensor *sensor, SensorData* sensor_data) {
    sensor->id = sensor_data->id;

    sensor->type = (char*)calloc(strlen(sensor_data->type) + 1, sizeof(char));		// check if the memory allocation was successful
    if (sensor->type != NULL) {
        strcpy(sensor->type, sensor_data->type);									// copies the contents to the position apointed 
    } else {
        free(sensor->unity); 														// free previously allocated memory
        return; 																	// return an error code or use a different mechanism to indicate an error
    }

    sensor->unity = (char*)calloc(strlen(sensor_data->unity) + 1, sizeof(char));	// check if the memory allocation was successful
    if (sensor->unity != NULL) {
        strcpy(sensor->unity, sensor_data->unity);									// copies the contents to the position apointed
    } else {
        free(sensor->type); 														// free previously allocated memory
        return; 																	// return an error code or use a different mechanism to indicate an error
    }

    sensor->write_counter = 0;														// initialize the 'write_counter' to 0
    sensor->last_read = 0;															// initialize the 'last_read' to 0
    sensor->time_out = sensor_data->timeOut;										// set the 'timeOut' value to the timestamp from sensor_data

    return;
}
