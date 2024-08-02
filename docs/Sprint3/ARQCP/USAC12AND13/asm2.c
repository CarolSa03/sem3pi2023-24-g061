#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <errno.h>
#include "/media/sf_partilha/arqcp23242dfg03/sprint3/structs/structs.h"

DataComponent* initializeDataComponent(char *dataExitDirectory, char *farmCoordinatorDirectory) {
    DataComponent *value = (DataComponent *)malloc(sizeof(DataComponent));

    if (value == NULL) {
        printf("Error allocating memory for ComponentData\n");
        exit(EXIT_FAILURE);
    }

    value->dataExitDirectory = dataExitDirectory;
    value->farmCoordinatorDirectory = farmCoordinatorDirectory;

    if (mkdir(value->dataExitDirectory, 0755) != 0 && errno != EEXIST) {
        printf("Error creating input directory\n");
        exit(EXIT_FAILURE);
    }

    if (mkdir(value->farmCoordinatorDirectory, 0755) != 0 && errno != EEXIST) {
        printf("Error creating output directory\n");
        exit(EXIT_FAILURE);
    }

    return value;
}
