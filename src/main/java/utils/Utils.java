package utils;

import BDDAD.dataAccess.repositories.Repositories;
import domain.BasketDistribution.VehiclesGFH;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Utils.
 */
public class Utils {

    /**
     * Read line from console string.
     *
     * @param prompt the prompt
     * @return the string
     */
    static public String readLineFromConsole(String prompt) {
        try {
            System.out.println("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read integer from console int.
     *
     * @param prompt the prompt
     * @return the int
     */
    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                int value = Integer.parseInt(input);

                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Read double from console double.
     *
     * @param prompt the prompt
     * @return the double
     */
    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                double value = Double.parseDouble(input);

                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Read float from console float.
     *
     * @param prompt the prompt
     * @return the float
     */
    static public float readFloatFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                float value = Float.parseFloat(input);

                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Read date from console local date.
     *
     * @param prompt the prompt
     * @return the local date
     */
    static public LocalDate readDateFromConsole(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String dateStringRead = readLineFromConsole("Please input date in the following format: DD/MM/YYYY");
                String finalDateString = parsedDateHelper(dateStringRead);
                return LocalDate.parse(finalDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Read time from console local time.
     *
     * @param prompt the prompt
     * @return the local time
     */
    static public LocalTime readTimeFromConsole(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String timeStringRead = readLineFromConsole("Please input time in the following format: HH:MM");
                String finalTimeString = parsedTimeHelper(timeStringRead);
                return LocalTime.parse(finalTimeString, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (Exception ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Confirm boolean.
     *
     * @param message the message
     * @return the boolean
     */
    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readLineFromConsole("\n" + message + "\n");
        } while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no"));

        return input.equalsIgnoreCase("yes");
    }

    /**
     * Show and select one object.
     *
     * @param list   the list
     * @param header the header
     * @return the object
     */
    static public Object showAndSelectOne(List list, String header) {
        showList(list, header);
        return selectsObject(list);
    }

    /**
     * Show and select index int.
     *
     * @param list   the list
     * @param header the header
     * @return the int
     */
    static public int showAndSelectIndex(List list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    /**
     * Show list.
     *
     * @param list   the list
     * @param header the header
     */
    static public void showList(List list, String header) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
    }

    /**
     * Selects object object.
     *
     * @param list the list
     * @return the object
     */
    static public Object selectsObject(List list) {
        String input;
        Integer value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            value = Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

    /**
     * Selects index int.
     *
     * @param list the list
     * @return the int
     */
    static public int selectsIndex(List list) {
        Integer value;
        do {
            value = Utils.readIntegerFromConsole("Type your option: ");
        } while (value < 0 || value > list.size());

        return value - 1;
    }

    /**
     * Gets ids.
     *
     * @param list the list
     * @return the ids
     */
    static public LinkedList<Integer> getIds(List<?> list) {
        LinkedList<Integer> ids = new LinkedList<>();
        if (list.isEmpty()) {
            return ids;
        }
        try {
            for (Object o : list) {
                ids.add((Integer) list.get(0).getClass().getMethod("getId").invoke(o));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ids.sort(Integer::compareTo);
        return ids;
    }

    /**
     * Read id from console integer.
     *
     * @param prompt the prompt
     * @param list   the list
     * @return the integer
     */
    static public int readIdFromConsole(String prompt, List<?> list) {
        do {
            try {
                int id = readIntegerFromConsole(prompt);
                if (list.contains(id)) {
                    return id;
                } else {
                    return readIdFromConsole("Invalid id. Please input a valid id:", list);
                }
            } catch (Exception ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Read id from console integer.
     *
     * @param prompt the prompt
     * @param list   the list
     * @return the integer
     */
    static public int readDifferentIdFromConsole(String prompt, List<?> list) {
        do {
            try {
                int id = readIntegerFromConsole(prompt);
                if (!list.contains(id)) {
                    return id;
                } else {
                    return readDifferentIdFromConsole("The provided id is already in use. Please input a valid id:", list);
                }
            } catch (Exception ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

//    /**
//     * Gets next id.
//     *
//     * @param list the list
//     * @return the next id
//     */
//    static public int getNextId(List<Integer> list) {
//        return list.get(list.size() - 1) + 1;
//    }

    /**
     * Gets next operationid.
     *
     * @return the next operationid
     * @throws SQLException the sql exception
     */
    static public int getNextOperationId() throws SQLException {
        return Repositories.getInstance().getOperationRepository().getNextOperationId();
    }

    /**
     * Parsed date HelperUI string.
     *
     * @param dateString the date string
     * @return the string
     */
    static public String parsedDateHelper(String dateString) {
        String[] dateStringArray = dateString.split("/");
        if (Integer.parseInt(dateStringArray[0]) < 10 && dateStringArray[0].charAt(0) != '0')
            dateStringArray[0] = "0" + dateStringArray[0];
        if (Integer.parseInt(dateStringArray[1]) < 10 && dateStringArray[1].charAt(0) != '0')
            dateStringArray[1] = "0" + dateStringArray[1];
        if (Integer.parseInt(dateStringArray[2]) < 100) dateStringArray[2] = "20" + dateStringArray[2];
        return dateStringArray[0] + "/" + dateStringArray[1] + "/" + dateStringArray[2];
    }

    /**
     * Parsed time HelperUI string.
     *
     * @param timeString the time string
     * @return the string
     */
    static public String parsedTimeHelper(String timeString) {
        String[] timeStringArray = timeString.split(":");
        if (Integer.parseInt(timeStringArray[0]) < 10 && timeStringArray[0].charAt(0) != 0)
            timeStringArray[0] = "0" + timeStringArray[0];
        if (Integer.parseInt(timeStringArray[1]) < 10 && timeStringArray[1].length() == 1)
            timeStringArray[1] = "0" + timeStringArray[1];
        return timeStringArray[0] + ":" + timeStringArray[1];
    }

    /**
     * Gets current time to minutes.
     *
     * @return the current time to minutes
     */
    static public LocalTime getCurrentTimeToMinutes() {
        return LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute());
    }

    /**
     * Gets current date to day.
     *
     * @return the current date to day
     */
    static public LocalDate getCurrentDateToDay() {
        return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
    }

    /**
     * Is today boolean.
     *
     * @param date the date
     * @return the boolean
     */
    static public boolean isToday(LocalDate date) {
        return date.isEqual(getCurrentDateToDay());
    }

    /**
     * Is now boolean.
     *
     * @param time the time
     * @return the boolean
     */
    static public boolean isNow(LocalTime time) {
        return time.getHour() == getCurrentTimeToMinutes().getHour() && time.getMinute() == getCurrentTimeToMinutes().getMinute();
    }

    /**
     * Is today and now boolean.
     *
     * @param date the date
     * @param time the time
     * @return the boolean
     */
    static public boolean isTodayAndNow(LocalDate date, LocalTime time) {
        return isToday(date) && isNow(time);
    }

    /**
     * Date equals boolean.
     *
     * @param date1 the date 1
     * @param date2 the date 2
     * @return the boolean
     */
    static public boolean dateEquals(LocalDate date1, LocalDate date2) {
        return date1.getYear() == date2.getYear() && date1.getMonthValue() == date2.getMonthValue() && date1.getDayOfMonth() == date2.getDayOfMonth();
    }

    /**
     * Time equals boolean.
     *
     * @param time1 the time 1
     * @param time2 the time 2
     * @return the boolean
     */
    static public boolean timeEquals(LocalTime time1, LocalTime time2) {
        return time1.getHour() == time2.getHour() && time1.getMinute() == time2.getMinute();
    }

    /**
     * Date and time equals boolean.
     *
     * @param dateTime1 the date time 1
     * @param dateTime2 the date time 2
     * @return the boolean
     */
    static public boolean dateAndTimeEquals(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return dateEquals(dateTime1.toLocalDate(), dateTime2.toLocalDate()) && timeEquals(dateTime1.toLocalTime(), dateTime2.toLocalTime());
    }

    /**
     * Read vehicle vehicles gfh.
     *
     * @return the vehicles gfh
     */
    public static VehiclesGFH readVehicle() {
        int avgAutonomy = Utils.readIntegerFromConsole("Average Autonomy [Km]: ");
        while (avgAutonomy <= 0) {
            if (avgAutonomy < 0) System.out.print("Average Autonomy can't be negative. ");
            if (avgAutonomy == 0) System.out.print("Average Autonomy can't be zero. ");
            System.out.println("Please try again.");
            avgAutonomy = Utils.readIntegerFromConsole("Average Autonomy [Km]: ");
        }

        int avgVelocity = Utils.readIntegerFromConsole("Average Velocity [Km/H]: ");
        while (avgVelocity <= 0) {
            if (avgVelocity < 0) System.out.print("Average Velocity can't be negative. ");
            if (avgVelocity == 0) System.out.print("Average Velocity can't be zero. ");
            System.out.println("Please try again.");
            avgVelocity = Utils.readIntegerFromConsole("Average Velocity [Km/H]: ");
        }

        int avgDischargeTime = Utils.readIntegerFromConsole("Average Discharge Time [minutes]: ");
        while (avgDischargeTime <= 0) {
            if (avgDischargeTime < 0) System.out.print("Average Discharge Time can't be negative. ");
            if (avgDischargeTime == 0) System.out.print("Average Discharge Time can't be zero. ");
            System.out.println("Please try again.");
            avgDischargeTime = Utils.readIntegerFromConsole("Average Discharge Time [minutes]: ");
        }

        return new VehiclesGFH(avgAutonomy, avgVelocity, avgDischargeTime);
    }

    static public String createStringFromListSeparatedBySemiColon(List<?> list) {
        StringBuilder builder = new StringBuilder();
        for (Object object : list) {
            builder.append(object.toString().replace(".",",")).append(";");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static String getOrdinal(int cycle) {
        return switch (cycle) {
            case 1 -> cycle + "st";
            case 2 -> cycle + "nd";
            case 3 -> cycle + "rd";
            default -> cycle + "th";
        };
    }
}