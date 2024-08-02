#ifndef ASM_H
#define ASM_H

typedef struct {
    char *dataExitDirectory;
    char *farmCoordinatorDirectory;
} DataComponent;

DataComponent* initializeDataComponent(char *dataExitDirectory, char *farmCoordinatorDirectory);
void processDataOfSensor(char *dataExitDirectory, char *farmCoordinatorDirectory);

#endif
