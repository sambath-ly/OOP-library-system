package model;

import java.util.HashSet;
import java.util.Set;

public class Librarian extends Person {
    private static int totalStaffCount = 0;
    private static final Set<String> activeShifts = new HashSet<>();

    private final String shiftTime;
    private final double salary;

    public Librarian(int staffId, String name, String shiftTime, String email, double salary) {
        super(staffId, name, email); 
        
        if (shiftTime != null && (shiftTime.equalsIgnoreCase("Morning") || 
            shiftTime.equalsIgnoreCase("Afternoon") || 
            shiftTime.equalsIgnoreCase("Night"))) {
            this.shiftTime = shiftTime;
        } else {
            this.shiftTime = "Not Assigned";
        }
        this.salary = (salary >= 0) ? salary : 0.0;

        totalStaffCount++; 
        activeShifts.add(this.shiftTime); 
    }

    public static int getTotalStaffCount() { return totalStaffCount; }
    public static Set<String> getActiveShifts() { return activeShifts; }
    public String getShiftTime() { return shiftTime; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("Staff ID: %d | Name: %-15s | Shift: %-10s", id, name, shiftTime);
    }

    @Override
    public void displayInfo() {
        System.out.println(this.toString() + " | Email: " + email + " | Salary: $" + salary);
    }
}