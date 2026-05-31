package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import model.Book;
import model.Librarian;
import model.Loan;
import model.Member;
import service.LibrarySystem;

public class Main {
    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();
        
        // Seed initial simulation data so console starts with preloaded items
        seedData(system);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=======================================================");
            System.out.println("       OOP LIBRARY MANAGEMENT SYSTEM (WEEK 6)          ");
            System.out.println("=======================================================");
            System.out.println(" 1. Run Predefined Simulation Demo");
            System.out.println(" 2. Open Interactive Administration Console");
            System.out.println(" 3. Exit");
            System.out.println("=======================================================");
            System.out.print("Please enter your choice (1-3): ");

            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                runAutomatedSimulation(system);
            } else if (choice.equals("2")) {
                openInteractiveConsole(system, scanner);
            } else if (choice.equals("3")) {
                System.out.println("\nThank you for using the OOP Library System. Goodbye!");
                break;
            } else {
                System.out.println("\n[Error] Invalid choice! Please select 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    private static void seedData(LibrarySystem system) {
        // Clear if not empty to ensure consistent seed state
        system.getBooks().clear();
        system.getMembers().clear();
        system.getLibrarians().clear();
        system.getLoans().clear();

        // Reset static class states to avoid double counting on re-runs!
        Book.resetStaticState();
        Member.resetStaticState();
        Librarian.resetStaticState();
        Loan.resetStaticState();

        Book b1 = new Book(1, "Java Basics", "John Smith", "Programming", "1234567890", 15.0);
        Book b2 = new Book(2, "Clean Code", "Robert Martin", "Programming", "0987654321", 25.0);
        Book b3 = new Book(3, "History of Angkor", "Vannak", "History", "1122334455", 30.0);

        Member m1 = new Member(101, "Sambath", "sambath@gmail.com", "012111111", "Phnom Penh");
        Member m2 = new Member(102, "Dany", "dany@gmail.com", "012222222", "Siem Reap");

        Librarian lib = new Librarian(10, "Ly Ratanaksambath", "Morning", "ly@gmail.com", 1500.0);

        system.addBook(b1); 
        system.addBook(b2); 
        system.addBook(b3);
        system.addMember(m1); 
        system.addMember(m2);
        system.addLibrarian(lib);
    }

    private static void runAutomatedSimulation(LibrarySystem system) {
        // Reset system and re-seed to make sure simulation runs from fresh state
        seedData(system);

        System.out.println("\n=======================================================");
        System.out.println("        RUNNING AUTOMATED SIMULATION DEMO              ");
        System.out.println("=======================================================");

        System.out.println("--- TRANSACTIONS ---");
        Book targetBook = system.getBooks().get(0);
        Member targetMember = system.getMembers().get(0);

        // Uses clean service level method!
        if (system.borrowBook(targetBook, targetMember)) {
            System.out.println("Success: " + targetMember.getName() + " borrowed " + targetBook.getTitle());
        }

        System.out.println("\n--- GLOBAL LIBRARY STATS ---");
        System.out.println("Total Books Registered: " + Book.getTotalBooksCreated());
        System.out.println("Total Members Registered: " + Member.getTotalMemberCount());
        System.out.println("Total Librarians on Staff: " + Librarian.getTotalStaffCount());

        System.out.println("\n--- DISPLAY VERIFICATION (ALL IMPLEMENT DISPLAYABLE) ---");
        System.out.println("[Books - Public Catalog View (Price & ISBN Hidden)]");
        for (Book b : system.getBooks()) b.displayInfo(false, false);

        System.out.println("\n[Books - Full View]");
        for (Book b : system.getBooks()) b.displayInfo();

        System.out.println("\n[Members]");
        for (Member m : system.getMembers()) m.displayInfo();

        System.out.println("\n[Librarians]");
        for (Librarian l : system.getLibrarians()) l.displayInfo();

        System.out.println("\n--- RETURNING & LOAN DISPLAY ---");
        Loan activeLoan = system.getLoans().get(0);
        activeLoan.displayInfo(); 
        
        System.out.println("\nProcessing Return (20 Days Overdue)...");
        // Refactored to make it mathematically 20 days overdue (14 + 20 = 34 days)
        if (system.returnBook(activeLoan.getLoanId(), LocalDate.now().plusDays(34))) {
            System.out.println(targetBook.getTitle() + " has been returned successfully via service layer.");
        }

        System.out.println("\n--- POST-RETURN STATUS ---");
        activeLoan.displayInfo();
        System.out.println("Total Fines Collected by Library: $" + Loan.getTotalFinesCollected());
        System.out.println("=======================================================\n");
    }

    private static void openInteractiveConsole(LibrarySystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n-------------------------------------------------------");
            System.out.println("      ADMINISTRATION CONSOLE - MAIN MODULES            ");
            System.out.println("-------------------------------------------------------");
            System.out.println(" 1. Book Inventory Management");
            System.out.println(" 2. Member Registry Management");
            System.out.println(" 3. Librarian Staff Management");
            System.out.println(" 4. Borrowing & Returns (Loans)");
            System.out.println(" 5. Global Statistics & Finance");
            System.out.println(" 6. Back to Main System Launcher");
            System.out.println("-------------------------------------------------------");
            System.out.print("Select a module (1-6): ");

            String moduleChoice = scanner.nextLine().trim();
            if (moduleChoice.equals("1")) {
                bookModule(system, scanner);
            } else if (moduleChoice.equals("2")) {
                memberModule(system, scanner);
            } else if (moduleChoice.equals("3")) {
                librarianModule(system, scanner);
            } else if (moduleChoice.equals("4")) {
                loanModule(system, scanner);
            } else if (moduleChoice.equals("5")) {
                displayGlobalStats(system);
            } else if (moduleChoice.equals("6")) {
                break;
            } else {
                System.out.println("\n[Error] Invalid module! Please choose 1 to 6.");
            }
        }
    }

    private static void bookModule(LibrarySystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- BOOK INVENTORY MANAGEMENT ---");
            System.out.println(" 1. Add New Book");
            System.out.println(" 2. Search Books");
            System.out.println(" 3. List All Books");
            System.out.println(" 4. List Available Books");
            System.out.println(" 5. Back to Admin Modules");
            System.out.println("---------------------------------");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                try {
                    System.out.print("Enter Book ID (Integer > 0): ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    if (id <= 0) {
                        System.out.println("[Error] ID must be a positive integer greater than 0!");
                        continue;
                    }
                    if (system.findBookById(id) != null) {
                        System.out.println("[Error] A book with this ID already exists!");
                        continue;
                    }
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine().trim();
                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine().trim();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine().trim();
                    System.out.print("Enter Price ($): ");
                    double price = Double.parseDouble(scanner.nextLine().trim());

                    Book newBook = new Book(id, title, author, genre, isbn, price);
                    system.addBook(newBook);
                    System.out.println("[Success] Book successfully registered in system.");
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Invalid number format entered!");
                }
            } else if (choice.equals("2")) {
                System.out.print("Enter Search Term (Title, Author, or Genre): ");
                String query = scanner.nextLine().trim();
                ArrayList<Book> results = system.searchBooks(query);
                if (results.isEmpty()) {
                    System.out.println("No matching books found.");
                } else {
                    System.out.println("\n--- SEARCH RESULTS ---");
                    for (Book b : results) b.displayInfo();
                }
            } else if (choice.equals("3")) {
                System.out.println("\n--- ALL REGISTERED BOOKS ---");
                if (system.getBooks().isEmpty()) {
                    System.out.println("No books registered in system.");
                } else {
                    for (Book b : system.getBooks()) b.displayInfo();
                }
            } else if (choice.equals("4")) {
                System.out.println("\n--- AVAILABLE BOOKS ---");
                ArrayList<Book> available = system.getAvailableBooks();
                if (available.isEmpty()) {
                    System.out.println("No books are currently available.");
                } else {
                    for (Book b : available) b.displayInfo();
                }
            } else if (choice.equals("5")) {
                break;
            } else {
                System.out.println("[Error] Invalid selection!");
            }
        }
    }

    private static void memberModule(LibrarySystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- MEMBER REGISTRY MANAGEMENT ---");
            System.out.println(" 1. Register New Member");
            System.out.println(" 2. Update Member Profile");
            System.out.println(" 3. List All Members");
            System.out.println(" 4. Back to Admin Modules");
            System.out.println("---------------------------------");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                try {
                    System.out.print("Enter Member ID (Integer > 0): ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    if (id <= 0) {
                        System.out.println("[Error] ID must be a positive integer greater than 0!");
                        continue;
                    }
                    if (system.findMemberById(id) != null) {
                        System.out.println("[Error] Member ID already registered!");
                        continue;
                    }
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine().trim();
                    System.out.print("Enter Phone (e.g., 012111111): ");
                    String phone = scanner.nextLine().trim();
                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine().trim();

                    Member m = new Member(id, name, email, phone, address);
                    system.addMember(m);
                    System.out.println("[Success] Member registered successfully!");
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Invalid ID entered!");
                }
            } else if (choice.equals("2")) {
                try {
                    System.out.print("Enter Member ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    Member m = system.findMemberById(id);
                    if (m == null) {
                        System.out.println("[Error] Member not found!");
                        continue;
                    }
                    System.out.println("Updating member details (leave blank to keep current):");
                    
                    System.out.print("New Name [" + m.getName() + "]: ");
                    String name = scanner.nextLine().trim();
                    if (!name.isEmpty()) m.setName(name);

                    System.out.print("New Email [" + m.getEmail() + "]: ");
                    String email = scanner.nextLine().trim();
                    if (!email.isEmpty()) {
                        if (!m.setEmail(email)) {
                            System.out.println("[Warning] Invalid email format! Email unchanged.");
                        }
                    }

                    System.out.print("New Phone [" + m.getPhoneNumber() + "]: ");
                    String phone = scanner.nextLine().trim();
                    if (!phone.isEmpty()) {
                        if (!m.setPhoneNumber(phone)) {
                            System.out.println("[Warning] Invalid phone format (must be standard Cambodia number)! Phone unchanged.");
                        }
                    }

                    System.out.print("New Address [" + m.getAddress() + "]: ");
                    String address = scanner.nextLine().trim();
                    if (!address.isEmpty()) m.setAddress(address);

                    System.out.println("[Success] Member details updated successfully!");
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Invalid input format!");
                }
            } else if (choice.equals("3")) {
                System.out.println("\n--- REGISTERED MEMBERS ---");
                if (system.getMembers().isEmpty()) {
                    System.out.println("No members registered in system.");
                } else {
                    for (Member m : system.getMembers()) m.displayInfo();
                }
            } else if (choice.equals("4")) {
                break;
            } else {
                System.out.println("[Error] Invalid selection!");
            }
        }
    }

    private static void librarianModule(LibrarySystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- STAFF LIBRARIAN MANAGEMENT ---");
            System.out.println(" 1. Register New Librarian");
            System.out.println(" 2. Update Shift or Salary");
            System.out.println(" 3. List All Librarians");
            System.out.println(" 4. Back to Admin Modules");
            System.out.println("---------------------------------");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                try {
                    System.out.print("Enter Staff ID (Integer > 0): ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    if (id <= 0) {
                        System.out.println("[Error] ID must be a positive integer greater than 0!");
                        continue;
                    }
                    if (system.findLibrarianById(id) != null) {
                        System.out.println("[Error] Staff ID already registered!");
                        continue;
                    }
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine().trim();
                    System.out.print("Enter Shift (Morning / Afternoon / Night): ");
                    String shift = scanner.nextLine().trim();
                    System.out.print("Enter Salary ($): ");
                    double salary = Double.parseDouble(scanner.nextLine().trim());

                    Librarian lib = new Librarian(id, name, shift, email, salary);
                    system.addLibrarian(lib);
                    System.out.println("[Success] Librarian registered successfully!");
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Invalid number input!");
                }
            } else if (choice.equals("2")) {
                try {
                    System.out.print("Enter Staff ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    Librarian lib = system.findLibrarianById(id);
                    if (lib == null) {
                        System.out.println("[Error] Librarian staff not found!");
                        continue;
                    }
                    System.out.println("Updating staff details (leave blank to keep current):");
                    
                    System.out.print("New Shift [" + lib.getShiftTime() + "]: ");
                    String shift = scanner.nextLine().trim();
                    if (!shift.isEmpty()) {
                        if (!lib.setShiftTime(shift)) {
                            System.out.println("[Error] Invalid shift (choose Morning, Afternoon, or Night)!");
                        }
                    }
                    System.out.print("New Salary [" + lib.getSalary() + "]: ");
                    String salaryStr = scanner.nextLine().trim();
                    if (!salaryStr.isEmpty()) {
                        double salary = Double.parseDouble(salaryStr);
                        if (!lib.setSalary(salary)) {
                            System.out.println("[Error] Salary must be non-negative!");
                        }
                    }
                    System.out.println("[Success] Staff details updated successfully!");
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Invalid input!");
                }
            } else if (choice.equals("3")) {
                System.out.println("\n--- REGISTERED LIBRARIANS ---");
                if (system.getLibrarians().isEmpty()) {
                    System.out.println("No librarians registered in system.");
                } else {
                    for (Librarian l : system.getLibrarians()) l.displayInfo();
                }
            } else if (choice.equals("4")) {
                break;
            } else {
                System.out.println("[Error] Invalid selection!");
            }
        }
    }

    private static void loanModule(LibrarySystem system, Scanner scanner) {
        while (true) {
            System.out.println("\n--- TRANSACTION & LOAN MANAGEMENT ---");
            System.out.println(" 1. Issue Book Loan (Borrow)");
            System.out.println(" 2. Process Return");
            System.out.println(" 3. List All Loans");
            System.out.println(" 4. List Active Loans");
            System.out.println(" 5. Back to Admin Modules");
            System.out.println("-------------------------------------");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                try {
                    System.out.print("Enter Book ID to borrow: ");
                    int bookId = Integer.parseInt(scanner.nextLine().trim());
                    Book b = system.findBookById(bookId);
                    if (b == null) {
                        System.out.println("[Error] Book not found!");
                        continue;
                    }
                    if (!b.isAvailable()) {
                        System.out.println("[Error] Book is already checked out!");
                        continue;
                    }

                    System.out.print("Enter Member ID: ");
                    int memberId = Integer.parseInt(scanner.nextLine().trim());
                    Member m = system.findMemberById(memberId);
                    if (m == null) {
                        System.out.println("[Error] Member not found!");
                        continue;
                    }

                    // Leverage overloaded borrowBook(int, int) method!
                    if (system.borrowBook(bookId, memberId)) {
                        System.out.println("[Success] Loan processed. Book '" + b.getTitle() + "' checked out to " + m.getName() + ".");
                    } else {
                        System.out.println("[Error] Transaction failed!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Invalid format! Please enter valid numeric IDs.");
                }
            } else if (choice.equals("2")) {
                try {
                    System.out.print("Enter Loan ID to process return: ");
                    int loanId = Integer.parseInt(scanner.nextLine().trim());
                    Loan loan = system.findLoanById(loanId);
                    if (loan == null) {
                        System.out.println("[Error] Loan record not found!");
                        continue;
                    }
                    if (loan.isReturned()) {
                        System.out.println("[Error] Book already returned for this loan!");
                        continue;
                    }

                    System.out.print("Enter days overdue (0 if on time): ");
                    int overdueDays = Integer.parseInt(scanner.nextLine().trim());
                    if (overdueDays < 0) {
                        System.out.println("[Error] Overdue days cannot be negative!");
                        continue;
                    }

                    System.out.print("Enter extra administrative fine ($0 if none): ");
                    double extraFine = Double.parseDouble(scanner.nextLine().trim());
                    if (extraFine < 0.0) {
                        System.out.println("[Error] Extra fine cannot be negative!");
                        continue;
                    }

                    LocalDate returnDate = loan.getDueDate().plusDays(overdueDays);
                    // Leverage overloaded returnBook(int, LocalDate, double) method!
                    if (system.returnBook(loanId, returnDate, extraFine)) {
                        double totalCalculatedFine = loan.calculateFine() + extraFine;
                        System.out.println("[Success] Book '" + loan.getBook().getTitle() + "' successfully returned.");
                        if (totalCalculatedFine > 0.0) {
                            System.out.printf("   --> Total Fine of $%.2f collected (Standard Overdue: $%.2f | Extra Penalty: $%.2f).\n", 
                                totalCalculatedFine, loan.calculateFine(), extraFine);
                        } else {
                            System.out.println("   --> No overdue fines incurred.");
                        }
                    } else {
                        System.out.println("[Error] Return processing failed!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("[Error] Invalid numeric format!");
                }
            } else if (choice.equals("3")) {
                System.out.println("\n--- ALL LOAN RECORDS ---");
                if (system.getLoans().isEmpty()) {
                    System.out.println("No loan transactions found in system.");
                } else {
                    for (Loan l : system.getLoans()) l.displayInfo();
                }
            } else if (choice.equals("4")) {
                System.out.println("\n--- ACTIVE LOANS OUTSTANDING ---");
                ArrayList<Loan> active = system.getActiveLoans();
                if (active.isEmpty()) {
                    System.out.println("No active outstanding loans.");
                } else {
                    for (Loan l : active) l.displayInfo();
                }
            } else if (choice.equals("5")) {
                break;
            } else {
                System.out.println("[Error] Invalid selection!");
            }
        }
    }

    private static void displayGlobalStats(LibrarySystem system) {
        System.out.println("\n=======================================================");
        System.out.println("          GLOBAL LIBRARY METRICS & FINANCIALS          ");
        System.out.println("=======================================================");
        System.out.println(" Total Books Registered:   " + Book.getTotalBooksCreated());
        System.out.println(" Total Members Registered: " + Member.getTotalMemberCount());
        System.out.println(" Total Staff Librarians:   " + Librarian.getTotalStaffCount());
        System.out.println(" Active Staff Shifts:      " + Librarian.getActiveShifts());
        System.out.println(" Total Transactions:       " + system.getLoans().size());
        System.out.println(" Outstanding Loans:        " + system.getActiveLoanCount());
        System.out.println(" Total Overdue Fines Paid: $" + Loan.getTotalFinesCollected());
        System.out.println("=======================================================");
    }
}
