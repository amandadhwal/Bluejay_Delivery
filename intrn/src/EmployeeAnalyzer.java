import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;


class EmployeeShift {
    String name;
    String position;
    Date startTime;
    Date endTime;

    public EmployeeShift(String name, String position, Date startTime, Date endTime) {
        this.name = name;
        this.position = position;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

public class EmployeeAnalyzer
{
    private static final SimpleDateFormat dateFormat1 = new SimpleDateFormat("MM-dd-yyyy HH:mm");
    private static final SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    public static void main(String[] args) {
        // Provide the path to the downloaded CSV file
        String filePath = "C:\\Users\\amand\\IdeaProjects\\intrn\\src\\Assignment_Timecard_csv.csv";

        try {
            List<List<EmployeeShift>> employeeShifts = readEmployeeData(filePath);
            analyzeEmployeeData(employeeShifts);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static List<List<EmployeeShift>> readEmployeeData(String filePath) throws IOException, ParseException {
        List<List<EmployeeShift>> employeeShifts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header line

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0].trim();
                String position = tokens[1].trim();

                // Check for empty date strings
                if (tokens[2].trim().isEmpty() || tokens[3].trim().isEmpty()) {
                    // Handle empty date strings, you can skip this line or log an error
                    continue;
                }

                Date startTime;
                try {
                    startTime = dateFormat1.parse(tokens[2].trim());
                } catch (ParseException e1) {
                    // If the first format fails, try the second format
                    startTime = dateFormat2.parse(tokens[2].trim());
                }

                Date endTime;
                try {
                    endTime = dateFormat1.parse(tokens[3].trim());
                } catch (ParseException e2) {
                    // If the first format fails, try the second format
                    endTime = dateFormat2.parse(tokens[3].trim());
                }

                EmployeeShift shift = new EmployeeShift(name, position, startTime, endTime);

                int employeeId = employeeShifts.size();
                employeeShifts.add(new ArrayList<>());
                employeeShifts.get(employeeId).add(shift);
            }
        }

        return employeeShifts;
    }

    private static void analyzeEmployeeData(List<List<EmployeeShift>> employeeShifts)
    {
        for (int employeeId = 0; employeeId < employeeShifts.size(); employeeId++) {
            List<EmployeeShift> shifts = employeeShifts.get(employeeId);

            for (int i = 0; i < shifts.size() - 1; i++) {
//                 Criteria (a): Worked for 7 consecutive days
                if (isConsecutiveDays(shifts.get(i).startTime, shifts.get(i + 1).endTime, 7)) {
                    System.out.println(shifts.get(i).name + " (" + shifts.get(i).position + ") has worked for 7 consecutive days.");
                }


                // Criteria (b): Less than 10 hours between shifts but greater than 1 hour
                long hoursBetweenShifts = getHoursBetween(shifts.get(i).endTime, shifts.get(i + 1).startTime);
                if (1 < hoursBetweenShifts && hoursBetweenShifts < 10) {
                    System.out.println(shifts.get(i).name + " (" + shifts.get(i).position + ") has less than 10 hours between shifts but greater than 1 hour.");
                }
            }

            for (EmployeeShift shifteed : shifts) {
                // Criteria (a): Worked for 7 consecutive days
                if (isConsecutiveDays(shifteed.endTime, shifteed.startTime, 7)) {
                    System.out.println(shifteed.name + " (" + shifteed.position + ") has worked for 7 consecutive days.");
                }
            }

            for (EmployeeShift shifted : shifts) {
                // Criteria (b): Less than 10 hours between shifts but greater than 1 hour
                long hoursBetweenShifts = getHoursBetween(shifted.endTime, shifted.startTime);
                if (1 < hoursBetweenShifts && hoursBetweenShifts < 10) {
                      System.out.println(shifted.name + " (" + shifted.position + ") has less than 10 hours between shifts but greater than 1 hour.");
                }
            }

            //Criteria (c): Worked for more than 14 hours in a single shift
            for (EmployeeShift shift : shifts) {
                long shiftDuration = getHoursBetween(shift.startTime, shift.endTime);
                if (shiftDuration > 14) {
                    System.out.println(shift.name + " (" + shift.position + ") has worked for more than 14 hours in a single shift.");
                }
            }
        }
    }
    private static boolean isConsecutiveDays(Date startDate, Date endDate, int consecutiveDays) {
        // Calculate the time difference in milliseconds between endDate and startDate
        long timeDifferenceMillis = endDate.getTime() - startDate.getTime();

        // Convert the time difference from milliseconds to days
        long diffInDays = timeDifferenceMillis / (1000 * 60 * 60 * 24);

        // Print the calculated difference in days (for debugging purposes)
       // System.out.println("Difference in days: " + diffInDays);

        // Check if the difference in days is equal to (consecutiveDays - 1)
        return diffInDays != (consecutiveDays - 1);
    }
    private static long getHoursBetween(Date date1, Date date2)
        {
            return (date2.getTime() - date1.getTime()) / (1000 * 60 * 60);
        }
}
