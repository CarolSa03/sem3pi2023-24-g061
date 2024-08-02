#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include "/media/sf_partilha/arqcp23242dfg03/sprint3/structs/structs.h"

SensorInfo *sensorsArray = NULL;
int n = 0;

void addOrUpdateSensor(int value, int id) {
    for (int i = 0; i < n; ++i) {
        if (sensorsArray[i].sensor_id == id) {
            sensorsArray[i].value = value;
            return;
        }
    }

    n++;
    sensorsArray = realloc(sensorsArray, n * sizeof(SensorInfo));
    if (sensorsArray == NULL) {
        printf("Error reallocating memory");
        exit(EXIT_FAILURE);
    }

    sensorsArray[n - 1].sensor_id = id;
    sensorsArray[n - 1].value = value;
}

void writeDataInOutputFIle(char *farmCoordinatorDirectory) {
    char outputFilePath[512];
    snprintf(outputFilePath, sizeof(outputFilePath), "%s/results.txt", farmCoordinatorDirectory);

    FILE *outputFile = fopen(outputFilePath, "w");
    if (outputFile == NULL) {
        printf("Error opening output file");
        return;
    }

    for (int i = 0; i < n; ++i) {
        fprintf(outputFile, "Sensor ID: %d\nLatest Sensor Value: %.2d\n\n",
                sensorsArray[i].sensor_id, sensorsArray[i].value);
    }

    if (fclose(outputFile) != 0) {
        printf("Error closing output file");
        return;
    }
}

void processDataOfSensor(char *dataExitDirectory, char *farmCoordinatorDirectory) {
    DIR *dir;
    struct dirent *ent;
    if ((dir = opendir(dataExitDirectory)) != NULL) {
        char dataFiles[100][512];
        int numDataFiles = 0;

        while ((ent = readdir(dir)) != NULL) {
            char filePath[512];
            snprintf(filePath, sizeof(filePath), "%s/%s", dataExitDirectory, ent->d_name);

            if (strstr(ent->d_name, "sensors.txt") != NULL) {
                strcpy(dataFiles[numDataFiles], filePath);
                numDataFiles++;
            }
        }

        if (numDataFiles == 0) {
            printf("No data files found in the directory.\n");
            closedir(dir);
            return;
        }

        for (int j = 0; j < numDataFiles; ++j) {
            FILE *file = fopen(dataFiles[j], "r");
            if (file == NULL) {
                printf("Error opening file");
                continue;
            }

            char line[256];

            while (fgets(line, sizeof(line), file) != NULL) {
                int sensorId;
                int sensorValue;

                if (sscanf(line, "%d,%*d,%*[^,],%*[^,],%d#", &sensorId, &sensorValue) == 3) {
                    addOrUpdateSensor(sensorValue / 100, sensorId);

                }
            }

            fclose(file);
        }

        writeDataInOutputFIle(farmCoordinatorDirectory);
        closedir(dir);
    } else {
        printf("Error opening directory");
    }
}

