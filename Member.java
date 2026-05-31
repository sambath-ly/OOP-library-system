package model;

import java.util.ArrayList;
import java.util.List;

public class Member extends Person {
    private static int totalMemberCount = 0;

    private String phoneNumber;
    private String address;
    private final List<Loan> loanHistory; 

    public Member(int memberId, String name, String email, String phoneNumber, String address) {
        super(memberId, name, email); 
        this.address = "Unknown Address";
        setAddress(address);
        this.loanHistory = new ArrayList<>(); 
        if (!setPhoneNumber(phoneNumber)) {
            this.phoneNumber = (phoneNumber != null && !phoneNumber.trim().isEmpty()) ? phoneNumber.trim() : "No Phone";
        }
        totalMemberCount++;
    }

    public static int getTotalMemberCount() { return totalMemberCount; }

    public static void resetStaticState() {
        totalMemberCount = 0;
    }

    public void recordLoan(Loan loan) { this.loanHistory.add(loan); }
    public List<Loan> getLoanHistory() { return new ArrayList<>(loanHistory); }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }

    public boolean setAddress(String address) {
        if (address != null && !address.trim().isEmpty()) {
            this.address = address.trim();
            return true;
        }
        return false;
    }

    public boolean setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        String cleanPhoneNumber = phoneNumber.trim().replaceAll("[^0-9]", "");
        if (cleanPhoneNumber.startsWith("0") && (cleanPhoneNumber.length() == 9 || cleanPhoneNumber.length() == 10)) {
            this.phoneNumber = "+855" + cleanPhoneNumber.substring(1);
            return true;
        } 
        if (cleanPhoneNumber.startsWith("855") && (cleanPhoneNumber.length() == 11 || cleanPhoneNumber.length() == 12)) {
            this.phoneNumber = "+" + cleanPhoneNumber;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Member [ID: %d] %s (Phone: %s)", id, name, phoneNumber);
    }

    @Override
    public void displayInfo() {
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| MEMBER PROFILE (ID: %-28d) |\n", id);
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| Name:        %-35s |\n", name.length() > 35 ? name.substring(0, 32) + "..." : name);
        System.out.printf("| Email:       %-35s |\n", email.length() > 35 ? email.substring(0, 32) + "..." : email);
        System.out.printf("| Phone:       %-35s |\n", phoneNumber);
        System.out.printf("| Address:     %-35s |\n", address.length() > 35 ? address.substring(0, 32) + "..." : address);
        System.out.println("| Loan History:                                    |");
        List<Loan> history = getLoanHistory();
        if (history.isEmpty()) {
            System.out.println("|   (No active or past loans registered)           |");
        } else {
            for (Loan l : history) {
                String loanSummary = String.format("ID: %d | %s (%s)", 
                    l.getLoanId(), 
                    l.getBook().getTitle(), 
                    l.isReturned() ? "Returned" : "Active"
                );
                if (loanSummary.length() > 44) {
                    loanSummary = loanSummary.substring(0, 41) + "...";
                }
                System.out.printf("|   * %-44s |\n", loanSummary);
            }
        }
        System.out.println("+--------------------------------------------------+");
    }
}
