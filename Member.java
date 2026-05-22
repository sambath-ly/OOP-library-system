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
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.loanHistory = new ArrayList<>(); 
        totalMemberCount++;
    }

    public static int getTotalMemberCount() { return totalMemberCount; }
    public void recordLoan(Loan loan) { this.loanHistory.add(loan); }
    public List<Loan> getLoanHistory() { return new ArrayList<>(loanHistory); }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }

    public boolean setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        String cleanPhoneNumber = phoneNumber.trim().replaceAll("[^0-9]", "");
        if (cleanPhoneNumber.startsWith("0") && (cleanPhoneNumber.length() == 9 || cleanPhoneNumber.length() == 10)) {
            this.phoneNumber = "+855" + cleanPhoneNumber.substring(1);
            return true;
        } 
        if (cleanPhoneNumber.startsWith("855")) {
            this.phoneNumber = "+" + cleanPhoneNumber;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Member ID: %d | Name: %-15s | Phone: %s", id, name, phoneNumber);
    }

    @Override
    public void displayInfo() {
        System.out.println(this.toString());
        System.out.println("   Loan History: " + this.getLoanHistory());
    }
}