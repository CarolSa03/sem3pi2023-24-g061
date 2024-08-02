# USLP02 - As Product Owner, I want a functionality that consists on simulating an irrigation system controller

# 4. Tests 

**Test 1: assert the irrigation plan is created successfully according to the imported data**

```java
    @Test
    void createIrrigationPlanTest() {
        IrrigationPlan expected = new IrrigationPlan(LocalTime.of(8, 30), LocalTime.of(9, 24), LocalTime.of(17,0), LocalTime.of(17, 54), cipList);
        IrrigationPlan actual = irrigationSystem.createIrrigationPlan(fileContent);
        assertEquals(expected, actual);
    }
```

**Test 2: assert it returns true when the system's on** 

```java
    @Test
    void testIfOn() {
        LocalDate date = LocalDate.of(2023, 10, 21);
        LocalTime time = LocalTime.of(8, 33);
        boolean expected = true;
        boolean actual = irrigationSystem.isOn(date, time);
        assertEquals(expected, actual);
    }
```

**Test 3: assert it returns false when the system's off** 

```java
    @Test
    void testIfOff() {
        LocalDate date = LocalDate.of(2021, 6, 1);
        LocalTime time = LocalTime.of(21, 12);
        boolean expected = false;
        boolean actual = irrigationSystem.isOn(date, time);
        assertEquals(expected, actual);
    }
```

**Test 4: assert it gets the currently irrigated plot without the regularity restriction**

```java
    @Test
    void getCurrentIrrigatedPlotWithoutRegularityRestriction() {
        LocalDate date = LocalDate.of(2023, 10, 21);
        LocalTime time = LocalTime.of(8, 33);
        CurrentlyIrrigatedPlot expected = new CurrentlyIrrigatedPlot(new Plot("A"), 11);
        CurrentlyIrrigatedPlot actual = irrigationSystem.getCurrentlyIrrigatedPlot(date, time);
        assertEquals(expected, actual);
    }
```

**Test 5: assert it gets the currently irrigated plot with the regularity restriction**

```java
    @Test
    void getCurrentIrrigatedPlotWithRegularityRestriction() {
        LocalDate date = LocalDate.of(2023, 10, 19);
        LocalTime time = LocalTime.of(9, 12);
        CurrentlyIrrigatedPlot expected = new CurrentlyIrrigatedPlot(new Plot("D"), 5);
        CurrentlyIrrigatedPlot actual = irrigationSystem.getCurrentlyIrrigatedPlot(date, time);
        assertEquals(expected, actual);
    }

```

**Test 6: assert the duration that a plot takes to be irrigated isn't a negative number**

```java
    @Test
    void checkThatDurationCantBeNegative() {
        fileContent = List.of("8:30,17:00", "A,-14,T", "B,8,T", "C,25,P", "D,25,I", "E,7,T", "F,10,3");
        assertThrowsExactly(IllegalArgumentException.class, () -> irrigationSystem.createIrrigationPlan(fileContent), "Duration in minutes must be a positive integer.");
    }

```

**Test 7: assert the beginning of the second cycle is after the end of the first one**

```java
    @Test
    void checkThatSecondCycleBeginCantBeBeforeFirstCycleEnd() {
        fileContent = List.of("8:30,9:00", "A,14,T", "B,8,T", "C,25,P", "D,25,I", "E,7,T", "F,10,3");
        assertThrowsExactly(IllegalArgumentException.class, () -> irrigationSystem.createIrrigationPlan(fileContent), "Second cycle begin must be after first cycle end.");
    }

```

# 5. Construction (Implementation)


## Class IrrigationSystemUI

