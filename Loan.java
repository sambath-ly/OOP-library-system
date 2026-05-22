package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan implements Displayable {
    private static double TotalFinesCollected = 0; 

    private final int loanId;
    private final Book book;      
    private final Member member;  
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned;

    public Loan(int loanId, Book book, Member member) {
        this.loanId = loanId;
        this.book = book;
        this.member = member;
        this.borrowDate = LocalDate.now();
        this.dueDate = LocalDate.now().plusDays(14);
        this.isReturned = false;
        this.returnDate = null;
    }

    public int getLoanId() { return loanId; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isReturned() { return isReturned; }
    
    public static double getTotalFinesCollected() { return TotalFinesCollected; }

    public double calculateFine() {
        LocalDate endDate = isReturned ? returnDate : LocalDate.now();
        if (endDate.isAfter(dueDate)) {
            return ChronoUnit.DAYS.between(dueDate, endDate) * 1.0; 
        }
        return 0.0;
    }

    public boolean processReturn(LocalDate date) {
        if (date != null && !date.isBefore(borrowDate) && !isReturned) {
            this.returnDate = date;
            this.isReturned = true;
            TotalFinesCollected += calculateFine(); 
            this.book.returnBook(); 
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String dateInfo = isReturned ? " | Returned: " + returnDate : " | Due: " + dueDate;
        return "Loan ID: " + loanId + " | Book: " + book.getTitle() + " | Member: " + member.getName() + dateInfo;
    }

    @Override
    public void displayInfo() {
        System.out.println(this.toString() + " | Status: " + (isReturned ? "Returned" : "Active") + " | Current Fine: $" + calculateFine());
    }
}