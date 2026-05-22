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
    public int getBookId()      { return bookId; }
    public String getTitle()    { return title; }
    public String getAuthor()   { return author; }
    public String getGenre()    { return genre; }
    public String getIsbn()     { return isbn; }
    public double getPrice()    { return price; }
    
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
        String status = isAvailable ? "AVAILABLE" : "BORROWED";
        return String.format("Book ID: %-5d | %-20s | Author: %-15s | Status: %s", 
                             bookId, title, author, status);
    }

    @Override
    public void displayInfo() {
        System.out.println(this.toString());
    }
}