```java
public void run() {

    int option = Utils.readIntegerFromConsole("Do you want to import a file with the irrigation plan? If so, please press 1 (take in mind that the file must be placed in folder docs/Sprint2/LAPR3/USLP02).\n" +
                                                "If you want to see the simulation for file irrigationPlanFor30Days.txt, press 2 and the system will import that information.");

    String fileName = null;
    if (option == 1) {
        fileName = Utils.readLineFromConsole("Please specify the file name: ");
    }

    controller.getFileContent(fileName);
    IrrigationPlanDTO irrigationPlanDTO = controller.getIrrigationPlanDTO();

    LocalDate irrigationPlanCreationDate = irrigationPlanDTO.getCreationDate();
    int numberOfDays = irrigationPlanDTO.getNumberOfDays();

    LocalDate date = LocalDate.now();
    LocalTime time = LocalTime.now();

    String answer = Utils.readLineFromConsole("Do you want to check if the system is on for a specific date/time slot? (Yes/No)\nIf so, enter the date and time; else, the system will show you the current status.\n");

    if (answer.equalsIgnoreCase("Yes")) {
        date = Utils.readDateFromConsole("Enter the date (YYYY-MMM-DD):\nPlease take in mind that the irrigation plan is valid for " + numberOfDays + " days, starting " + irrigationPlanCreationDate + ".");
        time = Utils.readTimeFromConsole("Enter the time (HH:MM):\nPlease take in mind that it must be a value between 00:00 and 23:59.");
    } else if (answer.equalsIgnoreCase("No")) {
        System.out.println("The system will show you the current status.");
    } else {
        System.out.println("Invalid answer. The system will show you the current status.");
    }

    if (controller.isOn(date, time)) {
        CurrentlyIrrigatedPlotDTO currentlyIrrigatedSectorDTO = controller.getCurrentlyIrrigatedPlotDTO(date, time);
        showStatus(time, irrigationPlanDTO, currentlyIrrigatedSectorDTO);
    } else {
        System.out.println("Irrigation system is off for the desired date and time.");
    }

    if (controller.createFile()) {
        System.out.println("File with the irrigation plan created successfully.\nIt can be found at docs/Sprint2/LAPR3/USLP02 by the name irrigationPlanData.csv.");
    } else {
        System.out.println("File not created.");
    }

}
```


## Class IrrigationSystemController

```java
public IrrigationSystemController() {
    irrigationSystem = getIrrigationSystem();
}
```

## Class IrrigationSystem

```java
public IrrigationPlan createIrrigationPlan(List<String> fileContent) {

    LocalDate date = irrigationPlan.getCreationDate();

    String line1 = fileContent.get(0);
    String[] splitLine1 = line1.split(",");

    LocalTime firstCycleBegin = LocalTime.parse(Utils.parsedTimeHelper(splitLine1[0]));
    LocalTime secondCycleBegin = LocalTime.parse(Utils.parsedTimeHelper(splitLine1[1]));

    LocalTime firstCycleEnd = firstCycleBegin;
    LocalTime secondCycleEnd = secondCycleBegin;

    List<CurrentlyIrrigatedPlot> cipList = new ArrayList<>();

    for (String line : fileContent.subList(1, fileContent.size())) {

        String[] split = line.split(",");
    
        Plot plot = new Plot(split[0]);
    
        int durationInMinutes = Integer.parseInt(split[1]);
    
        String regularity = split[2];
    
        cipList.add(new CurrentlyIrrigatedPlot(plot, durationInMinutes, regularity, 0));
    
        if (!checkIfOffInDay(date, regularity)) {
            firstCycleEnd = firstCycleEnd.plusMinutes(durationInMinutes);
            secondCycleEnd = secondCycleEnd.plusMinutes(durationInMinutes);
        }
    
        date.plusDays(1);

    }

    return irrigationPlan = new IrrigationPlan(firstCycleBegin, firstCycleEnd, secondCycleBegin, secondCycleEnd, cipList);

}
```

## Class IrrigationPlan

```java
public IrrigationPlan(LocalTime firstCycleBegin, LocalTime firstCycleEnd, LocalTime secondCycleBegin, LocalTime secondCycleEnd, List<CurrentlyIrrigatedPlot> cipList) {
    setFirstCycleBegin(firstCycleBegin);
    setFirstCycleEnd(firstCycleEnd);
    setSecondCycleBegin(secondCycleBegin);
    setSecondCycleEnd(secondCycleEnd);
    setCipList(cipList);
}
```


# 6. Integration and Demo 

* A new option on the main main was added to demonstrate the implementation of this user story.