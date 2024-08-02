#include <stdio.h>

#ifndef STRUCTS_H
#define STRUCTS_H

typedef struct {
    int id;
    char* type;
    char* unity;
    int timeOut;
} SensorData;

typedef struct {
    int *array;
    int length;
    int read;
    int write;
} CircularBuffer;

typedef struct {
    CircularBuffer *buffer;
    int sensor_id;
    int timestamp;
    char type[50];
    char unit[50];
} SensorConfig;

typedef struct {
    int id;
    char* type;
    char* unity;
    int write_counter;
    int last_read;
    int time_out;
} Sensor;

typedef struct {
    int sensor_id;
    int value;
    int timestamp;
} SensorInfo;


typedef struct{
    char *dataExitDirectory;
    char *farmCoordinatorDirectory;
} DataComponent;

#endif // STRUCTS_H
