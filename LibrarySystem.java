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
    /**
     * Borrow a book using the Book and Member objects.
     * @param book   the Book to borrow
     * @param member the Member borrowing the book
     * @return true if borrowing succeeded
     */
    public boolean borrowBook(Book book, Member member) {
        if (book.borrowBook()) {
            Loan newLoan = new Loan(loanIdSequence++, book, member);
            this.addLoan(newLoan);
            member.recordLoan(newLoan);
            return true;
        }
        return false;
    }

    /**
     * Borrow a book using its ID and the member's ID.
     * @param bookId   the ID of the book
     * @param memberId the ID of the member
     * @return true if borrowing succeeded
     */
    public boolean borrowBook(int bookId, int memberId) {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);
        if (book != null && member != null) {
            return borrowBook(book, member);
        }
        return false;
    }

    /**
     * Borrow a book by its title for a given member.
     * @param bookTitle the title of the book
     * @param member    the Member borrowing the book
     * @return true if borrowing succeeded
     */
    public boolean borrowBook(String bookTitle, Member member) {
        if (bookTitle == null || member == null) return false;
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(bookTitle.trim()) && b.isAvailable()) {
                return borrowBook(b, member);
            }
        }
        return false;
    }

    /**
     * Return a book for a given loan ID and return date.
     * @param loanId    the loan identifier
     * @param returnDate the date the book is returned
     * @return true if return processed
     */
    public boolean returnBook(int loanId, java.time.LocalDate returnDate) {
        Loan loan = findLoanById(loanId);
        if (loan != null && !loan.isReturned()) {
            return loan.processReturn(returnDate);
        }
        return false;
    }

    /**
     * Return a book for a given loan ID with an extra fine.
     * @param loanId    the loan identifier
     * @param returnDate the date the book is returned
     * @param extraFine  additional fine to apply
     * @return true if return processed
     */
    public boolean returnBook(int loanId, java.time.LocalDate returnDate, double extraFine) {
        Loan loan = findLoanById(loanId);
        if (loan != null && !loan.isReturned()) {
            return loan.processReturn(returnDate, extraFine);
        }
        return false;
    }

    public Book findBookById(int bookId) {
        for (Book b : books) {
            if (b.getBookId() == bookId) return b;
        }
        return null;
    }

    public Member findMemberById(int memberId) {
        for (Member m : members) {
            if (m.getId() == memberId) return m;
        }
        return null;
    }

    public Librarian findLibrarianById(int staffId) {
        for (Librarian l : librarians) {
            if (l.getId() == staffId) return l;
        }
        return null;
    }

    public Loan findLoanById(int loanId) {
        for (Loan l : loans) {
            if (l.getLoanId() == loanId) return l;
        }
        return null;
    }

    public ArrayList<Book> searchBooks(String query) {
        ArrayList<Book> results = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return results;
        String q = query.toLowerCase().trim();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(q) || 
                b.getAuthor().toLowerCase().contains(q) || 
                b.getGenre().toLowerCase().contains(q)) {
                results.add(b);
            }
        }
        return results;
    }

    public ArrayList<Book> searchBook(String title) {
        return searchBook(title, null, null);
    }

    public ArrayList<Book> searchBook(String title, String author) {
        return searchBook(title, author, null);
    }

    public ArrayList<Book> searchBook(String title, String author, String genre) {
        ArrayList<Book> results = new ArrayList<>();
        String t = (title != null) ? title.toLowerCase().trim() : "";
        String a = (author != null) ? author.toLowerCase().trim() : "";
        String g = (genre != null) ? genre.toLowerCase().trim() : "";
        
        for (Book b : books) {
            boolean matchTitle = t.isEmpty() || b.getTitle().toLowerCase().contains(t);
            boolean matchAuthor = a.isEmpty() || b.getAuthor().toLowerCase().contains(a);
            boolean matchGenre = g.isEmpty() || b.getGenre().toLowerCase().contains(g);
            
            if (matchTitle && matchAuthor && matchGenre) {
                results.add(b);
            }
        }
        return results;
    }

    public ArrayList<Book> getAvailableBooks() {
        ArrayList<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.isAvailable()) results.add(b);
        }
        return results;
    }

    public ArrayList<Loan> getActiveLoans() {
        ArrayList<Loan> results = new ArrayList<>();
        for (Loan l : loans) {
            if (!l.isReturned()) results.add(l);
        }
        return results;
    }

    public int getActiveLoanCount() {
        int count = 0;
        for (Loan loan : loans) {
            if (!loan.isReturned()) count++;
        }
        return count;
    }
}
