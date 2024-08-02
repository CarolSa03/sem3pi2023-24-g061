# USLP10 - As Product Owner, I intend to improve the functionality previously developed in USLP02, which consists of simulating an irrigation system controller; the improvement consists of modeling aspects related to fertigation.

# 4. Tests 

**Set Up: to help testing this user story, the following code was added to the IrrigationSystemTest class**

```java
private IrrigationSystem irrigationSystem;
private List<String> fileContent;

@BeforeEach
void setUp() {
    irrigationSystem = new IrrigationSystem();
    fileContent = ReadFile.readFile("docs/data/", "irrigationPlanFor30Days.txt");
    irrigationSystem.createIrrigationPlan(fileContent);
}
```

**Test 1: assert it returns true when the system's on** 

```java
@Test
void testIfOn() {
    LocalDate date = LocalDate.of(2023, 12, 25);
    LocalTime time = LocalTime.of(8, 33);
    boolean expected = true;
    boolean actual = irrigationSystem.isOn(date, time);
    assertEquals(expected, actual);
}
```

**Test 2: assert it returns false when the system's off** 

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

**Test 3: assert it gets the currently irrigated plot without the regularity restriction**

```java
@Test
void getCurrentIrrigationWithoutRegularityRestriction() {
    LocalDate date = LocalDate.of(2023, 12, 21);
    LocalTime time = LocalTime.of(8, 33);
    Pair<Irrigation, Integer> expected = new Pair<>(new Irrigation(new Sector(10), date, 14, LocalTime.of(8, 30), LocalTime.of(8, 44)), 11);
    Pair<Irrigation, Integer> actual = irrigationSystem.getCurrentIrrigation(date, time);
    assertEquals(expected, actual);
}
```

**Test 4: assert it gets the currently irrigated plot with the regularity restriction**

```java
@Test
void getCurrentIrrigationWithRegularityRestriction() {
    LocalDate date = LocalDate.of(2023, 12, 25);
    LocalTime time = LocalTime.of(9, 12);
    Pair<Irrigation, Integer> expected = new Pair<>(new Irrigation(new Sector(22), date, 25, LocalTime.of(8, 52), LocalTime.of(9, 17)), 5);
    Pair<Irrigation, Integer> actual = irrigationSystem.getCurrentIrrigation(date, time);
    assertEquals(expected, actual);
}
```

**Test 5: assert the duration that a sector takes to be irrigated isn't a negative number**

```java
@Test
void checkThatDurationCantBeNegative() {
    fileContent = List.of("8:30,17:00", "10,-14,T", "11,8,T", "21,25,P", "22,25,I", "41,7,T", "42,10,3");
    assertThrowsExactly(IllegalArgumentException.class, () -> irrigationSystem.createIrrigationPlan(fileContent), "Duration in minutes must be a positive integer.");
}
```

**Test 6: assert the beginning of the second cycle is after the end of the first one**

```java
@Test
void checkThatSecondCycleBeginCantBeBeforeFirstCycleEnd() {
    fileContent = List.of("8:30,9:00", "10,14,T", "11,8,T", "21,25,P", "22,25,I", "41,7,T", "42,10,3");
    assertThrowsExactly(IllegalArgumentException.class, () -> irrigationSystem.createIrrigationPlan(fileContent), "Cycle's begin must be after previous cycle's end.");
}
```

**Test 7: assert the beginning of the second cycle is after the beginning of the first one**

```java
@Test
void checkThatSecondCycleBeginCantBeBeforeFirstCycleBegin() {
    fileContent = List.of("8:30,8:00", "10,14,T", "11,8,T", "21,25,P", "22,25,I", "41,7,T", "42,10,3");
    assertThrowsExactly(IllegalArgumentException.class, () -> irrigationSystem.createIrrigationPlan(fileContent), "Cycle's begin must be after previous cycle's begin.");
}
```


# 5. Construction (Implementation)


## Class IrrigationSystemUI

