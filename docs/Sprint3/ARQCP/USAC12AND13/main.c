#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/time.h>
#include "asm.h"

int periodicityInSecs;
DataComponent *componentData;

void handleAlarm() {
    processDataOfSensor(componentData->dataExitDirectory, componentData->farmCoordinatorDirectory);
    alarm(periodicityInSecs);
}

int main(int argc, char *argv[]) {
    if (argc != 4) {
        printf("Wrong number of arguments!\n");
        exit(EXIT_FAILURE);
    }

    char *dataExitDirectoryValue = argv[1];
    char *farmCoordinatorDirectoryValue = argv[2];
    int periodicity = atoi(argv[3]);

    periodicityInSecs = periodicity / 1000;

    initializeDataComponent(dataExitDirectoryValue, farmCoordinatorDirectoryValue);

    struct sigaction sa;
    sa.sa_handler = (void (*)(int))handleAlarm;
    sigaction(SIGALRM, &sa, NULL);

    struct itimerval timer;
    timer.it_value.tv_sec = periodicityInSecs;
    timer.it_value.tv_usec = 0;

    setitimer(ITIMER_REAL, &timer, NULL);
    
    while (1) {
        sleep(1);
    }

    return 0;
}

