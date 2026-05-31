package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan implements Displayable {
    private static double totalFinesCollected = 0; 

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
    
    public static double getTotalFinesCollected() { return totalFinesCollected; }

    public static void resetStaticState() {
        totalFinesCollected = 0.0;
    }

    public double calculateFine() {
        LocalDate endDate = isReturned ? returnDate : LocalDate.now();
        if (endDate.isAfter(dueDate)) {
            return ChronoUnit.DAYS.between(dueDate, endDate) * 1.0; 
        }
        return 0.0;
    }

    public boolean processReturn() {
        return processReturn(LocalDate.now(), 0.0);
    }

    public boolean processReturn(LocalDate date) {
        return processReturn(date, 0.0);
    }

    public boolean processReturn(LocalDate date, double extraFine) {
        if (date != null && !date.isBefore(borrowDate) && !isReturned) {
            this.returnDate = date;
            this.isReturned = true;
            
            double calculatedFine = calculateFine() + (extraFine >= 0.0 ? extraFine : 0.0);
            totalFinesCollected += calculatedFine; 
            
            this.book.returnBook(); 
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Loan [ID: %d] %s -> %s (%s)", 
                             loanId, member.getName(), book.getTitle(), isReturned ? "Returned" : "Active");
    }

    @Override
    public void displayInfo() {
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| LOAN TRANSACTION RECORD (ID: %-19d) |\n", loanId);
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| Book Title:  %-35s |\n", book.getTitle().length() > 35 ? book.getTitle().substring(0, 32) + "..." : book.getTitle());
        System.out.printf("| Member Name: %-35s |\n", member.getName().length() > 35 ? member.getName().substring(0, 32) + "..." : member.getName());
        System.out.printf("| Borrow Date: %-35s |\n", borrowDate);
        System.out.printf("| Due Date:    %-35s |\n", dueDate);
        System.out.printf("| Status:      %-35s |\n", isReturned ? "Returned" : "Active");
        System.out.printf("| Return Date: %-35s |\n", isReturned ? returnDate.toString() : "Not Returned Yet");
        System.out.printf("| Fine Incur:  $%-34.2f |\n", calculateFine());
        System.out.println("+--------------------------------------------------+");
    }
}
