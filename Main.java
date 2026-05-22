package main;

import java.time.LocalDate;
import model.Book;
import model.Librarian;
import model.Loan;
import model.Member;
import service.LibrarySystem;

public class Main {
    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();

        Book b1 = new Book(1, "Java Basics", "John Smith", "Programming", "1234567890", 15.0);
        Book b2 = new Book(2, "Clean Code", "Robert Martin", "Programming", "0987654321", 25.0);
        Book b3 = new Book(3, "History of Angkor", "Vannak", "History", "1122334455", 30.0);

        Member m1 = new Member(101, "Sambath", "sambath@gmail.com", "012111111", "12000");
        Member m2 = new Member(102, "Dany", "dany@gmail.com", "012222222", "17000");

        Librarian lib = new Librarian(10, "Ly Ratanaksambath", "Morning", "ly@gmail.com", 1500.0);

        system.addBook(b1); system.addBook(b2); system.addBook(b3);
        system.addMember(m1); system.addMember(m2);
        system.addLibrarian(lib);

        System.out.println("--- TRANSACTIONS ---");
        Book targetBook = system.getBooks().get(0);
        Member targetMember = system.getMembers().get(0);

        // Main calls one clean method now!
        if (system.borrowBook(targetBook, targetMember)) {
            System.out.println("Success: " + targetMember.getName() + " borrowed " + targetBook.getTitle());
        }

        System.out.println("\n--- GLOBAL LIBRARY STATS ---");
        System.out.println("Total Books Registered: " + Book.getTotalBooksCreated());
        System.out.println("Total Members Registered: " + Member.getTotalMemberCount());
        System.out.println("Total Librarians on Staff: " + Librarian.getTotalStaffCount());

        System.out.println("\n--- DISPLAY VERIFICATION (ALL IMPLEMENT DISPLAYABLE) ---");
        System.out.println("[Books]");
        for (Book b : system.getBooks()) b.displayInfo();

        System.out.println("\n[Members]");
        for (Member m : system.getMembers()) m.displayInfo();

        System.out.println("\n[Librarians]");
        for (Librarian l : system.getLibrarians()) l.displayInfo();

        System.out.println("\n--- RETURNING & LOAN DISPLAY ---");
        Loan activeLoan = system.getLoans().get(0);
        activeLoan.displayInfo(); 
        
        System.out.println("\nProcessing Return (20 Days Overdue)...");
        if (activeLoan.processReturn(LocalDate.now().plusDays(20))) {
            System.out.println(targetBook.getTitle() + " has been returned successfully.");
        }

        System.out.println("\n--- POST-RETURN STATUS ---");
        activeLoan.displayInfo();
        System.out.println("Total Fines Collected by Library: $" + Loan.getTotalFinesCollected());
    }
}