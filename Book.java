package model;

import java.util.HashSet;
import java.util.Set;

public class Book implements Displayable, Borrowable {
    private static int totalBooksCreated = 0;
    private static final Set<String> allGenres = new HashSet<>();

    private final int bookId;
    private final String title;
    private final String author;
    private final String genre;
    private final String isbn;
    private double price;
    private boolean isAvailable;

    public Book(int bookId, String title, String author, String genre, String isbn, double price) {
        this.bookId = (bookId > 0) ? bookId : 0;
        this.title = (title != null) ? title : "Unknown Title";
        this.author = (author != null) ? author : "Unknown Author";
        this.genre = (genre != null) ? genre : "Unknown Genre";
        this.isbn = (isbn != null) ? isbn : "Unknown ISBN";
        this.price = (price >= 0) ? price : 0;
        this.isAvailable = true;

        totalBooksCreated++;
        allGenres.add(this.genre);
    }

    public static int getTotalBooksCreated() { return totalBooksCreated; }
    public static Set<String> getAllGenres() { return allGenres; }
    
    public static void resetStaticState() {
        totalBooksCreated = 0;
        allGenres.clear();
    }

    public int getBookId()      { return bookId; }
    public String getTitle()    { return title; }
    public String getAuthor()   { return author; }
    public String getGenre()    { return genre; }
    public String getIsbn()     { return isbn; }
    public double getPrice()    { return price; }

    public boolean setPrice(double price) {
        if (price >= 0.0) {
            this.price = price;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean isAvailable() { return isAvailable; }

    @Override
    public boolean borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        System.out.println("Alert: [" + title + "] is already checked out.");
        return false;
    }

    @Override
    public boolean returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Book [ID: %d] %s by %s (%s)", 
                             bookId, title, author, isAvailable ? "Available" : "Borrowed");
    }

    /**
 * Displays full book information, including price and ISBN.
 */
@Override
    public void displayInfo() {
        displayInfo(true, true);
    }

    public void displayInfo(boolean showPrice) {
        displayInfo(showPrice, true);
    }

    public void displayInfo(boolean showPrice, boolean showIsbn) {
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| BOOK PROFILE (ID: %-30d) |\n", bookId);
        System.out.println("+--------------------------------------------------+");
        System.out.printf("| Title:       %-35s |\n", title.length() > 35 ? title.substring(0, 32) + "..." : title);
        System.out.printf("| Author:      %-35s |\n", author.length() > 35 ? author.substring(0, 32) + "..." : author);
        System.out.printf("| Genre:       %-35s |\n", genre.length() > 35 ? genre.substring(0, 32) + "..." : genre);
        if (showIsbn) {
            System.out.printf("| ISBN:        %-35s |\n", isbn);
        }
        if (showPrice) {
            System.out.printf("| Price:       $%-34.2f |\n", price);
        }
        System.out.printf("| Status:      %-35s |\n", isAvailable ? "AVAILABLE" : "BORROWED");
        System.out.println("+--------------------------------------------------+");
    }
}
