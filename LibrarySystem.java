package service;

import java.util.ArrayList;
import model.Book;
import model.Librarian;
import model.Loan;
import model.Member;

public class LibrarySystem {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Librarian> librarians = new ArrayList<>();
    private ArrayList<Loan> loans = new ArrayList<>();
    private static int loanIdSequence = 5001; 

    public void addBook(Book book) { books.add(book); }
    public void addMember(Member member) { members.add(member); }
    public void addLibrarian(Librarian librarian) { librarians.add(librarian); }
    public void addLoan(Loan loan) { loans.add(loan); }

    public ArrayList<Book> getBooks() { return books; }
    public ArrayList<Member> getMembers() { return members; }
    public ArrayList<Librarian> getLibrarians() { return librarians; }
    public ArrayList<Loan> getLoans() { return loans; }

    // This handles the transaction cleanly so Main doesn't have to do it manually!
    public boolean borrowBook(Book book, Member member) {
        if (book.borrowBook()) {
            Loan newLoan = new Loan(loanIdSequence++, book, member);
            this.addLoan(newLoan);
            member.recordLoan(newLoan);
            return true;
        }
        return false;
    }

    public int getActiveLoanCount() {
        int count = 0;
        for (Loan loan : loans) {
            if (!loan.isReturned()) count++;
        }
        return count;
    }
}