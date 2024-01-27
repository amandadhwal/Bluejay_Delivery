//public class code {
//    private static void analyzeEmployeeData(List<List<EmployeeShift>> employeeShifts) {
//        for (int employeeId = 0; employeeId < employeeShifts.size(); employeeId++) {
//            List<EmployeeShift> shifts = employeeShifts.get(employeeId);
//
//            for (int i = 0; i < shifts.size() - 1; i++) {
//                // Criteria (a): Worked for 7 consecutive days
//                if (isConsecutiveDays(shifts.get(i).endTime, shifts.get(i + 1).startTime, 7)) {
//                    System.out.println(shifts.get(i).name + " (" + shifts.get(i).position + ") has worked for 7 consecutive days.");
//                }
//
//                // Criteria (b): Less than 10 hours between shifts but greater than 1 hour
//                long hoursBetweenShifts = getHoursBetween(shifts.get(i).endTime, shifts.get(i + 1).startTime);
//                if (1 < hoursBetweenShifts && hoursBetweenShifts < 10) {
//                    System.out.println(shifts.get(i).name + " (" + shifts.get(i).position + ") has less than 10 hours between shifts but greater than 1 hour.");
//                }
//            }
//
//            // Criteria (c): Worked for more than 14 hours in a single shift
//            for (EmployeeShift shift : shifts) {
//                long shiftDuration = getHoursBetween(shift.startTime, shift.endTime);
//                if (shiftDuration > 14) {
//                    System.out.println(shift.name + " (" + shift.position + ") has worked for more than 14 hours in a single shift.");
//                }
//            }
//        }
//    }
//
//}