```java
public void run() {

    System.out.println("###Irrigation System Controller Simulation###\n");

    boolean option = Utils.confirm("Do you want to import a file with the irrigation plan? (Yes/No).If so, take in mind that the file must be placed in folder docs/data.\n" +
            "If you want to see the simulation for file irrigationPlanFor30Days.txt, the system will import that information.");

    String fileName = null;
    if (option) {
        fileName = Utils.readLineFromConsole("Please specify the file name: ");
    }

    IrrigationPlanDTO irrigationPlanDTO = controller.getIrrigationPlanDTO(fileName);

    LocalDate irrigationPlanCreationDate = irrigationPlanDTO.getCreationDate();
    int numberOfDays = irrigationPlanDTO.getNumberOfDays();

    LocalDate date = Utils.getCurrentDateToDay();
    LocalTime time = Utils.getCurrentTimeToMinutes();

    boolean specificDateTimeSlot = Utils.confirm("Do you want to check if the system is on for a specific date/time slot? (Yes/No)\nIf so, enter the date and time; else, the system will show you the current status.\n");

    if (specificDateTimeSlot) {
        date = Utils.readDateFromConsole("Enter the date (DD/MM/YYYY):\nPlease take in mind that the irrigation plan is valid for " + numberOfDays + " days, starting " + irrigationPlanCreationDate + ".");
        time = Utils.readTimeFromConsole("Enter the time (HH:MM):\nPlease take in mind that it must be a value between 00:00 and 23:59.");
    } else {
        System.out.println("The system will show you its current status.");
    }

    if (controller.isOn(date, time)) {
        Pair<IrrigationDTO, Integer> currentIrrigation = controller.getCurrentIrrigation(date, time);
        showStatus(date, time, irrigationPlanDTO, currentIrrigation);
    } else {
        System.out.println("Irrigation system is off for the desired date and time.");
        IrrigationDTO nextIrrigation = controller.getNextIrrigation(date, time);
        if (nextIrrigation != null) {
            String dateToPrint = Utils.parsedDateHelper(nextIrrigation.getOperationDate().getDayOfMonth() + "/" + nextIrrigation.getOperationDate().getMonthValue() + "/" + nextIrrigation.getOperationDate().getYear());
            String timeToPrint = Utils.parsedTimeHelper(nextIrrigation.getStartTime().getHour() + ":" + nextIrrigation.getStartTime().getMinute());
            System.out.println("Next irrigation will take place at " + dateToPrint + " at " + timeToPrint + "h in sector " + nextIrrigation.getSector().getId() + ".");
        } else {
            System.out.println("There are no more irrigations scheduled.");
        }
    }

    if (controller.createFile()) {
        System.out.println("\nFile with the irrigation plan created successfully.\nIt can be found at docs/Sprint3/LAPR3/USLP10 under the name irrigationPlanData.csv.");
    } else {
        System.out.println("\nFile with the irrigation plan not created.");
    }

}
```


## Class IrrigationSystemController

```java
public IrrigationSystemController() {
    irrigationSystemRepository = getIrrigationSystemRepository();
    irrigationSystem = irrigationSystemRepository.getIrrigationSystem();
}
```


## Class Repositories

```java
public getIrrigationSystemRepository() {
    return irrigationSystemRepository();
}
```


## Class IrrigationSystemRepository

```java
public IrrigationSystemRepository() {
    irrigationSystem = new IrrigationSystem();
}
```


## Class IrrigationSystem

```java
public IrrigationPlan createIrrigationPlan(List<String> fileContent) {

    LocalDate date = irrigationPlan.getCreationDate();

    String line1 = fileContent.get(0);
    String[] splitLine1 = line1.split(",");

    Map<LocalDate, IrrigationPlanOfDay> irrigations = new TreeMap<>(LocalDate::compareTo);

    for (int i = 0; i < irrigationPlan.getNumberOfDays(); i++) {

        IrrigationPlanOfDay irrigationPlanOfDay = new IrrigationPlanOfDay();

        LocalTime firstCycleBegin = LocalTime.parse(Utils.parsedTimeHelper(splitLine1[0]), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime secondCycleBegin = LocalTime.parse(Utils.parsedTimeHelper(splitLine1[1]), DateTimeFormatter.ofPattern("HH:mm"));

        LocalTime firstCycleEnd = firstCycleBegin;
        LocalTime secondCycleEnd = secondCycleBegin;

        irrigationPlanOfDay.setFirstCycleBegin(firstCycleBegin);
        irrigationPlanOfDay.setFirstCycleEnd(firstCycleEnd);
        irrigationPlanOfDay.setSecondCycleBegin(secondCycleBegin);
        irrigationPlanOfDay.setSecondCycleEnd(secondCycleEnd);

        for (String line : fileContent.subList(1, fileContent.size())) {

            String[] split = line.split(",");

            Sector sector = new Sector(Integer.parseInt(split[0]));
            int durationInMinutes = Integer.parseInt(split[1]);
            if (durationInMinutes < 0) throw new IllegalArgumentException("Duration in minutes must be a positive integer.");
            String regularity = split[2];

            LocalTime firstStartTime = firstCycleEnd;
            LocalTime secondStartTime = secondCycleEnd;

            if (checkIfOnInDay(date, regularity)) {
                firstCycleEnd = firstCycleEnd.plusMinutes(durationInMinutes);
                secondCycleEnd = secondCycleEnd.plusMinutes(durationInMinutes);
                LocalTime firstEndTime = firstCycleEnd;
                LocalTime secondEndTime = secondCycleEnd;

                if (isFertigation(split)) {
                    Integer mixId = Integer.parseInt(split[3].substring(3));
                    Integer mixRecurrence = Integer.parseInt(split[4]);
                    if (checkIfFertigationInDay(date, mixRecurrence)) {
                        irrigationPlanOfDay.getIrrigationlist().add(new Fertigation(sector, date, durationInMinutes, firstStartTime, firstEndTime, regularity, mixId, mixRecurrence));
                        irrigationPlanOfDay.getIrrigationlist().add(new Fertigation(sector, date, durationInMinutes, secondStartTime, secondEndTime, regularity, mixId, mixRecurrence));
                    } else {
                        irrigationPlanOfDay.getIrrigationlist().add(new Irrigation(sector, date, durationInMinutes, firstStartTime, firstEndTime, regularity));
                        irrigationPlanOfDay.getIrrigationlist().add(new Irrigation(sector, date, durationInMinutes, secondStartTime, secondEndTime, regularity));
                    }
                } else {
                    irrigationPlanOfDay.getIrrigationlist().add(new Irrigation(sector, date, durationInMinutes, firstStartTime, firstEndTime, regularity));
                    irrigationPlanOfDay.getIrrigationlist().add(new Irrigation(sector, date, durationInMinutes, secondStartTime, secondEndTime, regularity));
                }

            } else if (isFertigation(split) && checkIfFertigationInDay(date, Integer.parseInt(split[4]))) {
                String dateString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                throw new IllegalArgumentException("Fertigation is not possible in this day (" + dateString + ").\nThe file provided is not valid and should be corrected.");
            }

        }

        irrigationPlanOfDay.setFirstCycleEnd(firstCycleEnd);
        irrigationPlanOfDay.setSecondCycleEnd(secondCycleEnd);

        irrigationPlanOfDay.getIrrigationlist().sort(Comparator.comparing(Irrigation::getStartTime));

        irrigations.put(date, irrigationPlanOfDay);
        date = date.plusDays(1);

    }

    return irrigationPlan = new IrrigationPlan(irrigations);

}
```

