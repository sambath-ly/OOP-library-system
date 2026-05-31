package model;

import java.util.HashSet;
import java.util.Set;

public class Librarian extends Person {
    private static int totalStaffCount = 0;
    private static final Set<String> activeShifts = new HashSet<>();

    private String shiftTime;
    private double salary;

    public Librarian(int staffId, String name, String shiftTime, String email, double salary) {
        super(staffId, name, email); 
        this.shiftTime = "Not Assigned";
        this.salary = 0.0;
        
        setShiftTime(shiftTime);
        setSalary(salary);

        totalStaffCount++; 
    }

    public static int getTotalStaffCount() { return totalStaffCount; }
    public static Set<String> getActiveShifts() { return activeShifts; }

    public static void resetStaticState() {
        totalStaffCount = 0;
        activeShifts.clear();
    }

    public String getShiftTime() { return shiftTime; }
    public double getSalary() { return salary; }

    public boolean setShiftTime(String shiftTime) {
        if (shiftTime != null && (shiftTime.equalsIgnoreCase("Morning") || 
            shiftTime.equalsIgnoreCase("Afternoon") || 
            shiftTime.equalsIgnoreCase("Night") || 
            shiftTime.equalsIgnoreCase("Not Assigned"))) {
            
            String oldShift = this.shiftTime;
            String formattedShift = shiftTime.substring(0, 1).toUpperCase() + shiftTime.substring(1).toLowerCase();
            this.shiftTime = formattedShift;
            
            // Manage active shifts tracker
            if (oldShift != null && !oldShift.equals("Not Assigned")) {
                activeShifts.remove(oldShift);
            }
            if (!formattedShift.equals("Not Assigned")) {
                activeShifts.add(formattedShift);
            }
            return true;
        }
        return false;
    }

    public boolean setSalary(double salary) {
        if (salary >= 0.0) {
            this.salary = salary;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Librarian [ID: %d] %s (Shift: %s)", id, name, shiftTime);
    }

    @Override
    public void displayInfo() {
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| LIBRARIAN STAFF PROFILE (ID: %-19d) |\n", id);
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| Name:        %-35s |\n", name.length() > 35 ? name.substring(0, 32) + "..." : name);
        System.out.printf("| Email:       %-35s |\n", email.length() > 35 ? email.substring(0, 32) + "..." : email);
        System.out.printf("| Shift Time:  %-35s |\n", shiftTime);
        System.out.printf("| Salary:      $%-34.2f |\n", salary);
        System.out.println("+--------------------------------------------------+");
    }
}