## Class IrrigationPlan

```java
private static final LocalDate creationDate = LocalDate.of(2023, 12, 20);
private static final int numberOfDays = 30;
private Map<LocalDate, IrrigationPlanOfDay> irrigationsPerDay;

public IrrigationPlan(Map<LocalDate, IrrigationPlanOfDay> irrigationsPerDay) {
    this.irrigationsPerDay = irrigationsPerDay;
}
```

## Class IrrigationPlanOfDay

```java
private LocalTime firstCycleBegin;
private LocalTime firstCycleEnd;
private LocalTime secondCycleBegin;
private LocalTime secondCycleEnd;
private List<Irrigation> irrigationList;

public IrrigationPlanOfDay(LocalTime firstCycleBegin, LocalTime firstCycleEnd, LocalTime secondCycleBegin, LocalTime secondCycleEnd, List<Irrigation> irrigationList) {
    setFirstCycleBegin(firstCycleBegin);
    setFirstCycleEnd(firstCycleEnd);
    setSecondCycleBegin(secondCycleBegin);
    setSecondCycleEnd(secondCycleEnd);
    setIrrigationList(irrigationList);
}
```

## Class Irrigation

```java
private Integer id;
private Sector sector;
private LocalDate operationDate;
private Integer durationInMinutes;
private LocalTime startTime;
private LocalTime endTime;
private String regularity;

public Irrigation(Sector sector, LocalDate operationDate, Integer durationInMinutes, LocalTime startTime, LocalTime endTime, String regularity) {
    this.sector = sector;
    this.operationDate = operationDate;
    this.durationInMinutes = durationInMinutes;
    this.startTime = startTime;
    this.endTime = endTime;
    this.regularity = regularity;
}
```

## Class Fertigation

```java
private Recipe mix;
private Integer mixRecurrence;

public Fertigation(Sector sector, LocalDate operationDate, Integer durationInMinutes, LocalTime startTime, LocalTime endTime, String regularity, Integer mixId, Integer mixRecurrence) {
    super(sector, operationDate, durationInMinutes, startTime, endTime, regularity);
    this.mix = new Recipe(mixId);
    this.mixRecurrence = mixRecurrence;
}
```

## Class Recipe

```java
private Integer id;
private List<ProductionFactor> productionFactors;

public Recipe(Integer id) {
    this.id = id;
    this.productionFactors = new ArrayList<>();
}
```

## Class ProductionFactor

```java
private Integer productionFactorId;
private String designation;

public ProductionFactor(Integer productionFactorId, String designation) {
    this.productionFactorId = productionFactorId;
    this.designation = designation;
}
```


# 6. Integration and Demo 

* An option to demonstrate the implementation of this user story was already added to the MainMenuUI class in Sprint 1, being updated in Sprints 2 and 3.